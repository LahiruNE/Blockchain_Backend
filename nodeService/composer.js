'use strict';

const BusinessNetworkConnection = require('composer-client').BusinessNetworkConnection;
const Table = require('cli-table');
const moment = require('moment-timezone');

let cardname = 'admin@backend';


class Composer {

   /**
    * Need to have the mapping from bizNetwork name to the URLs to connect to.
    * bizNetwork nawme will be able to be used by Composer to get the suitable model files.
    *
    */
    constructor() {
        this.bizNetworkConnection = new BusinessNetworkConnection();    
    }
   
   /** 
    * Updates a fixes asset for selling..
    * @return {Promise} resolved when this update has completed
    */
    async updatePlot(pid, value) {
        const METHOD = 'updatePlots';

        this.businessNetworkDefinition = await this.bizNetworkConnection.connect(cardname);
        console.log('Agri Blockchain:<init>', 'businessNetworkDefinition obtained', this.businessNetworkDefinition.getIdentifier());

        let registry = await this.bizNetworkConnection.getAssetRegistry('org.ucsc.agriblockchain.Plot');
        console.log(METHOD, 'Getting assest from the registry.');
        let plot = await registry.get(pid);

        let ecDates;
        let ecVar; 
        let nowDate = new Date();
        let d = nowDate.getFullYear().toString() +"-"+ ("0" + (nowDate.getMonth()+1).toString()).slice(-2) +"-"+ ("0" + nowDate.getDate().toString()).slice(-2);
        let t = ("0" + nowDate.getHours().toString()).slice(-2) + ":" + ("0" + nowDate.getMinutes().toString()).slice(-2);
         
        if(typeof plot.ECAvailDates != 'undefined'){
            ecDates = plot.ECAvailDates;

            if(!ecDates.includes(d)){
                ecDates.push(d);
            }
        }
        else{
            ecDates = [d];
        }         

        let factory = this.businessNetworkDefinition.getFactory();
        let ec = factory.newConcept('org.ucsc.agriblockchain', 'ECVar');
        ec.date = nowDate;
        ec.time = nowDate;
        ec.value = parseFloat(value); 

        if(typeof plot.ECVar != 'undefined'){
            ecVar = plot.ECVar;

            ecVar.push(ec);
        }
        else{
            ecVar = [ec];
        }

        plot.ECAvailDates = ecDates;
        plot.ECVar = ecVar;
        
        console.log(METHOD, 'updating plot');
        await registry.update(plot);
        return 'done';
    }   

   /**
    * List the land titles that are stored in the Land Title Resgitry
    * @return {Table} returns a table of the land titles.
    */
    async listTitles() {
        const METHOD = 'listTitles';

        console.log(METHOD, 'Getting the asset registry');
        // get the land title registry and then get all the files.

        try {
            this.businessNetworkDefinition = await this.bizNetworkConnection.connect(cardname);
            console.log('Agri Blockchain:<init>', 'businessNetworkDefinition obtained', this.businessNetworkDefinition.getIdentifier());

            let plotRegistry = await this.bizNetworkConnection.getAssetRegistry('org.ucsc.agriblockchain.Plot');
            let personRegistry = await this.bizNetworkConnection.getParticipantRegistry('org.ucsc.agriblockchain.Stakeholder');

            console.log(METHOD, 'Getting all assest from the registry.');

            let aResources = await plotRegistry.resolveAll();

            console.log(METHOD, 'Current Plots');

            let table = new Table({
                head: ['plotId', 'cultivationStartDate', 'extent', 'seededDate', 'seededAmount']
            });
            let arrayLength = aResources.length;

            for (let i = 0; i < arrayLength; i++) {

                let tableLine = [];
                tableLine.push(aResources[i].plotId);
                tableLine.push(aResources[i].cultivationStartDate);
                tableLine.push(aResources[i].extent);
                tableLine.push(aResources[i].seededDate);
                tableLine.push(aResources[i].seededAmount);
                table.push(tableLine);
            }

            // Put to stdout - as this is really a command line app
            return table;
        } catch(error) {
            console.log(error);
        }

    }

}
module.exports = Composer;