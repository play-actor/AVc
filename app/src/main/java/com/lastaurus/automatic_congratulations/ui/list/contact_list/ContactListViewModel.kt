package com.lastaurus.automatic_congratulations.ui.list.contact_list

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.github.terrakok.cicerone.Router
import com.lastaurus.automatic_congratulations.cicerone.Screens
import com.lastaurus.automatic_congratulations.dagger.ComponentManager
import com.lastaurus.automatic_congratulations.data.model.Contact
import javax.inject.Inject

class ContactListViewModel : ViewModel() {

   @Inject
   lateinit var contactListUseCase: ContactListUseCase

   @Inject
   lateinit var router: Router

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
      router.navigateTo(Screens.contactScreen(id))
   }

   fun openNewContact() {
      router.navigateTo(Screens.contactScreen(contactListUseCase.dataRepository.getContactListSize()))
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