package com.hfad.avc.ui.list.all_congratulations_list;

import com.hfad.avc.dagger.ComponentManager;
import com.hfad.avc.managers.DBManager;

import javax.inject.Inject;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class AllCongratulationsListPresenter extends MvpPresenter<IAllCongratulationsListViewModel> {

   @Inject
   DBManager dbManager;

   public AllCongratulationsListPresenter() {
      ComponentManager.Companion.getInstance().appComponent.inject(this);
   }

   @Override
   public void attachView(IAllCongratulationsListViewModel view) {
      super.attachView(view);
      getViewState().setData(dbManager.getContactList(1));
   }
}