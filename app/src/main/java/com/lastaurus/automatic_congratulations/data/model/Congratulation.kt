package com.lastaurus.automatic_congratulations.data.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Congratulation(
   @PrimaryKey
   private var id: Int = Int.MIN_VALUE,
   private var dateTime: Long = 0,
   private var dateTimeFuture: Long = 0,
   private var idContact: Int = Int.MIN_VALUE,
   private var phone: String = "",
   private var idTemplate: Int = Int.MIN_VALUE,
   private var periodic: Boolean = false,
   private var active: Boolean = false,
) : BaseObservable() {

   @Bindable
   fun setId(id: Int) {
      this.id = id
      notifyPropertyChanged(BR.id)
   }

   @Bindable
   fun getId(): Int {
      return id
   }

   @Bindable
   fun setIdContact(idContact: Int) {
      this.idContact = idContact
      notifyPropertyChanged(BR.idContact)
   }

   @Bindable
   fun getIdContact(): Int {
      return idContact
   }

   @Bindable
   fun setPhone(text: String) {
      this.phone = text
      notifyPropertyChanged(BR.phone)
   }

   @Bindable
   fun getPhone(): String {
      return this.phone
   }

   @Bindable
   fun setIdTemplate(idTemplate: Int) {
      this.idTemplate = idTemplate
      notifyPropertyChanged(BR.idTemplate)
   }

   @Bindable
   fun getIdTemplate(): Int {
      return idTemplate
   }

   @Bindable
   fun getDateTime(): Long {
      return dateTime
   }

   @Bindable
   fun setDateTime(dateTime: Long) {
      this.dateTime = dateTime
      notifyPropertyChanged(BR.dateTime)
   }

   @Bindable
   fun setDateTimeFuture(dateTimeFuture: Long) {
      this.dateTimeFuture = dateTimeFuture
      notifyPropertyChanged(BR.dateTimeFuture)
   }

   @Bindable
   fun getDateTimeFuture(): Long {
      return this.dateTimeFuture
   }

   @Bindable
   fun getActive(): Boolean {
      return active
   }

   @Bindable
   fun setActive(active: Boolean) {
      this.active = active
      notifyPropertyChanged(BR.active)
   }

   @Bindable
   fun getPeriodic(): Boolean {
      return periodic
   }

   @Bindable
   fun setPeriodic(periodic: Boolean) {
      this.periodic = periodic
      notifyPropertyChanged(BR.periodic)
   }
}