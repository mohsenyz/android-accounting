package com.mphj.accountry.dao;

import com.mphj.accountry.models.db.StorageProduct;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by mphj on 10/24/2017.
 */

public class StorageProductDao extends RealmBaseDao{

    public StorageProductDao(Realm realm) {
        super(realm);
    }

    public void create(StorageProduct storageProduct){
        storageProduct.setId(countAll());
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(storageProduct);
        realm.commitTransaction();
        // Nested transaction are not allowed
        // No need to log!
        // makeCreateLog(storageProduct);
    }


    public void update(StorageProduct storageProduct){
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(storageProduct);
        realm.commitTransaction();
    }

    /*
    Log wont be needed
    private void makeCreateLog(TransactionProduct transactionProduct){
        LogDao logDao = new LogDao(Realm.getDefaultInstance());
        logDao.create(Log.Builder.create(TransactionProduct.class).id(transactionProduct.getId()).object(TransactionProduct.toJson(transactionProduct)).build());
    }
    */

    public RealmResults<StorageProduct> listAll(){
        realm.refresh();
        return realm.where(StorageProduct.class).findAll();
    }

    public StorageProduct findById(int id){
        realm.refresh();
        return realm.where(StorageProduct.class).equalTo("id", id).findFirst();
    }

    public StorageProduct findByProductAndStorage(int productId, int storageId){
        realm.refresh();
        return realm.where(StorageProduct.class)
                .equalTo("storageID", storageId)
                .equalTo("productId", productId)
                .findFirst();
    }

    public RealmResults<StorageProduct> findByStorage(int storageId){
        realm.refresh();
        return realm.where(StorageProduct.class)
                .equalTo("storageID", storageId)
                .findAll();
    }

    public int countAll(){
        realm.refresh();
        return (int)realm.where(StorageProduct.class).count();
    }
}
