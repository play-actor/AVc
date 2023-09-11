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

   fun getTemplateList(sort: Boolean = true): LiveData<List<Template>> {
      return if (sort) {
         templateListUseCase.getTemplateList().asLiveData()
      } else {
         templateListUseCase.getTemplateListDESC().asLiveData()
      }
   }

   fun getTemplateListByPeaceText(searchText: String): LiveData<List<Template>> {
      return templateListUseCase.getTemplateListByPeaceText(searchText).asLiveData()
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