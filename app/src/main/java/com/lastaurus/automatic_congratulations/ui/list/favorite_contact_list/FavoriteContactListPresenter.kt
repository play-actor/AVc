package com.lastaurus.automatic_congratulations.ui.list.favorite_contact_list


import com.lastaurus.automatic_congratulations.dagger.ComponentManager
import com.lastaurus.automatic_congratulations.managers.DBManager
import moxy.MvpPresenter
import javax.inject.Inject

class FavoriteContactListPresenter: MvpPresenter<IFavoriteContactListViewModel>() {

   @Inject
   lateinit var dbManager: DBManager

   override fun onFirstViewAttach() {
      ComponentManager.instance.appComponent.inject(this)
      super.onFirstViewAttach()
   }

   override fun attachView(view: IFavoriteContactListViewModel?) {
      super.attachView(view)
      viewState.setData(dbManager.getContactList(1))
   }
}