package com.mphj.accountry.presenters;

import com.mphj.accountry.interfaces.SelectPaymentTypeView;
import com.mphj.accountry.models.db.Check;
import com.mphj.accountry.models.db.Transaction;

/**
 * Created by mphj on 11/20/17.
 */

public class SelectPaymentTypePresenterImpl implements SelectPaymentTypePresenter {

    SelectPaymentTypeView view;

    public SelectPaymentTypePresenterImpl(SelectPaymentTypeView view) {
        this.view = view;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void submitResult(int paymentType, String serial, String price, long dueDate, String bank, String description) {
        if (paymentType == Transaction.PAYMENT_CHECK) {
            Check check = new Check();
            check.setSerial(serial);
            check.setPrice(Integer.parseInt(price));
            check.setDueDate(dueDate);
            check.setBank(bank);
            check.setDescription(description);
            view.finishActivity(check, paymentType);
        } else {
            view.finishActivity(null, paymentType);
        }
    }
}
