package com.hfad.AVc.interactor;

import com.hfad.AVc.HelperModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {HelperModule.class})
public interface ContactCompanent {

    void inject(LoadDBInteractor loadDBInteractor);
}
