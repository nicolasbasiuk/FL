package com.example.mba.fl.Factories;

import com.example.mba.fl.Exceptions.CameraInUseException;
import com.example.mba.fl.Models.CurrentLedManager;
import com.example.mba.fl.Models.LedBase;
import com.example.mba.fl.Models.LegacyLedManager;
import com.example.mba.fl.Utils;

public class FlashFactory {
    private static final int newCameraApiLevel = 22;

    public LedBase createLed() throws CameraInUseException {
        if (Utils.getApiLevel() < newCameraApiLevel){
            return new LegacyLedManager();
        } else {
            return new CurrentLedManager();
        }
    }
}
