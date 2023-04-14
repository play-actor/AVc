package com.lastaurus.avc.ui.list.favorite_contact_list


import com.lastaurus.avc.dagger.ComponentManager
import com.lastaurus.avc.managers.DBManager
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
      getViewState().setData(dbManager!!.getContactList(2))
   }
}