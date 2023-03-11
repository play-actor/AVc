package com.hfad.avc

import android.app.Application
import com.hfad.avc.dagger.ComponentManager.Companion.instance

class AvcApplication : Application() {

   override fun onCreate() {
      instance.initAppComponent(applicationContext)
      instance.appComponent.inject(this)
      super.onCreate()
   }
}