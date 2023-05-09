package com.lastaurus.automatic_congratulations.Util

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.text.format.DateUtils
import java.util.Calendar

sealed class TimePickerDialogCreator(val context: Context, val function: (Calendar) -> Boolean) {
   var dateAndTime = Calendar.getInstance()

   class TimeDialog(context: Context, function: (Calendar) -> Boolean) :
      TimePickerDialogCreator(context, function) {
      fun create() {

         val onTimeSetListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            dateAndTime[Calendar.HOUR_OF_DAY] = hourOfDay
            dateAndTime[Calendar.MINUTE] = minute
            DateUtils.formatDateTime(
               context,
               dateAndTime.timeInMillis,
               DateUtils.FORMAT_SHOW_WEEKDAY or DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR
                     or DateUtils.FORMAT_SHOW_TIME
            )
            function.invoke(dateAndTime)
         }
         TimePickerDialog(
            context, onTimeSetListener,
            dateAndTime.get(Calendar.HOUR_OF_DAY),
            dateAndTime.get(Calendar.MINUTE), true
         ).show()
      }
   }

   class DateDialog(context: Context, function: (Calendar) -> Boolean) :
      TimePickerDialogCreator(context, function) {
      fun create() {
         val onDateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
               dateAndTime[Calendar.YEAR] = year
               dateAndTime[Calendar.MONTH] = monthOfYear
               dateAndTime[Calendar.DAY_OF_MONTH] = dayOfMonth
               DateUtils.formatDateTime(
                  context,
                  dateAndTime.timeInMillis,
                  DateUtils.FORMAT_SHOW_WEEKDAY or DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR
                        or DateUtils.FORMAT_SHOW_TIME
               )
               function.invoke(dateAndTime)
            }
         DatePickerDialog(
            context, onDateSetListener,
            dateAndTime.get(Calendar.YEAR),
            dateAndTime.get(Calendar.MONTH),
            dateAndTime.get(Calendar.DAY_OF_MONTH)
         ).show()
      }
   }
}