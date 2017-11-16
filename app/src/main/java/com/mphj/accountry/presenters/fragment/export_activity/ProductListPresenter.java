package com.mphj.accountry.presenters.fragment.export_activity;

import com.mphj.accountry.models.db.Product;
import com.mphj.accountry.presenters.BasePresenter;

import java.util.List;

/**
 * Created by mphj on 11/10/2017.
 */

public interface ProductListPresenter extends BasePresenter {
    void loadList();
    void addProduct(Product product);
    List<Product> getList();
}
