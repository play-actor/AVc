package com.lastaurus.automatic_congratulations.data.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Contact(
   @PrimaryKey
   var id: Int = Int.MIN_VALUE,
   var idInBase: String = "",
   var dateCongratulationId: Int = 0,
   var name: String = "",
   var phoneList: ArrayList<String> = ArrayList(),
   var uriThumbnail: String = "",
   var uriFull: String = "",
   var favorite: Boolean = false,
) {
   fun clear() {
      id = Int.MIN_VALUE
      name = ""
      phoneList.clear()
      uriThumbnail = ""
      uriFull = ""
      favorite = false
   }
}

