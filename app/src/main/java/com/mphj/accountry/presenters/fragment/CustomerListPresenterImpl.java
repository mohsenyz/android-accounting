package com.mphj.accountry.presenters.fragment;

import com.mphj.accountry.interfaces.fragment.CustomerListView;
import com.mphj.accountry.models.db.CustomerDao;
import com.mphj.accountry.utils.DaoManager;

/**
 * Created by mphj on 10/20/2017.
 */

public class CustomerListPresenterImpl implements CustomerListPresenter {

    CustomerListView view;

    public CustomerListPresenterImpl(CustomerListView view){
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
