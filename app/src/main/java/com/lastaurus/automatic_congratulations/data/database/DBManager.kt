package com.lastaurus.automatic_congratulations.data.database

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.database.Cursor
import android.provider.ContactsContract
import android.util.Log
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.lastaurus.automatic_congratulations.R
import com.lastaurus.automatic_congratulations.dagger.ComponentManager.Companion.instance
import com.lastaurus.automatic_congratulations.data.model.Contact
import com.lastaurus.automatic_congratulations.data.model.Template
import com.lastaurus.automatic_congratulations.managers.WorkerManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
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
   private val APP_PREFERENCES = "settings";
   private val preferences: SharedPreferences by lazy {
      context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
   }

   init {
      instance.appComponent.inject(this)
      loadTemplateList()
   }

   private fun cancelWorkRequest(id: UUID) {
      scope.launch {
         WorkManager.getInstance(context).cancelWorkById(id)
      }
   }

   fun createWorkRequest() {
      scope.launch {
         val currentDateTime: Long =
            ZonedDateTime.of(LocalDateTime.now(), ZoneId.systemDefault()).toInstant().toEpochMilli()
         val targetCongratulation = db.congratulationsDao().targetCongratulation(currentDateTime)
         Log.d("gera", "createWorkRequest: targetCongratulation=$targetCongratulation")
         targetCongratulation?.apply {
            val saveId = getSavedId()
            saveId?.let { uuid ->
               cancelWorkRequest(uuid)
            }
            val inputData = workDataOf("idCongratulation" to this.getId())
            val duration = this.getDateTimeFuture() - currentDateTime
            val workRequest = OneTimeWorkRequest.Builder(
               WorkerManager::class.java
            )
               .setInitialDelay(duration, TimeUnit.MILLISECONDS)
               .setInputData(inputData)
               .build()
            val workManager = WorkManager.getInstance(context)
            workManager.enqueue(workRequest)
            saveId(workRequest.id)
         }
      }
   }

   private fun saveId(id: UUID) {
      val editor: SharedPreferences.Editor = preferences.edit()
      editor.putString("workRequestID", id.toString())
      editor.apply()
   }

   private fun getSavedId(): UUID? {
      if (preferences.contains("workRequestID")) {
         return UUID.fromString(preferences.getString("workRequestID", ""))
      }
      return null
   }

   fun loadSystemContactList() {
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

   private fun loadTemplateList() {
      if (db.templateDao().size == 0) {
         scope.launch {
            val countries = context.resources.getStringArray(R.array.congratulations_templates)
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