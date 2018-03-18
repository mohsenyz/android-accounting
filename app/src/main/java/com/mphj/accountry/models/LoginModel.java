package com.mphj.accountry.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by mphj on 10/15/2017.
 */

public class LoginModel {

    @SerializedName("user_id")
    private int userId;

    @SerializedName("token")
    private String token;

    @SerializedName("work_id")
    private int workId;

    @SerializedName("status")
    private int status = -1;

    @SerializedName("msg")
    String msg;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getWorkId() {
        return workId;
    }

    public void setWorkId(int workId) {
        this.workId = workId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSucceed() {
        return status == 200;
    }
}
