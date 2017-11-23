package com.mphj.accountry.presenters.fragment.export_activity;

import com.mphj.accountry.interfaces.fragment.export_activity.InfoView;
import com.mphj.accountry.interfaces.fragment.export_activity.ProductListView;
import com.mphj.accountry.interfaces.fragment.export_activity.ReaddedListView;
import com.mphj.accountry.models.db.Product;
import com.mphj.accountry.models.db.ProductPrice;
import com.mphj.accountry.models.db.ProductPriceDao;
import com.mphj.accountry.models.db.TransactionReAdded;
import com.mphj.accountry.utils.DaoManager;

import java.util.List;

/**
 * Created by mphj on 11/17/17.
 */

public class InfoPresenterImpl implements InfoPresenter {

    InfoView view;
    List<TransactionReAdded> transactionReAddeds;
    List<Product> products;

    public InfoPresenterImpl(InfoView view) {
        this.view = view;
    }

    @Override
    public void onResume() {


    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void setTransactionReaddedList(List<TransactionReAdded> list) {
        this.transactionReAddeds = list;
    }

    @Override
    public void setProductList(List<Product> list) {
        this.products = list;
    }

    @Override
    public void configWith(ProductListView view) {
        setProductList(view.getList());
    }

    @Override
    public void configWith(ReaddedListView view) {
        setTransactionReaddedList(view.getList());
    }

    @Override
    public void load() {
        int intTax = view.getTax();
        int intOff = view.getOff();
        int productPrice = computeProductsPrice();
        int readdedPrice = computeReaddedPrice();
        int price = 0;
        price += (100 + intTax - intOff) * productPrice / 100;
        price += readdedPrice;
        int priceWithoutOff = (100 + intTax) * productPrice / 100;
        priceWithoutOff += readdedPrice;
        view.setTotalPriceWithOff(price);
        view.setTotalOffPrice(priceWithoutOff - price);
    }


    private int computeProductsPrice() {
        int price = 0;
        for (Product product : products) {
            ProductPriceDao productPriceDao = DaoManager.session().getProductPriceDao();
            ProductPrice productPrice = productPriceDao.queryBuilder()
                    .where(ProductPriceDao.Properties.ProductId.eq(product.getId()))
                    .orderDesc(ProductPriceDao.Properties.CreatedAt)
                    .unique();
            product.setCurrentProductPrice(productPrice);
            price += productPrice.getCustomerPrice() * product.getPendingCount();
        }
        return price;
    }


    private int computeReaddedPrice() {
        int price = 0;
        for (TransactionReAdded transactionReAdded : transactionReAddeds) {
            price += transactionReAdded.getPrice()
                    * (transactionReAdded.getType() == TransactionReAdded.INC ? 1 : -1);
        }
        return price;
    }
}
