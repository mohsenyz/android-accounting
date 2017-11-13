package com.mphj.accountry.presenters;

import com.mphj.accountry.models.db.Customer;

/**
 * Created by mphj on 10/23/2017.
 */

public interface D_CustomerSettingPresenter extends BasePresenter {
    void loadList(Customer customer);
}
