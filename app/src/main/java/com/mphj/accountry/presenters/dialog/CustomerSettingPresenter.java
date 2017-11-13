package com.mphj.accountry.presenters.dialog;

import com.mphj.accountry.models.db.Customer;
import com.mphj.accountry.presenters.BasePresenter;

/**
 * Created by mphj on 10/23/2017.
 */

public interface CustomerSettingPresenter extends BasePresenter {
    void loadList(Customer customer);
}
