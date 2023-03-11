package com.hfad.avc.ui.main_avtivity;

import com.github.terrakok.cicerone.Router;
import com.hfad.avc.cicerone.Screens;
import com.hfad.avc.dagger.ComponentManager;

import javax.inject.Inject;

import moxy.InjectViewState;
import moxy.MvpPresenter;


@InjectViewState
public class MainPresenter extends MvpPresenter<IMainViewModel> {

   @Inject
   Router router;

   public MainPresenter() {
      ComponentManager.Companion.getInstance().appComponent.inject(this);
   }

   @Override
   protected void onFirstViewAttach() {
      super.onFirstViewAttach();
      this.router.navigateTo(Screens.main());
   }
}

