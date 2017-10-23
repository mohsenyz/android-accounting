package com.mphj.accountry.dao;

import com.mphj.accountry.models.db.Log;
import com.mphj.accountry.models.db.Storage;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by mphj on 10/20/2017.
 */

public class StorageDao extends RealmBaseDao{

    public StorageDao(Realm realm) {
        super(realm);
    }

    public void create(Storage storage){
        storage.setId(countAll());
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(storage);
        realm.commitTransaction();
        // Nested transaction are not allowed
        makeCreateLog(storage);
    }

    private void makeCreateLog(Storage storage){
        LogDao logDao = new LogDao(Realm.getDefaultInstance());
        logDao.create(Log.Builder.create(Storage.class).id(storage.getId()).object(Storage.toJson(storage)).build());
    }

    public RealmResults<Storage> listAll(){
        realm.refresh();
        return realm.where(Storage.class).findAll();
    }

    public Storage findById(int id){
        realm.refresh();
        return realm.where(Storage.class).equalTo("id", id).findFirst();
    }

    public int countAll(){
        realm.refresh();
        return (int)realm.where(Storage.class).count();
    }
}
