package com.lastaurus.automatic_congratulations.ui.contact

import android.view.MenuItem
import android.widget.ImageView
import androidx.lifecycle.ViewModel
import com.lastaurus.automatic_congratulations.R
import com.lastaurus.automatic_congratulations.dagger.ComponentManager
import com.lastaurus.automatic_congratulations.dagger.module.ImageModule
import com.lastaurus.automatic_congratulations.data.model.Contact
import javax.inject.Inject

class ContactViewModel : ViewModel() {
   @Inject
   lateinit var changeContactUseCase: ContactUseCase

   @Inject
   lateinit var imageModule: ImageModule

   private var contactName: String = ""
   private var contact: Contact? = null
   private var favorite: Boolean = false

   init {
      ComponentManager.instance.appComponent.inject(this)
   }

   fun initContact(id: Int? = null) {
      if (id == null) {
         if (contact == null) {
            contact = Contact()
            contact?.setId(getListSize())
         }
      } else {
         contact = changeContactUseCase.getContact(id)
         if (contact == null) {
            contact = Contact()
            contact?.setId(getListSize())
         }
      }
   }

   private fun getListSize(): Int {
      return changeContactUseCase.getContactListSize()
   }

   fun update() {
      contact?.let { changeContactUseCase.updateContactDB(it) }
   }

   fun upsert() {
      contact?.let { changeContactUseCase.upsertContactDB(it) }
   }

   fun getName(): String {
      contactName = contact?.getName() ?: ""
      return contactName
   }

   fun getPhoneListFromContact(): ArrayList<String>? {
      return contact?.getId()?.let { changeContactUseCase.getPhoneListFromContact(it) }
   }

   fun getFavorite(): Boolean {
      return contact?.getFavorite() ?: false
   }

   fun setIconContact(iconContact: ImageView) {
      contact?.getUriFull()?.let { imageModule.showImageForContact(iconContact, it, true) }
   }

   fun changeFavorite(): Boolean {
      this.favorite = !this.favorite
      contact?.setFavorite(this.favorite)
      return this.favorite
   }

   fun setFavoriteContact(favoriteContact: MenuItem, favorite: Boolean?) {
      favorite?.let {
         favoriteContact.setIcon(
            if (it) R.drawable.ic_baseline_star_favorite
            else R.drawable.ic_baseline_star_no_favorite
         )
      }
   }
}