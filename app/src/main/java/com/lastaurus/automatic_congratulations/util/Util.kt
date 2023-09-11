package com.lastaurus.automatic_congratulations.util

import com.lastaurus.automatic_congratulations.data.model.Congratulation
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

class Util {
   companion object {
      fun getDateTime(congratulation: Congratulation?, dateTimeCongratulation: Long) {
         val currentDateTime = LocalDateTime.now()
         val congratulationTime =
            Instant.ofEpochMilli(dateTimeCongratulation)
               .atZone(ZoneId.of(ZoneId.systemDefault().id))
               .toLocalDateTime()
         if (congratulationTime.isAfter(currentDateTime)) {
            congratulation?.dateTimeFuture = dateTimeCongratulation
         } else {
            if (congratulation?.periodic == true) {
               val localDateTime = generateDateTime(congratulationTime)
               congratulation.dateTimeFuture =
                  ZonedDateTime.of(localDateTime, ZoneId.systemDefault()).toInstant().toEpochMilli()
            }
         }
      }


      private fun generateDateTime(
         time: LocalDateTime,
      ): LocalDateTime {
         val futureDateTime: LocalDateTime = time.plusYears(1)
         return if (futureDateTime.isAfter(time)) {
            futureDateTime
         } else {
            generateDateTime(futureDateTime)
         }
      }
   }
}

fun <T> T.isNull(): Boolean {
   return this == null
}