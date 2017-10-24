package com.mphj.accountry.dao;

import com.mphj.accountry.models.db.Log;
import com.mphj.accountry.models.db.Product;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by mphj on 10/22/2017.
 */

public class ProductDao extends RealmBaseDao {

    public ProductDao(Realm realm) {
        super(realm);
    }

    public int create(Product product){
        product.setId(countAll());
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(product);
        realm.commitTransaction();
        // Nested transaction are not allowed
        makeCreateLog(product);
        return product.getId();
    }

    private void makeCreateLog(Product product){
        LogDao logDao = new LogDao(Realm.getDefaultInstance());
        logDao.create(Log.Builder.create(Product.class).id(product.getId()).object(Product.toJson(product)).build());
    }

    public RealmResults<Product> listAll(){
        realm.refresh();
        return realm.where(Product.class).findAll();
    }

    public int countAll(){
        realm.refresh();
        return (int)realm.where(Product.class).count();
    }

    public Product findBySerial(String serial){
        realm.refresh();
        return realm.where(Product.class).equalTo("token", serial).findFirst();
    }

    public Product findById(int id){
        realm.refresh();
        return realm.where(Product.class).equalTo("id", id).findFirst();
    }
}
