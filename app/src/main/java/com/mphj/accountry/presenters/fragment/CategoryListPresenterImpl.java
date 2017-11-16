package com.mphj.accountry.presenters.fragment;

import com.mphj.accountry.interfaces.fragment.CategoryListView;
import com.mphj.accountry.models.db.CategoryDao;
import com.mphj.accountry.utils.DaoManager;


/**
 * Created by mphj on 10/20/2017.
 */

public class CategoryListPresenterImpl implements CategoryListPresenter {

    CategoryListView view;

    public CategoryListPresenterImpl(CategoryListView view){
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
