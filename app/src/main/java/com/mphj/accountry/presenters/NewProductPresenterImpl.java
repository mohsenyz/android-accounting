package com.mphj.accountry.presenters;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.mphj.accountry.interfaces.NewProductView;
import com.mphj.accountry.utils.BarcodeGenerator;

/**
 * Created by mphj on 10/20/2017.
 */

public class NewProductPresenterImpl implements NewProductPresenter, BarcodeGenerator.OnLoadListener {

    NewProductView view;

    public NewProductPresenterImpl(NewProductView view){
        this.view = view;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void createProduct(String name, String token) {

    }

    @Override
    public void generateBarcode(String barcode) {
        if (TextUtils.isEmpty(barcode))
            return;
        BarcodeGenerator.create(barcode, 300, 50, this);
    }

    @Override
    public void generateBarcode() {
        String randomBarcode = BarcodeGenerator.random(16);
        generateBarcode(randomBarcode);
        view.setSerial(randomBarcode);
    }

    @Override
    public void onBitmapLoad(Bitmap bitmap) {
        view.setSerial(bitmap);
    }
}
