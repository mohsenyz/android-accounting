package com.mphj.accountry.presenters;

import android.support.annotation.StringDef;

/**
 * Created by mphj on 11/20/17.
 */

@Deprecated
public interface SelectPaymentTypePresenter extends BasePresenter {
    void submitResult(int paymentType, String serial, String price, long dueDate, String bank, String description);
}
