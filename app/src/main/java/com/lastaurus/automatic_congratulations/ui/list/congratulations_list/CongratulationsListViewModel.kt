package com.lastaurus.automatic_congratulations.ui.list.congratulations_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.lastaurus.automatic_congratulations.dagger.ComponentManager
import com.lastaurus.automatic_congratulations.data.model.Congratulation
import javax.inject.Inject

class CongratulationsListViewModel : ViewModel() {

   @Inject
   lateinit var congratulationsListUseCase: CongratulationsListUseCase

   init {
      ComponentManager.instance.appComponent.inject(this)
   }

   fun getCongratulationsList(sort: Boolean = true): LiveData<List<Congratulation>> {
      return if (sort) {
         congratulationsListUseCase.getCongratulationsList().asLiveData()
      } else {
         congratulationsListUseCase.getCongratulationsListDESC().asLiveData()
      }
   }

   fun getCongratulationsListByPeaceNameInContact(searchText: String): LiveData<List<Congratulation>> {
      return congratulationsListUseCase.getCongratulationsListByPeaceNameInContact(searchText)
         .asLiveData()
   }

   fun getActiveCongratulationsList(): LiveData<List<Congratulation>> {
      return congratulationsListUseCase.getActiveCongratulationsList().asLiveData()
   }

   fun openCongratulation(id: Int) {
      congratulationsListUseCase.openCongratulation(id)
   }

   fun openNewContact() {
      congratulationsListUseCase.openNewCongratulation()
   }
}