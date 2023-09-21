package com.lastaurus.automatic_congratulations.ui.list.congratulations_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.github.terrakok.cicerone.Router
import com.lastaurus.automatic_congratulations.cicerone.Screens
import com.lastaurus.automatic_congratulations.dagger.ComponentManager
import com.lastaurus.automatic_congratulations.data.model.Congratulation
import javax.inject.Inject

class CongratulationsListViewModel : ViewModel() {

   @Inject
   lateinit var congratulationsListUseCase: CongratulationsListUseCase

   @Inject
   lateinit var router: Router

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
      router.navigateTo(Screens.congratulationScreen(id))
   }

   fun openNewCongratulation() {
      router.navigateTo(Screens.congratulationScreen(congratulationsListUseCase.dataRepository.getCongratulationListSize()))
   }
}