package com.lastaurus.automatic_congratulations.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Template(
   @PrimaryKey
   var id: Int = Int.MIN_VALUE,
   var textTemplate: String = "",
   var favorite: Boolean = false,
) {
   fun clear() {
      id = Int.MIN_VALUE
      textTemplate = ""
      favorite = false
   }
}