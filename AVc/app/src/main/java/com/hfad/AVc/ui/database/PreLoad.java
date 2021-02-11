package com.hfad.avc.ui.database;

import android.os.AsyncTask;
import android.util.Log;

import com.hfad.avc.Applications;

public class PreLoad extends AsyncTask<Integer, Void, Boolean> {
    public String TAG ="AVc";
    @Override
    protected Boolean doInBackground(Integer... integers) {
        Applications.INSTANCE.getAVcDatabaseHelper().loadDB();
        Log.w(TAG, "doInBackground: ok");
        return false;
    }
}