package com.hfad.AVc;

import android.os.AsyncTask;

public class PreLoad extends AsyncTask<Integer, Void, Boolean> {
    @Override
    protected Boolean doInBackground(Integer... integers) {
        Applications.INSTANCE.getAVcDatabaseHelper().loadDB();
        return false;
    }
}