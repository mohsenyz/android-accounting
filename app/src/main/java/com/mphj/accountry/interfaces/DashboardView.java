package com.mphj.accountry.interfaces;

/**
 * Created by mphj on 10/20/2017.
 */

public interface DashboardView {
    void startProgress();
    void endProgress();
    void showFab();
    void hideFab();
    void showNewProductActivity();
    void showNewStorageActivity();
    void showNewTransactionActivity();
}
