package net.agriblockchain.data.services;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WalletService {

    @POST("wallet/{name}/setDefault")
    Call<Void> setDefault(@Path("name") String name);
}
