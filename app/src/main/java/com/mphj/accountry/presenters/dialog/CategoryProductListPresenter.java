package com.mphj.accountry.presenters.dialog;

import com.mphj.accountry.models.db.Category;
import com.mphj.accountry.presenters.BasePresenter;

/**
 * Created by mphj on 10/24/2017.
 */

public interface CategoryProductListPresenter extends BasePresenter {

    void loadList(Category category);

}
