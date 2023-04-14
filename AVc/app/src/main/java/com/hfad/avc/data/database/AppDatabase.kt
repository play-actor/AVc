package com.lastaurus.avc.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lastaurus.avc.data.model.Contact
import com.lastaurus.avc.data.model.Template

@Database(entities = [Contact::class, Template::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
   abstract fun contactDao(): ContactDao

   abstract fun templateDao(): TemplateDao
}
