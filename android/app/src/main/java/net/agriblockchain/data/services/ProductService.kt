package net.agriblockchain.data.services

import net.agriblockchain.data.model.Product
import retrofit2.Call
import retrofit2.http.GET

interface ProductService {

    @GET("org.ucsc.agriblockchain.Product/{id}")
    fun getById(id: String): Call<Product>
}