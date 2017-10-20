package com.mphj.accountry.interfaces;

import com.mphj.accountry.models.db.Customer;

import io.realm.RealmResults;

/**
 * Created by mphj on 10/20/2017.
 */

public interface F_CustomerListView {
    void setAdapter(RealmResults<Customer> realmResults);
}
