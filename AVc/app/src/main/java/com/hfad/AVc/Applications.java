package com.hfad.AVc;

import android.app.Application;
import android.util.Log;

import com.hfad.AVc.ui.database.AVcDatabaseHelper;

public class Applications extends Application {

    public static Applications INSTANCE = null;
    private com.hfad.AVc.ui.database.AVcDatabaseHelper AVcDatabaseHelper = null;
    public String TAG ="AVc";

    @Override
    public void onCreate() {
        Applications.INSTANCE = this;
        this.AVcDatabaseHelper = new AVcDatabaseHelper(this);
        super.onCreate();
        Log.w(TAG, "Создание Applications: ok");
    }

    public AVcDatabaseHelper getAVcDatabaseHelper() {
        return AVcDatabaseHelper;
    }
}
