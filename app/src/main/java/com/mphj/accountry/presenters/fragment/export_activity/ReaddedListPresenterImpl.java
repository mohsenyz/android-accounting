package com.mphj.accountry.presenters.fragment.export_activity;

import com.mphj.accountry.interfaces.fragment.export_activity.ReaddedListView;
import com.mphj.accountry.models.db.TransactionReAdded;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mphj on 11/10/2017.
 */

public class ReaddedListPresenterImpl implements ReaddedListPresenter {

    ReaddedListView view;
    List<TransactionReAdded> list = new ArrayList<>();

    public ReaddedListPresenterImpl(ReaddedListView view) {
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
    public void addNew(TransactionReAdded transactionReAdded) {
        list.add(transactionReAdded);
        loadList();
    }

    @Override
    public List<TransactionReAdded> getList() {
        return list;
    }


    @Override
    public void delete(TransactionReAdded transactionReAdded) {
        list.remove(transactionReAdded);
    }
}
