package com.mphj.accountry.dao;

import io.realm.Realm;

/**
 * Created by mphj on 10/20/2017.
 */

public class RealmBaseDao implements BaseDao {

    Realm realm;

    public RealmBaseDao(Realm realm){
        this.realm = realm;
    }

    @Override
    public void close() {
        realm.close();
    }
}
