package com.hfad.avc.ui.main_fragment;

import com.hfad.avc.Applications;
import com.hfad.avc.interactor.LoadDBInteractor;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class AllCongratulationsPresenter extends MvpPresenter<IAllCongratulationsViewModel> {

    private final LoadDBInteractor interactor1;

    public AllCongratulationsPresenter() {
        this.interactor1 = Applications.INSTANCE.getHelperInteractors().getContactInteractor();
    }

    @Override
    public void attachView(IAllCongratulationsViewModel view) {
        super.attachView(view);
        getViewState().setData(this.interactor1.getContactList(1));
    }
}