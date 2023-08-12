package com.lastaurus.automatic_congratulations.ui.list.contact_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.lastaurus.automatic_congratulations.dagger.ComponentManager
import com.lastaurus.automatic_congratulations.data.model.Contact
import javax.inject.Inject

class ContactListViewModel : ViewModel() {

   @Inject
   lateinit var contactListUseCase: ContactListUseCase

   init {
      ComponentManager.instance.appComponent.inject(this)
   }

   fun getContactList(): LiveData<List<Contact>> {
      return contactListUseCase.getContactList().asLiveData()
   }

   fun getContactListFavorite(): LiveData<List<Contact>> {
      return contactListUseCase.getFavoriteContactList().asLiveData()
   }

   fun openContact(id: Int) {
      contactListUseCase.openContact(id)
   }

   fun openNewContact() {
      contactListUseCase.openNewContact()
   }

   fun loadSystemContactList() {
      contactListUseCase.loadSystemContactList()
   }
}