package com.hfad.avc.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.hfad.avc.interactor.SendIteractor;

public class CustomDialogFragment extends DialogFragment {
    SendIteractor interactor;
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        return builder
                .setTitle("Автопоздравления")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Сегодня нужно поздравить ")
                .setPositiveButton("OK", null)
                .setNegativeButton("Отмена", null)
                .create();
    }
/*                this.interactor = Applications.INSTANCE.getHelperInteractors().getSendIteractor();
            this.interactor.smsSend(Applications.INSTANCE,inputData.getString("Phone"),inputData.getString("TextTemplate"));*/
}
