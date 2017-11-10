package com.mphj.accountry.presenters;

import com.mphj.accountry.interfaces.F_CategoryListView;
import com.mphj.accountry.models.db.CategoryDao;
import com.mphj.accountry.utils.DaoManager;


/**
 * Created by mphj on 10/20/2017.
 */

public class F_CategoryListPresenterImpl implements F_CategoryListPresenter {

    F_CategoryListView view;

    public F_CategoryListPresenterImpl(F_CategoryListView view){
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
        CategoryDao dao = DaoManager.session().getCategoryDao();
        view.setAdapter(dao.loadAll());
    }
}
