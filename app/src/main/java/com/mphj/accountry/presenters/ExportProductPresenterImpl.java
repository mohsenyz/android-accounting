package com.mphj.accountry.presenters;

import com.mphj.accountry.interfaces.ExportProductView;
import com.mphj.accountry.models.db.Category;
import com.mphj.accountry.models.db.CategoryDao;
import com.mphj.accountry.models.db.Customer;
import com.mphj.accountry.models.db.CustomerDao;
import com.mphj.accountry.models.db.Product;
import com.mphj.accountry.models.db.ProductDao;
import com.mphj.accountry.models.db.ProductPrice;
import com.mphj.accountry.models.db.ProductPriceDao;
import com.mphj.accountry.models.db.Transaction;
import com.mphj.accountry.models.db.TransactionDao;
import com.mphj.accountry.models.db.TransactionProduct;
import com.mphj.accountry.models.db.TransactionProductDao;
import com.mphj.accountry.utils.DaoManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mphj on 10/24/2017.
 */

public class ExportProductPresenterImpl implements ExportProductPresenter{
    ExportProductView view;

    List<Product> list = new ArrayList<>();

    Product pendingProduct;

    Category category;

    Customer customer;

    boolean isPriceStatusShown = false;

    public ExportProductPresenterImpl(ExportProductView view){
        this.view = view;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void setStorageById(int id) {
        CategoryDao storageDao = DaoManager.session().getCategoryDao();
        setCategory(storageDao.load((long) id));
    }

    @Override
    public void setCustomerById(int id) {
        CustomerDao customerDao = DaoManager.session().getCustomerDao();
        setCustomer(customerDao.load((long) id));
    }

    @Override
    public void setCategory(Category category) {
        this.category = category;
        view.setStorageName(category.getName());
    }

    @Override
    public void setCustomer(Customer customer) {
        this.customer = customer;
        view.setCustomerName(customer.getName());
    }

    @Override
    public void setPendingProduct(Product product) {
        pendingProduct = product;
    }

    @Override
    public List<Product> getProductList() {
        return list;
    }

    @Override
    public void addProduct(Product product) {
        if (isProductExists(product.getId())){
            view.productAlreadyExists();
            return;
        }
        setPendingProduct(product);
        view.showGetCountActivity();
    }

    @Override
    public void addProduct(String serial) {
        ProductDao productDao = DaoManager.session().getProductDao();
        Product product = productDao.queryBuilder()
                .where(ProductDao.Properties.Token.eq(serial))
                .unique();
        if (product == null){
            view.productNotFound();
            return;
        }
        ProductPriceDao productPriceDao = DaoManager.session().getProductPriceDao();
        ProductPrice productPrice = productPriceDao.queryBuilder()
                .where(ProductPriceDao.Properties.ProductId.eq(product.getId()))
                .orderDesc(ProductPriceDao.Properties.CreatedAt)
                .unique();
        product.setCurrentProductPrice(productPrice);
        addProduct(product);
    }

    @Override
    public void setProductCount(int count) {
        if (count <= 0){
            deleteProduct(pendingProduct.getId());
            pendingProduct = null;
        } else {
            pendingProduct.setPendingCount(count);
            if (!isProductExists(pendingProduct.getId())){
                list.add(pendingProduct);
            }
            view.notifyDataSetChanged();
        }
    }

    @Override
    public void saveTransaction() {
        ProductDao productDao = DaoManager.session().getProductDao();
        for (Product product : list){
            if (product.getPendingCount() < product.getCount()){
                view.specialProductNotDount(product);
                return;
            }
        }
        for (Product product : list){
            product.setCount(product.getCount() - product.getPendingCount());
        }
        productDao.saveInTx(list);
        TransactionDao transactionDao = DaoManager.session().getTransactionDao();
        Transaction transaction = new Transaction();
        transaction.setCustomerId(customer.getId().intValue());
        transaction.setType(Transaction.TYPE_OUTGOING);
        transaction.setCreatedAt(Math.round(System.currentTimeMillis() / 1000L));
        transactionDao.save(transaction);
        int transactionId = transaction.getId().intValue();
        TransactionProductDao transactionProductDao = DaoManager.session().getTransactionProductDao();
        for (Product product : list){
            TransactionProduct transactionProduct = new TransactionProduct();
            transactionProduct.setTransactionId(transactionId);
            transactionProduct.setCount(product.getCount());
            transactionProduct.setProductId(product.getId().intValue());
            transactionProductDao.save(transactionProduct);
        }
        view.finishActivity();
    }

    private boolean isProductExists(Long id){
        for (Product product : list)
            if (product.getId() == id)
                return true;

        return false;
    }

    private void deleteProduct(Long id){
        for (Product product : list) {
            if (product.getId() == id) {
                list.remove(product);
            }
        }
        view.notifyDataSetChanged();
    }
}
