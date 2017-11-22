package com.mphj.accountry.presenters.fragment;

import com.mphj.accountry.interfaces.fragment.CheckListView;
import com.mphj.accountry.models.db.CheckDao;
import com.mphj.accountry.utils.DaoManager;

/**
 * Created by mphj on 11/20/17.
 */

public class CheckListPresenterImpl implements CheckListPresenter {

    CheckListView view;

    public CheckListPresenterImpl(CheckListView view) {
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
        CheckDao checkDao = DaoManager.session().getCheckDao();
        view.setAdapter(checkDao.loadAll());
    }
}
