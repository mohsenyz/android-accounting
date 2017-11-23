package com.mphj.accountry.interfaces.fragment.export_activity;

import com.mphj.accountry.models.db.TransactionReAdded;

import java.util.List;

/**
 * Created by mphj on 11/10/2017.
 */

public interface ReaddedListView {
    void loadList(List<TransactionReAdded> list);
    void addNew(TransactionReAdded transactionReAdded);
    List<TransactionReAdded> getList();
}
