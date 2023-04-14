package com.lastaurus.automatic_congratulations.ui.list.contact_list

import com.github.terrakok.cicerone.Router
import com.lastaurus.automatic_congratulations.cicerone.Screens.changeContactScreen
import com.lastaurus.automatic_congratulations.dagger.ComponentManager.Companion.instance
import com.lastaurus.automatic_congratulations.managers.DBManager
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class ContactListPresenter : MvpPresenter<IContactListViewModel>() {
   @Inject
   lateinit var router: Router

   @Inject
   lateinit var dbManager: DBManager

   init {
      instance.appComponent.inject(this)
   }

   override fun attachView(view: IContactListViewModel?) {
      super.attachView(view)
      reMove(0)
   }

   fun openContact(id: Int) {
      router.navigateTo(changeContactScreen(id))
   }

   fun reMove(typesort: Int) {
      viewState!!.setData(dbManager.getContactList(0), typesort)
   }
}