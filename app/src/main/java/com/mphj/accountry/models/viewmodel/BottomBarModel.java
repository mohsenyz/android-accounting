package com.mphj.accountry.models.viewmodel;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;

/**
 * Created by mphj on 11/22/17.
 */


/**
 * This model is for bottom bar items
 * I tried to make it as flexible as possible
 * It supports showing fragments and urls
 */
public class BottomBarModel {

    public int bitmapResourceId = -1;
    public Bitmap bitmap = null;
    public String tabTitle = null;
    public String selectedTabTitle = null;
    public int selectedTabTitleColor = -1;
    public Fragment fragment = null;
    public String pageUrl = null;

    public BottomBarModel(int bitmapResourceId, String tabTitle, Fragment fragment) {
        this.bitmapResourceId = bitmapResourceId;
        this.tabTitle = tabTitle;
        this.fragment = fragment;
    }


    public BottomBarModel(int bitmapResourceId, String tabTitle, String pageUrl) {
        this.bitmapResourceId = bitmapResourceId;
        this.tabTitle = tabTitle;
        this.pageUrl = pageUrl;
    }
}
