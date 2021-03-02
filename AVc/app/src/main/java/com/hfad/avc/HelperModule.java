package com.hfad.avc;

import androidx.room.Room;

import com.hfad.avc.ui.database.AppDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class HelperModule {
    AppDatabase database;

    @Provides
    @Singleton
    AppDatabase getDatabase() {
        //Избавиться от контакста в виде INSTANCE
        database = Room.databaseBuilder(Applications.INSTANCE, AppDatabase.class, "database")
                .allowMainThreadQueries()
                .build();
        return database;
    }

}
