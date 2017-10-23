package com.mphj.accountry.presenters;

import com.mphj.accountry.dao.ProductDao;
import com.mphj.accountry.dao.ProductPriceDao;
import com.mphj.accountry.interfaces.F_ProductListView;
import com.mphj.accountry.models.db.Product;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by mphj on 10/22/2017.
 */

public class F_ProductListPresenterImpl implements F_ProductListPresenter {

    F_ProductListView view;

    public F_ProductListPresenterImpl(F_ProductListView view){
        this.view = view;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void loadList() {
        List<Product> list = new ArrayList<>();
        ProductDao productDao = new ProductDao(Realm.getDefaultInstance());
        ProductPriceDao productPriceDao = new ProductPriceDao(Realm.getDefaultInstance());
        RealmResults<Product> realmResults = productDao.listAll();
        for (Product product : realmResults){
            product.setCurrentProductPrice(productPriceDao.findLatestByProductId(product.getId()));
            list.add(product);
        }
        view.setAdapter(list);
    }
}
