package com.mphj.accountry.mvp.activity;

import com.mphj.accountry.models.LoginModel;
import com.mphj.accountry.models.rest.LoginRest;
import com.mphj.accountry.mvp.Mvp;

/**
 * Created by mphj on 11/28/17.
 */

public interface Login {

    interface View extends Mvp.View {
        void showProgressBar();
        void hideProgressBar();
        void loginSucceed();
        void accountRecoveryEmailSent();
        void invalidUsernameOrPassword();
        void invalidEmail();
        void networkFailed();
        void unknownProblem(int status);
    }


    interface Presenter extends Mvp.Presenter<View> {
        void login(String username, String password);
    }


    interface Model {
        void login(String username, String password, LoginRest.LoginListener loginListener);
        void forgetPassword(String emailOrEmail, LoginRest.LoginListener listener);
        void saveAuthToken(LoginModel loginModel);
    }

}
