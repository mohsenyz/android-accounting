package com.mphj.accountry.interfaces;

import com.mphj.accountry.models.db.Product;

import java.util.List;

/**
 * Created by mphj on 10/24/2017.
 */

public interface D_CategoryProductListView {
    void setAdapter(List<Product> list);
}
