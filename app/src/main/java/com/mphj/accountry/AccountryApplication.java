package com.mphj.accountry;

import android.app.Application;
import android.content.Context;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by mphj on 10/14/2017.
 */

public class AccountryApplication extends Application{

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/iran.ttf")
                .build()
        );
        context = this;
    }


    public static Context context(){
        if (context == null)
            throw new NullPointerException("Context is null");
        return context;
    }
}
