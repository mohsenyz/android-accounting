package com.mphj.accountry.interfaces;

import com.mphj.accountry.models.db.Customer;

import java.util.List;


/**
 * Created by mphj on 10/20/2017.
 */

public interface F_CustomerListView {
    void setAdapter(List<Customer> realmResults);
}
