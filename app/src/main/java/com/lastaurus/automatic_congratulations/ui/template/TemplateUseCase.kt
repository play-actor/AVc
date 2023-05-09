package com.lastaurus.automatic_congratulations.ui.template

import com.lastaurus.automatic_congratulations.data.DataRepository
import com.lastaurus.automatic_congratulations.data.model.Template
import javax.inject.Inject

class TemplateUseCase @Inject constructor(var dataRepository: DataRepository) {
   fun getTemplate(id: Int): Template? {
      return dataRepository.getTemplateById(id)
   }

   fun updateTemplateDB(template: Template) {
      dataRepository.updateTemplateDB(template)
   }

   fun upsertTemplateDB(template: Template) {
      dataRepository.upsertTemplateDB(template)
   }

   fun getTemplateListSize(): Int {
      return dataRepository.getTemplateListSize()
   }
}