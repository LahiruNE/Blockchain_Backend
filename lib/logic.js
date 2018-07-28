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
 * Sample transaction
 * @param {org.ucsc.agriblockchain.TransferPackage} transferPackage
 * @transaction
 */
async function transferPackage(transferPackage) {
    let cowner = transferPackage.product.currentOwner;
    let owner = transferPackage.product.issuer;
    transferPackage.product.currentOwner = transferPackage.newOwner;
    transferPackage.product.issuer = transferPackage.oldOwner;
    let assetRegistry = getAssetRegistry('org.ucsc.agriblockchain.Product');

    await assetRegistry.update(transferPackage.product);

}

