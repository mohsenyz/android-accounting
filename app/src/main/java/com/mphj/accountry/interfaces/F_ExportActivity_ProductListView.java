package com.mphj.accountry.interfaces;

import com.mphj.accountry.models.db.Product;

import java.util.List;

/**
 * Created by mphj on 11/10/2017.
 */

public interface F_ExportActivity_ProductListView {
    void loadList(List<Product> list);
    void addProduct(Product product);
    List<Product> getList();
}
