package com.mphj.accountry.dao;

import com.mphj.accountry.models.db.Log;
import com.mphj.accountry.models.db.Transaction;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by mphj on 10/24/2017.
 */

public class TransactionDao extends RealmBaseDao{

    public TransactionDao(Realm realm) {
        super(realm);
    }

    public int create(Transaction transaction){
        transaction.setId(countAll());
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(transaction);
        realm.commitTransaction();
        // Nested transaction are not allowed
        makeCreateLog(transaction);
        return transaction.getId();
    }

    private void makeCreateLog(Transaction transaction){
        LogDao logDao = new LogDao(Realm.getDefaultInstance());
        logDao.create(Log.Builder.create(Transaction.class).id(transaction.getId()).object(Transaction.toJson(transaction)).build());
    }

    public RealmResults<Transaction> listAll(){
        realm.refresh();
        return realm.where(Transaction.class).findAll();
    }

    public Transaction findById(int id){
        realm.refresh();
        return realm.where(Transaction.class).equalTo("id", id).findFirst();
    }

    public int countAll(){
        realm.refresh();
        return (int)realm.where(Transaction.class).count();
    }
}
