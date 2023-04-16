package com.lastaurus.automatic_congratulations.data.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Template(
   @PrimaryKey
   private var id: Int = Int.MIN_VALUE,
   private var textTemplate: String = "",
   private var favorite: Boolean = false
) : BaseObservable() {

   @Bindable
   fun getId(): Int {
      return id
   }

   fun setId(id: Int) {
      this.id = id
      notifyPropertyChanged(BR.id)
   }

   @Bindable
   fun getTextTemplate(): String {
      return textTemplate
   }

   fun setTextTemplate(textTemplate: String) {
      this.textTemplate = textTemplate
      notifyPropertyChanged(BR.textTemplate)
   }

   @Bindable
   fun getFavorite(): Boolean {
      return favorite
   }

   fun setFavorite(favorite: Boolean) {
      this.favorite = favorite
      notifyPropertyChanged(BR.favorite)
   }

   override fun toString(): String {
      return "id = " + id +
            ", Текст: " + textTemplate +
            ", В избранном: " + favorite
   }
}