package com.mphj.accountry.presenters;

import com.mphj.accountry.models.db.Product;

import java.util.List;

/**
 * Created by mphj on 11/10/2017.
 */

public interface F_ExportActivity_ProductListPresenter extends BasePresenter{
    void loadList();
    void addProduct(Product product);
    List<Product> getList();
}
