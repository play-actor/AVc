package com.lastaurus.automatic_congratulations.ui.main_avtivity

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import com.lastaurus.automatic_congratulations.managers.DBManager
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

   @Inject
   lateinit var navigatorHolder: NavigatorHolder

   @Inject
   lateinit var dbManager: DBManager

   @Inject
   lateinit var eventHandler: EventHandler

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
      this.viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
      this.viewModel.initMainScrean(supportFragmentManager)
      if (this.viewModel.getContactListSize() == 0) uploadContactList()
      subscribeOnEventBus()
   }

   private fun subscribeOnEventBus() {
      eventHandler.subscribeEvent { busEvent ->
         (busEvent as? BusEvent.TextOfSave)?.let {
            Snackbar.make(
               findViewById(R.id.root),
               it.getTextSave(),
               Snackbar.LENGTH_SHORT
            ).show()
         }
         (busEvent as? BusEvent.RequestPermissionsResult)?.let {
            if (it.result == REQUEST_CODE_READ_CONTACTS) {
               uploadContactList()
            }
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
      eventHandler.postEvent(BusEvent.RequestPermissionsResult(requestCode))
      super.onRequestPermissionsResult(requestCode, permissions, grantResults)
   }

   override fun onDestroy() {
      dbManager.cancelJob()
      super.onDestroy()
   }
}