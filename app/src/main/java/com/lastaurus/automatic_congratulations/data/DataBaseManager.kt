package com.lastaurus.automatic_congratulations.data

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
import com.lastaurus.automatic_congratulations.data.database.AppDatabase
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


class DataBaseManager @Singleton constructor() {

   @Inject
   lateinit var database: AppDatabase

   @Inject
   lateinit var context: Context
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
         val targetCongratulation =
            database.congratulationsDao().targetCongratulation(currentDateTime)
         Log.d("gera", "createWorkRequest: targetCongratulation=$targetCongratulation")
         targetCongratulation?.apply {
            getSavedId()?.let { uuid ->
               cancelWorkRequest(uuid)
            }
            val inputData = workDataOf("idCongratulation" to this.id)
            val duration = this.dateTimeFuture - currentDateTime
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

   /**
    * Метод возврвщает объект класса Contact для дальнейшей работы с ним.
    * @param update требуется ли тоько обновление или добавление, если null - обнавление и добавление
    * @param idInBase ID контакта в системной базе телефона
    * @return новый/существующий контакт или null (есди не нужно)
    */
   private fun getContactForUpload(update: Boolean?, idInBase: String): Contact? {
      update?.apply {
         return if (update) {
            database.contactDao().getByIdInBase(idInBase)
         } else {
            return if (!database.contactDao().contactInBase(idInBase)) {
               Contact().apply {
                  this.id = database.contactDao().size
                  this.idInBase = idInBase
               }
            } else null
         }
      }
      // Обновление и добавление
      return database.contactDao().getByIdInBase(idInBase) ?: Contact().apply {
         this.id = database.contactDao().size
         this.idInBase = idInBase
      }
   }

   fun loadSystemContactList(update: Boolean? = null) {
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
                  @SuppressLint("Range") val idInBase =
                     cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))

                  val contact = getContactForUpload(update = update, idInBase = idInBase)
                  contact?.apply {
                     @SuppressLint("Range") val name = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                     )
                     this.name = name ?: ""
                     @SuppressLint("Range") val uriThumbnail = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI)
                     )
                     this.uriThumbnail = uriThumbnail ?: ""
                     @SuppressLint("Range") val uriFull = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI)
                     )
                     this.uriFull = uriFull ?: ""
                     @SuppressLint("Range") val hasPhone = cursor.getString(
                        cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)
                     )
                     if (hasPhone.toInt() > 0) {
                        val phoneCursor: Cursor? = context.contentResolver.query(
                           ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                           null,
                           ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                           arrayOf(idInBase),
                           null
                        )
                        while (phoneCursor!!.moveToNext()) {
                           @SuppressLint("Range") val phone = phoneCursor.getString(
                              phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                           )
                           this.phoneList.add(phone)
                        }
                        phoneCursor.close()
                     }
                     database.contactDao().upsert(this)
                  }?.clear()
               }
            }
         } catch (exception: Exception) {
            Log.e("gera", "loadSystemContactList", exception)
         }
      }
   }

   private fun loadTemplateList() {
      val template = Template()
      if (database.templateDao().size == 0) {
         scope.launch {
            val countries = context.resources.getStringArray(R.array.congratulations_templates)
            for (i in countries.indices) {
               template.id = i
               template.textTemplate = countries[i]
               template.favorite = false
               database.templateDao().insert(template)
               template.clear()
            }
         }
      }
   }

   fun cancelJob() {
      dbJob?.cancel()
   }
}