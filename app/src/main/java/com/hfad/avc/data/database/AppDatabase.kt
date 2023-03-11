package com.hfad.avc.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hfad.avc.Util.Converter
import com.hfad.avc.data.model.Contact
import com.hfad.avc.data.model.Event
import com.hfad.avc.data.model.Template

@Database(entities = [Contact::class, Template::class, Event::class], version = 1)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
   abstract fun contactDao(): ContactDao

   abstract fun templateDao(): TemplateDao

   abstract fun eventDao(): EventDao
}
