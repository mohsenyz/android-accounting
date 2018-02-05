package com.mphj.accountry.rest;

import com.mphj.accountry.models.LoginModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by mphj on 10/15/2017.
 */

public interface ApiInterface {
    @GET("login.php")
    Call<LoginModel> login(@Query("phone") String phone);

    @GET("verify.php")
    Call<LoginModel> verify(@Query("phone") String phone, @Query("code") String code);
}