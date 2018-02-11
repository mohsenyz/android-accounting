package com.mphj.accountry.models.rest;

import com.mphj.accountry.rest.ApiClient;
import com.mphj.accountry.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mphj on 2/11/18.
 */

public class MainRest {

    public interface StatusListener {
        void onSuccess(int status);
        void onError(Throwable t);
    }

    public static void getConnected(final StatusListener statusListener) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        apiInterface.getStatus().enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (statusListener != null && response.body() != null)
                    statusListener.onSuccess(response.body().intValue());
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                statusListener.onError(t);
            }
        });
    }

}
