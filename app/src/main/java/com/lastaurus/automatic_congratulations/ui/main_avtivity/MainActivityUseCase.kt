package com.lastaurus.automatic_congratulations.ui.main_avtivity

import com.lastaurus.automatic_congratulations.data.DataRepository
import javax.inject.Inject

class MainActivityUseCase @Inject constructor(var dataRepository: DataRepository) {

   fun getContactListSize(): Int {
      return dataRepository.getContactListSize()
   }
}