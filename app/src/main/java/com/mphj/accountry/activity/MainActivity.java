package com.mphj.accountry.activity;

import android.content.Intent;
import android.os.Bundle;

import com.mphj.accountry.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(this, SelectProductActivity.class));
        finish();
    }
}
