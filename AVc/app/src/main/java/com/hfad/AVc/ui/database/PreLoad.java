package com.hfad.AVc.ui.database;

import android.os.AsyncTask;

import com.hfad.AVc.Applications;

public class PreLoad extends AsyncTask<Integer, Void, Boolean> {
    @Override
    protected Boolean doInBackground(Integer... integers) {
        Applications.INSTANCE.getAVcDatabaseHelper().loadDB();
        return false;
    }
}