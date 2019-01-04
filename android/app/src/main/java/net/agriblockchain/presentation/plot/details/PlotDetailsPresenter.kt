package net.agriblockchain.presentation.plot.details

import net.agriblockchain.data.model.Plot
import net.agriblockchain.data.services.PlotService
import net.agriblockchain.util.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlotDetailsPresenter(val view: PlotDetailsContract.View): PlotDetailsContract.Presenter {

    override fun getPlotDetails(plotId: String) {
        val service: PlotService = ServiceGenerator.createService(PlotService::class.java, ServiceGenerator.AUTH_TOKEN)

        service.getById(plotId).enqueue(object : Callback<Plot> {
            override fun onFailure(call: Call<Plot>, t: Throwable) {
                view.onError(t.localizedMessage)
            }

            override fun onResponse(call: Call<Plot>, response: Response<Plot>) {
                if (response.isSuccessful && response.body() != null) {
                    view.onPlotDetailsReceived(response.body()!!)
                } else {
                    view.onError("Plot not found")
                }
            }
        })
    }
}