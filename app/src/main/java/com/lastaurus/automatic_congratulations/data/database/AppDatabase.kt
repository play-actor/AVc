package com.lastaurus.automatic_congratulations.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lastaurus.automatic_congratulations.Util.Converter
import com.lastaurus.automatic_congratulations.data.model.Contact
import com.lastaurus.automatic_congratulations.data.model.EventCongratulations
import com.lastaurus.automatic_congratulations.data.model.Template

@Database(entities = [Contact::class, Template::class, EventCongratulations::class], version = 1)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
   abstract fun contactDao(): ContactDao

   abstract fun templateDao(): TemplateDao

   abstract fun eventDao(): EventCongratulationsDao
}
