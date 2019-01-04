package net.agriblockchain.presentation.main;

import com.google.android.gms.vision.barcode.Barcode;

import net.agriblockchain.data.model.Product;
import net.agriblockchain.presentation.BaseView;

interface MainContract {

    interface View extends BaseView<Presenter> {
        void startTraceActivity(String productId);
    }

    interface Presenter {

        void onBarcodeDetected(Barcode barcode);
    }
}
