package com.mphj.accountry.presenters;

import com.mphj.accountry.models.db.Category;
import com.mphj.accountry.models.db.Customer;
import com.mphj.accountry.models.db.Product;

import java.util.List;

/**
 * Created by mphj on 10/24/2017.
 */

public interface ExportProductPresenter extends BasePresenter {
    void setStorageById(int id);
    void setCustomerById(int id);
    void setCategory(Category category);
    void setCustomer(Customer customer);
    void setPendingProduct(Product product);
    List<Product> getProductList();
    void addProduct(Product product);
    void addProduct(String serial);
    void setProductCount(int count);
    void saveTransaction();
}
