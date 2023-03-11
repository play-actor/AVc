package com.hfad.avc.ui.list.favorite_contact_list


import com.hfad.avc.dagger.ComponentManager
import com.hfad.avc.managers.DBManager
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
      viewState.setData(dbManager.getContactList(2))
   }
}