package com.mphj.accountry.utils;

import android.os.Environment;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
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
import com.mphj.accountry.models.db.TransactionReadded;
import com.mphj.accountry.models.db.TransactionReaddedDao;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Created by mphj on 11/20/17.
 */


public class XlsReportExporter {

    public static File destinationFolder = Environment.getExternalStoragePublicDirectory("ShafaghAccounting");

    protected Transaction transaction;
    protected List<TransactionProduct> transactionProducts;
    protected List<TransactionReadded> transactionReaddeds;

    public XlsReportExporter(Transaction transaction,
                             List<TransactionProduct> transactionProductList,
                             List<TransactionReadded> transactionReaddedList) {
        this.transaction = transaction;
        this.transactionProducts = transactionProductList;
        this.transactionReaddeds = transactionReaddedList;
    }

    public static XlsReportExporter byId(int id) {
        TransactionDao transactionDao = DaoManager.session().getTransactionDao();
        TransactionProductDao transactionProductDao = DaoManager.session().getTransactionProductDao();
        TransactionReaddedDao transactionReaddedDao = DaoManager.session().getTransactionReaddedDao();

        Transaction transaction = transactionDao.load((long) id);
        List<TransactionProduct> transactionProducts = transactionProductDao.queryBuilder()
                .where(TransactionProductDao.Properties.TransactionId.eq(id))
                .list();
        List<TransactionReadded> transactionReaddeds = transactionReaddedDao.queryBuilder()
                .where(TransactionReaddedDao.Properties.TransactionId.eq(id))
                .list();
        return new XlsReportExporter(transaction, transactionProducts, transactionReaddeds);
    }


    public void export(String fileName) throws Exception{
        if (!destinationFolder.exists()) {
            destinationFolder.mkdirs();
        }
        File outputFile = new File(destinationFolder + File.separator + fileName);
        if (!outputFile.exists()) {
            outputFile.createNewFile();
        }

        Workbook wb = new HSSFWorkbook();
        CellStyle cHeader = wb.createCellStyle();
        cHeader.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
        cHeader.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        Sheet productSheet = wb.createSheet("محصولات");

        CustomerDao customerDao = DaoManager.session().getCustomerDao();
        Customer customer = customerDao.load((long)transaction.getCustomerId());
        String customerTitle = customer.getName() + " (" + customer.getPhone() + ")";


        float[] t1ColumnsSize = new float[]{1, 6, 3, 2, 3};
        String[] t1Headers = new String[] {"شماره", "نام", "قیمت واحد", "تعداد", "قیمت کل"};
        String[][] t1Body = new String[transactionProducts.size()][5];
        for (int i = 0; i < transactionProducts.size(); i++) {
            TransactionProduct transactionProduct = transactionProducts.get(i);
            ProductDao productDao = DaoManager.session().getProductDao();
            ProductPriceDao productPriceDao = DaoManager.session().getProductPriceDao();
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
        XlsUtils.getDefaultTable(productSheet, t1ColumnsSize, t1Headers, t1Body, cHeader);


        Sheet readdedSheet = wb.createSheet("اضافات و کسورات");

        float[] t2ColumnsSize = new float[]{1, 6, 3, 2};
        String[] t2Headers = new String[] {"شماره", "توضیحات", "قیمت", "نوع"};
        String[][] t2Body = new String[transactionReaddeds.size()][4];
        for (int i = 0; i < transactionReaddeds.size(); i++) {
            TransactionReadded transactionReadded = transactionReaddeds.get(i);
            String type = (transactionReadded.getType() == TransactionReadded.INC)
                    ? "اضافات" : "کسورات";
            String[] row = {LocaleUtils.e2f(String.valueOf(i + 1)),
                    transactionReadded.getDescription(),
                    LocaleUtils.e2f(String.valueOf((int)transactionReadded.getPrice())),
                    LocaleUtils.e2f(type)};
            t2Body[i] = row;
        }
        XlsUtils.getDefaultTable(readdedSheet, t2ColumnsSize, t2Headers, t2Body, cHeader);

        Sheet finallySheet = wb.createSheet("جمع بندی");

        float[] t3ColumnsSize = {2, 6};
        String paymentType =
                (transaction.getPaymentType() == Transaction.PAYMENT_CHECK)
                        ? "چک" : "نقد";
        String[][] t3Body = {
                {"تخفیف", LocaleUtils.e2f(String.valueOf((int)transaction.getOff())) + " درصد"},
                {"مالیات", LocaleUtils.e2f(String.valueOf((int)transaction.getTax())) + " درصد"},
                {"توضیحات", transaction.getDescription()},
                {"مشتری", customerTitle},
                {"نوع پرداخت", paymentType},
                {"مبلغ قابل پرداخت", LocaleUtils.e2f(String.valueOf((int)transaction.getCustomerPrice()))}
        };

        XlsUtils.getDefaultTable(finallySheet, t3ColumnsSize, new String[]{"", ""}, t3Body, cHeader);
        FileOutputStream os = new FileOutputStream(outputFile);
        wb.write(os);
        os.close();
    }
}
