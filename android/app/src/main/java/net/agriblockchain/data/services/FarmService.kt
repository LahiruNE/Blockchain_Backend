package net.agriblockchain.data.services

import net.agriblockchain.data.model.Farm
import retrofit2.Call
import retrofit2.http.GET

interface FarmService {

    @GET("org.ucsc.agriblockchain.Farm/{id}")
    fun getById(id: String): Call<Farm>
}