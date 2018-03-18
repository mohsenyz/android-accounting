package com.mphj.wimagepicker;

import android.content.Context;

import pl.aprilapps.easyphotopicker.EasyImage;

/**
 * Created by mphj on 12/20/17.
 */

public class WImagePicker {

    public static final String IMAGE_FOLDER_NAME = "WImagePicker";

    public static void init(Context context, String imageFolderName) {
        EasyImage.configuration(context)
                .setImagesFolderName(imageFolderName);
    }


    public static void init(Context context) {
        init(context, IMAGE_FOLDER_NAME);
    }

}
