package com.mphj.accountry.mvp.activity;

import android.support.annotation.Nullable;

import com.mphj.accountry.interfaces.CommonView;
import com.mphj.accountry.mvp.Mvp;

/**
 * Created by mphj on 11/23/17.
 */

public interface ExportType {

    interface View extends
            Mvp.View,
            CommonView.RateProgressbar{
        void onException(String msg, int type);
    }


    interface Presenter extends
            Mvp.Presenter<View> {
        String exportPdf(int transactionId);
        String exportXls(int transactionId);
    }


    interface Model {

    }
}
