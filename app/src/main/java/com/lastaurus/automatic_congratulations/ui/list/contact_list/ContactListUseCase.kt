package com.lastaurus.automatic_congratulations.ui.list.contact_list

import com.lastaurus.automatic_congratulations.data.DataBaseManager
import com.lastaurus.automatic_congratulations.data.model.Contact
import com.lastaurus.automatic_congratulations.repository.DataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ContactListUseCase @Inject constructor(
   var dbManager: DataBaseManager,
   var dataRepository: DataRepository,
) {
   fun getContactList(): Flow<List<Contact>> {
      return dataRepository.getContactList()
   }

   fun getFavoriteContactList(): Flow<List<Contact>> {
      return dataRepository.getFavoriteContactList()
   }

   fun getContactListDESC(): Flow<List<Contact>> {
      return dataRepository.getContactListDESC()
   }

   fun firstLoadSystemContactList() {
      dbManager.loadSystemContactList()
   }

   fun updateSystemContactList() {
      dbManager.loadSystemContactList(true)
   }

   fun insertNewSystemContactInList() {
      dbManager.loadSystemContactList(false)
   }

   fun getNeedVisibilityLoadContact(): Boolean {
      return dbManager.database.contactDao().size == 0
   }

   fun getContactByPeaceName(searchText: String): Flow<List<Contact>> {
      return dataRepository.getContactByPeaceName(searchText)
   }
}