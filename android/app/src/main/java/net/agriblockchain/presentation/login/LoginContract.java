package net.agriblockchain.presentation.login;

import net.agriblockchain.data.model.Stakeholder;
import net.agriblockchain.presentation.BaseView;

interface LoginContract {

    interface View extends BaseView<Presenter> {
        void loginSuccessful(Stakeholder loggedInStakeholder);
        void loginFailure(String error);
    }

    interface Presenter {
        void login(final String username, final String pass);
    }
}
