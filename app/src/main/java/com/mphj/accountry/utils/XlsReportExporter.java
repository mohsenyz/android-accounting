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


public class XlsReportExporter extends ReportExporter{

    public XlsReportExporter(Transaction transaction,
                             List<TransactionProduct> transactionProductList,
                             List<TransactionReAdded> transactionReAddedList) {
        super(transaction, transactionProductList, transactionReAddedList);
    }

    public XlsReportExporter(List<ReportListPresenterImpl.ReportV1Model> list) {
        super(list);
    }

    public static XlsReportExporter byId(int id) {
        return (XlsReportExporter) ReportExporter.byId(id, Type.XLS);
    }


    public static XlsReportExporter byReportV1(List<ReportListPresenterImpl.ReportV1Model> list) {
        return new XlsReportExporter(list);
    }


    public void export(String fileName) throws Exception{
        File outputFile = initDir(fileName);
        Workbook wb = new HSSFWorkbook();
        CellStyle cHeader = wb.createCellStyle();
        cHeader.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
        cHeader.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        Sheet productSheet = wb.createSheet("محصولات");

        CustomerDao customerDao = DaoManager.session().getCustomerDao();
        Customer customer = customerDao.load((long)transaction.getCustomerId());
        //String customerTitle = customer.getName() + " (" + customer.getPhone() + ")";

        XlsUtils.getDefaultTable(productSheet, getT1ColumnsSize(), getT1Headers(), getT1Body(), cHeader);

        Sheet readdedSheet = wb.createSheet("اضافات و کسورات");
        XlsUtils.getDefaultTable(readdedSheet, getT2ColumnsSize(), getT2Headers(), getT3Body(), cHeader);

        Sheet finallySheet = wb.createSheet("جمع بندی");

        XlsUtils.getDefaultTable(finallySheet, getT3ColumnsSize(), new String[]{"", ""}, getT3Body(), cHeader);
        FileOutputStream os = new FileOutputStream(outputFile);
        wb.write(os);
        os.close();
    }


    public void exportReportV1(String fileName) throws Exception{
        File outputFile = initDir(fileName);
        Workbook wb = new HSSFWorkbook();
        CellStyle cHeader = wb.createCellStyle();
        cHeader.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
        cHeader.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        Sheet productSheet = wb.createSheet("محصولات");
        float[] t1ColumnsSize = new float[]{1, 6, 6, 3, 4, 4};
        String[] t1Headers = new String[] {"شماره", "نام", "شناسه", "تعداد فروش", "قیمت کل فروش", "قیمت کل خرید"};
        String[][] t1Body = new String[reportV1Models.size()][6];
        for (int i = 0; i < reportV1Models.size(); i++) {
            t1Body[i] = new String[] {
                    LocaleUtils.e2f(String.valueOf(i + 1)),
                    reportV1Models.get(i).product.getName(),
                    reportV1Models.get(i).product.getToken(),
                    LocaleUtils.e2f(String.valueOf((int)reportV1Models.get(i).count)),
                    LocaleUtils.e2f(String.valueOf((int)reportV1Models.get(i).customerPrice)),
                    LocaleUtils.e2f(String.valueOf((int)reportV1Models.get(i).price))
            };
        }

        XlsUtils.getDefaultTable(productSheet, t1ColumnsSize, t1Headers, t1Body, cHeader);
        FileOutputStream os = new FileOutputStream(outputFile);
        wb.write(os);
        os.close();
    }
}
