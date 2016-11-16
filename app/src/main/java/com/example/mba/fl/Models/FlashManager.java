package com.example.mba.fl.Models;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.view.View;

import com.example.mba.fl.Exceptions.CameraInUseException;
import com.example.mba.fl.R;
import com.example.mba.fl.Utils;

public class FlashManager {

    private boolean isSwitchedOn;
    private FlashMode mFlashMode;
    private LedBase mLedFlash;
    private Resources mResources;
    //private ScreenBase mScreenFlash;

    public FlashManager(LedBase led, FlashMode mode, Resources resources){
        this.mLedFlash = led;
        this.mFlashMode = mode;
        this.mResources = resources;
    }

    public boolean getIsSwitchedOn() { return isSwitchedOn; }
    public void setIsSwitchedOn(boolean switchedOn) { this.isSwitchedOn = switchedOn; }

    public FlashMode getFlashMode(){ return mFlashMode; }
    public void setFlashMode(FlashMode flashMode){this.mFlashMode = flashMode; }

    public void turnOn(View view) throws CameraInUseException{
        if (mFlashMode == FlashMode.LED){
            mLedFlash.lampOn();
        } else if (mFlashMode == FlashMode.SCREEN) {
            view.setBackgroundColor(Color.argb(255, 255, 255, 255));
            //mScreenFlash.screenOn();
        }
    }
    public void turnOff(View view){
        if (mFlashMode == FlashMode.LED){
            mLedFlash.lampOff();
        } else if (mFlashMode == FlashMode.SCREEN){
            if (Utils.getApiLevel() < 16){
                setBackgroundDrawable(view);
            } else {
                setBackground(view);
            }
        }
    }

    public void release(){
        if (isSwitchedOn && getFlashMode() == FlashMode.LED)
            mLedFlash.lampOff();

        if (mLedFlash != null)
            mLedFlash.release();
    }

    @TargetApi(15)
    private void setBackgroundDrawable(View view){
        view.setBackgroundDrawable(mResources.getDrawable(R.drawable.flb1_off));
    }

    @TargetApi(16)
    private void setBackground(View view){
        view.setBackground(mResources.getDrawable(R.drawable.flb1_off));
    }
}
