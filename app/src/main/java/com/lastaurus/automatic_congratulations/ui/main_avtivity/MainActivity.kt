package com.lastaurus.automatic_congratulations.ui.main_avtivity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.google.android.material.snackbar.Snackbar
import com.lastaurus.automatic_congratulations.ChainHolder
import com.lastaurus.automatic_congratulations.R
import com.lastaurus.automatic_congratulations.bus.RxBus
import com.lastaurus.automatic_congratulations.dagger.ComponentManager.Companion.instance
import com.lastaurus.automatic_congratulations.dagger.ExtSupportAppNavigator
import com.lastaurus.automatic_congratulations.data.model.Contact
import com.lastaurus.automatic_congratulations.managers.DBManager
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import java.lang.ref.WeakReference
import javax.inject.Inject

class MainActivity : MvpAppCompatActivity(), ChainHolder, IMainViewModel {
   override val chain = ArrayList<WeakReference<Fragment>>()

   @Inject
   lateinit var navigatorHolder: NavigatorHolder

   @Inject
   lateinit var dbManager: DBManager

   @Inject
   lateinit var rxBus: RxBus

   @InjectPresenter
   lateinit var presenter: MainPresenter

   @ProvidePresenter
   fun ProvidePresenterMainActivityPresenter(): MainPresenter {
      return MainPresenter()
   }

   private val navigator: Navigator = object : ExtSupportAppNavigator(this, R.id.root) {
      override fun applyCommands(commands: Array<out Command>) {
         super.applyCommands(commands)
         supportFragmentManager.executePendingTransactions()
      }
   }
   private val PERMISSION_REQUEST_CODE_READ_CONTACTS = 1
   private var coordLayout: CoordinatorLayout? = null

   override fun onCreate(savedInstanceState: Bundle?) {
      instance.appComponent.inject(this)
      super.onCreate(savedInstanceState)
      this.coordLayout = findViewById(R.id.mainFragApp)
      setContentView(R.layout.activity_main)
      val intent = intent
      if (intent != null) {
         val textTemplate = intent.getStringExtra("TextTemplate")
         val phone = intent.getStringExtra("Phone")
         if ((textTemplate != null) and (phone != null)) {
            smsSend(phone, textTemplate)
         }
      }
      updateDB()
   }

   @SuppressLint("IntentReset")
   private fun smsSend(toSms: String?, messageText: String?) {
      try {
         val sms = Intent(Intent.ACTION_SEND, Uri.parse("smsto: ${toSms?.let { art(it) }}"))
         sms.apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, messageText)
         }
         startActivity(Intent.createChooser(sms, "Отправить"))
      } catch (exception: Exception) {
         Log.e("AVc", "smsSend $exception")
      }
   }

   private fun art(text: String): String {
      var finalText = text
      val taboo = "+-"
      for (c in taboo.toCharArray()) {
         finalText = finalText.replace(c, ' ').replace(" ".toRegex(), "")
      }
      return finalText
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
   fun updateDB() {
      if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
         == PackageManager.PERMISSION_GRANTED
      ) {
         dbManager.loadDB()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
               { list: List<Contact?> ->
                  Log.i(
                     "gera", "Обновление базы данных завершено. " +
                           "Количество записей: " + list.size
                  )
               }
            ) { obj: Throwable -> obj.printStackTrace() }
      } else {
         ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_CONTACTS),
            PERMISSION_REQUEST_CODE_READ_CONTACTS
         )
      }
   }

   override fun onRequestPermissionsResult(
      requestCode: Int,
      permissions: Array<String>,
      grantResults: IntArray,
   ) {
      if (requestCode == PERMISSION_REQUEST_CODE_READ_CONTACTS) {
         updateDB()
      }
      super.onRequestPermissionsResult(requestCode, permissions, grantResults)
   }

   override fun sendMsg(text: String) {
      Snackbar.make(findViewById(R.id.root), text, Snackbar.LENGTH_SHORT).show()
   }
}