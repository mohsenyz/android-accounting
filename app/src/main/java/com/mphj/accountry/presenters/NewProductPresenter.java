package com.mphj.accountry.presenters;

/**
 * Created by mphj on 10/20/2017.
 */

public interface NewProductPresenter extends BasePresenter {
    void createProduct(String name, String token, String price, String off);
    void generateBarcode(String barcode);
    void generateBarcode();
}
