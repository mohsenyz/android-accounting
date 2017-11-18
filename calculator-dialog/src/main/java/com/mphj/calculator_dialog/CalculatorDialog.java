package com.mphj.calculator_dialog;

import android.app.Activity;
import android.content.Intent;

/**
 * Created by mphj on 11/17/17.
 */

public class CalculatorDialog {
    public static void show(Activity activity) {
        activity.startActivity(new Intent(activity, MainActivity.class));
    }
}
