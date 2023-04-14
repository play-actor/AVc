package com.lastaurus.automatic_congratulations.ui.template

import android.os.Bundle
import android.view.MenuItem
import com.github.terrakok.cicerone.Router
import com.lastaurus.automatic_congratulations.R
import com.lastaurus.automatic_congratulations.dagger.ComponentManager.Companion.instance
import com.lastaurus.automatic_congratulations.data.database.AppDatabase
import com.lastaurus.automatic_congratulations.data.model.Template
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class СhangeTemplatePresenter(bundle: Bundle? = null) :
   MvpPresenter<IСhangeTemplateViewModel?>() {
   @Inject
   lateinit var db: AppDatabase

   @Inject
   lateinit var router: Router

   private var template: Template? = null
   private val templateId: String
   private var newTemplateText: String? = null
   private val localId: Int

   init {
      templateId = bundle!!.getInt("template_Id", -1).toString()
      localId = bundle.getInt("template_Id", -1)
      instance.appComponent.inject(this)
      if (localId != -1) {
         template = db.templateDao().getById(localId.toString())
      }
      if (template == null) {
         template = Template()
         template?.setId(db.templateDao().size)
      }
      viewState!!.setData(template)
   }

   fun setNewTemplateText() {
      newTemplateText = template!!.getTextTemplate()
   }

   fun setFavoriteTemplate(favoriteTemplate: MenuItem) {
      val iconId: Int =
         if (template!!.getFavorite()) R.drawable.ic_baseline_star_favorite else R.drawable.ic_baseline_star_no_favorite
      favoriteTemplate.setIcon(iconId)
   }

   fun setOnClickFavoriteTemplate() {
      template?.setFavorite(!template!!.getFavorite())
   }

   fun updateTemplateDB() {
      db.templateDao().update(template!!)
   }
}