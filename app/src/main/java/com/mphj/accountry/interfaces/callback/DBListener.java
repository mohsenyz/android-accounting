package com.mphj.accountry.interfaces.callback;

/**
 * Created by mphj on 11/22/17.
 */

public interface DBListener {

    interface OnSave<T>{
        void onSave(T object);
    }

}
