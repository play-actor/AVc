package com.lastaurus.automatic_congratulations.ui.list.template_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.lastaurus.automatic_congratulations.dagger.ComponentManager
import com.lastaurus.automatic_congratulations.data.model.Template
import javax.inject.Inject

class TemplateListViewModel : ViewModel() {

   @Inject
   lateinit var templateListUseCase: TemplateListUseCase

   init {
      ComponentManager.instance.appComponent.inject(this)
   }

   fun getTemplateList(): LiveData<List<Template>> {
      return templateListUseCase.getTemplateList().asLiveData()
   }

   fun getTemplateListFavorite(): LiveData<List<Template>> {
      return templateListUseCase.getFavoriteTemplateList().asLiveData()
   }

   fun openTemplate(id: Int) {
      templateListUseCase.openTemplate(id)
   }

   fun openNewTemplate() {
      templateListUseCase.openNewTemplate()
   }
}