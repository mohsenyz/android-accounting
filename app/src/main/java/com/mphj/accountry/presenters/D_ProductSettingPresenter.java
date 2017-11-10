package com.mphj.accountry.presenters;

import com.mphj.accountry.models.db.Product;

/**
 * Created by mphj on 10/23/2017.
 */

public interface D_ProductSettingPresenter extends BasePresenter {
    void loadList();
    void increaseProduct(Product product, int count);
}
