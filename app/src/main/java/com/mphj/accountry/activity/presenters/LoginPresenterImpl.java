package com.mphj.accountry.activity.presenters;

import android.app.Activity;

import com.mphj.accountry.AccountryApplication;
import com.mphj.accountry.activity.interfaces.LoginView;
import com.mphj.accountry.activity.models.LoginModel;
import com.mphj.accountry.activity.models.rest.LoginRest;
import com.mphj.accountry.activity.rest.ApiClient;
import com.mphj.accountry.activity.rest.ApiInterface;
import com.mphj.accountry.activity.utils.Auth;
import com.mphj.accountry.activity.utils.DeviceUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mphj on 10/15/2017.
 */

public class LoginPresenterImpl implements LoginPresenter, LoginRest.LoginListener{

    private LoginView loginView;

    public LoginPresenterImpl(LoginView loginView){
        this.loginView = loginView;
    }

    @Override
    public void login(String username, String password) {
        loginView.showProgressBar();
        LoginRest.login(username, password, this);
    }

    @Override
    public void onSuccess(LoginModel loginModel) {
        Auth.login(loginModel);
        loginView.loginSucceed();
        loginView.hideProgressBar();
    }

    @Override
    public void onFailed(LoginModel loginModel) {
        if (loginModel.getStatus() == -1){
            loginView.networkFailed();
        } else if (loginModel.getStatus() == 403){
            loginView.badUsernameOrPassword();
        } else {
            loginView.unknownProblem(loginModel.getStatus());
        }
        loginView.hideProgressBar();
    }

    @Override
    public void onResume() {
        // Do nothing
    }

    @Override
    public void onDestroy() {
        // Do nothing!
    }
}
