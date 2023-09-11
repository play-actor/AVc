package com.lastaurus.automatic_congratulations.ui.list.contact_list

import android.view.View
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

   fun getContactList(sort: Boolean = true): LiveData<List<Contact>> {
      return if (sort) {
         contactListUseCase.getContactList().asLiveData()
      } else {
         contactListUseCase.getContactListDESC().asLiveData()
      }
   }

   fun getNeedVisibilityLoadContact(): Int {
      return if (contactListUseCase.getNeedVisibilityLoadContact()) {
         View.VISIBLE
      } else {
         View.GONE
      }
   }

   fun getContactByPeaceName(searchText: String): LiveData<List<Contact>> {
      return contactListUseCase.getContactByPeaceName(searchText).asLiveData()
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

   fun firstLoadSystemContactList() {
      contactListUseCase.firstLoadSystemContactList()
   }

   fun updateSystemContactList() {
      contactListUseCase.updateSystemContactList()
   }

   fun insertNewSystemContactInList() {
      contactListUseCase.insertNewSystemContactInList()
   }
}