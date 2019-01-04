package net.agriblockchain.presentation.trace;

import net.agriblockchain.data.model.Product;
import net.agriblockchain.data.services.ProductService;
import net.agriblockchain.util.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TracePresenter implements TraceContract.Presenter {

    private final TraceContract.View view;
    private final ProductService productService;

    TracePresenter(final TraceContract.View view) {
        this.view = view;

        productService = ServiceGenerator.createService(ProductService.class, ServiceGenerator.AUTH_TOKEN);
    }

    @Override
    public void getTrace(final String produceId) {

        productService.getById(produceId).enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful() && response.body() != null)
                    view.onTraceReceived(response.body().getProductpath());
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {

            }
        });
    }
}
