package com.mphj.accountry.activity;

import android.os.Bundle;

import com.mphj.accountry.R;
import com.mphj.accountry.interfaces.AlertView;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlertActivity extends BaseActivity implements AlertView {

    public static final int REJECT = 1, SUBMIT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.submit)
    void onSubmit() {
        closeAlert(SUBMIT);
    }


    @OnClick(R.id.reject)
    void reject() {
        closeAlert(REJECT);
    }


    @Override
    public void closeAlert(int type) {
        setResult(type);
        finish();
    }
}
