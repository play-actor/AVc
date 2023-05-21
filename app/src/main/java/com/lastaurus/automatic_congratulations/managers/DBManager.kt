package com.lastaurus.automatic_congratulations.managers

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import android.util.Log
import androidx.work.Data
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.lastaurus.automatic_congratulations.R
import com.lastaurus.automatic_congratulations.dagger.ComponentManager.Companion.instance
import com.lastaurus.automatic_congratulations.data.database.AppDatabase
import com.lastaurus.automatic_congratulations.data.model.Contact
import com.lastaurus.automatic_congratulations.data.model.Template
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton


class DBManager @Singleton constructor() {

   @Inject
   lateinit var db: AppDatabase

   @Inject
   lateinit var context: Context
   private val contact: Contact by lazy { Contact() }
   private val template: Template by lazy { Template() }
   private val scope: CoroutineScope by lazy { CoroutineScope(Job()) }
   private var dbJob: Job? = null

   init {
      instance.appComponent.inject(this)
      loadTemplateList()
   }

   fun cancelWorkRequest(idCongratulation: Int) {
      scope.launch {
         db.congratulationsDao().getById(idCongratulation)?.let { congratulation ->
            congratulation.getIdWorker()?.let {
               WorkManager.getInstance(context).cancelWorkById(it)
               congratulation.claenIdWorker()
            }
         }
      }
   }


   fun setEveryYearInterval(idCongratulation: Int) {
      scope.launch {
         val congratulationTime = db.congratulationsDao().getById(idCongratulation)?.getDateTime()
         val currentTime = Calendar.getInstance().timeInMillis
         var seconds = (congratulationTime?.minus(currentTime))
         seconds = seconds?.div(1000)
         seconds?.let {
            if (seconds < 0) {
               seconds += 31536000
            }
         }
         //TODO
      }
   }

   fun createWorkRequest(data: Data, idCongratulation: Int) {
      var periodicWorkRequest: PeriodicWorkRequest?
      scope.launch {
         periodicWorkRequest =
            PeriodicWorkRequest.Builder(
               WorkerManager::class.java,
               365,
               TimeUnit.DAYS,
               24,
               TimeUnit.HOURS,
            )
               .setInputData(data)
               .setInitialDelay(10, TimeUnit.SECONDS)
               .build()

         periodicWorkRequest?.let {
            db.congratulationsDao().getById(idCongratulation)?.let { congratulation ->
               congratulation.getIdWorker()?.let { uuid ->
                  WorkManager.getInstance(context).cancelWorkById(uuid)
               }
               congratulation.setIdWorker(it.id)
               db.congratulationsDao().update(congratulation)
            }
            WorkManager.getInstance(context).enqueue(it)
         }
      }
   }

   fun loadSistemContactList() {
      dbJob = scope.launch {
         val contentResolver = context.contentResolver
         @SuppressLint("Recycle") val cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            null
         )
         try {
            cursor?.let {
               while (cursor.moveToNext()) {
                  @SuppressLint("Range") val id =
                     cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                  contact.setId(db.contactDao().size)
                  @SuppressLint("Range") val name = cursor.getString(
                     cursor.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME
                     )
                  )
                  contact.setName(name ?: "")
                  @SuppressLint("Range") val uriThumbnail = cursor.getString(
                     cursor.getColumnIndex(
                        ContactsContract.Contacts.PHOTO_THUMBNAIL_URI
                     )
                  )
                  contact.setUriThumbnail(uriThumbnail ?: "")
                  @SuppressLint("Range") val uriFull = cursor.getString(
                     cursor.getColumnIndex(
                        ContactsContract.Contacts.PHOTO_URI
                     )
                  )
                  contact.setUriFull(uriFull ?: "")
                  @SuppressLint("Range") val has_phone = cursor.getString(
                     cursor.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER
                     )
                  )
                  if (has_phone.toInt() > 0) {
                     val pCur: Cursor? = context.contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", arrayOf(id),
                        null
                     )
                     while (pCur!!.moveToNext()) {
                        @SuppressLint("Range") val phone = pCur.getString(
                           pCur.getColumnIndex(
                              ContactsContract.CommonDataKinds.Phone.NUMBER
                           )
                        )
                        contact.addToPhoneList(phone)
                     }
                     pCur.close()
                  }
                  db.contactDao().insert(contact)
                  db.contactDao().update(contact)
                  contact.clear()
               }
            }
         } catch (exception: Exception) {
            Log.e("gera", "loadSistemContactList", exception)
         }
      }
   }

   fun loadTemplateList() {
      scope.launch {
         val countries = context.resources.getStringArray(R.array.congratulations_templates)
         if (db.templateDao().size == 0) {
            for (i in countries.indices) {
               template.setId(i)
               template.setTextTemplate(countries[i])
               template.setFavorite(false)
               db.templateDao().insert(template)
            }
         }
      }
   }

   fun cancelJob() {
      dbJob?.cancel()
   }
}