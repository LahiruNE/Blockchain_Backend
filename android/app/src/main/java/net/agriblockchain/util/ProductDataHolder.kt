package net.agriblockchain.util

import net.agriblockchain.data.model.Certification
import net.agriblockchain.data.model.Product
import net.agriblockchain.data.model.Trace

class ProductDataHolder private constructor() {

    var product = Product()

    val plot: String
        get() = product.plot

    val certification: Certification
        get() = product.certification

    val productPath: Array<Trace>
        get() = product.productpath

    private object Holder {
        val INSTANCE = ProductDataHolder()
    }

    companion object {
        val instance: ProductDataHolder by lazy { Holder.INSTANCE }
    }
}
