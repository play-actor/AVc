package com.hfad.avc;

import android.app.Application;
import android.util.Log;

import com.hfad.avc.ui.database.AVcDatabaseHelper;

public class Applications extends Application {

    public static Applications INSTANCE = null;
    private com.hfad.avc.ui.database.AVcDatabaseHelper aVcDatabaseHelper = null;
    public String TAG ="AVc";

    @Override
    public void onCreate() {
        Applications.INSTANCE = this;
        this.aVcDatabaseHelper = new AVcDatabaseHelper(this);
        Log.i(TAG, "AVcDatabaseHelper: ok");
        super.onCreate();
        Log.i(TAG, "Applications: ok");

    }

    public AVcDatabaseHelper getAVcDatabaseHelper() {
        return aVcDatabaseHelper;
    }
}
