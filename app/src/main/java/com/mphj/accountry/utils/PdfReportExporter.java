package com.mphj.accountry.utils;

import android.graphics.PointF;
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
import com.mphj.accountry.models.db.TransactionReAdded;
import com.mphj.accountry.models.db.TransactionReAddedDao;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Created by mphj on 11/20/17.
 */

public class PdfReportExporter extends ReportExporter{

    public static File destinationFolder = Environment.getExternalStoragePublicDirectory("ShafaghAccounting");

    protected Transaction transaction;
    protected List<TransactionProduct> transactionProducts;
    protected List<TransactionReAdded> transactionReAddeds;

    public PdfReportExporter(Transaction transaction,
                          List<TransactionProduct> transactionProductList,
                          List<TransactionReAdded> transactionReAddedList) {
        super(transaction, transactionProductList, transactionReAddedList);
    }

    public static PdfReportExporter byId(int id) {
        return (PdfReportExporter) ReportExporter.byId(id, Type.PDF);
    }

    public Document initDocument(File outputFile) throws Exception {
        Document document = new Document(PageSize.A4);
        PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(outputFile));
        PdfUtils.DefaultFooter event = new PdfUtils.DefaultFooter("حسابداری شفق", "صفحه ی");
        pdfWriter.setPageEvent(event);
        document.open();
        return document;
    }

    public Paragraph getPageTitleParagraph() {
        String pageTitle = "فاکتور شماره ی ";
        pageTitle += LocaleUtils.englishNumberToArabic("" + transaction.getId());
        Paragraph pTitle = new Paragraph(PdfUtils.asRtl(pageTitle), PdfUtils.getDefaultFont(15));
        pTitle.setAlignment(Element.ALIGN_CENTER);
        return pTitle;
    }

    public Paragraph getCustomerParagraph() {
        Paragraph pCustomer = new Paragraph(new Phrase(PdfUtils.asRtl(getCustomerTitle()),
                PdfUtils.getDefaultFont()));
        pCustomer.setAlignment(Element.ALIGN_RIGHT);
        pCustomer.setExtraParagraphSpace(5);
        return pCustomer;
    }


    public Paragraph getTransactionReAddedParagraph() {
        Paragraph pReadded = new Paragraph(new Phrase(
                PdfUtils.asRtl("اضافات و کسورات"),
                PdfUtils.getDefaultFont()
        ));
        pReadded.setAlignment(Element.ALIGN_CENTER);
        return pReadded;
    }

    public void export(String fileName) throws Exception{
        File outputFile = initDir(fileName);
        Document document = initDocument(outputFile);
        Paragraph pBody = new Paragraph();
        pBody.add(getPageTitleParagraph());
        PdfUtils.addEmptyLine(pBody, 1);
        pBody.add(getCustomerParagraph());
        PdfUtils.addEmptyLine(pBody, 1);
        PdfPTable t1 = PdfUtils.getDefaultTable(getT1ColumnsSize(), getT1Headers(), getT1Body());
        pBody.add(t1);
        PdfUtils.addEmptyLine(pBody, 1);
        if (transactionReAddeds.size() != 0) {
            pBody.add(getTransactionReAddedParagraph());
            PdfUtils.addEmptyLine(pBody, 1);
            PdfPTable t2 = PdfUtils.getDefaultTable(getT2ColumnsSize(), getT2Headers(), getT2Body());
            pBody.add(t2);
        }
        pBody.add(getFinallyParagraph());
        PdfUtils.addEmptyLine(pBody, 1);
        PdfPTable t3 = PdfUtils.getDefaultTable(getT3ColumnsSize(), getNullStringArr(), getT3Body());
        pBody.add(t3);
        document.add(pBody);
        document.close();
    }

    public Paragraph getFinallyParagraph() {
        Paragraph pFinally = new Paragraph(PdfUtils.asRtl("جمع بندی"), PdfUtils.getDefaultFont(15));
        pFinally.setAlignment(Element.ALIGN_CENTER);
        return pFinally;
    }
}
