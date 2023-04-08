package com.hfad.avc.ui.list.template_list

import com.github.terrakok.cicerone.Router
import com.hfad.avc.cicerone.Screens.changeTemplateScreen
import com.hfad.avc.dagger.ComponentManager.Companion.instance
import com.hfad.avc.managers.DBManager
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class TemplateListPresenter : MvpPresenter<ITemplateListViewModel?>() {
   @Inject
   lateinit var router: Router

   @Inject
   lateinit var dbManager: DBManager

   init {
      instance.appComponent.inject(this)
   }

   override fun attachView(view: ITemplateListViewModel?) {
      super.attachView(view)
      dbManager.getTemplateList(0)?.let { viewState!!.setData(it) }
   }

   fun openTemplate(id: Int) {
      router.navigateTo(changeTemplateScreen(id))
   }
}