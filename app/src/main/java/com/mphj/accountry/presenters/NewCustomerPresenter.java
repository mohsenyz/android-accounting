package com.mphj.accountry.presenters;

/**
 * Created by mphj on 10/20/2017.
 */


@Deprecated
public interface NewCustomerPresenter extends BasePresenter {
    void createCustomer(String name, String phone);
}