package net.agriblockchain.presentation.farm

import net.agriblockchain.data.model.Farm
import net.agriblockchain.presentation.BaseView

interface FarmDetailsContract {

    interface View : BaseView<Presenter> {
        fun onFarmDetailsReceived(farm: Farm)

        fun onError(errorMsg: String)
    }

    interface Presenter {
        fun getFarmDetails(plotId: String)
    }
}