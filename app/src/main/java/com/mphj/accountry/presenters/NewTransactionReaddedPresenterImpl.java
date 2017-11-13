package com.mphj.accountry.presenters;

import android.text.TextUtils;

import com.mphj.accountry.interfaces.NewTransactionReaddedView;
import com.mphj.accountry.models.db.TransactionReadded;
import com.mphj.accountry.utils.DoubleValidator;

/**
 * Created by mphj on 11/11/2017.
 */

public class NewTransactionReaddedPresenterImpl implements NewTransactionReaddedPresenter {

    NewTransactionReaddedView view;

    public NewTransactionReaddedPresenterImpl(NewTransactionReaddedView view) {
        this.view = view;
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void sendResult(String description, String price, int type) {

        if (TextUtils.isEmpty(description)) {
            view.invalidDescription();
            return;
        }


        if (!DoubleValidator.isValid(price)) {
            view.invalidPrice();
            return;
        }

        TransactionReadded transactionReadded = new TransactionReadded();
        transactionReadded.setDescription(description);
        transactionReadded.setPrice(Integer.parseInt(price));
        transactionReadded.setType(type);
        view.finishActivity(transactionReadded);
    }
}
