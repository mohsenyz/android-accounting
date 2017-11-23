package com.mphj.accountry.interfaces.callback;

/**
 * Created by mphj on 11/23/17.
 */

public interface JobCallback {
    void onFailed(String msg, int type);
    void onDone();
}
