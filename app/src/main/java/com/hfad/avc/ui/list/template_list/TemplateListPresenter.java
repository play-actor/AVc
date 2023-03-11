package com.hfad.avc.ui.list.template_list;

import com.github.terrakok.cicerone.Router;
import com.hfad.avc.cicerone.Screens;
import com.hfad.avc.dagger.ComponentManager;
import com.hfad.avc.managers.DBManager;

import javax.inject.Inject;

import moxy.InjectViewState;
import moxy.MvpPresenter;


@InjectViewState
public class TemplateListPresenter extends MvpPresenter<ITemplateListViewModel> {

   @Inject
   Router router;
   @Inject
   DBManager dbManager;


   public TemplateListPresenter() {
      ComponentManager.Companion.getInstance().appComponent.inject(this);
   }

   @Override
   public void attachView(ITemplateListViewModel view) {
      super.attachView(view);
      getViewState().setData(dbManager.getTemplateList(0));
   }

   public void openTemplate(int id) {
      this.router.navigateTo(Screens.changeTemplateScreen(id));
   }

}


