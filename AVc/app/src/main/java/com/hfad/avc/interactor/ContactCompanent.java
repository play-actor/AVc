package com.hfad.avc.interactor;

import com.hfad.avc.HelperModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {HelperModule.class})
public interface ContactCompanent {

    void inject(LoadDBInteractor loadDBInteractor);
}
