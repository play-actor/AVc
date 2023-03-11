package com.hfad.avc.dagger.module


import android.content.Context
import androidx.room.Room
import com.hfad.avc.data.database.AppDatabase
import com.hfad.avc.managers.DBManager
import dagger.Module
import dagger.Provides
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Module
class HelperModule constructor(
   var database: AppDatabase? = null
) {

   @JvmName("getDatabase1")
   @Provides
   @Singleton
   fun getDatabase(context: Context): AppDatabase {
      if (database == null) {
         //Избавиться от контакста в виде INSTANCE
         database =
            Room.databaseBuilder(context, AppDatabase::class.java, "database_db1")
               .allowMainThreadQueries()
               .fallbackToDestructiveMigration()
               .build()
      }
      return database as AppDatabase
   }

   @Provides
   @Singleton
   fun getDBManager(): DBManager {
      return DBManager()
   }


}