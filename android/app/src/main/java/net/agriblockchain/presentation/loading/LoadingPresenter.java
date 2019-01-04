package net.agriblockchain.presentation.loading;

import android.support.annotation.NonNull;

import net.agriblockchain.data.model.Product;
import net.agriblockchain.data.services.ProductService;
import net.agriblockchain.util.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

final class LoadingPresenter implements LoadingContract.Presenter {

    private final ProductService productService;
    private final LoadingContract.View view;

    LoadingPresenter(final LoadingContract.View view) {
        productService = ServiceGenerator.createService(ProductService.class, ServiceGenerator.AUTH_TOKEN);
        this.view = view;
    }

    @Override
    public void getProduct(String productId) {
        productService.getById(productId).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(@NonNull Call<Product> call, @NonNull Response<Product> response) {
                if (response.isSuccessful() && response.body() != null)
                    view.onProductReceived(response.body());
                else
                    view.onError(response.message());
            }

            @Override
            public void onFailure(@NonNull Call<Product> call, @NonNull Throwable t) {
                view.onError(t.getMessage());
            }
        });
    }
}
