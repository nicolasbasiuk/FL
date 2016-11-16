package com.example.mba.fl.Models;

import android.hardware.Camera;

import com.example.mba.fl.Exceptions.CameraInUseException;

/**
 * Created by mba on 11/13/2016.
 */
public class LegacyLedManager extends LedBase {
    private android.hardware.Camera mCamera;

    public LegacyLedManager() throws CameraInUseException{
        try{
            mCamera = android.hardware.Camera.open();
        } catch (RuntimeException ex){
            throw new CameraInUseException("Camera is already in use by another application.");
        }
    }

    @Override
    public void lampOn() throws CameraInUseException {
        if (mCamera == null)
            try {
                mCamera = android.hardware.Camera.open();
            } catch (RuntimeException ex){
                throw new CameraInUseException("Camera is already in use by another application");
            }

        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        mCamera.setParameters(parameters);
        mCamera.startPreview();
    }

    @Override
    public void lampOff() {
        if (mCamera != null){
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            mCamera.setParameters(parameters);
            mCamera.stopPreview();
        }
    }

    @Override
    public void release() {
        if (mCamera != null){
            mCamera.release();
            mCamera = null;
        }
    }
}
