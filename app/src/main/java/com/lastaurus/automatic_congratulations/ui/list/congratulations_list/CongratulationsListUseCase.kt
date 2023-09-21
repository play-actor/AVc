package com.lastaurus.automatic_congratulations.ui.list.congratulations_list

import com.lastaurus.automatic_congratulations.data.model.Congratulation
import com.lastaurus.automatic_congratulations.repository.DataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CongratulationsListUseCase @Inject constructor(
   var dataRepository: DataRepository,
) {
   fun getCongratulationsList(): Flow<List<Congratulation>> {
      return dataRepository.getCongratulationsList()
   }

   fun getCongratulationsListDESC(): Flow<List<Congratulation>> {
      return dataRepository.getCongratulationsListDESC()
   }

   fun getCongratulationsListByPeaceNameInContact(searchText: String): Flow<List<Congratulation>> {
      return dataRepository.getCongratulationsListByPeaceNameInContact(searchText)
   }

   fun getActiveCongratulationsList(): Flow<List<Congratulation>> {
      return dataRepository.getActiveCongratulationsList()
   }

}