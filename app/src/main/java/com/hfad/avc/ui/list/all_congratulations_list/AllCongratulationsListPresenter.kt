package com.hfad.avc.ui.list.all_congratulations_list

import com.hfad.avc.dagger.ComponentManager.Companion.instance
import moxy.InjectViewState
import moxy.MvpPresenter


@InjectViewState
class AllCongratulationsListPresenter :
   MvpPresenter<IAllCongratulationsListViewModel>() {
   init {
      instance.appComponent.inject(this)
   }

   override fun attachView(view: IAllCongratulationsListViewModel) {
      super.attachView(view)
      viewState?.setData()
   }
}