package net.agriblockchain.data.services

import net.agriblockchain.data.model.Transfer
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface TransferService {

    @POST("org.ucsc.agriblockchain.TransferPackage")
    fun transfer(@Body transfer: Transfer) : Call<Void>
}