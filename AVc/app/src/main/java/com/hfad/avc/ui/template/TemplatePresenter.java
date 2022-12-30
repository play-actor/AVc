package com.hfad.avc.ui.template;

import com.hfad.avc.Applications;
import com.hfad.avc.Screens;
import com.hfad.avc.interactor.LoadDBInteractor;

import javax.inject.Inject;

import moxy.InjectViewState;
import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class TemplatePresenter extends MvpPresenter<ITemplateViewModel> {

   @Inject
   Router router;

   private final LoadDBInteractor interactor;

   public TemplatePresenter() {
      Applications.INSTANCE.getContactCompanent().inject(this);
      this.interactor = Applications.INSTANCE.getHelperInteractors().getContactInteractor();
   }

   @Override
   public void attachView(ITemplateViewModel view) {
      super.attachView(view);
      getViewState().setData(this.interactor.getTemplateList(0));
   }

   public void openTemplate(int id) {
      this.router.navigateTo(new Screens.TemplateWriteScreen(id));
   }

   public void back() {
      this.router.backTo(new Screens.MainScreen());
   }
}


