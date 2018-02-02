package com.mphj.accountry.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by mphj on 2/1/18.
 */

public class GsonHelper {

    private static Gson gson;

    static {
        gson = new GsonBuilder()
                .enableComplexMapKeySerialization()
                .serializeNulls()
                .create();
    }

    public static String toJson(Object o) {
        return gson.toJson(o);
    }


    public static <T> T fromJson(String json, Class<T> tClass) {
        return gson.fromJson(json, tClass);
    }

}
