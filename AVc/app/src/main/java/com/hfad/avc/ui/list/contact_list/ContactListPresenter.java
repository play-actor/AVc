package com.lastaurus.automatic_congratulations.ui.list.contact_list;

import com.github.terrakok.cicerone.Router;
import com.lastaurus.automatic_congratulations.cicerone.Screens;
import com.lastaurus.automatic_congratulations.dagger.ComponentManager;
import com.lastaurus.automatic_congratulations.managers.DBManager;

import javax.inject.Inject;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class ContactListPresenter extends MvpPresenter<IContactListViewModel> {

   @Inject
   Router router;

   @Inject
   DBManager dbManager;


   public ContactListPresenter() {
      ComponentManager.Companion.getInstance().appComponent.inject(this);
   }

   @Override
   public void attachView(IContactListViewModel view) {
      super.attachView(view);
      reMove(0);
   }

   public void openContact(int id) {
      this.router.navigateTo(Screens.changeContactScreen(id));
   }


   public void reMove(int typesort) {
      getViewState().setData(dbManager.getContactList(0), typesort);
   }

}