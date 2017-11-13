package com.mphj.accountry.presenters;

import com.mphj.accountry.interfaces.F_ExportActivity_ProductListView;
import com.mphj.accountry.models.db.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mphj on 11/10/2017.
 */

public class F_ExportActivity_ProductListPresenterImpl implements F_ExportActivity_ProductListPresenter {

    F_ExportActivity_ProductListView view;

    List<Product> list = new ArrayList<>();

    public F_ExportActivity_ProductListPresenterImpl(F_ExportActivity_ProductListView view){
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
        view.loadList(list);
    }

    @Override
    public void addProduct(Product product) {
        if (!productExists(product)) {
            list.add(product);
        } else {
            if (product.getPendingCount() == 0) {
                deleteProduct(product);
            } else {
                updateProduct(product);
            }
        }
        loadList();
    }

    void deleteProduct(Product product) {
        list.remove(product);
    }

    boolean productExists(Product product) {
        for (Product product1 : list) {
            if (product1.getId().intValue() == product.getId().intValue()) {
                return true;
            }
        }
        return false;
    }

    void updateProduct(Product product) {
        for (Product product1 : list) {
            if (product1.getId().intValue() == product.getId().intValue()) {
                product1.setPendingCount(product.getPendingCount());
            }
        }
    }

    @Override
    public List<Product> getList() {
        return list;
    }
}
