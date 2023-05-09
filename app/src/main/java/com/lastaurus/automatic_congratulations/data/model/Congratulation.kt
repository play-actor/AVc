package com.lastaurus.automatic_congratulations.data.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class Congratulation(
   @PrimaryKey
   private var id: Int = Int.MIN_VALUE,
   private var idWorker: UUID? = null,
   private var date: Long = 0,
   private var time: Long = 0,
   private var idContact: Int = Int.MIN_VALUE,
   private var idTemplate: Int = Int.MIN_VALUE,
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
   fun setIdTemplate(idTemplate: Int) {
      this.idTemplate = idTemplate
      notifyPropertyChanged(BR.idTemplate)
   }

   @Bindable
   fun getIdTemplate(): Int {
      return idTemplate
   }

   @Bindable
   fun getIdWorker(): UUID? {
      return idWorker
   }

   @Bindable
   fun setIdWorker(idWorker: UUID) {
      this.idWorker = idWorker
      notifyPropertyChanged(BR.idWorker)
   }

   @Bindable
   fun getDate(): Long {
      return date
   }

   @Bindable
   fun setDate(date: Long) {
      this.date = date
      notifyPropertyChanged(BR.date)
   }

   @Bindable
   fun getTime(): Long {
      return date
   }

   @Bindable
   fun setTime(time: Long) {
      this.time = time
      notifyPropertyChanged(BR.time)
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
}