package net.agriblockchain.data.services

import net.agriblockchain.data.model.Activity
import retrofit2.Call
import retrofit2.http.GET

interface ActivityService {

    @GET("org.ucsc.agriblockchain.Activity/{id}")
    fun getById(id: String): Call<Activity>
}