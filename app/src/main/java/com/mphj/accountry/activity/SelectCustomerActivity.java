package com.mphj.accountry.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.mphj.accountry.R;
import com.mphj.accountry.fragment.CustomerListFragment;
import com.mphj.accountry.interfaces.OnObjectItemClick;
import com.mphj.accountry.models.db.Customer;

import org.parceler.Parcels;

public class SelectCustomerActivity extends BaseActivity implements OnObjectItemClick<Customer>{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_customer);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        CustomerListFragment fragment = CustomerListFragment.newInstance(true);
        fragment.setOnObjectItemClickListener(this);
        ft.add(R.id.fragment, fragment);
        ft.commit();
        setFinishOnTouchOutside(false);
    }

    @Override
    public void onClick(View v, Customer object) {
        Intent i = new Intent();
        i.putExtra("customer", Parcels.wrap(Customer.class, object));
        setResult(Activity.RESULT_OK, i);
        finish();
    }
}
