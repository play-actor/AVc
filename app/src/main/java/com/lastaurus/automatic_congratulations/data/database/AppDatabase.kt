package com.lastaurus.automatic_congratulations.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.lastaurus.automatic_congratulations.data.model.Congratulation
import com.lastaurus.automatic_congratulations.data.model.Contact
import com.lastaurus.automatic_congratulations.data.model.Template
import com.lastaurus.automatic_congratulations.util.Converter

@Database(entities = [Contact::class, Template::class, Congratulation::class], version = 1)
@TypeConverters(Converter::class)
abstract class AppDatabase : RoomDatabase() {
   abstract fun contactDao(): ContactDao

   abstract fun templateDao(): TemplateDao

   abstract fun congratulationsDao(): CongratulationsDao
}
