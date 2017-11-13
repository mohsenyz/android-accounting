package com.mphj.accountry.interfaces.fragment;

import com.mphj.accountry.models.db.Customer;

import java.util.List;


/**
 * Created by mphj on 10/20/2017.
 */

public interface CustomerListView {
    void setAdapter(List<Customer> realmResults);
}
