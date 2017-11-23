package com.mphj.accountry.mvp.activity;

import android.graphics.Bitmap;

import com.mphj.accountry.interfaces.callback.JobCallback;
import com.mphj.accountry.models.db.Category;
import com.mphj.accountry.models.db.Product;
import com.mphj.accountry.mvp.Mvp;

/**
 * Created by mphj on 11/23/17.
 */

public interface NewProduct {

    interface View extends Mvp.View {
        void invalidName();
        void invalidSerial();
        void invalidPrice();
        void invalidCustomerPrice();
        void invalidCategory();
        void invalidCount();
        void setSerial(Bitmap bitmap);
        void setSerial(String serial);
        void setCategory(Category category);
    }


    interface Presenter extends Mvp.Presenter<View> {
        void createProduct(String name, String token, String price, String customerPrice, Category category, String count);
        void generateBarcode(String barcode);
        void generateRandomBarcode();
    }


    interface Model {
        void newProduct(Product product, JobCallback jobCallback);
    }

}
