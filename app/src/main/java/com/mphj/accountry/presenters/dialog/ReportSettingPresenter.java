package com.mphj.accountry.presenters.dialog;

import com.mphj.accountry.models.db.Transaction;
import com.mphj.accountry.presenters.BasePresenter;

/**
 * Created by mphj on 11/20/17.
 */

public interface ReportSettingPresenter extends BasePresenter {
    void loadList(Transaction transaction);
}
