package net.agriblockchain.presentation.loading;

import net.agriblockchain.data.model.Product;
import net.agriblockchain.presentation.BaseView;

interface LoadingContract {

    interface View extends BaseView<Presenter> {
        void onProductReceived(final Product product);

        void onError(final String errorMessage);
    }

    interface Presenter {
        void getProduct(String productId);
    }
}
