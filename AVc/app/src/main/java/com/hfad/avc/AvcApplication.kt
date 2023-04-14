package com.lastaurus.avc

import android.app.Application
import com.lastaurus.avc.dagger.ComponentManager.Companion.instance

class AvcApplication : Application() {
   companion object {
      var INSTANCE: AvcApplication? = null
   }

   override fun onCreate() {
      instance.initAppComponent(applicationContext)
      instance.appComponent.inject(this)
      INSTANCE = this
      super.onCreate()
   }
}