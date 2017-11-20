package com.mphj.accountry.presenters.fragment;

import com.mphj.accountry.interfaces.fragment.ReportListView;
import com.mphj.accountry.models.db.Transaction;
import com.mphj.accountry.models.db.TransactionDao;
import com.mphj.accountry.utils.DaoManager;

/**
 * Created by mphj on 11/20/17.
 */

public class ReportListPresenterImpl implements ReportListPresenter {

    ReportListView view;

    public ReportListPresenterImpl(ReportListView view) {
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
        TransactionDao transactionDao = DaoManager.session().getTransactionDao();
        view.setAdapter(
                transactionDao.queryBuilder()
                    .where(TransactionDao.Properties.Type.eq(Transaction.TYPE_OUTGOING))
                    .listLazy()
        );
    }

    @Override
    public void loadList(long fromDate, long toDate) {
        TransactionDao transactionDao = DaoManager.session().getTransactionDao();
        view.setAdapter(
                transactionDao.queryBuilder()
                        .where(TransactionDao.Properties.Type.eq(Transaction.TYPE_OUTGOING))
                        .where(TransactionDao.Properties.CreatedAt.between(fromDate, toDate))
                        .listLazy()
        );
    }
}
