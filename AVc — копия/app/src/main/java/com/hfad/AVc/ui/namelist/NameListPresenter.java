package com.hfad.AVc.ui.namelist;

import com.hfad.AVc.Applications;
import com.hfad.AVc.interactor.LoadDBInteractor;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class NameListPresenter extends MvpPresenter<INameListViewModel> {

    private final LoadDBInteractor interactor;

    public NameListPresenter() {
        this.interactor = Applications.INSTANCE.getHelperInteractors().getContactInteractor();
    }

    @Override
    public void attachView(INameListViewModel view) {
        super.attachView(view);
        getViewState().setData(this.interactor.getContactList(0));
    }
}