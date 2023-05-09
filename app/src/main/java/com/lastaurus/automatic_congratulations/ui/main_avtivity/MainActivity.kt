package com.lastaurus.automatic_congratulations.ui.main_avtivity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.lastaurus.automatic_congratulations.R
import com.lastaurus.automatic_congratulations.bus.RxBus
import com.lastaurus.automatic_congratulations.dagger.ComponentManager.Companion.instance
import com.lastaurus.automatic_congratulations.dagger.ExtSupportAppNavigator
import com.lastaurus.automatic_congratulations.managers.DBManager
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

   @Inject
   lateinit var navigatorHolder: NavigatorHolder

   @Inject
   lateinit var dbManager: DBManager

   @Inject
   lateinit var rxBus: RxBus

   private lateinit var viewModel: MainActivityViewModel

   private val navigator: Navigator = object : ExtSupportAppNavigator(this, R.id.root) {
      override fun applyCommands(commands: Array<out Command>) {
         super.applyCommands(commands)
         supportFragmentManager.executePendingTransactions()
      }
   }
   private val REQUEST_CODE_READ_CONTACTS = 1

   override fun onCreate(savedInstanceState: Bundle?) {
      instance.appComponent.inject(this)
      super.onCreate(savedInstanceState)
      setContentView(R.layout.activity_main)
      val intent: Intent = intent
      this.viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
      viewModel.initIntent(intent, supportFragmentManager)
      if (viewModel.getContactListSize() == 0) uploadContactList()
   }

//   @SuppressLint("IntentReset")
//   private fun smsSend(toSms: String?, messageText: String?) {
//      try {
//         val sms = Intent(Intent.ACTION_SEND, Uri.parse("smsto: ${toSms?.let { art(it) }}"))
//         sms.apply {
//            type = "text/plain"
//            putExtra(Intent.EXTRA_TEXT, messageText)
//         }
//         startActivity(Intent.createChooser(sms, "Отправить"))
//      } catch (exception: Exception) {
//         Log.e("AVc", "smsSend $exception")
//      }
//   }

//   private fun art(text: String): String {
//      var finalText = text
//      val taboo = "+-"
//      for (c in taboo.toCharArray()) {
//         finalText = finalText.replace(c, ' ').replace(" ".toRegex(), "")
//      }
//      return finalText
//   }

   override fun onResume() {
      navigatorHolder.setNavigator(navigator)
      super.onResume()
   }

   override fun onPause() {
      navigatorHolder.removeNavigator()
      super.onPause()
   }

   @SuppressLint("CheckResult")
   fun uploadContactList() {
      if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
         == PackageManager.PERMISSION_GRANTED
      ) {
         dbManager.loadSistemContactList()
      } else {
         ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_CONTACTS),
            REQUEST_CODE_READ_CONTACTS
         )
      }
   }

   override fun onRequestPermissionsResult(
      requestCode: Int,
      permissions: Array<String>,
      grantResults: IntArray,
   ) {
      if (requestCode == REQUEST_CODE_READ_CONTACTS) {
         uploadContactList()
      }
      super.onRequestPermissionsResult(requestCode, permissions, grantResults)
   }

//   fun sendMsg(text: String) {
//      Snackbar.make(findViewById(R.id.root), text, Snackbar.LENGTH_SHORT).show()
//   }

   override fun onDestroy() {
      dbManager.cancelJob()
      super.onDestroy()
   }
}