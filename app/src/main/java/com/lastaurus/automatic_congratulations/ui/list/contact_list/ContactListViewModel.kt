package com.lastaurus.automatic_congratulations.ui.list.contact_list

import androidx.lifecycle.ViewModel
import com.lastaurus.automatic_congratulations.dagger.ComponentManager
import com.lastaurus.automatic_congratulations.data.model.Contact
import javax.inject.Inject

class ContactListViewModel : ViewModel() {

   @Inject
   lateinit var templateListUseCase: ContactListUseCase

   init {
      ComponentManager.instance.appComponent.inject(this)
   }

   fun getContactList(): List<Contact> {
      return templateListUseCase.getTemplateList()
   }

   fun getContactListFavorite(): List<Contact> {
      return templateListUseCase.getFavoriteTemplateList()
   }

   fun openContact(id: Int) {
      templateListUseCase.openContact(id)
   }

   fun openNewContact() {
      templateListUseCase.openNewContact()
   }
}