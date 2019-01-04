package net.agriblockchain.presentation.main;

import com.google.android.gms.vision.barcode.Barcode;

public class MainPresenter implements MainContract.Presenter {

    private final MainContract.View view;

    MainPresenter(final MainContract.View view) {
        this.view = view;
    }

    @Override
    public void onBarcodeDetected(final Barcode barcode) {
        view.startTraceActivity(barcode.rawValue);
    }
}
