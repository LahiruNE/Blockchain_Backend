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
 * @param {org.ucsc.agriblockchain.divideAsset} tx
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
                    subProduct.parentProduct = product;

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
 * Sample transaction
 * @param {org.ucsc.agriblockchain.TransferPackage} transferPackage
 * @transaction
 */
async function transferPackage(transferPackage) {

    let NS = 'org.ucsc.agriblockchain';
    let factory = getFactory();
  	var product= transferPackage.product;

    let oldOwner = transferPackage.product.currentOwner;

    transferPackage.product.currentOwner = transferPackage.newOwner;
    
    transferPackage.product.issuer = oldOwner;

    let newProductpath = factory.newConcept(NS, 'Trace');
  	newProductpath.timestamp = new Date();
    newProductpath.authperson = transferPackage.product.issuer;
    product.productpath.push(newProductpath);

    return getAssetRegistry('org.ucsc.agriblockchain.Product')
   		.then(function (assetRegistry) {
       		return assetRegistry.update(transferPackage.product);
     	});
}

