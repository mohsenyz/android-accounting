package com.mphj.accountry.mvp.activity;

import com.mphj.accountry.interfaces.callback.JobCallback;
import com.mphj.accountry.models.db.Customer;
import com.mphj.accountry.mvp.Mvp;

/**
 * Created by mphj on 11/23/17.
 */

public interface NewCustomer {

    interface View extends
            Mvp.View,
            JobCallback{
        void invalidPhone();
        void invalidName();
    }

    interface Presenter extends
            Mvp.Presenter<View> {
        void createCustomer(String name, String phone);
    }

    interface Model {
        void newCustomer(Customer customer, JobCallback jobCallback);
    }

}
