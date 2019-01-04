package net.agriblockchain.data.services;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SystemService {

    @GET("system/ping")
    Call<Void> ping();

}
