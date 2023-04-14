package com.lastaurus.automatic_congratulations.ui.list.all_congratulations_list;

import com.lastaurus.automatic_congratulations.dagger.ComponentManager;
import com.lastaurus.automatic_congratulations.managers.DBManager;

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