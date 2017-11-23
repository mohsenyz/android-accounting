package com.mphj.accountry.mvp.activity;

import com.mphj.accountry.interfaces.callback.JobCallback;
import com.mphj.accountry.models.db.Category;
import com.mphj.accountry.mvp.Mvp;

/**
 * Created by mphj on 11/23/17.
 */

public interface NewCategory {

    interface View extends
            Mvp.View,
            JobCallback{
        void invalidCategoryName();
    }


    interface Presenter extends Mvp.Presenter<View> {
        void newCategory(String name);
    }


    interface Model {
        void newCategory(Category category, JobCallback jobCallback);
    }

}
