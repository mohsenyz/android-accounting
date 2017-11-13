package com.mphj.accountry.presenters.fragment;

import com.mphj.accountry.interfaces.fragment.ProductListView;
import com.mphj.accountry.models.db.Product;
import com.mphj.accountry.models.db.ProductDao;
import com.mphj.accountry.models.db.ProductPrice;
import com.mphj.accountry.models.db.ProductPriceDao;
import com.mphj.accountry.utils.DaoManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mphj on 10/22/2017.
 */

public class ProductListPresenterImpl implements ProductListPresenter {

    ProductListView view;

    public ProductListPresenterImpl(ProductListView view){
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
        ProductDao productDao = DaoManager.session().getProductDao();
        ProductPriceDao productPriceDao = DaoManager.session().getProductPriceDao();
        List<Product> realmResults = productDao.loadAll();
        for (Product product : realmResults){
            ProductPrice productPrice = productPriceDao.queryBuilder()
                    .where(ProductPriceDao.Properties.ProductId.eq(product.getId()))
                    .orderDesc(ProductPriceDao.Properties.CreatedAt)
                    .unique();
            product.setCurrentProductPrice(productPrice);
            list.add(product);
        }
        view.setAdapter(list);
    }
}
