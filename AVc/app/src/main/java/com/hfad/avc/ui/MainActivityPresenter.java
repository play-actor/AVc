package com.hfad.avc.ui;

import com.hfad.avc.Applications;
import com.hfad.avc.Screens;

import javax.inject.Inject;

import moxy.InjectViewState;
import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class MainActivityPresenter extends MvpPresenter<IMainActivityViewModel> {

    @Inject
    Router router;

    public MainActivityPresenter() {
        Applications.INSTANCE.getContactCompanent().inject(this);
    }

    public void openTemplateWrite() {
        this.router.navigateTo(new Screens.TemplateWriteScreen(-1));
    }
    public void openDbContact() {
        this.router.navigateTo(new Screens.DbScreen());
    }

    public void openTemplateList() {
        this.router.navigateTo(new Screens.TemplateScreen());
    }
}
