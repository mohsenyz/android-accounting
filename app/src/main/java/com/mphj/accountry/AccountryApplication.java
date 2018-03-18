package com.mphj.accountry;

import android.app.Application;
import android.content.Context;

import com.mphj.accountry.utils.DaoManager;
import com.mphj.accountry.utils.TypefaceUtils;
import com.mphj.wimagepicker.WImagePicker;
import com.squareup.leakcanary.LeakCanary;

import es.dmoral.toasty.Toasty;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by mphj on 10/14/2017.
 */

public class AccountryApplication extends Application{

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/iran.ttf")
                .build()
        );
        DaoManager.init(this);
        Toasty.Config.getInstance()
                .setToastTypeface(TypefaceUtils.def())
                .setTextSize(14)
                .apply();
        WImagePicker.init(this);
    }


    public static Context context(){
        if (context == null)
            throw new NullPointerException("Context is null");
        return context;
    }
}
