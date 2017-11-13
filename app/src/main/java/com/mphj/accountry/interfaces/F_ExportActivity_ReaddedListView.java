package com.mphj.accountry.interfaces;

import com.mphj.accountry.models.db.TransactionReadded;

import java.util.List;

/**
 * Created by mphj on 11/10/2017.
 */

public interface F_ExportActivity_ReaddedListView {
    void loadList(List<TransactionReadded> list);
    void addNew(TransactionReadded transactionReadded);
    List<TransactionReadded> getList();
}
