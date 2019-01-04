package net.agriblockchain.presentation.loading;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import net.agriblockchain.R;
import net.agriblockchain.data.model.Product;
import net.agriblockchain.presentation.overview.OverviewActivity;
import net.agriblockchain.util.ProductDataHolder;

public class LoadingActivity extends AppCompatActivity implements LoadingContract.View {

    private LoadingContract.Presenter presenter;
    private ProgressBar progressBar;
    private TextView txtError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        presenter = new LoadingPresenter(this);

        progressBar = findViewById(R.id.progressBar2);
        txtError = findViewById(R.id.txt_error);

        presenter.getProduct(getIntent().getStringExtra(getString(R.string.product_id)));
    }

    @Override
    public void onProductReceived(Product product) {
        ProductDataHolder.Companion.getInstance().setProduct(product);

        Intent overview = new Intent(this, OverviewActivity.class);
        startActivity(overview);

        finish();
    }

    @Override
    public void onError(String errorMessage) {
        progressBar.setVisibility(View.GONE);

        txtError.setText(errorMessage);
        txtError.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
