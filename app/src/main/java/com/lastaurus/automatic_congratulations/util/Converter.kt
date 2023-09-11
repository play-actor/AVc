package com.lastaurus.automatic_congratulations.util

import androidx.room.TypeConverter

class Converter {
   @TypeConverter
   fun fromListIDWorker(list: ArrayList<String>): String {
      return list.joinToString(separator = ",")
   }

   @TypeConverter
   fun toListIDWorker(data: String): ArrayList<String> {
      return (data.split(",").toTypedArray()).toCollection(ArrayList())
   }
}