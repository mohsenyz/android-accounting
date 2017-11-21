package com.mphj.accountry.utils;

import android.graphics.Typeface;

import com.mphj.accountry.AccountryApplication;

/**
 * Created by mphj on 11/19/17.
 */

public class TypefaceUtils {
    public static Typeface def() {
        return Typeface.createFromAsset(AccountryApplication.context().getAssets(), "fonts/iran.ttf");
    }
}
