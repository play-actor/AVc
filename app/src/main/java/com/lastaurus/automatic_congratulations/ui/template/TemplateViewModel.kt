package com.lastaurus.automatic_congratulations.ui.template

import androidx.lifecycle.ViewModel
import com.lastaurus.automatic_congratulations.dagger.ComponentManager
import com.lastaurus.automatic_congratulations.data.model.Template
import javax.inject.Inject

class TemplateViewModel : ViewModel() {
   private var textTemplate: String = ""
   private var template: Template? = null
   private var favorite: Boolean = false

   @Inject
   lateinit var changeTemplateUseCase: ChangeTemplateUseCase

   init {
      ComponentManager.instance.appComponent.inject(this)
   }

   fun initTemplate(id: Int? = null) {
      if (id == null) {
         if (template == null) {
            template = Template()
            template?.setId(getListSize())
         }
      } else {
         template = changeTemplateUseCase.getTemplate(id)
         if (template == null) {
            template = Template()
            template?.setId(getListSize())
         }
      }
   }

   fun update() {
      template?.let { changeTemplateUseCase.updateTemplateDB(it) }
   }

   fun upsert() {
      template?.let { changeTemplateUseCase.upsertTemplateDB(it) }
   }

   fun getListSize(): Int {
      return changeTemplateUseCase.getTemplateListSize()
   }

   fun getFavorite(): Boolean {
      return template?.getFavorite() ?: false
   }

   fun setFavorite(favorite: Boolean) {
      this.favorite = favorite
   }

   fun changeFavorite(): Boolean {
      this.favorite = !this.favorite
      template?.setFavorite(this.favorite)
      return this.favorite
   }

   fun getText(): String {
      textTemplate = template?.getTextTemplate() ?: ""
      return textTemplate
   }

   fun setText(text: String) {
      template?.setTextTemplate(text)
      this.textTemplate = text
   }

   fun saveText(text: String) {
      setText(text)
      update()
   }
}