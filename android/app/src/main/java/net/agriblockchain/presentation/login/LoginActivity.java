package net.agriblockchain.presentation.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.data.DataHolder;

import net.agriblockchain.R;
import net.agriblockchain.data.model.Stakeholder;
import net.agriblockchain.presentation.overview.OverviewActivity;
import net.agriblockchain.presentation.transfer.TransferActivity;

public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private LoginContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new LoginPresenter(this);

        final EditText inpUsername = findViewById(R.id.input_username);
        final EditText inpPassword = findViewById(R.id.input_password);

        inpUsername.setText("admin");
        inpPassword.setText("admin");

        Button loginBtn = findViewById(R.id.btn_login);
        loginBtn.setOnClickListener(v -> presenter.login(inpUsername.getText().toString(), inpPassword.getText().toString()));

    }

    @Override
    public void loginSuccessful(final Stakeholder loggedInStakeholder) {
        final String stakeholderId = "resource:org.ucsc.agriblockchain.Stakeholder#" + loggedInStakeholder.getStakeholderId();
        Intent transferIntent = new Intent(this, TransferActivity.class);
        transferIntent.putExtra("stakeholderId", stakeholderId);
        startActivity(transferIntent);
    }

    @Override
    public void loginFailure(final String error) {
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
    }
}
