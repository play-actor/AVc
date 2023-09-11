package com.lastaurus.automatic_congratulations.ui.main_avtivity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.google.android.material.snackbar.Snackbar
import com.lastaurus.automatic_congratulations.R
import com.lastaurus.automatic_congratulations.bus.BusEvent
import com.lastaurus.automatic_congratulations.bus.EventHandler
import com.lastaurus.automatic_congratulations.dagger.ComponentManager.Companion.instance
import com.lastaurus.automatic_congratulations.dagger.ExtSupportAppNavigator
import com.lastaurus.automatic_congratulations.data.DataBaseManager
import com.lastaurus.automatic_congratulations.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

   @Inject
   lateinit var navigatorHolder: NavigatorHolder

   @Inject
   lateinit var dbManager: DataBaseManager

   @Inject
   lateinit var eventHandler: EventHandler

   private lateinit var viewModel: MainActivityViewModel

   private lateinit var binding: ActivityMainBinding

   private val navigator: Navigator = object : ExtSupportAppNavigator(this, R.id.root) {
      override fun applyCommands(commands: Array<out Command>) {
         super.applyCommands(commands)
         supportFragmentManager.executePendingTransactions()
      }
   }

   override fun onCreate(savedInstanceState: Bundle?) {
      instance.appComponent.inject(this)
      super.onCreate(savedInstanceState)
      binding = ActivityMainBinding.inflate(layoutInflater)
      val view = binding.root
      setContentView(view)
      this.viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
      this.viewModel.initMainScrean(supportFragmentManager)
      subscribeOnEventBus()
      intent?.apply {
         intentNotification(this)
      }
   }

   @SuppressLint("IntentReset")
   private fun intentNotification(intent: Intent) {
      val textTemplate = intent.getStringExtra("TextTemplate")
      val phone = intent.getStringExtra("Phone")
      phone?.apply {
         val sms = Intent(
            Intent.ACTION_SEND,
            Uri.parse("smsto:" + this.filterNot { it == '-' }.trimMargin("+"))
         )
         sms.type = "text/plain"
         sms.putExtra(Intent.EXTRA_TEXT, textTemplate)
         startActivity(Intent.createChooser(sms, "Отправить"))
      }
   }

   private fun subscribeOnEventBus() {
      eventHandler.subscribeEvent { busEvent ->
         (busEvent as? BusEvent.TextOfSave)?.apply {
            Snackbar.make(
               findViewById(R.id.root),
               this.getTextSave(),
               Snackbar.LENGTH_SHORT
            ).show()
         }
         (busEvent as? BusEvent.Text)?.apply {
            Log.d("gera", "subscribeOnEventBus: ${this.text}")
         }
         false
      }
   }

   override fun onResume() {
      navigatorHolder.setNavigator(navigator)
      super.onResume()
   }

   override fun onPause() {
      navigatorHolder.removeNavigator()
      super.onPause()
   }


   override fun onDestroy() {
      dbManager.cancelJob()
      super.onDestroy()
   }
}