package com.hfad.avc.data.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.hfad.avc.Util.Converter
import java.util.*
import kotlin.collections.ArrayList


@Entity
data class Contact(
   @PrimaryKey
   private var id: String = "",
   private var name: String = "",
   private var phoneList: ArrayList<String> = ArrayList(),
   private var uriThumbnail: String = "",
   private var uriFull: String = "",
   private var favorite: Boolean = false,
) : BaseObservable() {

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
   fun getPhoneList(): ArrayList<String> {
      return phoneList
   }

   fun setPhoneList(phoneList: ArrayList<String>) {
      this.phoneList = phoneList
      notifyPropertyChanged(BR.phoneList)
   }

   fun addToPhoneList(phone: String) {
      try {
         this.phoneList.add(phone)
      } catch (e: Exception) {
      }

      notifyPropertyChanged(BR.phoneList)
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

   fun clear() {
      id = ""
      name = ""
      phoneList.clear()
      uriThumbnail = ""
      uriFull = ""
      favorite = false
   }

}
