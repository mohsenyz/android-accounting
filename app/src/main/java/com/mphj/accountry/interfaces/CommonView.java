package com.mphj.accountry.interfaces;

/**
 * Created by mphj on 11/22/17.
 */

public interface CommonView {

    /**
     * Interface for every view that has loader, progressbar, spinner
     */
    interface Progressbar {
        void finishProgress();
        void startProgress();
    }


    /**
     * Interface for every view with a progressbar that support present
     * Like circular or linear progressbar
     */
    interface RateProgressbar extends Progressbar {
        void setProgress(int percent);
    }


    /**
     * An interface for every view that has only one reactive fab
     */
    interface ReactiveFab {
        void hideFab();
        void showFab();
    }

    interface ResultReturner<T>{
        void setResult(T obj, Object... etc);
    }

}
