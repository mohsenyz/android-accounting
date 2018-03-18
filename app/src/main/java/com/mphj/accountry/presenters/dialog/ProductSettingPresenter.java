package com.mphj.accountry.presenters.dialog;

import com.mphj.accountry.models.db.Product;
import com.mphj.accountry.presenters.BasePresenter;

/**
 * Created by mphj on 10/23/2017.
 */

public interface ProductSettingPresenter extends BasePresenter {
    void loadList(Product product);
    void increaseProduct(Product product, int count);
}
