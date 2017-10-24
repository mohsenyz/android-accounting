package com.mphj.accountry.presenters;

import com.mphj.accountry.models.db.Product;
import com.mphj.accountry.models.db.Storage;

import java.util.List;

/**
 * Created by mphj on 10/24/2017.
 */

public interface ImportProductPresenter extends BasePresenter {
    void setStorageById(int id);
    void setStorage(Storage storage);
    void setPendingProduct(Product product);
    List<Product> getProductList();
    void addProduct(Product product);
    void addProduct(String serial);
    void setProductCount(int count);
    void saveTransaction();
}
