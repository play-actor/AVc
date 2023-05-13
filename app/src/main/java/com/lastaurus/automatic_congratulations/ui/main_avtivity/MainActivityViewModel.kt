package com.lastaurus.automatic_congratulations.ui.main_avtivity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
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

   @Inject
   lateinit var context: Context

   init {
      ComponentManager.instance.appComponent.inject(this)
   }

   fun initMainScrean(supportFragmentManager: FragmentManager) {
      if (supportFragmentManager.fragments.isEmpty()) router.navigateTo(Screens.mainScreen())
   }

   fun getContactListSize(): Int {
      return mainActivityUseCase.getContactListSize()
   }

   @SuppressLint("IntentReset")
   private fun smsSend(intent: Intent, toSms: String?, messageText: String?) {
      try {
         val sms = Intent(Intent.ACTION_SEND, Uri.parse("smsto:" + art(toSms)))
         sms.type = "text/plain"
         sms.putExtra(Intent.EXTRA_TEXT, messageText)
         startActivity(context, intent, null)
      } catch (ex: Exception) {
         Log.e("AVc", "", ex)
      }
   }

   private fun art(text: String?): String? {
      var finalText = text
      val taboo = "+-"
      for (c in taboo.toCharArray()) {
         finalText = finalText?.replace(c, ' ')
         finalText = finalText?.replace(" ".toRegex(), "")
      }
      return finalText
   }

}