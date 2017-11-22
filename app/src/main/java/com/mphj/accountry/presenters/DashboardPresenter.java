package com.mphj.accountry.presenters;

/**
 * Created by mphj on 10/20/2017.
 */

/**
 * @TODO Changing dashboard presenter ( Dashboard presenter is more than i wrote )
 * @deprecated Presenter should be placed in mvp package
 * Dashboard activity needs presenter, just to provide bottom tabs item
 * Also we may need to get notifications from server or etc.
 */

@Deprecated
public interface DashboardPresenter extends BasePresenter {
    void onPageChanged(int position);
    void onFabClick();
}
