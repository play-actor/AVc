package com.lastaurus.automatic_congratulations.Util

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
            congratulation?.setDateTimeFuture(dateTimeCongratulation)
         } else {
            if (congratulation?.getPeriodic() == true) {
               val localDateTime = generateDateTime(congratulationTime)
               congratulation.setDateTimeFuture(
                  ZonedDateTime.of(localDateTime, ZoneId.systemDefault()).toInstant().toEpochMilli()
               )
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