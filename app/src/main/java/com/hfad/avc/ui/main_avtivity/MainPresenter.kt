package com.hfad.avc.ui.main_avtivity

import com.github.terrakok.cicerone.Router
import com.hfad.avc.cicerone.Screens.main
import com.hfad.avc.dagger.ComponentManager.Companion.instance
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class MainPresenter : MvpPresenter<IMainViewModel>() {
   @Inject
   lateinit var router: Router

   init {
      instance.appComponent.inject(this)
   }

   override fun onFirstViewAttach() {
      super.onFirstViewAttach()
      router.navigateTo(main())
   }
}