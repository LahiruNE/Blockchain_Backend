package net.agriblockchain.presentation.login;

import android.support.annotation.NonNull;

import net.agriblockchain.data.model.Stakeholder;
import net.agriblockchain.data.services.QueryService;
import net.agriblockchain.data.services.WalletService;
import net.agriblockchain.util.ServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

final class LoginPresenter implements LoginContract.Presenter {

    private final LoginContract.View view;

    private final QueryService queryService;
    private final WalletService walletService;

    LoginPresenter(final LoginContract.View view) {
        this.view = view;

        queryService = ServiceGenerator.createService(QueryService.class, ServiceGenerator.AUTH_TOKEN);
        walletService = ServiceGenerator.createService(WalletService.class, ServiceGenerator.AUTH_TOKEN);
    }

    @Override
    public void login(final String username, final String pass) {
        queryService.getUserFromUsernamePassword(username, pass).enqueue(new Callback<List<Stakeholder>>() {
            @Override
            public void onResponse(@NonNull Call<List<Stakeholder>> call,@NonNull final Response<List<Stakeholder>> validationResponse) {
                if (validationResponse.isSuccessful()) {
                    walletService.setDefault(username).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(@NonNull Call<Void> call,@NonNull Response<Void> loginResponse) {
                            if (loginResponse.isSuccessful() && validationResponse.body() != null) {
                                view.loginSuccessful(validationResponse.body().get(0));
                            } else {
                                view.loginFailure(loginResponse.message());
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                            view.loginFailure(t.getLocalizedMessage());
                        }
                    });
                } else {
                    view.loginFailure(validationResponse.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Stakeholder>> call,@NonNull Throwable t) {
                view.loginFailure(t.getLocalizedMessage());
            }
        });
    }
}
