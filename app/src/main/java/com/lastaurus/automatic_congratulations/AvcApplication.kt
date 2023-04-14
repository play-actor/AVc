package com.lastaurus.automatic_congratulations

import android.app.Application
import com.lastaurus.automatic_congratulations.dagger.ComponentManager.Companion.instance

class AvcApplication : Application() {

   override fun onCreate() {
      instance.initAppComponent(applicationContext)
      instance.appComponent.inject(this)
      super.onCreate()
   }
}