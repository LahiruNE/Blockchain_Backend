package net.agriblockchain.presentation.transfer

import net.agriblockchain.data.model.Product
import net.agriblockchain.data.model.Request
import net.agriblockchain.data.model.Transfer
import net.agriblockchain.presentation.BaseView

interface TransferContract {

    interface View : BaseView<Presenter> {
        fun transferSuccess()

        fun onFailure(errorMsg: String)

        fun onSubmittedRequests(requests: List<Request>)

        fun onReceivedRequests(requests: List<Request>)

        fun onOwnedProducts(products: List<Product>)
    }

    interface Presenter {
        fun transfer(transferModel: Transfer)

        fun getSubmittedRequests(stakeholderId: String)

        fun getReceivedRequests(stakeholderId: String)

        fun getOwnedProducts(stakeholderId: String)
    }
}