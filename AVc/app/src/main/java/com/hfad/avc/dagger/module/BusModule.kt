package com.lastaurus.avc.dagger.module

import com.lastaurus.avc.bus.RxBus
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