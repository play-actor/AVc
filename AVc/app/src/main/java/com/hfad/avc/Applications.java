package com.hfad.avc;

import android.app.Application;

import androidx.room.Room;

import com.hfad.avc.interactor.ContactCompanent;
import com.hfad.avc.interactor.DaggerContactCompanent;
import com.hfad.avc.interactor.LoadDBInteractor;
import com.hfad.avc.interactor.SendIteractor;
import com.hfad.avc.ui.database.AppDatabase;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

public class Applications extends Application {

    public static Applications INSTANCE;
    private AppDatabase database;
    private HelperInteractors helperInteractors = null;
    public String TAG = "AVc";
    private Cicerone<Router> cicerone;
    ContactCompanent component;


    @Override
    public void onCreate() {
        this.helperInteractors = new HelperInteractors();
        INSTANCE = this;
        database = Room.databaseBuilder(this, AppDatabase.class, "database")
                .allowMainThreadQueries()
                .build();
        this.component = DaggerContactCompanent.create();
        super.onCreate();
        initCicerone();
        //Log.i(TAG, "Applications: ok");
    }

    private void initCicerone() {
        cicerone = Cicerone.create();
    }

    public NavigatorHolder getNavigatorHolder() {
        return cicerone.getNavigatorHolder();
    }

    public Router getRouter() {
        return cicerone.getRouter();
    }

    public static Applications getInstance() {
        return INSTANCE;
    }

    public AppDatabase getDatabase() {
        return database;
    }

    public HelperInteractors getHelperInteractors() {
        return helperInteractors;
    }

    public final class HelperInteractors {
        public LoadDBInteractor getContactInteractor() {
            return new LoadDBInteractor();
        }
        public SendIteractor getSendIteractor() {
            return new SendIteractor();
        }
    }

    public ContactCompanent getContactCompanent() {
        return component;
    }
}
