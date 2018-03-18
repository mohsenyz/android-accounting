package com.mphj.accountry.presenters;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.mphj.accountry.interfaces.NewProductView;
import com.mphj.accountry.models.db.Category;
import com.mphj.accountry.models.db.Product;
import com.mphj.accountry.models.db.ProductDao;
import com.mphj.accountry.models.db.ProductPrice;
import com.mphj.accountry.models.db.ProductPriceDao;
import com.mphj.accountry.models.db.Transaction;
import com.mphj.accountry.models.db.TransactionDao;
import com.mphj.accountry.models.db.TransactionProduct;
import com.mphj.accountry.models.db.TransactionProductDao;
import com.mphj.accountry.utils.BarcodeGenerator;
import com.mphj.accountry.utils.DaoManager;
import com.mphj.accountry.utils.DoubleValidator;
import com.mphj.accountry.utils.GsonHelper;
import com.mphj.accountry.utils.LogBuilder;

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
    public void createProduct(String name, String token, String price, String customerPrice, Category category, String count) {
        if (category == null) {
            view.invalidCategory();
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

        if (!DoubleValidator.isValid(count)) {
            view.invalidCount();
            return;
        }

        long currentTime = Math.round(System.currentTimeMillis() / 1000f);
        Product product = new Product();
        product.setCreatedAt(currentTime);
        product.setName(name);
        product.setToken(token);
        product.setCategoryId(category.getId().intValue());
        product.setCount(Integer.parseInt(count));

        ProductDao productDao = DaoManager.session().getProductDao();
        productDao.save(product);
        int id = product.getId().intValue();

        try {
            LogBuilder.create(Product.class)
                    .id(product.getId().intValue())
                    .object(GsonHelper.toJson(product.reportDiff(null)))
                    .build()
                    .save();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // We should define price and off too

        ProductPrice productPrice = new ProductPrice();
        productPrice.setCreatedAt(currentTime);
        productPrice.setProductId(id);
        productPrice.setPrice(Double.parseDouble(price));
        productPrice.setCustomerPrice(Double.parseDouble(customerPrice));

        ProductPriceDao productPriceDao = DaoManager.session().getProductPriceDao();
        productPriceDao.save(productPrice);

        try {
            LogBuilder.create(ProductPrice.class)
                    .id(productPrice.getId().intValue())
                    .object(GsonHelper.toJson(productPrice.reportDiff(null)))
                    .build()
                    .save();
        } catch (Exception e) {
            e.printStackTrace();
        }

        TransactionDao transactionDao = DaoManager.session().getTransactionDao();
        Transaction transaction = new Transaction();
        transaction.setCreatedAt(System.currentTimeMillis() / 1000L);
        transaction.setType(Transaction.TYPE_INCOMING);
        transactionDao.save(transaction);

        try {
            LogBuilder.create(Transaction.class)
                    .id(transaction.getId().intValue())
                    .object(GsonHelper.toJson(transaction.reportDiff(null)))
                    .build()
                    .save();
        } catch (Exception e) {
            e.printStackTrace();
        }

        TransactionProductDao transactionProductDao = DaoManager.session().getTransactionProductDao();

        TransactionProduct transactionProduct = new TransactionProduct();
        transactionProduct.setCount(product.getCount());
        transactionProduct.setProductId(product.getId().intValue());
        transactionProduct.setTransactionId(transaction.getId().intValue());
        transactionProductDao.save(transactionProduct);

        try {
            LogBuilder.create(TransactionProduct.class)
                    .id(transactionProduct.getId().intValue())
                    .object(GsonHelper.toJson(transactionProduct.reportDiff(null)))
                    .build()
                    .save();
        } catch (Exception e) {
            e.printStackTrace();
        }

        view.finishActivity();
    }


    @Override
    public void updateProduct(String name, String token, String price, String customerPrice, Category category, String count, int productId) {
        if (category == null) {
            view.invalidCategory();
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

        if (!DoubleValidator.isValid(count)) {
            view.invalidCount();
            return;
        }

        long currentTime = Math.round(System.currentTimeMillis() / 1000f);
        Product product = DaoManager.session().getProductDao().load((long) productId);
        Product oldProduct = null;
        try {
            oldProduct = (Product) product.clone();
        } catch (Exception e) {
            e.printStackTrace();
        }

        product.setCreatedAt(currentTime);
        product.setName(name);
        product.setToken(token);
        product.setCategoryId(category.getId().intValue());
        product.setCount(Integer.parseInt(count));

        ProductDao productDao = DaoManager.session().getProductDao();
        productDao.save(product);
        int id = product.getId().intValue();

        try {
            LogBuilder.update(Product.class)
                    .id(product.getId().intValue())
                    .object(GsonHelper.toJson(product.reportDiff(oldProduct)))
                    .build()
                    .save();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // We should define price and off too

        ProductPrice productPrice = new ProductPrice();
        productPrice.setCreatedAt(currentTime);
        productPrice.setProductId(id);
        productPrice.setPrice(Double.parseDouble(price));
        productPrice.setCustomerPrice(Double.parseDouble(customerPrice));

        ProductPriceDao productPriceDao = DaoManager.session().getProductPriceDao();
        productPriceDao.save(productPrice);

        try {
            LogBuilder.create(ProductPrice.class)
                    .id(productPrice.getId().intValue())
                    .object(GsonHelper.toJson(productPrice.reportDiff(null)))
                    .build()
                    .save();
        } catch (Exception e) {
            e.printStackTrace();
        }

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
