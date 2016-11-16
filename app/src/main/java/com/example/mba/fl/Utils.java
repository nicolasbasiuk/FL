package com.example.mba.fl;

import android.content.res.Resources;
import android.os.Build;

/**
 * Created by mba on 11/13/2016.
 */
public final class Utils {

    //getting device api level
    public static int getApiLevel(){
        return Build.VERSION.SDK_INT;
    }
}
