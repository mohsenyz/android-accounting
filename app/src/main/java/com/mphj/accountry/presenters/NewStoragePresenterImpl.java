package com.mphj.accountry.presenters;

import android.text.TextUtils;

import com.mphj.accountry.dao.StorageDao;
import com.mphj.accountry.interfaces.NewStorageView;
import com.mphj.accountry.models.db.Storage;

import io.realm.Realm;

/**
 * Created by mphj on 10/20/2017.
 */

public class NewStoragePresenterImpl implements NewStoragePresenter {

    NewStorageView view;

    public NewStoragePresenterImpl(NewStorageView view){
        this.view = view;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void newStorage(String name) {
        if (TextUtils.isEmpty(name)) {
            view.invalidStorageName();
            return;
        }
        StorageDao dao = new StorageDao(Realm.getDefaultInstance());
        Storage storage = new Storage();
        storage.setName(name);
        dao.create(storage);
        view.finishActivity();
    }
}
