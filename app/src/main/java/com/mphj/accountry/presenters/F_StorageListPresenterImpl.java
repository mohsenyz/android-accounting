package com.mphj.accountry.presenters;

import com.mphj.accountry.dao.StorageDao;
import com.mphj.accountry.interfaces.F_StorageListView;

import io.realm.Realm;

/**
 * Created by mphj on 10/20/2017.
 */

public class F_StorageListPresenterImpl implements F_StorageListPresenter{

    F_StorageListView view;

    public F_StorageListPresenterImpl(F_StorageListView view){
        this.view = view;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void loadList() {
        StorageDao dao = new StorageDao(Realm.getDefaultInstance());
        view.setAdapter(dao.listAll());
    }
}
