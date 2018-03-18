package com.mphj.accountry.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import com.mphj.accountry.AccountryApplication;

/**
 * Created by mphj on 10/15/2017.
 */

public class DeviceUtils {

    @SuppressLint("HardwareIds")
    public static String getUnique(Context context){
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public static String getDeviceId(Context context) {
        try {
            TelephonyManager tel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tel.getDeviceId();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String getDeviceIdChecksum(Context context) {
        return Checksum.get(getDeviceId(context));
    }


    public static long getFreeDisk() {
        StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
        long free = (statFs.getAvailableBlocks() * (long) statFs.getBlockSize());
        return free;
    }


    public static int getFreeMemory(){
        int max = (int) (Runtime.getRuntime().maxMemory() / 1024);
        return max;
    }

    public static int getScreenWidth(){
        DisplayMetrics displayMetrics = AccountryApplication.context().getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

}
