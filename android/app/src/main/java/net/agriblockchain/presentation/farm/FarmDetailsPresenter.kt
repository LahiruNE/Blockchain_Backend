package net.agriblockchain.presentation.farm

import net.agriblockchain.data.model.Farm
import net.agriblockchain.data.model.Plot
import net.agriblockchain.data.services.FarmService
import net.agriblockchain.data.services.PlotService
import net.agriblockchain.util.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FarmDetailsPresenter(val view: FarmDetailsContract.View): FarmDetailsContract.Presenter {

    override fun getFarmDetails(plotId: String) {
        val plotService: PlotService = ServiceGenerator.createService(PlotService::class.java, ServiceGenerator.AUTH_TOKEN)

        plotService.getById(plotId).enqueue(object : Callback<Plot> {
            override fun onFailure(call: Call<Plot>, t: Throwable) {
                view.onError(t.localizedMessage)
            }

            override fun onResponse(call: Call<Plot>, response: Response<Plot>) {
                if (!response.isSuccessful && response.body() == null) {
                    view.onError("Get plot failed")
                    return
                }

                val farmService: FarmService = ServiceGenerator.createService(FarmService::class.java, ServiceGenerator.AUTH_TOKEN)
                farmService.getById(response.body()!!.farm).enqueue(object : Callback<Farm> {
                    override fun onFailure(call: Call<Farm>, t: Throwable) {
                        view.onError(t.localizedMessage)
                    }

                    override fun onResponse(call: Call<Farm>, response: Response<Farm>) {
                        if (!response.isSuccessful && response.body() == null) {
                            view.onError("Get plot failed")
                            return
                        }

                        view.onFarmDetailsReceived(response.body()!!)
                    }

                })
            }
        })
    }

}