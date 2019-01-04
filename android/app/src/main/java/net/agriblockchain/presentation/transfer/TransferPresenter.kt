package net.agriblockchain.presentation.transfer

import net.agriblockchain.data.model.Product
import net.agriblockchain.data.model.Request
import net.agriblockchain.data.model.Transfer
import net.agriblockchain.data.services.QueryService
import net.agriblockchain.data.services.TransferService
import net.agriblockchain.util.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransferPresenter(view: TransferContract.View) : TransferContract.Presenter {
    private val view: TransferContract.View = view

    private val transferService: TransferService = ServiceGenerator.createService(TransferService::class.java, ServiceGenerator.AUTH_TOKEN)

    private val queryService: QueryService = ServiceGenerator.createService(QueryService::class.java, ServiceGenerator.AUTH_TOKEN)

    override fun transfer(transferModel: Transfer) {
        transferService.transfer(transferModel).enqueue(object : Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                view.onFailure(t.localizedMessage)
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    view.transferSuccess()
                } else {
                    view.onFailure("Request was not successful")
                }
            }
        })
    }

    override fun getSubmittedRequests(stakeholderId: String) {
        queryService.getSubmittedRequests(stakeholderId).enqueue(object : Callback<List<Request>> {
            override fun onFailure(call: Call<List<Request>>, t: Throwable) {
                view.onFailure(t.localizedMessage)
            }

            override fun onResponse(call: Call<List<Request>>, response: Response<List<Request>>) {
                if (response.isSuccessful) {
                    view.onSubmittedRequests(response.body()!!)
                } else {
                    view.onFailure(response.message())
                }
            }

        })
    }

    override fun getReceivedRequests(stakeholderId: String) {
        queryService.getPendingRequests(stakeholderId).enqueue(object : Callback<List<Request>> {
            override fun onFailure(call: Call<List<Request>>, t: Throwable) {
                view.onFailure(t.localizedMessage)
            }

            override fun onResponse(call: Call<List<Request>>, response: Response<List<Request>>) {
                if (response.isSuccessful) {
                    view.onReceivedRequests(response.body()!!)
                } else {
                    view.onFailure(response.message())
                }
            }

        })
    }


    override fun getOwnedProducts(stakeholderId: String) {
        queryService.getOwnedProducts(stakeholderId).enqueue(object : Callback<List<Product>> {
            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                view.onFailure(t.localizedMessage)
            }

            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) {
                    view.onOwnedProducts(response.body()!!)
                } else {
                    view.onFailure(response.message())
                }
            }

        })
    }

}
