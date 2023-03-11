package com.hfad.avc.Util

import androidx.room.TypeConverter
import java.util.*
import java.util.stream.Collectors

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