package com.mphj.accountry.presenters;

import com.mphj.accountry.models.db.TransactionReadded;

import java.util.List;

/**
 * Created by mphj on 11/10/2017.
 */

public interface F_ExportActivity_ReaddedListPresenter extends BasePresenter {
    void loadList();
    void addNew(TransactionReadded transactionReadded);
    void delete(TransactionReadded transactionReadded);
    List<TransactionReadded> getList();
}
