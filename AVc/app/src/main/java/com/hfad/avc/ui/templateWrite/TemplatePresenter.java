package com.hfad.avc.ui.templateWrite;

import android.os.Bundle;

import com.hfad.avc.Applications;
import com.hfad.avc.ui.database.AppDatabase;
import com.hfad.avc.ui.database.Template;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class TemplatePresenter extends MvpPresenter<ITemplateWriteViewModel> {
    private AppDatabase db;
    private Template template;
    private String templateId;

    public TemplatePresenter(Bundle bundle) {
        templateId = String.valueOf(bundle.getInt("template_Id", -1));
        db = Applications.getInstance().getDatabase();
        template = db.templateDao().getById(templateId);
        getViewState().setData(template);
    }
    public void updateTemplate() {this.db.templateDao().update(this.template);

    }
}
