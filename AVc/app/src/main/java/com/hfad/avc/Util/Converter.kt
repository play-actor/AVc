package com.hfad.avc.Util

import androidx.room.TypeConverter
import java.util.*
import java.util.stream.Collectors

class Converter {
   @TypeConverter
   fun fromListIDWorker(list: List<String>): String {
      return list.stream().collect(Collectors.joining(","))
   }

   @TypeConverter
   fun toListIDWorker(data: String): List<String> {
      return Arrays.asList(*data.split(",").toTypedArray())
   }
}