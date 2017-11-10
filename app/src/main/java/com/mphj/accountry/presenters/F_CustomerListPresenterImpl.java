package com.mphj.accountry.presenters;

import com.mphj.accountry.interfaces.F_CustomerListView;
import com.mphj.accountry.models.db.CustomerDao;
import com.mphj.accountry.utils.DaoManager;

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
        CustomerDao dao = DaoManager.session().getCustomerDao();
        view.setAdapter(dao.loadAll());
    }
}
