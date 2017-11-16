package com.mphj.accountry.interfaces.dialog;

import com.mphj.accountry.models.db.Product;

import java.util.List;

/**
 * Created by mphj on 10/24/2017.
 */

public interface CategoryProductListView {
    void setAdapter(List<Product> list);
}
