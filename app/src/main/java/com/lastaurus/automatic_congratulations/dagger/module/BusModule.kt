package com.lastaurus.automatic_congratulations.dagger.module

import com.lastaurus.automatic_congratulations.bus.EventHandler
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class BusModule {

   @Singleton
   @Provides
   fun provideEventHandler(): EventHandler {
      return EventHandler()
   }
}