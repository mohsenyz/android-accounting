package com.mphj.accountry.activity.utils;

import android.content.Context;
import android.provider.Settings;

/**
 * Created by mphj on 10/15/2017.
 */

public class DeviceUtils {

    public static String getUnique(Context context){
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

}
