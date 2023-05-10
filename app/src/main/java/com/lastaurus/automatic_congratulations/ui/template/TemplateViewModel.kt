package com.lastaurus.automatic_congratulations.ui.template

import android.view.MenuItem
import androidx.lifecycle.ViewModel
import com.lastaurus.automatic_congratulations.R
import com.lastaurus.automatic_congratulations.bus.BusEvent
import com.lastaurus.automatic_congratulations.bus.EventHandler
import com.lastaurus.automatic_congratulations.dagger.ComponentManager
import com.lastaurus.automatic_congratulations.data.model.Template
import javax.inject.Inject

class TemplateViewModel : ViewModel() {
   private var textTemplate: String = ""
   private var template: Template? = null
   private var favorite: Boolean = false

   @Inject
   lateinit var changeTemplateUseCase: TemplateUseCase

   @Inject
   lateinit var eventHandler: EventHandler

   init {
      ComponentManager.instance.appComponent.inject(this)
   }

   fun init(id: Int? = null) {
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

   private fun getListSize(): Int {
      return changeTemplateUseCase.getTemplateListSize()
   }

   fun getFavorite(): Boolean {
      return template?.getFavorite() ?: false
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

   private fun setText(text: String?) {
      eventHandler.postEvent(BusEvent.TextOfSave)
      text?.let {
         template?.setTextTemplate(it)
         this.textTemplate = it
      }
   }

   fun saveText(text: String?) {
      setText(text)
      upsert()
   }

   fun setFavoriteTemplate(favoriteTemplate: MenuItem?, favorite: Boolean?) {
      val iconId: Int =
         if (favorite == true) R.drawable.ic_baseline_star_favorite else R.drawable.ic_baseline_star_no_favorite
      favoriteTemplate?.setIcon(iconId)
   }
}