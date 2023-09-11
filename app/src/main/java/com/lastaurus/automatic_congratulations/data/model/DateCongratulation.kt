package com.lastaurus.automatic_congratulations.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DateCongratulation(
   @PrimaryKey
   var id: Int = Int.MIN_VALUE,
   var type: String = "",
   var nameCongratulation: String = "",
   var dateTime: Long = 0,
) {
   fun clear() {
      id = Int.MIN_VALUE
      type = ""
      nameCongratulation = ""
      dateTime = 0
   }
}