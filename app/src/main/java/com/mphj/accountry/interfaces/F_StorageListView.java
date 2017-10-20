package com.mphj.accountry.interfaces;

import com.mphj.accountry.models.db.Storage;

import io.realm.RealmResults;

/**
 * Created by mphj on 10/20/2017.
 */

public interface F_StorageListView {
    void setAdapter(RealmResults<Storage> realmResults);
}
