package com.mphj.accountry.dao;

import com.mphj.accountry.models.db.Log;
import com.mphj.accountry.models.db.TransactionProduct;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by mphj on 10/24/2017.
 */

public class TransactionProductDao extends RealmBaseDao {
    public TransactionProductDao(Realm realm) {
        super(realm);
    }

    public void create(TransactionProduct transactionProduct){
        transactionProduct.setId(countAll());
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(transactionProduct);
        realm.commitTransaction();
        // Nested transaction are not allowed
        makeCreateLog(transactionProduct);
    }

    private void makeCreateLog(TransactionProduct transactionProduct){
        LogDao logDao = new LogDao(Realm.getDefaultInstance());
        logDao.create(Log.Builder.create(TransactionProduct.class).id(transactionProduct.getId()).object(TransactionProduct.toJson(transactionProduct)).build());
    }

    public RealmResults<TransactionProduct> listAll(){
        realm.refresh();
        return realm.where(TransactionProduct.class).findAll();
    }

    public TransactionProduct findById(int id){
        realm.refresh();
        return realm.where(TransactionProduct.class).equalTo("id", id).findFirst();
    }

    public int countAll(){
        realm.refresh();
        return (int)realm.where(TransactionProduct.class).count();
    }
}
