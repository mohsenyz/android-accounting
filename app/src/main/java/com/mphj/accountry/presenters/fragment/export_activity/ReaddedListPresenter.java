package com.mphj.accountry.presenters.fragment.export_activity;

import com.mphj.accountry.models.db.TransactionReAdded;
import com.mphj.accountry.presenters.BasePresenter;

import java.util.List;

/**
 * Created by mphj on 11/10/2017.
 */

public interface ReaddedListPresenter extends BasePresenter {
    void loadList();
    void addNew(TransactionReAdded transactionReAdded);
    void delete(TransactionReAdded transactionReAdded);
    List<TransactionReAdded> getList();
}
