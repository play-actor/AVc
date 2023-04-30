package com.lastaurus.automatic_congratulations.ui.list.template_list

import androidx.lifecycle.ViewModel
import com.lastaurus.automatic_congratulations.dagger.ComponentManager
import com.lastaurus.automatic_congratulations.data.model.Template
import javax.inject.Inject

class TemplateListViewModel : ViewModel() {

   @Inject
   lateinit var templateListUseCase: TemplateListUseCase

   init {
      ComponentManager.instance.appComponent.inject(this)
   }

   fun getTemplateList(): List<Template> {
      return templateListUseCase.getTemplateList()
   }

   fun getTemplateListFavorite(): List<Template> {
      return templateListUseCase.getFavoriteTemplateList()
   }

   fun openTemplate(id: Int) {
      templateListUseCase.openTemplate(id)
   }

   fun openNewTemplate() {
      templateListUseCase.openNewTemplate()
   }
}