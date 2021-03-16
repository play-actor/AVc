package com.hfad.avc.interactor;

import com.hfad.avc.HelperModule;
import com.hfad.avc.dagger.module.NavigationModule;
import com.hfad.avc.ui.MainActivity;
import com.hfad.avc.ui.namelist.NameListPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {HelperModule.class, NavigationModule.class})
public interface ContactCompanent {

    void inject(LoadDBInteractor loadDBInteractor);

    void inject(NameListPresenter loadDBInteractor);

    void inject(MainActivity loadDBInteractor);

}