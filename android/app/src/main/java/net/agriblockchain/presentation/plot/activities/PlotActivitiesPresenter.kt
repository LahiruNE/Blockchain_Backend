package net.agriblockchain.presentation.plot.activities

import android.os.AsyncTask
import net.agriblockchain.data.model.Activity
import net.agriblockchain.data.model.Plot
import net.agriblockchain.data.services.ActivityService
import net.agriblockchain.data.services.PlotService
import net.agriblockchain.util.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlotActivitiesPresenter(val view: PlotActivitiesContract.View): PlotActivitiesContract.Presenter {

    override fun getPlotActivities(plotId: String) {
        val plotService: PlotService = ServiceGenerator.createService(PlotService::class.java, ServiceGenerator.AUTH_TOKEN)

        plotService.getById(plotId).enqueue(object : Callback<Plot> {
            override fun onFailure(call: Call<Plot>, t: Throwable) {
                view.onError(t.localizedMessage)
            }

            override fun onResponse(call: Call<Plot>, response: Response<Plot>) {
                if (!response.isSuccessful || response.body() == null) {
                    view.onError(response.message())
                } else {
                    val plot: Plot = response.body()!!

                    val activityGetter = ActivitiesGetter(view)

                    activityGetter.execute(*plot.activities)

                }
            }
        })
    }

    class ActivitiesGetter(val view: PlotActivitiesContract.View): AsyncTask<String, Void, ArrayList<Activity>>() {

        override fun doInBackground(vararg activityIds: String): ArrayList<Activity> {
            val activityService: ActivityService = ServiceGenerator.createService(ActivityService::class.java, ServiceGenerator.AUTH_TOKEN)
            val activities: ArrayList<Activity> = arrayListOf()

            activityIds.forEach { activityId ->
                val response: retrofit2.Response<Activity> = activityService.getById(activityId).execute()

                if (response.isSuccessful && response.body() != null) {
                    activities.add(response.body()!!)
                } else {
                    cancel(true)
                }
            }

            return activities
        }

        override fun onPostExecute(result: ArrayList<Activity>?) {
            super.onPostExecute(result)

            if (result != null) {
                view.onPlotActivitiesReceived(result)
            }
        }

        override fun onCancelled() {
            super.onCancelled()

            view.onError("Error while getting plot activities")
        }
    }
}