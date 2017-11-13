package com.mphj.accountry.presenters;

import com.mphj.accountry.models.db.Category;

/**
 * Created by mphj on 10/23/2017.
 */

public interface D_CategorySettingPresenter extends BasePresenter{
    void loadList(Category category);
}
