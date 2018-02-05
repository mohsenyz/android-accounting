package com.mphj.accountry.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.mphj.accountry.utils.Auth;
import com.mphj.accountry.utils.GsonHelper;
import com.mphj.accountry.utils.PermissionCompat;

import es.dmoral.toasty.Toasty;

public class MainActivity extends BaseActivity implements PermissionCompat.Callback{

    public static final int REQUEST_PERMISSIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermissions();
    }


    void checkPermissions() {
        if (PermissionCompat.isGranted(this)) {
            onGrant();
        } else {
            PermissionCompat.request(this, REQUEST_PERMISSIONS);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {
            PermissionCompat.verify(grantResults, this);
        }
    }


    @Override
    public void onGrant() {
        Class activity = DashboardActivity.class;
        Toasty.normal(this, GsonHelper.toJson(Auth.getInfo(this))).show();
        Toasty.normal(this, GsonHelper.toJson(Auth.getInfo(this))).show();
        Toasty.normal(this, GsonHelper.toJson(Auth.getInfo(this))).show();
        Toasty.normal(this, GsonHelper.toJson(Auth.getInfo(this))).show();
        if (!Auth.isLoggedIn(this))
            activity = LoginActivity.class;
        startActivity(
                new Intent(this, activity)
        );
    }


    @Override
    public void onReject() {
        Toasty.error(this, "This app needs permissions!").show();
        checkPermissions();
    }
}
