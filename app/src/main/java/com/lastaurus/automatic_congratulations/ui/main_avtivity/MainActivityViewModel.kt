package com.lastaurus.automatic_congratulations.ui.main_avtivity

import android.content.Intent
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import com.github.terrakok.cicerone.Router
import com.lastaurus.automatic_congratulations.cicerone.Screens
import com.lastaurus.automatic_congratulations.dagger.ComponentManager
import javax.inject.Inject

class MainActivityViewModel : ViewModel() {
   @Inject
   lateinit var mainActivityUseCase: MainActivityUseCase

   @Inject
   lateinit var router: Router

   init {
      ComponentManager.instance.appComponent.inject(this)
   }

   fun initIntent(intent: Intent?, supportFragmentManager: FragmentManager) {
      if (supportFragmentManager.fragments.isEmpty()) router.navigateTo(Screens.mainScreen())
      intent?.let {
         val textTemplate = intent.getStringExtra("TextTemplate")
         val phone = intent.getStringExtra("Phone")
//         if ((textTemplate != null) and (phone != null)) {
//            smsSend(phone, textTemplate)
//         }
      }
   }

   fun getContactListSize(): Int {
      return mainActivityUseCase.getContactListSize()
   }

}