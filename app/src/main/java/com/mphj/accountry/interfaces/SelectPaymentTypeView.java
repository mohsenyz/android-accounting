package com.mphj.accountry.interfaces;

import com.mphj.accountry.models.db.Check;

/**
 * Created by mphj on 11/20/17.
 */

public interface SelectPaymentTypeView {
    void showCheckInfo();
    void hideCheckInfo();
    void finishActivity(Check check, int paymentType);
}
