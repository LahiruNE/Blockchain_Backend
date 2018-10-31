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
 */

'use strict';
/**
 * Write your transction processor functions here
 */

/**
 * Divide asset transaction
 * @param {org.ucsc.agriblockchain.DivideAsset} tx
 * @transaction
 */
async function divideAssetTransaction(tx) {
    var product = "";
    var seed = "";
    var fertilizer = "";
    var pesticide = "";
    var divideArr = tx.divideQty;
    var sumQty = divideArr.reduce((a, b) => a + b, 0);
    var newAssets = [];
    var count = 0; 
    var parentQty = 0; 

    if(tx.hasOwnProperty('product')){
        product = tx.product;
        parentQty = tx.product.quantity;
    }
    if(tx.hasOwnProperty('seed')){
        seed = tx.seed;
        parentQty = tx.seed.amount;
    }
    if(tx.hasOwnProperty('fertilizer')){
        fertilizer = tx.fertilizer;
        parentQty = tx.fertilizer.amount;
    }
    if(tx.hasOwnProperty('pesticide')){
        pesticide = tx.pesticide;
        parentQty = tx.pesticide.amount;
    }    
    

    if(sumQty <= parentQty) {
        if(product != ""){
            return getAssetRegistry('org.ucsc.agriblockchain.Product')
            .then(function (productAssetRegistry) {
                var factory = getFactory();
              
                divideArr.forEach(function (qty) {
                    var subProduct = factory.newResource('org.ucsc.agriblockchain', 'Product', product.productId + "_" + count);
                    subProduct.plot = product.plot;
                    subProduct.pluckedDate = product.pluckedDate;
                    subProduct.certification = product.certification;
                    subProduct.productType = product.productType;
                    subProduct.currentOwner = product.currentOwner;
                    subProduct.issuer = product.issuer;
                  	subProduct.pluckedDate = product.pluckedDate;
                    subProduct.quantity = qty;
                    subProduct.unit = product.unit;
                    subProduct.divideStatus = "DIVIDED";
                    subProduct.activeStatus = "ACTIVE";
                    subProduct.parentProduct = product;
                  
                    if(product.hasOwnProperty('productpath')) {                        
                        subProduct.productpath = product.productpath;   
                    }

                    if(product.hasOwnProperty('transferDetails')) {                        
                        subProduct.transferDetails = product.transferDetails;   
                    }

                    newAssets.push(subProduct);
                    count++;
                }); 
                                               
              try{  
              productAssetRegistry.addAll(newAssets);
              }
              catch(err){
              	throw new Error(err);
              }
                return productAssetRegistry;
            })
            .then(function(productAssetRegistry) {
                var factory = getFactory();

                product.quantity = parentQty - sumQty;

                if(sumQty == parentQty) {
                    product.activeStatus = "CLOSED";
                }

                return productAssetRegistry.update(product);
            })
            .catch(function (error) {
            // Add optional error handling here.
              throw new Error(error);
            });
        }
        if(seed != ""){
            return getAssetRegistry('org.ucsc.agriblockchain.Seed')
            .then(function (seedAssetRegistry) {
                var factory = getFactory();

                divideArr.forEach(function (qty) {
                    var subProduct = factory.newResource('org.ucsc.agriblockchain', 'Seed', seed.seedId + "_" + count);
                    subProduct.name = seed.name;
                    subProduct.type = seed.type;
                    subProduct.manufactureDate = seed.manufactureDate;
                    subProduct.currentOwner = seed.currentOwner;
                    subProduct.issuer = seed.issuer;
                    subProduct.amount = qty;
                    subProduct.price = seed.price;
                    subProduct.divideStatus = "DIVIDED";
                    subProduct.activeStatus = "ACTIVE";
                    subProduct.parentProduct = seed;
                    subProduct.activeChemicals = seed.activeChemicals;
                    subProduct.certification = seed.certification;

                    if(seed.hasOwnProperty('expiryDate')) {                        
                        subProduct.expiryDate = seed.expiryDate;   
                    }

                    if(seed.hasOwnProperty('dateOfSale')) {                        
                        subProduct.dateOfSale = seed.dateOfSale;   
                    }

                    newAssets.push(subProduct);
                    count++;
                }); 
                                               
              	try{  
                  seedAssetRegistry.addAll(newAssets);
                }
                catch(err){
                  throw new Error(err);
                }

                return seedAssetRegistry;
            })
            .then(function(seedAssetRegistry) {
                var factory = getFactory();

                seed.amount = parentQty - sumQty;

                if(sumQty == parentQty) {
                    seed.activeStatus = "CLOSED";
                }

                return seedAssetRegistry.update(seed);
            })
            .catch(function (error) {
            // Add optional error handling here.
              throw new Error(error);
            });
        }
        if(fertilizer != ""){
            return getAssetRegistry('org.ucsc.agriblockchain.Fertilizer')
            .then(function (fertilizerAssetRegistry) {
                var factory = getFactory();
              
              	divideArr.forEach(function (qty) {
                    var subProduct = factory.newResource('org.ucsc.agriblockchain', 'Fertilizer', fertilizer.fertilizerId + "_" + count);
                    subProduct.name = fertilizer.name;
                    subProduct.manufactureDate = fertilizer.manufactureDate;
                    subProduct.currentOwner = fertilizer.currentOwner;
                    subProduct.issuer = fertilizer.issuer;
                    subProduct.amount = qty;
                    subProduct.price = fertilizer.price;
                    subProduct.divideStatus = "DIVIDED";
                    subProduct.activeStatus = "ACTIVE";
                    subProduct.parentProduct = fertilizer;
                    subProduct.activeChemicals = fertilizer.activeChemicals;
                    subProduct.certification = fertilizer.certification;

                    if(fertilizer.hasOwnProperty('expiryDate')) {                        
                        subProduct.expiryDate = fertilizer.expiryDate;   
                    }

                    if(fertilizer.hasOwnProperty('dateOfSale')) {                        
                        subProduct.dateOfSale = fertilizer.dateOfSale;   
                    }

                    newAssets.push(subProduct);                  
                    count++;
                });
                                               
              	try{  
                  fertilizerAssetRegistry.addAll(newAssets);                  
                }
                catch(err){
                  throw new Error(err);
                }

                return fertilizerAssetRegistry;
            })
            .then(function(fertilizerAssetRegistry) {
                var factory = getFactory();

                fertilizer.amount = parentQty - sumQty;

                if(sumQty == parentQty) {
                    fertilizer.activeStatus = "CLOSED";
                }

                return fertilizerAssetRegistry.update(fertilizer);
            })
            .catch(function (error) {
            // Add optional error handling here.
              throw new Error(error);
            });
        }
        if(pesticide != ""){
            return getAssetRegistry('org.ucsc.agriblockchain.Pesticide')
            .then(function (pesticideAssetRegistry) {
                var factory = getFactory();

                divideArr.forEach(function (qty) {
                    var subProduct = factory.newResource('org.ucsc.agriblockchain', 'Pesticide', pesticide.pesticideId + "_" + count);
                    subProduct.name = pesticide.name;
                    subProduct.manufactureDate = pesticide.manufactureDate;
                    subProduct.currentOwner = pesticide.currentOwner;
                    subProduct.issuer = pesticide.issuer;
                    subProduct.amount = qty;
                    subProduct.price = pesticide.price;
                    subProduct.divideStatus = "DIVIDED";
                    subProduct.activeStatus = "ACTIVE";
                    subProduct.parentProduct = pesticide;
                    subProduct.activeChemicals = pesticide.activeChemicals;
                    subProduct.certification = pesticide.certification;

                    if(pesticide.hasOwnProperty('expiryDate')) {                        
                        subProduct.expiryDate = pesticide.expiryDate;   
                    }

                    if(pesticide.hasOwnProperty('dateOfSale')) {                        
                        subProduct.dateOfSale = pesticide.dateOfSale;   
                    }

                    newAssets.push(subProduct);
                    count++;
                }); 
                                               
              	try{  
                  pesticideAssetRegistry.addAll(newAssets);
                }
                catch(err){
                  throw new Error(err);
                }

                return pesticideAssetRegistry;
            })
            .then(function(pesticideAssetRegistry) {
                var factory = getFactory();

                pesticide.amount = parentQty - sumQty;

                if(sumQty == parentQty) {
                    pesticide.activeStatus = "CLOSED";
                }

                return pesticideAssetRegistry.update(pesticide);
            })
            .catch(function (error) {
            // Add optional error handling here.
              throw new Error(error);
            });
        }   

        
    }
    else {
        throw new Error("Can't divide. Exceeding quantities.");
    }    
}

/**
 * Product transfer transaction
 * @param {org.ucsc.agriblockchain.TransferPackage} transferPackage
 * @transaction
 */
async function transferPackage(transferPackage) {
    let NS = 'org.ucsc.agriblockchain';
    let factory = getFactory();
  	let product;
    let oldOwner;
  	let qtytransfer;
    let currentqty; 

    if(transferPackage.hasOwnProperty('product')){
        product= transferPackage.product;
        oldOwner = transferPackage.product.currentOwner;

        transferPackage.product.currentOwner = transferPackage.newOwner;    
        transferPackage.product.issuer = oldOwner;
      
      	let newProductpath = factory.newConcept(NS, 'Trace');
        newProductpath.timestamp = new Date();
        newProductpath.authperson = oldOwner;
        newProductpath.quantity = transferPackage.product.quantity;
        newProductpath.type = transferPackage.product.productType;

        if(product.hasOwnProperty('productpath')) {
            product.productpath.push(newProductpath);   
        }
        else {
            product.productpath = [newProductpath];
        }

      return getAssetRegistry('org.ucsc.agriblockchain.Product')
            .then(function (assetRegistry) {
                return assetRegistry.update(transferPackage.product);
            });
      	
     
    }
    if(transferPackage.hasOwnProperty('seed')){
        product= transferPackage.seed;
        oldOwner = transferPackage.seed.currentOwner;

        transferPackage.seed.currentOwner = transferPackage.newOwner;    
        transferPackage.seed.issuer = oldOwner;
      
      	let newProductpath = factory.newConcept(NS, 'Trace');
        newProductpath.timestamp = new Date();
        newProductpath.authperson = oldOwner;

        if(product.hasOwnProperty('productpath')) {
            product.productpath.push(newProductpath);   
        }
        else {
            product.productpath = [newProductpath];
        }

      return getAssetRegistry('org.ucsc.agriblockchain.Seed')
            .then(function (assetRegistry) {
                return assetRegistry.update(transferPackage.seed);
            });
    }
    if(transferPackage.hasOwnProperty('fertilizer')){
        product= transferPackage.fertilizer;
        oldOwner = transferPackage.fertilizer.currentOwner;

        transferPackage.fertilizer.currentOwner = transferPackage.newOwner;    
        transferPackage.fertilizer.issuer = oldOwner;
      
      	let newProductpath = factory.newConcept(NS, 'Trace');
        newProductpath.timestamp = new Date();
        newProductpath.authperson = oldOwner;

        if(product.hasOwnProperty('productpath')) {
            product.productpath.push(newProductpath);   
        }
        else {
            product.productpath = [newProductpath];
        }

      return getAssetRegistry('org.ucsc.agriblockchain.Fertilizer')
            .then(function (assetRegistry) {
                return assetRegistry.update(transferPackage.fertilizer);
            });
    }
    if(transferPackage.hasOwnProperty('pesticide')){
        product= transferPackage.pesticide;
        oldOwner = transferPackage.pesticide.currentOwner;

        transferPackage.pesticide.currentOwner = transferPackage.newOwner;    
        transferPackage.pesticide.issuer = oldOwner;
      
      	let newProductpath = factory.newConcept(NS, 'Trace');
        newProductpath.timestamp = new Date();
        newProductpath.authperson = oldOwner;

        if(product.hasOwnProperty('productpath')) {
            product.productpath.push(newProductpath);   
        }
        else {
            product.productpath = [newProductpath];
        }

      return getAssetRegistry('org.ucsc.agriblockchain.Pesticide')
            .then(function (assetRegistry) {
                return assetRegistry.update(transferPackage.pesticide);
            });
    }   
    

    let newProductpath = factory.newConcept(NS, 'Trace');
  	newProductpath.timestamp = new Date();
    newProductpath.authperson = oldOwner;
  	newProductpath.Qty = qtytransfer; 
  	newProductpath.curQty = currentqty;
  	if(product.hasOwnProperty('productpath')) {
    	product.productpath.push(newProductpath);   
    }
  	else {
    	product.productpath = [newProductpath];
    }

    return getAssetRegistry('org.ucsc.agriblockchain.Product')
   		.then(function (assetRegistry) {
       		return assetRegistry.update(transferPackage.product);
     	});
}

/**
 * Merge asset transaction
 * @param {org.ucsc.agriblockchain.MergeAsset} tx
 * @transaction
 */
async function mergeAssetTransaction(tx) {
    let productArr = tx.product;
    let sumQty = 0;
    let count = 0 ;
    let updateArr = [];

    return getAssetRegistry('org.ucsc.agriblockchain.Product')
            .then(function(productAssetRegistry) {
                var factory = getFactory();

                productArr.forEach(function (product) {
                  	sumQty+=product.quantity;
                    product.quantity = 0;
                    product.activeStatus = "CLOSED";
                    
                    updateArr.push(product);
                }); 
				
                productAssetRegistry.updateAll(updateArr);

                return productAssetRegistry;
            })
            .then(function (productAssetRegistry) {
                var factory = getFactory();
                
                var mergedProduct = factory.newResource('org.ucsc.agriblockchain', 'Product', "PR_MERGE_" + productArr[0].productId);
                mergedProduct.plot = productArr[0].plot;
                mergedProduct.certification = productArr[0].certification;
                mergedProduct.productType = productArr[0].productType;
                mergedProduct.currentOwner = productArr[0].currentOwner;
                mergedProduct.issuer = productArr[0].issuer;
                mergedProduct.quantity = sumQty;
                mergedProduct.unit = productArr[0].unit;
                mergedProduct.divideStatus = "MERGED";
                mergedProduct.activeStatus = "ACTIVE";
                mergedProduct.parentProduct = productArr; 
                
                if(productArr[0].hasOwnProperty('productpath')) {                        
                    subProduct.productpath = productArr[0].productpath;   
                }
                                               
                return productAssetRegistry.add(mergedProduct);
            })
            .catch(function (error) {
                throw new Error(error);
            });
}

/**
 * Sample transaction
 * @param {org.ucsc.agriblockchain.PHReading} phreading
 * @transaction
 */
async function phreading(phreading) {
    let NS = 'org.ucsc.agriblockchain';
    let factory = getFactory();
	var plot = phreading.plot;
  
    plot.phReadings.push(phreading);

    if (phreading.ph < 3 ||
        phreading.ph > 8) {

            var phEvent = factory.newEvent(NS, 'PHThresholdEvent');
            phEvent.plot = phreading.plot;
            phEvent.phvalue = phreading.ph;
            phEvent.readingTime = phreading.readingTime;
            phEvent.message = 'Ph threshold violated! for plot: ' + plot.$identifier;
            emit(phEvent);
        }

    return getAssetRegistry('org.ucsc.agriblockchain.Plot')
   		.then(function (assetRegistry) {
       		return assetRegistry.update(phreading.plot);
     	});
    
}

/**
 * Sample transaction
 * @param {org.ucsc.agriblockchain.Activity} plotactivities
 * @transaction
 */
async function plotactivities(plotactivities) {
    let NS = 'org.ucsc.agriblockchain';
    let factory = getFactory();
    var plot = plotactivities.plot;

    plot.activities.push(plotactivities);

    return getAssetRegistry('org.ucsc.agriblockchain.Plot')
   		.then(function (assetRegistry) {
       		return assetRegistry.update(plotactivities.plot);
     	});
}

/**
 * Sample transaction
 * @param {org.ucsc.agriblockchain.Inspection} certactivities
 * @transaction
 */
async function certactivities(certactivities) {
    let NS = 'org.ucsc.agriblockchain';
    let factory = getFactory();
    var farm = certactivities.farm;
    var stakeholder = certactivities.stakeholder;
    var plot = certactivities.plot;
    if(certactivities.hasOwnProperty('farm')){
        
    farm.certificationactivity.push(certactivities);
    return getAssetRegistry('org.ucsc.agriblockchain.Farm')
   		.then(function (assetRegistry) {
       		return assetRegistry.update(certactivities.farm);
     	});
    }
    if(certactivities.hasOwnProperty('stakeholder')){
        
    stakeholder.certificationactivity.push(certactivities);
    return getAssetRegistry('org.ucsc.agriblockchain.Stakeholder')
    .then(function (assetRegistry) {
        return assetRegistry.update(certactivities.stakeholder);
  });
    }
    if(certactivities.hasOwnProperty('plot')){
        
        plot.certificationactivity.push(certactivities);
        return getAssetRegistry('org.ucsc.agriblockchain.Plot')
        .then(function (assetRegistry) {
            return assetRegistry.update(certactivities.plot);
      });
        }
   

    
}