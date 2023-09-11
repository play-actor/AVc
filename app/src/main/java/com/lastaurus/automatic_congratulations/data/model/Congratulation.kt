package com.lastaurus.automatic_congratulations.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Congratulation(
   @PrimaryKey
   var id: Int = Int.MIN_VALUE,
   var dateTime: Long = 0,
   var dateTimeFuture: Long = 0,
   var idContact: Int = Int.MIN_VALUE,
   var phone: String = "",
   var idTemplate: Int = Int.MIN_VALUE,
   var periodic: Boolean = false,
   var active: Boolean = false,
)