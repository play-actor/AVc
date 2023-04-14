package com.lastaurus.automatic_congratulations.dagger.module

import com.lastaurus.automatic_congratulations.bus.RxBus
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class BusModule {

   @Singleton
   @Provides
   fun provideRxBus(): RxBus {
      return RxBus()
   }
}