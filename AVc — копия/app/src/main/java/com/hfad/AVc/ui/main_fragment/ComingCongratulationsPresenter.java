package com.hfad.AVc.ui.main_fragment;

import com.hfad.AVc.Applications;
import com.hfad.AVc.interactor.LoadDBInteractor;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class ComingCongratulationsPresenter extends MvpPresenter<IComingCongratulationsViewModel> {

    private final LoadDBInteractor interactor;

    public ComingCongratulationsPresenter() {
        this.interactor = Applications.INSTANCE.getHelperInteractors().getContactInteractor();
    }
//при каждом открытии фрагмента
    @Override
    public void attachView(IComingCongratulationsViewModel view) {
        super.attachView(view);
        getViewState().setData(this.interactor.getContactList(2));
    }
}