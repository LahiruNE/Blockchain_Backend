package net.agriblockchain.data.services;

import net.agriblockchain.data.model.Product;
import net.agriblockchain.data.model.Request;
import net.agriblockchain.data.model.Stakeholder;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface QueryService {

    @GET("queries/getUserFromUsernamePassword")
    Call<List<Stakeholder>> getUserFromUsernamePassword(@Query("username") String username, @Query("password") String pass);

    @GET("queries/getSubmittedRequests")
    Call<List<Request>> getSubmittedRequests(@Query("user") String stakeholderId);

    @GET("queries/getReceivedRequests")
    Call<List<Request>> getPendingRequests(@Query("user") String stakeholderId);

    @GET("queries/getOwnedProducts")
    Call<List<Product>> getOwnedProducts(@Query("user") String stakeholderId);

}
