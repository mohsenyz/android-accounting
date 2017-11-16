package com.mphj.accountry.presenters.dialog;

import com.mphj.accountry.models.db.Category;
import com.mphj.accountry.presenters.BasePresenter;

/**
 * Created by mphj on 10/23/2017.
 */

public interface CategorySettingPresenter extends BasePresenter {
    void loadList(Category category);
}
