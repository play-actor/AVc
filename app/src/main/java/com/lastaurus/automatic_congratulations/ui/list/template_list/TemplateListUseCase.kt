package com.lastaurus.automatic_congratulations.ui.list.template_list

import com.github.terrakok.cicerone.Router
import com.lastaurus.automatic_congratulations.cicerone.Screens
import com.lastaurus.automatic_congratulations.data.DataRepository
import com.lastaurus.automatic_congratulations.data.model.Template
import javax.inject.Inject

class TemplateListUseCase @Inject constructor(
   var dataRepository: DataRepository,
   var router: Router,
) {
   fun getTemplateList(): List<Template> {
      return dataRepository.getTemplateList()
   }

   fun getFavoriteTemplateList(): List<Template> {
      return dataRepository.getFavoriteTemplateList()
   }

   fun openTemplate(id: Int) {
      router.navigateTo(Screens.changeTemplateScreen(id))
   }

   fun openNewTemplate() {
      router.navigateTo(Screens.changeTemplateScreen(dataRepository.getTemplateListSize()))
   }
}