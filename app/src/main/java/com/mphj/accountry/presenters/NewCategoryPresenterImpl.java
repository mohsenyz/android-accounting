package com.mphj.accountry.presenters;

import android.text.TextUtils;

import com.mphj.accountry.interfaces.NewCategoryView;
import com.mphj.accountry.models.db.Category;
import com.mphj.accountry.models.db.CategoryDao;
import com.mphj.accountry.utils.DaoManager;
import com.mphj.accountry.utils.GsonHelper;
import com.mphj.accountry.utils.LogBuilder;

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

        try {
            LogBuilder.create(Category.class)
                    .id(category.getId().intValue())
                    .object(GsonHelper.toJson(category.reportDiff(null)))
                    .build()
                    .save();
        } catch (Exception e) {
            e.printStackTrace();
        }

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
        Category oldCategory = null;
        try {
            oldCategory = (Category) category.clone();
        } catch (Exception e) {
            e.printStackTrace();
        }

        category.setName(name);
        dao.save(category);

        try {
            LogBuilder.update(Category.class)
                    .id(category.getId().intValue())
                    .object(GsonHelper.toJson(category.reportDiff(oldCategory)))
                    .build()
                    .save();
        } catch (Exception e) {
            e.printStackTrace();
        }

        view.finishActivity();
    }
}
