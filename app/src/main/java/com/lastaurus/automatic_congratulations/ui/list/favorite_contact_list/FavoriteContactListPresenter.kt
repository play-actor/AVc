package com.lastaurus.automatic_congratulations.ui.list.favorite_contact_list


import com.github.terrakok.cicerone.Router
import com.lastaurus.automatic_congratulations.cicerone.Screens
import com.lastaurus.automatic_congratulations.dagger.ComponentManager
import com.lastaurus.automatic_congratulations.managers.DBManager
import moxy.MvpPresenter
import javax.inject.Inject

class FavoriteContactListPresenter: MvpPresenter<IFavoriteContactListViewModel>() {

   @Inject
   lateinit var dbManager: DBManager

   @Inject
   lateinit var router: Router

   override fun onFirstViewAttach() {
      ComponentManager.instance.appComponent.inject(this)
      super.onFirstViewAttach()
   }

   fun openContact(id: Int) {
      router.navigateTo(Screens.changeTemplateScreen(id))
   }

   override fun attachView(view: IFavoriteContactListViewModel?) {
      super.attachView(view)
      viewState.setData(dbManager.getContactList(1))
   }
}