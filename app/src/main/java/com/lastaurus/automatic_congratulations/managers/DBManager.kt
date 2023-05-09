package com.lastaurus.automatic_congratulations.managers

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import android.util.Log
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import com.lastaurus.automatic_congratulations.R
import com.lastaurus.automatic_congratulations.dagger.ComponentManager.Companion.instance
import com.lastaurus.automatic_congratulations.data.database.AppDatabase
import com.lastaurus.automatic_congratulations.data.model.Congratulation
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
   private var myWorkRequest: PeriodicWorkRequest? = null

   @Inject
   lateinit var db: AppDatabase
   private var contactsList: List<Contact>? = null
   private var eventList: List<Congratulation>? = null

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

//   fun startWorkRequest(data: Data, idContact: Int, start: Boolean) {
//      if(db.eventDao().getById(idContact)?.getIdWorker()!=null)
//      WorkManager.getInstance(context).enqueue(it)
//   }

   fun createWorkRequest(data: Data, idContact: Int) {
//      var periodicWorkRequest: PeriodicWorkRequest?
      var periodicWorkRequest: OneTimeWorkRequest?
      scope.launch {
         periodicWorkRequest = OneTimeWorkRequest.Builder(WorkerManager::class.java)
//            PeriodicWorkRequest.Builder(WorkerManager::class.java, 1, TimeUnit.MINUTES)
            .setInputData(data)
            .setInitialDelay(10, TimeUnit.SECONDS)
            .build()

         periodicWorkRequest?.let {
            db.eventDao().getById(idContact)?.let { congratulation ->
               congratulation.setIdWorker(it.id)
               db.eventDao().update(congratulation)
            }
         }
      }
   }

//      return Observable.just(data)
//         .flatMapCompletable { data1 ->
//            myWorkRequest11 = PeriodicWorkRequest.Builder(WorkerManager::class.java, seconds, TimeUnit.MINUTES)
//               .setInputData(data1)
//               .setInitialDelay(10, TimeUnit.SECONDS)
//               .build()
//            WorkManager.getInstance(context).enqueue(myWorkRequest11!!)
//            Completable.complete()
//         }

   //   public Completable createWorkRequest(Data data, long seconds, String id) {
   //      return Observable.just(data)
   //            .flatMapCompletable(data1 -> {
   //               this.myWorkRequest = new PeriodicWorkRequest.Builder(WorkerManager.class, seconds, TimeUnit.MINUTES)
   //                     .setInputData(data1)
   //                     .setInitialDelay(10, TimeUnit.SECONDS)
   //                     .build();
   //               WorkManager.getInstance(context).enqueue(this.myWorkRequest);
   //               return Completable.complete();
   //            }).doFinally(() -> {
   //               updateListIDWorker(id);
   //            });
   //   }
   //
   //   public Completable cancelWorkRequest(String id) {
   //      Contact contact = db.contactDao().getById(id);
   //      ArrayList<String> listIDWorker = new ArrayList<>(contact.getListIDWorker());
   //      return Observable.just(listIDWorker)
   //            .flatMapCompletable(listID -> {
   //               for (String idfromList : listID) {
   //                  UUID uuid = UUID.fromString(idfromList);
   //                  WorkManager.getInstance(context).cancelWorkById(uuid);
   //               }
   //               return Completable.complete();
   //            }).doFinally(() -> {
   //               clineListIDWorker(id);
   //            });
   //   }
   //   public void updateListIDWorker(String id) {
   //      //FIXME добавляет но медленно, лучше в RX
   //      Contact contact = db.contactDao().getById(id);
   //      ArrayList<String> listIDWorker = new ArrayList<>(contact.getListIDWorker());
   //      listIDWorker.add(this.myWorkRequest.getId().toString());
   //      contact.setListIDWorker(listIDWorker);
   //      db.contactDao().update(contact);
   //   }
   //
   //   public void clineListIDWorker(String id) {
   //      //FIXME удаляет но медленно, лучше в RX
   //      Contact contact = db.contactDao().getById(id);
   //      contact.setListIDWorker(Collections.emptyList());
   //      db.contactDao().update(contact);
   //   }

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

//   fun Filt(searchText: String): Single<List<Contact>> {
//      contactsList = getContactList(0)
//      return Observable.fromIterable(this.contactsList!!)
//         .filter { contact: Contact ->
//            val searchTextfinal = searchText.lowercase(Locale.getDefault())
//            if (contact.getName().lowercase(Locale.getDefault())
//                  .contains(searchTextfinal)
//            ) {
//               return@filter true
//            }
//            contact.getPhoneList()[0].lowercase(Locale.getDefault())
//               .contains(searchTextfinal)
//         }
//         .toList()
//   }
}