package com.lastaurus.automatic_congratulations.ui.contact

import com.lastaurus.automatic_congratulations.data.DataRepository
import com.lastaurus.automatic_congratulations.data.model.Contact
import javax.inject.Inject

class ChangeContactUseCase @Inject constructor(var dataRepository: DataRepository) {
   fun getContact(id: Int): Contact? {
      return dataRepository.getContactById(id)
   }

   fun updateContactDB(contact: Contact) {
      dataRepository.updateContactDB(contact)
   }

   fun upsertContactDB(contact: Contact) {
      dataRepository.upsertContactDB(contact)
   }

   fun getContactListSize(): Int {
      return dataRepository.getContactListSize()
   }

   fun getPhoneListFromContact(id: Int): ArrayList<String>? {
      return dataRepository.getPhoneListFromContact(id)
   }
}