package com.mphj.accountry.presenters;

import com.mphj.accountry.dao.ProductDao;
import com.mphj.accountry.dao.ProductPriceDao;
import com.mphj.accountry.dao.StorageProductDao;
import com.mphj.accountry.interfaces.D_StorageProductListView;
import com.mphj.accountry.models.db.Product;
import com.mphj.accountry.models.db.ProductPrice;
import com.mphj.accountry.models.db.Storage;
import com.mphj.accountry.models.db.StorageProduct;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by mphj on 10/24/2017.
 */

public class D_StorageProductListPresenterImpl implements D_StorageProductListPresenter {

    D_StorageProductListView view;

    public D_StorageProductListPresenterImpl(D_StorageProductListView view){
        this.view = view;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void loadList(Storage storage) {
        StorageProductDao storageProductDao = new StorageProductDao(Realm.getDefaultInstance());
        ProductPriceDao productPriceDao = new ProductPriceDao(Realm.getDefaultInstance());
        ProductDao productDao = new ProductDao(Realm.getDefaultInstance());
        RealmResults<StorageProduct> list = storageProductDao.findByStorage(storage.getId());
        List<Product> products = new ArrayList<>();
        for (StorageProduct storageProduct : list){
            ProductPrice productPrice = productPriceDao.findLatestByProductId(storageProduct.getId());
            Product product = productDao.findById(storageProduct.getId());
            product.setCurrentProductPrice(productPrice);
            product.setCount(storageProduct.getCount());
            products.add(product);
        }
        view.setAdapter(products);
    }
}
