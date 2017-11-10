package com.mphj.accountry.presenters;

import com.mphj.accountry.interfaces.D_CategoryProductListView;
import com.mphj.accountry.models.db.Category;
import com.mphj.accountry.models.db.Product;
import com.mphj.accountry.models.db.ProductDao;
import com.mphj.accountry.models.db.ProductPrice;
import com.mphj.accountry.models.db.ProductPriceDao;
import com.mphj.accountry.utils.DaoManager;

import java.util.List;


/**
 * Created by mphj on 10/24/2017.
 */

public class D_CategoryProductListPresenterImpl implements D_CategoryProductListPresenter {

    D_CategoryProductListView view;

    public D_CategoryProductListPresenterImpl(D_CategoryProductListView view){
        this.view = view;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void loadList(Category category) {
        ProductPriceDao productPriceDao = DaoManager.session().getProductPriceDao();
        ProductDao productDao = DaoManager.session().getProductDao();
        List<Product> products = productDao.queryBuilder()
                .where(ProductDao.Properties.CategoryId.eq(category.getId()))
                .list();
        for (Product product : products) {
            ProductPrice productPrice = productPriceDao.queryBuilder()
                    .where(ProductPriceDao.Properties.ProductId.eq(product.getId()))
                    .orderDesc(ProductPriceDao.Properties.CreatedAt)
                    .list()
                    .get(0);
            product.setCurrentProductPrice(productPrice);
        }
        view.setAdapter(products);
    }
}
