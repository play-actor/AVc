package com.hfad.avc.ui.namelist;

import com.hfad.avc.Applications;
import com.hfad.avc.Screens;
import com.hfad.avc.interactor.LoadDBInteractor;

import javax.inject.Inject;

import moxy.InjectViewState;
import moxy.MvpPresenter;
import ru.terrakok.cicerone.Router;

@InjectViewState
public class NameListPresenter extends MvpPresenter<INameListViewModel> {

    @Inject
    Router router;

    private final LoadDBInteractor interactor;

    public NameListPresenter() {
        Applications.INSTANCE.getContactCompanent().inject(this);
        this.interactor = Applications.INSTANCE.getHelperInteractors().getContactInteractor();
    }

    @Override
    public void attachView(INameListViewModel view) {
        super.attachView(view);
        getViewState().setData(this.interactor.getContactList(0));
    }

    public void openContact(int id) {
        this.router.navigateTo(new Screens.ConatctScreen(id));
    }

    public void back() {
        this.router.exit();
    }
}