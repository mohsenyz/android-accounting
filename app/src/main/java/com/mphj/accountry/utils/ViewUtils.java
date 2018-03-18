package com.mphj.accountry.utils;

import android.graphics.Rect;
import android.support.design.widget.Snackbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by mphj on 1/30/18.
 */

public class ViewUtils {

    public static boolean isInViewBound(View view, MotionEvent motionEvent) {
        Rect rect = new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        if (rect.contains(view.getLeft() + (int) motionEvent.getX(),
                view.getTop() + (int) motionEvent.getY()))
            return true;
        return false;
    }


    public static void prepareSnackbar(Snackbar snackbar, int textSize) {
        View view = ((ViewGroup) snackbar.getView()).getChildAt(0);
        if (view instanceof ViewGroup) {
            for (int b = 0; b < ((ViewGroup) view).getChildCount(); b++) {
                View nestedView = ((ViewGroup) view).getChildAt(b);
                if (nestedView instanceof TextView) {
                    ((TextView) nestedView).setTextSize(textSize);
                }
            }
        }
    }

}