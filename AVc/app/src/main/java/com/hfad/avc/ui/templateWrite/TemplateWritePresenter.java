package com.hfad.avc.ui.templateWrite;

import android.os.Bundle;
import android.util.Log;

import com.hfad.avc.Applications;
import com.hfad.avc.ui.database.AppDatabase;
import com.hfad.avc.ui.database.Template;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class TemplateWritePresenter extends MvpPresenter<ITemplateWriteViewModel> {
    private AppDatabase db;
    private Template template;
    private String templateId, newId, newTemplateText;
    private int localId;
    Bundle dataInsert;


    public TemplateWritePresenter(Bundle bundle) {
        this.templateId = String.valueOf(bundle.getInt("template_Id", -1));
        this.localId = bundle.getInt("template_Id", -1);
        this.db = Applications.getInstance().getDatabase();
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
}
