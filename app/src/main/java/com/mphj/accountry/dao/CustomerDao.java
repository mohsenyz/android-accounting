package com.mphj.accountry.dao;

import com.mphj.accountry.models.db.Customer;
import com.mphj.accountry.models.db.Log;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by mphj on 10/20/2017.
 */

public class CustomerDao extends  RealmBaseDao{

    public CustomerDao(Realm realm) {
        super(realm);
    }

    public void create(Customer customer){
        customer.setId(countAll());
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(customer);
        realm.commitTransaction();
        // Nested transaction are not allowed
        makeCreateLog(customer);
    }

    private void makeCreateLog(Customer customer){
        LogDao logDao = new LogDao(Realm.getDefaultInstance());
        logDao.create(Log.Builder.create(Customer.class).id(customer.getId()).object(Customer.toJson(customer)).build());
    }

    public Customer findById(int id){
        realm.refresh();
        return realm.where(Customer.class)
                .equalTo("id", id)
                .findFirst();
    }

    public RealmResults<Customer> listAll(){
        realm.refresh();
        return realm.where(Customer.class).findAll();
    }

    public int countAll(){
        realm.refresh();
        return (int)realm.where(Customer.class).count();
    }
}
