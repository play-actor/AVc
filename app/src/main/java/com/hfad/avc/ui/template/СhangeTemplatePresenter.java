package com.hfad.avc.ui.template;

import android.os.Bundle;
import android.view.MenuItem;

import com.github.terrakok.cicerone.Router;
import com.hfad.avc.R;
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
      getViewState().setData(template);
   }

   public void setNewTemplateText() {
      newTemplateText = this.template.getTextTemplate();
   }

   public void setFavoriteTemplate(MenuItem favoriteTemplate) {
      int iconId = template.getFavorite() ? R.drawable.ic_baseline_star_favorite : R.drawable.ic_baseline_star_no_favorite;
      favoriteTemplate.setIcon(iconId);
   }
   public void setOnClickFavoriteTemplate() {
      template.setFavorite(!template.getFavorite());
   }
   public void updateTemplateDB() {
      this.db.templateDao().update(this.template);
   }
}
