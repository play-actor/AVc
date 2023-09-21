package com.lastaurus.automatic_congratulations.ui.list.template_list

import com.lastaurus.automatic_congratulations.data.model.Template
import com.lastaurus.automatic_congratulations.repository.DataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TemplateListUseCase @Inject constructor(
   var dataRepository: DataRepository,
) {
   fun getTemplateList(): Flow<List<Template>> {
      return dataRepository.getTemplateList()
   }

   fun getTemplateListDESC(): Flow<List<Template>> {
      return dataRepository.getTemplateListDESC()
   }

   fun getTemplateListByPeaceText(searchText: String): Flow<List<Template>> {
      return dataRepository.getTemplateListByPeaceText(searchText)
   }

   fun getFavoriteTemplateList(): Flow<List<Template>> {
      return dataRepository.getFavoriteTemplateList()
   }
}