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

   fun getContactList(): LiveData<List<Congratulation>> {
      return congratulationsListUseCase.getCongratulationsList().asLiveData()
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