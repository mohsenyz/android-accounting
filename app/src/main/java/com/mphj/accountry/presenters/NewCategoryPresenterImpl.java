package com.mphj.accountry.presenters;

import android.text.TextUtils;

import com.mphj.accountry.interfaces.NewCategoryView;
import com.mphj.accountry.models.db.Category;
import com.mphj.accountry.models.db.CategoryDao;
import com.mphj.accountry.utils.DaoManager;

/**
 * Created by mphj on 10/20/2017.
 */

public class NewCategoryPresenterImpl implements NewCategoryPresenter {

    NewCategoryView view;

    public NewCategoryPresenterImpl(NewCategoryView view){
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
        CategoryDao dao = DaoManager.session().getCategoryDao();
        Category category = new Category();
        category.setName(name);
        dao.save(category);
        view.finishActivity();
    }


    @Override
    public void updateStorage(String name, int id) {
        if (TextUtils.isEmpty(name)) {
            view.invalidStorageName();
            return;
        }
        CategoryDao dao = DaoManager.session().getCategoryDao();
        Category category = dao.load((long) id);
        category.setName(name);
        dao.save(category);
        view.finishActivity();
    }
}
