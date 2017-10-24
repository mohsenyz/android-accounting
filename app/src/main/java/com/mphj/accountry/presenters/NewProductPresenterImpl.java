package com.mphj.accountry.presenters;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.mphj.accountry.dao.ProductDao;
import com.mphj.accountry.dao.ProductPriceDao;
import com.mphj.accountry.interfaces.NewProductView;
import com.mphj.accountry.models.db.Product;
import com.mphj.accountry.models.db.ProductPrice;
import com.mphj.accountry.utils.BarcodeGenerator;
import com.mphj.accountry.utils.DoubleValidator;

import io.realm.Realm;

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
    public void createProduct(String name, String token, String price, String customerPrice, String off) {
        if (!DoubleValidator.isValid(off)){
            view.invalidOff();
            return;
        }

        if (!DoubleValidator.isValid(price)){
            view.invalidPrice();
            return;
        }

        if (!DoubleValidator.isValid(customerPrice)){
            view.invalidCustomerPrice();
            return;
        }

        if (TextUtils.isEmpty(name)){
            view.invalidName();
            return;
        }

        if (TextUtils.isEmpty(token)){
            view.invalidSerial();
            return;
        }

        long currentTime = Math.round(System.currentTimeMillis() / 1000f);
        Product product = new Product();
        product.setCreatedAt(currentTime);
        product.setName(name);
        product.setToken(token);

        ProductDao productDao = new ProductDao(Realm.getDefaultInstance());
        int id = productDao.create(product);
        productDao.close();

        // We should define price and off too

        ProductPrice productPrice = new ProductPrice();
        productPrice.setCreatedAt(currentTime);
        productPrice.setProductId(id);
        productPrice.setOff(Double.parseDouble(off));
        productPrice.setPrice(Double.parseDouble(price));
        productPrice.setCustomerPrice(Double.parseDouble(customerPrice));

        ProductPriceDao productPriceDao = new ProductPriceDao(Realm.getDefaultInstance());
        productPriceDao.create(productPrice);
        productPriceDao.close();
        view.finishActivity();
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
