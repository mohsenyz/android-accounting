package com.mphj.accountry.activity.models.rest;

import com.mphj.accountry.AccountryApplication;
import com.mphj.accountry.activity.models.LoginModel;
import com.mphj.accountry.activity.presenters.LoginPresenter;
import com.mphj.accountry.activity.rest.ApiClient;
import com.mphj.accountry.activity.rest.ApiInterface;
import com.mphj.accountry.activity.utils.DeviceUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mphj on 10/15/2017.
 */

public class LoginRest {
    public interface LoginListener{
        void onSuccess(LoginModel loginModel);
        void onFailed(LoginModel loginModel);
    }

    public static void login(String username, String password, final LoginListener loginListener){
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.login(
                DeviceUtils.getUnique(AccountryApplication.context()),
                username,
                password)
                .enqueue(new Callback<LoginModel>() {
                    @Override
                    public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                        if (response.body().isSucceed()){
                            loginListener.onSuccess(response.body());
                        } else {
                            loginListener.onFailed(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginModel> call, Throwable t) {
                        loginListener.onFailed(new LoginModel());
                    }
                });
    }
}
