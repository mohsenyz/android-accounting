package com.mphj.accountry.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.mphj.accountry.models.LoginModel;

/**
 * Created by mphj on 10/15/2017.
 */

public class Auth {

    private static final String DB_NAME = "account";

    public static void login(LoginModel loginModel, Context context){
        try{
            SharedPreferences.Editor editor = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE).edit();
            editor.putString("account", GsonHelper.toJson(loginModel));
            editor.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void logout(Context context){
        try{
            SharedPreferences.Editor editor = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE).edit();
            editor.remove("account");
            editor.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static boolean isLoggedIn(Context context){
        try{
            SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
            return sharedPreferences.contains("account");
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


    public static LoginModel getInfo(Context context){
        if (isLoggedIn(context)) {
            try {
                SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
                String accountJson = sharedPreferences.getString("account", null);
                return GsonHelper.fromJson(accountJson, LoginModel.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
