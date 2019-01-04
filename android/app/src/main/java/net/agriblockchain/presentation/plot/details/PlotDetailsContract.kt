package net.agriblockchain.presentation.plot.details

import net.agriblockchain.data.model.Plot
import net.agriblockchain.presentation.BaseView

interface PlotDetailsContract {

    interface View: BaseView<Presenter> {
        fun onPlotDetailsReceived(plot: Plot)

        fun onError(errorMsg: String)
    }

    interface Presenter {
        fun getPlotDetails(plotId: String)
    }
}