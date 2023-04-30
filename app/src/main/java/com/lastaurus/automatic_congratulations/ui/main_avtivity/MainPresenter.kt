package com.lastaurus.automatic_congratulations.ui.main_avtivity

import com.github.terrakok.cicerone.Router
import com.lastaurus.automatic_congratulations.bus.RxBus
import com.lastaurus.automatic_congratulations.cicerone.Screens.main
import com.lastaurus.automatic_congratulations.dagger.ComponentManager.Companion.instance
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class MainPresenter : MvpPresenter<IMainViewModel>() {
   @Inject
   lateinit var router: Router

   @Inject
   lateinit var rxBus: RxBus
   private val disposables = CompositeDisposable()

   init {
      instance.appComponent.inject(this)
   }

   override fun onFirstViewAttach() {
      super.onFirstViewAttach()
      router.navigateTo(main())
   }
}