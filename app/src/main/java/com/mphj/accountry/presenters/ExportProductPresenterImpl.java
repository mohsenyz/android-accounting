package com.mphj.accountry.presenters;

import com.mphj.accountry.dao.CustomerDao;
import com.mphj.accountry.dao.ProductDao;
import com.mphj.accountry.dao.ProductPriceDao;
import com.mphj.accountry.dao.StorageDao;
import com.mphj.accountry.dao.StorageProductDao;
import com.mphj.accountry.dao.TransactionDao;
import com.mphj.accountry.dao.TransactionProductDao;
import com.mphj.accountry.interfaces.ExportProductView;
import com.mphj.accountry.models.db.Customer;
import com.mphj.accountry.models.db.Product;
import com.mphj.accountry.models.db.ProductPrice;
import com.mphj.accountry.models.db.Storage;
import com.mphj.accountry.models.db.StorageProduct;
import com.mphj.accountry.models.db.Transaction;
import com.mphj.accountry.models.db.TransactionProduct;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Created by mphj on 10/24/2017.
 */

public class ExportProductPresenterImpl implements ExportProductPresenter{
    ExportProductView view;

    List<Product> list = new ArrayList<>();

    Product pendingProduct;

    Storage storage;

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
        StorageDao storageDao = new StorageDao(Realm.getDefaultInstance());
        setStorage(storageDao.findById(id));
    }

    @Override
    public void setCustomerById(int id) {
        CustomerDao customerDao = new CustomerDao(Realm.getDefaultInstance());
        setCustomer(customerDao.findById(id));
    }

    @Override
    public void setStorage(Storage storage) {
        this.storage = storage;
        view.setStorageName(storage.getName());
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

    private void invalidatePrice(){
        double totalPrice = 0;
        double totalCustomerPrice = 0;
        double totalCustomerPriceWithOff = 0;
        for (Product product : list){
            totalPrice += product.getCurrentProductPrice().getPrice() * product.getCount();
            totalCustomerPrice += product.getCurrentProductPrice().getCustomerPrice() * product.getCount();
            totalCustomerPriceWithOff += product.getCurrentProductPrice().getCustomerPrice()
                    * (100 - product.getCurrentProductPrice().getOff()) / 100 * product.getCount();
        }
        view.setPriceStatus(totalPrice, totalCustomerPrice, totalCustomerPriceWithOff);
    }

    @Override
    public void addProduct(String serial) {
        ProductDao productDao = new ProductDao(Realm.getDefaultInstance());
        Product product = productDao.findBySerial(serial);
        if (product == null){
            view.productNotFound();
            return;
        }
        ProductPriceDao productPriceDao = new ProductPriceDao(Realm.getDefaultInstance());
        ProductPrice productPrice = productPriceDao.findLatestByProductId(product.getId());
        product.setCurrentProductPrice(productPrice);
        addProduct(product);
    }

    @Override
    public void setProductCount(int count) {
        if (count <= 0){
            deleteProduct(pendingProduct.getId());
            pendingProduct = null;
        } else {
            pendingProduct.setCount(count);
            if (!isProductExists(pendingProduct.getId())){
                list.add(pendingProduct);
            }
            view.notifyDataSetChanged();
        }
        if (list.size() == 0){
            if (isPriceStatusShown) {
                view.hidePriceStatus();
                isPriceStatusShown = false;
            }
        } else {
            if (!isPriceStatusShown) {
                view.showPriceStatus();
                isPriceStatusShown = true;
            }
            invalidatePrice();
        }
    }

    @Override
    public void saveTransaction() {
        StorageProductDao storageProductDao = new StorageProductDao(Realm.getDefaultInstance());
        for (Product product : list){
            StorageProduct storageProduct = storageProductDao.findByProductAndStorage(product.getId(), storage.getId());
            if (storageProduct == null || storageProduct.getCount() < product.getCount()){
                view.specialProductNotDount(product);
                return;
            }
        }
        // Let's modify the storage product
        for (Product product : list){
            StorageProduct storageProduct = storageProductDao.findByProductAndStorage(product.getId(), storage.getId());
            storageProductDao.beginTransaction();
            storageProduct.setCount(storageProduct.getCount() - product.getCount());
            storageProductDao.commitTransaction();
            if (storageProduct.getCount() == 0){
                storageProductDao.beginTransaction();
                storageProduct.removeFromRealm();
                storageProductDao.commitTransaction();
            }
        }
        storageProductDao.close();
        TransactionDao transactionDao = new TransactionDao(Realm.getDefaultInstance());
        Transaction transaction = new Transaction();
        transaction.setStorageId(storage.getId());
        transaction.setCustomerId(customer.getId());
        transaction.setType(Transaction.TYPE_OUTGOING);
        transaction.setCreatedAt(Math.round(System.currentTimeMillis() / 1000L));
        int transactionId = transactionDao.create(transaction);
        transactionDao.close();
        TransactionProductDao transactionProductDao = new TransactionProductDao(Realm.getDefaultInstance());
        for (Product product : list){
            TransactionProduct transactionProduct = new TransactionProduct();
            transactionProduct.setTransactionId(transactionId);
            transactionProduct.setCount(product.getCount());
            transactionProduct.setProductId(product.getId());
            transactionProductDao.create(transactionProduct);
        }
        transactionProductDao.close();
        view.finishActivity();
    }

    private boolean isProductExists(int id){
        for (Product product : list)
            if (product.getId() == id)
                return true;

        return false;
    }

    private void deleteProduct(int id){
        for (Product product : list) {
            if (product.getId() == id) {
                list.remove(product);
            }
        }
        view.notifyDataSetChanged();
    }
}
