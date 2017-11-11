package com.mphj.accountry.presenters;

import com.mphj.accountry.interfaces.F_ExportActivity_ReaddedListView;
import com.mphj.accountry.models.db.TransactionReadded;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mphj on 11/10/2017.
 */

public class F_ExportActivity_ReaddedListPresenterImpl implements F_ExportActivity_ReaddedListPresenter {

    F_ExportActivity_ReaddedListView view;
    List<TransactionReadded> list = new ArrayList<>();

    public F_ExportActivity_ReaddedListPresenterImpl(F_ExportActivity_ReaddedListView view) {
        this.view = view;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void loadList() {
        view.loadList(list);
    }

    @Override
    public void addNew(TransactionReadded transactionReadded) {
        list.add(transactionReadded);
        loadList();
    }

    @Override
    public List<TransactionReadded> getList() {
        return list;
    }


    @Override
    public void delete(TransactionReadded transactionReadded) {
        list.remove(transactionReadded);
    }
}
