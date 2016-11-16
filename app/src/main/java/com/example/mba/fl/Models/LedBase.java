package com.example.mba.fl.Models;

import com.example.mba.fl.Exceptions.CameraInUseException;

/**
 * Created by mba on 11/13/2016.
 */
public abstract class LedBase {
    public abstract void lampOn() throws CameraInUseException;
    public abstract void lampOff();
    public abstract void release();
}
