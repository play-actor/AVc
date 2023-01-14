package com.hfad.avc.ui.template;

import android.os.Bundle;
import android.util.Log;

import com.github.terrakok.cicerone.Router;
import com.hfad.avc.cicerone.Screens;
import com.hfad.avc.dagger.ComponentManager;
import com.hfad.avc.data.database.AppDatabase;
import com.hfad.avc.data.model.Template;

import javax.inject.Inject;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class СhangeTemplatePresenter extends MvpPresenter<IСhangeTemplateViewModel> {
   @Inject
   AppDatabase db;
   @Inject
   Router router;
   private Template template;
   private String templateId, newTemplateText;
   private int localId;


   public СhangeTemplatePresenter(Bundle bundle) {
      this.templateId = String.valueOf(bundle.getInt("template_Id", -1));
      this.localId = bundle.getInt("template_Id", -1);
      ComponentManager.Companion.getInstance().appComponent.inject(this);
      if (this.localId != -1) {
         this.template = db.templateDao().getById(String.valueOf(this.localId));
      }
      if (this.template == null) {
         this.template = new Template();
         this.template.setId(db.templateDao().getSize());
      }
      if (this.template == null) {
         Template templateNew = new Template();
         templateNew.setTextTemplate("С праздником!");
         Log.i("AVc", "template = " + templateNew.getTextTemplate());
         this.template = templateNew;
      }
      getViewState().setData(template);
   }

   public void setNewTemplateText() {
      newTemplateText = this.template.getTextTemplate();
   }

   public void updateTemplate() {
      if (this.localId > 0) {
         this.db.templateDao().update(this.template);
      } else {
         db.templateDao().insert(template);
      }
      getViewState().sendOkUsers();
   }
   public void back() {
      this.router.backTo(Screens.main());
   }
}
