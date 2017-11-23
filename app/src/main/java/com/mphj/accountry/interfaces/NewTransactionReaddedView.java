package com.mphj.accountry.interfaces;

import com.mphj.accountry.models.db.TransactionReAdded;

/**
 * Created by mphj on 11/11/2017.
 */

public interface NewTransactionReaddedView {
    void invalidDescription();
    void invalidPrice();
    void finishActivity(TransactionReAdded result);
}
