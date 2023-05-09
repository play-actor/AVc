package com.lastaurus.automatic_congratulations.ui.congratulation

import com.lastaurus.automatic_congratulations.data.DataRepository
import com.lastaurus.automatic_congratulations.data.model.Congratulation
import com.lastaurus.automatic_congratulations.data.model.Contact
import com.lastaurus.automatic_congratulations.data.model.Template
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CongratulationUseCase @Inject constructor(var dataRepository: DataRepository) {
   fun getContact(id: Int?): Contact? {
      return dataRepository.getContactById(id)
   }

   fun getContact(name: String?): Contact? {
      return dataRepository.getContactByName(name)
   }

   fun getContactList(): Flow<List<Contact>> {
      return dataRepository.getContactList()
   }

   fun getTemplateList(): Flow<List<Template>> {
      return dataRepository.getTemplateList()
   }

   fun getTemplate(id: Int?): Template? {
      return dataRepository.getTemplateById(id)
   }

   fun getCongratulation(id: Int): Congratulation? {
      return dataRepository.getCongratulationById(id)
   }

   fun updateCongratulationDB(congratulation: Congratulation) {
      dataRepository.updateCongratulationDB(congratulation)
   }

   fun upsertCongratulationDB(congratulation: Congratulation) {
      dataRepository.upsertCongratulationDB(congratulation)
   }

   fun getCongratulationListSize(): Int {
      return dataRepository.getCongratulationListSize()
   }
}