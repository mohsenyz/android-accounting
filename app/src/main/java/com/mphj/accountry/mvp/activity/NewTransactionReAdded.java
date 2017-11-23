package com.mphj.accountry.mvp.activity;

import com.mphj.accountry.interfaces.CommonView;
import com.mphj.accountry.models.db.TransactionReAdded;
import com.mphj.accountry.mvp.Mvp;

/**
 * Created by mphj on 11/23/17.
 */

public interface NewTransactionReAdded {

    interface View extends
            Mvp.View,
            CommonView.ResultReturner<TransactionReAdded>{
        void invalidDescription();
        void invalidPrice();
    }

}
