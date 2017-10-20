package com.mphj.accountry.presenters;

import com.mphj.accountry.dao.CustomerDao;
import com.mphj.accountry.interfaces.F_CustomerListView;

import io.realm.Realm;

/**
 * Created by mphj on 10/20/2017.
 */

public class F_CustomerListPresenterImpl implements F_CustomerListPresenter {

    F_CustomerListView view;

    public F_CustomerListPresenterImpl(F_CustomerListView view){
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
        CustomerDao dao = new CustomerDao(Realm.getDefaultInstance());
        view.setAdapter(dao.listAll());
    }
}
