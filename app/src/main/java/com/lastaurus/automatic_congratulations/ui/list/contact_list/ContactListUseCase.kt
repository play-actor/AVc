package com.lastaurus.automatic_congratulations.ui.list.contact_list

import com.github.terrakok.cicerone.Router
import com.lastaurus.automatic_congratulations.cicerone.Screens
import com.lastaurus.automatic_congratulations.data.DataRepository
import com.lastaurus.automatic_congratulations.data.model.Contact
import javax.inject.Inject

class ContactListUseCase @Inject constructor(
   var dataRepository: DataRepository,
   var router: Router,
) {
   fun getTemplateList(): List<Contact> {
      return dataRepository.getContactList()
   }

   fun getFavoriteTemplateList(): List<Contact> {
      return dataRepository.getFavoriteContactList()
   }

   fun openContact(id: Int) {
      router.navigateTo(Screens.changeContactScreen(id))
   }

   fun openNewContact() {
      router.navigateTo(Screens.changeContactScreen(dataRepository.getContactListSize()))
   }
}