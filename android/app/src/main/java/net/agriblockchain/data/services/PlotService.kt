package net.agriblockchain.data.services

import net.agriblockchain.data.model.Plot
import retrofit2.Call
import retrofit2.http.GET

interface PlotService {

    @GET("org.ucsc.agriblockchain.Plot/{id}")
    fun getById(id: String): Call<Plot>
}