package com.lastaurus.automatic_congratulations.ui.list.contact_list

import com.github.terrakok.cicerone.Router
import com.lastaurus.automatic_congratulations.cicerone.Screens
import com.lastaurus.automatic_congratulations.data.model.Contact
import com.lastaurus.automatic_congratulations.repository.DataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ContactListUseCase @Inject constructor(
   var dataRepository: DataRepository,
   var router: Router,
) {
   fun getContactList(): Flow<List<Contact>> {
      return dataRepository.getContactList()
   }

   fun getFavoriteContactList(): Flow<List<Contact>> {
      return dataRepository.getFavoriteContactList()
   }

   fun openContact(id: Int) {
      router.navigateTo(Screens.contactScreen(id))
   }

   fun openNewContact() {
      router.navigateTo(Screens.contactScreen(dataRepository.getContactListSize()))
   }
}