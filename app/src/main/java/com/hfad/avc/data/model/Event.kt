package com.hfad.avc.data.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.hfad.avc.Util.Converter
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import androidx.databinding.library.baseAdapters.BR

@Entity
data class Event(
   @PrimaryKey
   private var id: String = "",
   private var listIDWorker: ArrayList<String> = ArrayList(),
   private var date: Long = 0,
   private var date_string: String = "",
   private var time: Long = 0,
   private var time_string: String = "",
   private var worked: Boolean = false
) : BaseObservable() {

   companion object {
      private val FORMATTER_DATE: DateTimeFormatter = DateTimeFormat.forPattern("dd.MM.yyyy")
      private val FORMATTER_TIME: DateTimeFormatter = DateTimeFormat.forPattern("HH:mm")
   }

   @Bindable
   fun setId(id: String) {
      this.id = id
      notifyPropertyChanged(BR.id)
   }

   @Bindable
   fun getId(): String {
      return id
   }

   @Bindable
   fun getListIDWorker(): ArrayList<String> {
      return listIDWorker
   }

   @Bindable
   fun setListIDWorker(lisdIDWorker: ArrayList<String>) {
      this.listIDWorker = lisdIDWorker
      notifyPropertyChanged(BR.listIDWorker)
   }

   @Bindable
   fun getDate(): Long {
      return date
   }

   @Bindable
   fun setDate(date: Long) {
      this.date = date
      setDate_string(FORMATTER_DATE.print(date))
      notifyPropertyChanged(BR.date)
   }

   @Bindable
   fun getTime(): Long {
      return date
   }

   @Bindable
   fun setTime(time: Long) {
      this.time = time
      setTime_string(FORMATTER_DATE.print(time))
   }

   @Bindable
   fun getDate_string(): String {
      return date_string
   }

   @Bindable
   fun setDate_string(date_string: String) {
      this.date_string = date_string
      notifyPropertyChanged(BR.date_string)
   }

   @Bindable
   fun getTime_string(): String {
      return time_string
   }

   @Bindable
   fun setTime_string(time_string: String) {
      this.time_string = time_string
      notifyPropertyChanged(BR.time_string)
   }

   @Bindable
   fun getWorked(): Boolean {
      return worked
   }

   @Bindable
   fun setWorked(worked: Boolean) {
      this.worked = worked
      notifyPropertyChanged(BR.worked)
   }
}