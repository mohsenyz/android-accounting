package com.mphj.accountry.interfaces;

import android.graphics.Bitmap;

/**
 * Created by mphj on 10/20/2017.
 */

public interface NewProductView {
    void invalidName();
    void invalidSerial();
    void invalidPrice();
    void invalidOff();
    void setSerial(Bitmap bitmap);
    void setSerial(String serial);
    void finishActivity();
}
