package com.hfad.avc

import android.app.Application
import com.hfad.avc.dagger.ComponentManager.Companion.instance

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