package com.mphj.accountry.mvp;

/**
 * Created by mphj on 11/22/17.
 */

public interface Mvp {

    interface Presenter<T> {
        void setView(T view);
        void start();
        void stop();
    }


    interface View{
        void attach();
        void detach();
    }

}

