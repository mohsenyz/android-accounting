package com.mphj.accountry.utils;

/**
 * Created by mphj on 2/2/18.
 */

public class Checksum {

    public static String get(String value) {
        int stringLength = value.length();
        int sum = 0;
        for (int i = 0; i < stringLength; i++) {
            try {
                sum += Integer.parseInt(String.valueOf(value.charAt(i)));
            } catch (Exception e) {
                // error occurred
            }
        }
        return String.valueOf(sum);
    }

}
