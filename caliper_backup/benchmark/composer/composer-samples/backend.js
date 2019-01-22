/*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*
*  Basic Sample Network
*  Updates the value of an Asset through a Transaction.
*  - Example test round (txn <= testAssets)
*      {
*        "label" : "basic-sample-network",
*        "txNumber" : [50],
*        "trim" : 0,
*        "rateControl" : [{"type": "fixed-rate", "opts": {"tps" : 10}}],
*        "arguments": {"testAssets": 50},
*        "callback" : "benchmark/composer/composer-samples/basic-sample-network.js"
*      }
*  - Init:
*    - Single Participant created (PARTICIPANT_0)
*    - Test specified number of Assets created, belonging to a PARTICIPANT_0
*  - Run:
*    - Transactions run against all created assets to update their values
*
*/

'use strict';

module.exports.info  = 'Basic Sample Network Performance Test';

const composerUtils = require('../../../src/composer/composer_utils');
const removeExisting = require('../composer-test-utils').clearAll;
const Log = require('../../../src/comm/util').log;
const os = require('os');

const namespace = 'org.ucsc.agriblockchain';
const busNetName = 'backend';
const uuid = os.hostname() + process.pid; // UUID for client within test

let bc;                 // The blockchain main (Composer)
let busNetConnections;  // Global map of all business network connections to be used
let testAssetNum;       // Number of test assets to create
let factory;            // Global Factory

module.exports.init = async function(blockchain, context, args) {
    // Create Participants and Assets to use in main test
    bc = blockchain;
    busNetConnections = new Map();
    busNetConnections.set('admin', context);
    testAssetNum = args.testAssets;

    let participantRegistry = await busNetConnections.get('admin').getParticipantRegistry(namespace + '.Stakeholder');
    let assetRegistry_farm = await busNetConnections.get('admin').getAssetRegistry(namespace + '.Farm');
    let assetRegistry_plot = await busNetConnections.get('admin').getAssetRegistry(namespace + '.Plot');
    let assetRegistry_product = await busNetConnections.get('admin').getAssetRegistry(namespace + '.Product');
    let assets = Array();

    try {
		factory = busNetConnections.get('admin').getBusinessNetwork().getFactory();

        let exists_farmer = await participantRegistry.exists('PARTICIPANT_FARMER' + uuid);
        let exists_certification = await participantRegistry.exists('PARTICIPANT_CAERTIFICATION' + uuid);
        let exists_farm = await assetRegistry_farm.exists('ASSET_FARM' + uuid);
        let exists_plot = await assetRegistry_plot.exists('ASSET_PLOT' + uuid);
                
        let address = factory.newConcept(namespace, 'Address');
        address.city = 'Matara';
		address.country = 'Sri Lanka'; 
        
		let company = factory.newConcept(namespace, 'Company');
		company.name = 'co.';
		company.address = address;
		
		let surr = factory.newConcept(namespace, 'Directions');            
		surr.North = 'n';
		surr.South = 's';
		surr.West = 'w';
		surr.East = 'e';
        
        if (!exists_farmer) {     
            let farmer = factory.newResource(namespace, 'Stakeholder', 'PARTICIPANT_FARMER' + uuid);                       
            farmer.name = 'penguin';
            farmer.address = address;
            farmer.company = company;							
            farmer.username = 'wombat';
            farmer.password = 'wombat';
            farmer.type = 'FARMER';
            farmer.authPerson = 'wombat';
            await participantRegistry.add(farmer);
		}
		
		if (!exists_certification) {              
            let certi = factory.newResource(namespace, 'Stakeholder', 'PARTICIPANT_CAERTIFICATION' + uuid);                       
            certi.name = 'penguin';
            certi.address = address;
            certi.company = company;							
            certi.username = 'wombat';
            certi.password = 'wombat';
            certi.type = 'CERTIFICATION';
            certi.authPerson = 'wombat';
            await participantRegistry.add(certi);
        }
        
        let certification = factory.newConcept(namespace, 'Certification');  
		certification.certificationNo = '00002';
		certification.certificationBody = factory.newRelationship(namespace, 'Stakeholder', 'PARTICIPANT_CAERTIFICATION' + uuid);
        
        if (!exists_farm) {              
            let farm = factory.newResource(namespace, 'Farm', 'ASSET_FARM' + uuid);                       
            farm.FarmLocation = 'sss';
            farm.waterSources = surr;
            farm.nearFactories = surr;							
            farm.certification = certification;
            farm.owner = factory.newRelationship(namespace, 'Stakeholder', 'PARTICIPANT_FARMER' + uuid);
            farm.farmers = [factory.newRelationship(namespace, 'Stakeholder', 'PARTICIPANT_FARMER' + uuid)];
            await assetRegistry_farm.add(farm);
        }
        
        if (!exists_plot) {              
            let plot = factory.newResource(namespace, 'Plot', 'ASSET_PLOT' + uuid);                       
            plot.cultivationStartDate = new Date();
            plot.extent = 600;
            plot.closerplots = surr;							
            plot.activities = [];
            plot.phReadings = [];
            plot.certificationBodyComments = [];
            plot.status = 'NEW';
            plot.farm = factory.newRelationship(namespace, 'Farm', 'ASSET_FARM' + uuid);
            await assetRegistry_plot.add(plot);           
        }

        // Create Test Assets
        for (let i=0; i<testAssetNum; i++) { 
            let asset = factory.newResource(namespace, 'Product', 'ASSET_PRODUCT' + uuid + '_' + i);  
            asset.pluckedDate = new Date();
			asset.certification = certification;
            asset.productType = 'CARROT';
            asset.quantity = 50;
            asset.unit = 'KG';	
            asset.divideStatus = 'ORIGINAL';
            asset.activeStatus = 'ACTIVE';	
            asset.plot = factory.newRelationship(namespace, 'Plot', 'ASSET_PLOT' + uuid);	
            asset.currentOwner = factory.newRelationship(namespace, 'Stakeholder', 'PARTICIPANT_FARMER' + uuid);
            asset.issuer = factory.newRelationship(namespace, 'Stakeholder', 'PARTICIPANT_FARMER' + uuid);
            assets.push(asset);
            
            let populated = await assetRegistry_product.exists('ASSET_PRODUCT' + uuid + '_' + i);
            
            if(populated){
				await removeExisting(assetRegistry_product, 'ASSET_PRODUCT' + uuid + '_' + i);
			}
        }
	
		await assetRegistry_product.addAll(assets);
        
    } catch (error) {
        Log('error in test init(): ', error);
        return Promise.reject(error);
    }
};

module.exports.run = function() {	
    let transaction = factory.newTransaction(namespace, 'TransferPackage');
    transaction.product = factory.newRelationship(namespace, 'Product', 'ASSET_PRODUCT' + uuid + '_' + --testAssetNum);
    transaction.newOwner = factory.newRelationship(namespace, 'Stakeholder', 'PARTICIPANT_CERTIFICATION' + uuid);
    
    return bc.bcObj.submitTransaction(busNetConnections.get('admin'), transaction);
};

module.exports.end = function() {
    return Promise.resolve(true);
};
