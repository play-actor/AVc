package com.hfad.AVc.ui.main_fragment;

import com.hfad.AVc.Applications;
import com.hfad.AVc.interactor.LoadDBInteractor;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class AllCongratulationsPresenter extends MvpPresenter<IAllCongratulationsViewModel> {

    private final LoadDBInteractor interactor;

    public AllCongratulationsPresenter() {
        this.interactor = Applications.INSTANCE.getHelperInteractors().getContactInteractor();
    }

    @Override
    public void attachView(IAllCongratulationsViewModel view) {
        super.attachView(view);
        getViewState().setData(this.interactor.getContactList(1));
    }
}