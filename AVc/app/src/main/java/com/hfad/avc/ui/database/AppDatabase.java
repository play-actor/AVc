package com.hfad.avc.ui.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Contact.class,Template.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ContactDao contactDao();
    public abstract TemplateDao templateDao();
}
