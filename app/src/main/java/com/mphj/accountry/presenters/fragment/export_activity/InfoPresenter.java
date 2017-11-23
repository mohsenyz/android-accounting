package com.mphj.accountry.presenters.fragment.export_activity;

import com.mphj.accountry.interfaces.fragment.export_activity.ProductListView;
import com.mphj.accountry.interfaces.fragment.export_activity.ReaddedListView;
import com.mphj.accountry.models.db.Product;
import com.mphj.accountry.models.db.TransactionReAdded;
import com.mphj.accountry.presenters.BasePresenter;

import java.util.List;

/**
 * Created by mphj on 11/17/17.
 */

public interface InfoPresenter extends BasePresenter{
    void setTransactionReaddedList(List<TransactionReAdded> list);
    void setProductList(List<Product> list);
    void configWith(ProductListView view);
    void configWith(ReaddedListView view);
    void load();
}
