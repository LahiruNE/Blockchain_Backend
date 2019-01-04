package net.agriblockchain.presentation.plot.activities

import net.agriblockchain.data.model.Activity
import net.agriblockchain.presentation.BaseView

interface PlotActivitiesContract {

    interface View : BaseView<Presenter> {
        fun onPlotActivitiesReceived(activities: List<Activity>)

        fun onError(errorMsg: String)
    }

    interface Presenter {
        fun getPlotActivities(plotId: String)
    }
}