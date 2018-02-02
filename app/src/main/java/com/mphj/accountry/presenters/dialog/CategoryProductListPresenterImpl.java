package com.mphj.accountry.presenters.dialog;

import com.mphj.accountry.interfaces.dialog.CategoryProductListView;
import com.mphj.accountry.models.db.Category;
import com.mphj.accountry.models.db.Product;
import com.mphj.accountry.models.db.ProductDao;
import com.mphj.accountry.models.db.ProductPrice;
import com.mphj.accountry.models.db.ProductPriceDao;
import com.mphj.accountry.models.db.Transaction;
import com.mphj.accountry.models.db.TransactionDao;
import com.mphj.accountry.models.db.TransactionProduct;
import com.mphj.accountry.models.db.TransactionProductDao;
import com.mphj.accountry.utils.DaoManager;
import com.mphj.accountry.utils.GsonHelper;
import com.mphj.accountry.utils.LogBuilder;

import java.util.List;


/**
 * Created by mphj on 10/24/2017.
 */

public class CategoryProductListPresenterImpl implements CategoryProductListPresenter {

    CategoryProductListView view;

    public CategoryProductListPresenterImpl(CategoryProductListView view){
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

    @Override
    public void increaseProduct(Product product, int count) {
        if (count <= 0)
            return;

        ProductDao productDao = DaoManager.session().getProductDao();
        TransactionProductDao transactionProductDao = DaoManager.session().getTransactionProductDao();
        TransactionDao transactionDao = DaoManager.session().getTransactionDao();

        product.setCount(product.getCount() + count);
        productDao.save(product);

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

        TransactionProduct transactionProduct = new TransactionProduct();
        transactionProduct.setTransactionId(transaction.getId().intValue());
        transactionProduct.setProductId(product.getId().intValue());
        transactionProduct.setCount(count);

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

        view.increasedSuccessfully();
    }
}
