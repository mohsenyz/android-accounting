package com.mphj.accountry.interfaces;

import com.mphj.accountry.models.db.Product;

import java.util.List;

/**
 * Created by mphj on 10/22/2017.
 */

public interface F_ProductListView {
    void setAdapter(List<Product> realmResults);
}
