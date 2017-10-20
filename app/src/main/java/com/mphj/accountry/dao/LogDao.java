package com.mphj.accountry.dao;

import com.mphj.accountry.models.db.Log;

import io.realm.Realm;

/**
 * Created by mphj on 10/20/2017.
 */

public class LogDao extends RealmBaseDao {

    public LogDao(Realm realm) {
        super(realm);
    }

    public void create(Log log){
        log.setId(countAll());
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(log);
        realm.commitTransaction();
    }

    public int countAll(){
        realm.refresh();
        return (int)realm.where(Log.class).count();
    }
}
