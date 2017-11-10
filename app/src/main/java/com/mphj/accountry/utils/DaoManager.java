package com.mphj.accountry.utils;

import android.content.Context;

import com.mphj.accountry.models.db.DaoMaster;
import com.mphj.accountry.models.db.DaoSession;

/**
 * Created by mphj on 11/7/2017.
 */

public class DaoManager {

    public static final String DB_NAME = "mphj.db";

    private static DaoSession daoSession;

    public static synchronized void init(Context c){
        if (daoSession == null)
            daoSession = new DaoMaster(
                    new DaoMaster.DevOpenHelper(c, DB_NAME).getWritableDb()).newSession();
    }


    public static DaoSession session(){
        return daoSession;
    }

}
