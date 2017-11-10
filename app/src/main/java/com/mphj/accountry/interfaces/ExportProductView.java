package com.mphj.accountry.interfaces;

import com.mphj.accountry.models.db.Product;

/**
 * Created by mphj on 10/24/2017.
 */

public interface ExportProductView {
    void productAlreadyExists();
    void productNotFound();
    void showGetCountActivity();
    void setStorageName(String name);
    void setCustomerName(String name);
    void notifyDataSetChanged();
    void finishActivity();
    void specialProductNotDount(Product product);
}
