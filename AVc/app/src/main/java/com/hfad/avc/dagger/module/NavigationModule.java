package com.hfad.avc.dagger.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

@Module
public class NavigationModule {

   private Cicerone<Router> cicerone;

   public NavigationModule() {
      cicerone = Cicerone.create();
   }

   @Provides
   @Singleton
   Router provideRouter() {
      return cicerone.getRouter();
   }

   @Provides
   @Singleton
   NavigatorHolder provideNavigatorHolder() {
      return cicerone.getNavigatorHolder();
   }
}