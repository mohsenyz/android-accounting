package com.mphj.accountry.utils;

import android.os.Environment;

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
import com.mphj.accountry.models.db.TransactionReAdded;
import com.mphj.accountry.models.db.TransactionReAddedDao;
import com.mphj.accountry.presenters.fragment.ReportListPresenterImpl;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by mphj on 11/27/17.
 */

public abstract class ReportExporter {

    public enum Type{
        XLS, PDF
    }

    public static File destinationFolder = Environment.getExternalStoragePublicDirectory("ShafaghAccounting");

    protected Transaction transaction;
    protected List<TransactionProduct> transactionProducts;
    protected List<TransactionReAdded> transactionReAddeds;
    protected List<ReportListPresenterImpl.ReportV1Model> reportV1Models;

    public ReportExporter(Transaction transaction,
                          List<TransactionProduct> transactionProductList,
                          List<TransactionReAdded> transactionReAddedList) {
        this.transaction = transaction;
        this.transactionProducts = transactionProductList;
        this.transactionReAddeds = transactionReAddedList;
    }


    public ReportExporter(List<ReportListPresenterImpl.ReportV1Model> list) {
        this.reportV1Models = list;
    }

    public static ReportExporter byId(int id, Type type) {
        TransactionDao transactionDao = DaoManager.session().getTransactionDao();
        TransactionProductDao transactionProductDao = DaoManager.session().getTransactionProductDao();
        TransactionReAddedDao transactionReaddedDao = DaoManager.session().getTransactionReAddedDao();

        Transaction transaction = transactionDao.load((long) id);
        List<TransactionProduct> transactionProducts = transactionProductDao.queryBuilder()
                .where(TransactionProductDao.Properties.TransactionId.eq(id))
                .list();
        List<TransactionReAdded> transactionReAddeds = transactionReaddedDao.queryBuilder()
                .where(TransactionReAddedDao.Properties.TransactionId.eq(id))
                .list();
        switch (type) {
            case PDF:
                return new PdfReportExporter(transaction, transactionProducts, transactionReAddeds);
            case XLS:
                return new XlsReportExporter(transaction, transactionProducts, transactionReAddeds);
        }
        return null;
    }


    public abstract void export(String fileName) throws Exception;

    public File initDir(String fileName) throws IOException {
        if (!destinationFolder.exists()) {
            destinationFolder.mkdirs();
        }
        File outputFile = new File(destinationFolder + File.separator + fileName);
        if (!outputFile.exists()) {
            outputFile.createNewFile();
        }
        return outputFile;
    }

    public String[][] getT1Body() {
        String[][] t1Body = new String[transactionProducts.size()][5];
        ProductDao productDao = DaoManager.session().getProductDao();
        ProductPriceDao productPriceDao = DaoManager.session().getProductPriceDao();
        for (int i = 0; i < transactionProducts.size(); i++) {
            TransactionProduct transactionProduct = transactionProducts.get(i);
            ProductPrice productPrice = productPriceDao.queryBuilder()
                    .where(ProductPriceDao.Properties.ProductId.eq(transactionProduct.getProductId()))
                    .orderDesc(ProductPriceDao.Properties.CreatedAt)
                    .unique();
            Product product = productDao.load((long)transactionProduct.getProductId());
            String[] row = {LocaleUtils.e2f(String.valueOf(i + 1)),
                    product.getName(),
                    LocaleUtils.e2f(String.valueOf((int) productPrice.getCustomerPrice())),
                    LocaleUtils.e2f(String.valueOf(transactionProduct.getCount())),
                    LocaleUtils.e2f(String.valueOf((int)(transactionProduct.getCount() * productPrice.getCustomerPrice())))};
            t1Body[i] = row;
        }
        return t1Body;
    }


    public String[][] getT2Body() {
        String[][] t2Body = new String[transactionReAddeds.size()][4];
        for (int i = 0; i < transactionReAddeds.size(); i++) {
            TransactionReAdded transactionReAdded = transactionReAddeds.get(i);
            String type = (transactionReAdded.getType() == TransactionReAdded.INC)
                    ? "اضافات" : "کسورات";
            String[] row = {LocaleUtils.e2f(String.valueOf(i + 1)),
                    transactionReAdded.getDescription(),
                    LocaleUtils.e2f(String.valueOf((int) transactionReAdded.getPrice())),
                    LocaleUtils.e2f(type)};
            t2Body[i] = row;
        }
        return t2Body;
    }


    public String[][] getT3Body() {
        String paymentType =
                (transaction.getPaymentType() == Transaction.PAYMENT_CHECK)
                        ? "چک" : "نقد";
        String[][] t3Body = {
                {"تخفیف", LocaleUtils.e2f(String.valueOf((int)transaction.getOff())) + " درصد"},
                {"مالیات", LocaleUtils.e2f(String.valueOf((int)transaction.getTax())) + " درصد"},
                {"توضیحات", transaction.getDescription()},
                {"نوع پرداخت", paymentType},
                {"مبلغ قابل پرداخت", LocaleUtils.e2f(String.valueOf(transaction.getCustomerPrice()))}
        };
        return t3Body;
    }

    public String getCustomerTitle() {
        CustomerDao customerDao = DaoManager.session().getCustomerDao();
        Customer customer = customerDao.load((long)transaction.getCustomerId());
        String customerTitle = "مشتری : " + customer.getName() + " (" + customer.getPhone() + ")";
        return customerTitle;
    }


    public float[] getT1ColumnsSize() {
        return new float[]{1, 6, 3, 2, 3};
    }

    public String[] getT1Headers() {
        return new String[] {"شماره", "نام", "قیمت واحد", "تعداد", "قیمت کل"};
    }

    public float[] getT2ColumnsSize() {
        return new float[]{1, 6, 3, 2};
    }

    public String[] getT2Headers() {
        return new String[] {"شماره", "توضیحات", "قیمت", "نوع"};
    }

    public float[] getT3ColumnsSize() {
        return new float[]{2, 6};
    }

    public String[] getNullStringArr() {
        return new String[]{};
    }
}