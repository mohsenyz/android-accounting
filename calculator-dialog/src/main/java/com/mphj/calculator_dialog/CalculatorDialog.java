package com.mphj.calculator_dialog;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.text.TextUtilsCompat;
import android.text.TextUtils;

/**
 * Created by mphj on 11/17/17.
 */

public class CalculatorDialog {
    public static void show(Activity activity) {
        activity.startActivity(new Intent(activity, MainActivity.class));
    }



    @Nullable
    public static String parseResult(Intent data, int responseCode) {
        if (responseCode == Activity.RESULT_OK) {
            return String.valueOf(data.getIntExtra("result", 0));
        }
        return null;
    }
}
