package com.hfad.avc.data.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.hfad.avc.Util.Converter
import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import java.util.*


@Entity
@TypeConverters(Converter::class)
data class Contact(
   @PrimaryKey
   private var id: String = "",
   private var name: String = "",
   private var phone: String = "",
   private var listIDWorker: List<String> = emptyList(),
   private var date_congratulations: Long = 0,
   private var date_congratulationsString: String = "",
   private var uriThumbnail: String = "",
   private var uriFull: String = "",
   private var favorite: Boolean = false,
   private var worked: Boolean = false,
) : BaseObservable() {

   companion object {
      private val FORMATTER: DateTimeFormatter = DateTimeFormat.forPattern("dd.MM.yyyy, HH:mm")
   }

   @Bindable
   fun getListIDWorker(): List<String> {
      return listIDWorker
   }

   fun setListIDWorker(lisdIDWorker: List<String>) {
      this.listIDWorker = lisdIDWorker
      notifyPropertyChanged(BR.listIDWorker)
   }

   @Bindable
   fun getUriThumbnail(): String {
      return uriThumbnail
   }

   fun setUriThumbnail(uriThumbnail: String) {
      this.uriThumbnail = uriThumbnail
      notifyPropertyChanged(BR.uriThumbnail)
   }

   @Bindable
   fun getUriFull(): String {
      return uriFull
   }

   fun setUriFull(uriFull: String) {
      this.uriFull = uriFull
      notifyPropertyChanged(BR.uriFull)
   }

   @Bindable
   fun getName(): String {
      return name
   }

   fun setId(id: String) {
      this.id = id
      notifyPropertyChanged(BR.id)
   }

   fun setName(name: String) {
      this.name = name
      notifyPropertyChanged(BR.name)
   }

   @Bindable
   fun getPhone(): String {
      return phone
   }

   fun setPhone(phone: String) {
      this.phone = phone
      notifyPropertyChanged(BR.phone)
   }

   @Bindable
   fun getId(): String {
      return id
   }

   @Bindable
   fun getFavorite(): Boolean {
      return favorite
   }

   fun setFavorite(favorite: Boolean) {
      this.favorite = favorite
      notifyPropertyChanged(BR.favorite)
   }

   @Bindable
   fun getWorked(): Boolean {
      return worked
   }

   fun setWorked(worked: Boolean) {
      this.worked = worked
      notifyPropertyChanged(BR.worked)
   }

   @Bindable
   fun getDate_congratulations(): Long {
      return date_congratulations
   }

   fun setDate_congratulations(date_congratulations: Long) {
      this.date_congratulations = date_congratulations
      date_congratulationsString = FORMATTER.print(date_congratulations)
   }

   @Bindable
   fun getDate_congratulationsString(): String? {
      return date_congratulationsString
   }

   fun setDate_congratulationsString(date: String) {
      date_congratulationsString = date
      notifyPropertyChanged(BR.date_congratulationsString)
   }

   override fun toString(): String {
      val someDate = DateTime(date_congratulations, DateTimeZone.UTC)
      return "id = " + id +
            ", Имя : " + name +
            ", Телефон : " + phone +
            ", Дата : " + someDate +
            ", Дата UTS : " + date_congratulations +
            ", В избранном: " + favorite +
            ", Поздравляется : " + worked
   }
}

