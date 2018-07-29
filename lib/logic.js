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
    let product = tx.product;
    let parentQty = tx.product.quantity;
    let divideArr = tx.divideQty;
    let sumQty = divideArr.reduce((a, b) => a + b, 0);
    let newAssets = [];
    let count = 0; 

    if(sumQty <= parentQty) {
        return getAssetRegistry('org.ucsc.agriblockchain.Product')
            .then(function (productAssetRegistry) {
                var factory = getFactory();

                divideArr.forEach(function (qty) {
                    var subProduct = factory.newResource('org.ucsc.agriblockchain', 'Product', product.productId + "_" + count);
                    subProduct.plot = product.plot;
                    subProduct.certification = product.certification;
                    subProduct.productType = product.productType;
                    subProduct.currentOwner = product.currentOwner;
                    subProduct.issuer = product.issuer;
                    subProduct.quantity = qty;
                    subProduct.unit = product.unit;
                    subProduct.divideStatus = "DIVIDED";
                    subProduct.activeStatus = "ACTIVE";
                    subProduct.parentProduct = [product];

                    if(product.hasOwnProperty('productpath')) {                        
                        subProduct.productpath = product.productpath;   
                    }

                    newAssets.push(subProduct);
                    count++;
                }); 
                                               
                productAssetRegistry.addAll(newAssets);

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
            });
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
  	let product= transferPackage.product;
    let oldOwner = transferPackage.product.currentOwner;

    transferPackage.product.currentOwner = transferPackage.newOwner;    
    transferPackage.product.issuer = oldOwner;

    let newProductpath = factory.newConcept(NS, 'Trace');
  	newProductpath.timestamp = new Date();
    newProductpath.authperson = transferPackage.product.issuer;
  	  
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