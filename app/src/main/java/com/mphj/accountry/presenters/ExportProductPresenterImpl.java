package com.mphj.accountry.presenters;

import android.support.annotation.Nullable;

import com.mphj.accountry.interfaces.ExportProductView;
import com.mphj.accountry.interfaces.fragment.export_activity.InfoView;
import com.mphj.accountry.interfaces.fragment.export_activity.ProductListView;
import com.mphj.accountry.interfaces.fragment.export_activity.ReaddedListView;
import com.mphj.accountry.models.db.Check;
import com.mphj.accountry.models.db.CheckDao;
import com.mphj.accountry.models.db.Log;
import com.mphj.accountry.models.db.Product;
import com.mphj.accountry.models.db.ProductDao;
import com.mphj.accountry.models.db.Transaction;
import com.mphj.accountry.models.db.TransactionDao;
import com.mphj.accountry.models.db.TransactionProduct;
import com.mphj.accountry.models.db.TransactionProductDao;
import com.mphj.accountry.models.db.TransactionReadded;
import com.mphj.accountry.utils.DaoManager;
import com.mphj.accountry.utils.GsonHelper;
import com.mphj.accountry.utils.LogBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mphj on 10/24/2017.
 */

public class ExportProductPresenterImpl implements ExportProductPresenter{

    ExportProductView view;

    public ExportProductPresenterImpl(ExportProductView view) {
        this.view = view;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void submit(InfoView infoView, ProductListView productListView, ReaddedListView readdedListView, int paymentType, @Nullable Check check) {
        List<Product> products = productListView.getList();
        List<TransactionReadded> transactionReaddeds = readdedListView.getList();
        String description = infoView.getFactorDescription();
        int customerPrice = infoView.getTotalCustomerPrice();
        int tax = infoView.getTax();
        int off = infoView.getOff();
        int customerId = infoView.getCustomer().getId().intValue();

        TransactionDao transactionDao = DaoManager.session().getTransactionDao();
        Transaction transaction = new Transaction();
        transaction.setCreatedAt(System.currentTimeMillis() / 1000l);
        transaction.setCustomerId(customerId);
        transaction.setCustomerPrice(customerPrice);
        transaction.setPrice(calculateProductPrice(products));
        transaction.setDescription(description);
        transaction.setOff(off);
        transaction.setTax(tax);
        transaction.setPaymentType(paymentType);
        transaction.setReaddedPrice(calculateReaddedPrice(transactionReaddeds));
        transaction.setType(Transaction.TYPE_OUTGOING);
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


        ProductDao productDao = DaoManager.session().getProductDao();
        TransactionProductDao transactionProductDao = DaoManager.session().getTransactionProductDao();
        List<TransactionProduct> transactionProducts = new ArrayList<>();
        for (Product product : products) {
            product.setCount(product.getCount() - product.getPendingCount());
            TransactionProduct transactionProduct = new TransactionProduct();
            transactionProduct.setTransactionId(transaction.getId().intValue());
            transactionProduct.setProductId(product.getId().intValue());
            transactionProduct.setCount(product.getPendingCount());
            transactionProducts.add(transactionProduct);
        }

        transactionProductDao.saveInTx(transactionProducts);
        List<Log> logs = new ArrayList<>();
        try {
            for (TransactionProduct transactionProduct : transactionProducts) {
                logs.add(
                        LogBuilder.create(TransactionProduct.class)
                                .id(transactionProduct.getId().intValue())
                                .object(GsonHelper.toJson(transactionProduct.reportDiff(null)))
                                .build()
                );
            }
            DaoManager.session().getLogDao().saveInTx(logs);
        } catch (Exception e) {
            e.printStackTrace();
        }

        productDao.saveInTx(products);

        if (check != null) {
            CheckDao checkDao = DaoManager.session().getCheckDao();
            check.setTransactionId(transaction.getId().intValue());
            checkDao.save(check);

            try {
                LogBuilder.create(Check.class)
                        .id(check.getId().intValue())
                        .object(GsonHelper.toJson(check.reportDiff(null)))
                        .build()
                        .save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        view.transactionSaved(transaction.getId().intValue());
    }


    private int calculateProductPrice(List<Product> products) {
        int price = 0;
        for (Product product : products) {
            price += product.getPendingCount() * product.getCurrentProductPrice().getPrice();
        }
        return price;
    }

    private int calculateReaddedPrice(List<TransactionReadded> transactionReaddeds) {
        int price = 0;
        for (TransactionReadded transactionReadded : transactionReaddeds) {
            price += transactionReadded.getPrice()
                    * (transactionReadded.getType() == TransactionReadded.INC ? 1 : -1);
        }
        return price;
    }
}
