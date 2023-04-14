package com.lastaurus.avc.dagger.module


import android.content.Context
import androidx.room.Room
import com.lastaurus.avc.AvcApplication.Companion.INSTANCE
import com.lastaurus.avc.data.database.AppDatabase
import com.lastaurus.avc.managers.DBManager
import dagger.Module
import dagger.Provides
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Module
class HelperModule constructor(
   var database: AppDatabase? = null,
) {



   @JvmName("getDatabase1")
   @Provides
   @Singleton
   fun getDatabase(): AppDatabase {
      if (database == null) {
         //Избавиться от контакста в виде INSTANCE
         database =
            Room.databaseBuilder(INSTANCE!!, AppDatabase::class.java, "database_db")
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