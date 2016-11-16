package com.example.mba.fl.Exceptions;

/**
 * Created by mba on 11/13/2016.
 */
public class CameraInUseException extends Exception {
    public CameraInUseException() { super(); }

    public CameraInUseException(String message) {
        super(message);
    }
}
