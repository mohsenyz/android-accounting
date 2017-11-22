package com.mphj.accountry.mvp.activity;

import android.support.annotation.Nullable;

import com.mphj.accountry.interfaces.callback.DBListener;
import com.mphj.accountry.interfaces.fragment.export_activity.InfoView;
import com.mphj.accountry.interfaces.fragment.export_activity.ProductListView;
import com.mphj.accountry.interfaces.fragment.export_activity.ReaddedListView;
import com.mphj.accountry.models.db.Check;
import com.mphj.accountry.models.db.Product;
import com.mphj.accountry.models.db.Transaction;
import com.mphj.accountry.models.db.TransactionProduct;
import com.mphj.accountry.mvp.Mvp;

import java.util.List;

/**
 * Created by mphj on 11/22/17.
 */

public interface ExportProduct {

    interface View extends
            Mvp.View,
            DBListener.OnSave<Transaction> {

    }


    interface Presenter extends Mvp.Presenter<View> {
        void submit(InfoView infoView,
                    ProductListView productListView,
                    ReaddedListView readdedListView,
                    int paymentType,
                    @Nullable Check check);
    }


    interface Model {
        void saveTransaction(Transaction transaction,
                             List<TransactionProduct> transactionProducts);
        void saveCheck(Check check);
        void saveProducts(List<Product> products);
    }

}
