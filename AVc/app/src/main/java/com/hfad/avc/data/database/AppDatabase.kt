package com.hfad.avc.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hfad.avc.data.model.Contact
import com.hfad.avc.data.model.Template

@Database(entities = [Contact::class, Template::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
   abstract fun contactDao(): ContactDao

   abstract fun templateDao(): TemplateDao
}
