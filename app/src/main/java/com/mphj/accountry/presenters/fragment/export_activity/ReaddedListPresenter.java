package com.mphj.accountry.presenters.fragment.export_activity;

import com.mphj.accountry.models.db.TransactionReadded;
import com.mphj.accountry.presenters.BasePresenter;

import java.util.List;

/**
 * Created by mphj on 11/10/2017.
 */

public interface ReaddedListPresenter extends BasePresenter {
    void loadList();
    void addNew(TransactionReadded transactionReadded);
    void delete(TransactionReadded transactionReadded);
    List<TransactionReadded> getList();
}
