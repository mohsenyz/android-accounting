package com.mphj.accountry.interfaces.fragment.export_activity;

import com.mphj.accountry.models.db.TransactionReadded;

import java.util.List;

/**
 * Created by mphj on 11/10/2017.
 */

public interface ReaddedListView {
    void loadList(List<TransactionReadded> list);
    void addNew(TransactionReadded transactionReadded);
    List<TransactionReadded> getList();
}
