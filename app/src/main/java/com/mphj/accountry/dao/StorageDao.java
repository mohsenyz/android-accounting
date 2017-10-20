package com.mphj.accountry.dao;

import com.mphj.accountry.models.db.Log;
import com.mphj.accountry.models.db.Storage;

import io.realm.Realm;

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
        LogDao logDao = new LogDao(Realm.getDefaultInstance());
        logDao.create(Log.Builder.create(Storage.class).id(storage.getId()).object(storage).build());
        realm.commitTransaction();
    }

    public int countAll(){
        realm.refresh();
        return (int)realm.where(Storage.class).count();
    }
}
