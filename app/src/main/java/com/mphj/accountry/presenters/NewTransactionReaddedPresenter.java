package com.mphj.accountry.presenters;

/**
 * Created by mphj on 11/11/2017.
 */

@Deprecated
public interface NewTransactionReaddedPresenter extends BasePresenter {
    void sendResult(String description, String price, int type);
}
