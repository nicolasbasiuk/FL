package com.example.mba.fl.Dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.example.mba.fl.Interfaces.CameraInUseDialogListener;
import com.example.mba.fl.R;

/**
 * Created by mba on 11/13/2016.
 */
public class CameraInUseDialog extends DialogFragment {

    CameraInUseDialogListener mListener;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);

    try {
        mListener = (CameraInUseDialogListener) activity;
    } catch(ClassCastException ex){
        throw new ClassCastException(activity.toString()
                + " must implement CameraInUseDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_use_monitor)
                .setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        mListener.onDialogPositiveClick(CameraInUseDialog.this);
                    }
                });

        return builder.create();
    }
}
