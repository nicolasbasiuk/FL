package com.example.mba.fl;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.mba.fl.Dialogs.CameraInUseDialog;
import com.example.mba.fl.Exceptions.CameraInUseException;
import com.example.mba.fl.Factories.FlashFactory;
import com.example.mba.fl.Interfaces.CameraInUseDialogListener;
import com.example.mba.fl.Models.FlashManager;
import com.example.mba.fl.Models.FlashMode;
import com.example.mba.fl.Models.LedBase;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity
                implements CameraInUseDialogListener{

    private View mOuterView;
    private FlashManager mFlashManager;
    private FlashFactory mFlashFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        mOuterView = findViewById(R.id.OuterFrame);
        ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }

        mOuterView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        initFlashManager();
    }

    @Override
    public void onResume(){
        super.onResume();

        if (mFlashManager != null && mFlashManager.getFlashMode() == FlashMode.SCREEN){
            try {
                mFlashManager.turnOff(mOuterView);
                LedBase led = mFlashFactory.createLed();
                mFlashManager = new FlashManager(led, FlashMode.LED, getResources());
            } catch (CameraInUseException e) {
                showDialog(null);
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mFlashManager.release();
    }

    @Override
    public void onStop(){
        super.onStop();
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        //Use screen as flash
        mFlashManager = new FlashManager(null, FlashMode.SCREEN, getResources());
        mFlashManager.setFlashMode(FlashMode.SCREEN);
        try{
            mFlashManager.turnOn(mOuterView);
            mFlashManager.setIsSwitchedOn(true);
        } catch (CameraInUseException ex){
            ex.printStackTrace();
        }
    }

    public void showDialog(View view) {
        CameraInUseDialog dialog = new CameraInUseDialog();
        dialog.show(getFragmentManager(), "tag");
    }

    public void switchStatus(View view) throws CameraInUseException {
        //TODO change style
        if (mFlashManager.getIsSwitchedOn()){
            mFlashManager.turnOff(mOuterView);
        } else {
            mFlashManager.turnOn(mOuterView);
        }
        mFlashManager.setIsSwitchedOn(!mFlashManager.getIsSwitchedOn());
    }

    private void initFlashManager(){
        if (mFlashFactory == null){
            mFlashFactory = new FlashFactory();
            LedBase led;

            try {
                led = mFlashFactory.createLed();
                mFlashManager = new FlashManager(led, FlashMode.LED, getResources());
            } catch (CameraInUseException e) {
                showDialog(null);
                e.printStackTrace();
                return;
            }
        }
    }

    //endregion
}
