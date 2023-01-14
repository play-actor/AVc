package com.hfad.avc.dagger.module

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NavigationModule {
   private var cicerone: Cicerone<Router> = Cicerone.create(Router())

   @Provides
   @Singleton
   fun provideRouter(): Router {
      return cicerone.router
   }

   @Provides
   @Singleton
   fun provideNavigatorHolder(): NavigatorHolder {
      return cicerone.getNavigatorHolder()
   }
}