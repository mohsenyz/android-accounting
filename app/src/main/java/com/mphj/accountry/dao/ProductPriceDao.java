package com.mphj.accountry.dao;

import com.mphj.accountry.models.db.Log;
import com.mphj.accountry.models.db.ProductPrice;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by mphj on 10/22/2017.
 */

public class ProductPriceDao extends RealmBaseDao {
    public ProductPriceDao(Realm realm) {
        super(realm);
    }

    public void create(ProductPrice productPrice){
        productPrice.setId(countAll());
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(productPrice);
        realm.commitTransaction();
        // Nested transaction are not allowed
        makeCreateLog(productPrice);
    }

    private void makeCreateLog(ProductPrice productPrice){
        LogDao logDao = new LogDao(Realm.getDefaultInstance());
        logDao.create(Log.Builder
                .create(ProductPrice.class)
                .id(productPrice.getId())
                .object(ProductPrice.toJson(productPrice))
                .build());
    }

    public RealmResults<ProductPrice> listAll(){
        realm.refresh();
        return realm.where(ProductPrice.class).findAll();
    }

    public ProductPrice findLatestByProductId(int id){
        realm.refresh();
        return realm.where(ProductPrice.class)
                .equalTo("productId", id)
                .findAllSorted("createdAt", false)
                .get(0);
    }

    public int countAll(){
        realm.refresh();
        return (int)realm.where(ProductPrice.class).count();
    }
}
