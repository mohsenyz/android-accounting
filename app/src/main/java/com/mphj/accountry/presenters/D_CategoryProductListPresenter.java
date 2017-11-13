package com.mphj.accountry.presenters;

import com.mphj.accountry.models.db.Category;

/**
 * Created by mphj on 10/24/2017.
 */

public interface D_CategoryProductListPresenter extends BasePresenter {

    void loadList(Category category);

}
