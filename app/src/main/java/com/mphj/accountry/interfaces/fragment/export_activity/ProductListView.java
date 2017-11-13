package com.mphj.accountry.interfaces.fragment.export_activity;

import com.mphj.accountry.models.db.Product;

import java.util.List;

/**
 * Created by mphj on 11/10/2017.
 */

public interface ProductListView {
    void loadList(List<Product> list);
    void addProduct(Product product);
    List<Product> getList();
}
