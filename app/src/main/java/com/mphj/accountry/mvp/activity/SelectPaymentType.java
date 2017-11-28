package com.mphj.accountry.mvp.activity;

import com.mphj.accountry.interfaces.CommonView;
import com.mphj.accountry.models.db.Check;
import com.mphj.accountry.mvp.Mvp;

/**
 * Created by mphj on 11/28/17.
 */

public interface SelectPaymentType {

    interface View extends Mvp.View,
            CommonView.ResultReturner<Check>{
        void showCheckInfo();
        void hideCheckInfo();
    }

    interface Presenter extends Mvp.Presenter<View> {
        void submitResult(int paymentType,
                          String serial,
                          String price,
                          long dueDate,
                          String bank,
                          String description);
    }

    interface Model {
        // No need to database or server!
    }

}
