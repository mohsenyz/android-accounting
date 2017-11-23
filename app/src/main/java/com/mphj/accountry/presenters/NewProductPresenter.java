package com.mphj.accountry.presenters;

import com.mphj.accountry.models.db.Category;

/**
 * Created by mphj on 10/20/2017.
 */

@Deprecated
public interface NewProductPresenter extends BasePresenter {
    void createProduct(String name, String token, String price, String customerPrice, Category category, String count);
    void generateBarcode(String barcode);
    void generateBarcode();
}
