package com.lastaurus.automatic_congratulations.managers

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import android.util.Log
import androidx.work.PeriodicWorkRequest
import com.lastaurus.automatic_congratulations.R
import com.lastaurus.automatic_congratulations.dagger.ComponentManager.Companion.instance
import com.lastaurus.automatic_congratulations.data.database.AppDatabase
import com.lastaurus.automatic_congratulations.data.model.Contact
import com.lastaurus.automatic_congratulations.data.model.EventCongratulations
import com.lastaurus.automatic_congratulations.data.model.Template
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

class DBManager @Singleton constructor() {
   private val myWorkRequest: PeriodicWorkRequest? = null

   @Inject
   lateinit var db: AppDatabase
   private var contactsList: List<Contact>? = null
   private var templateList: List<Template>? = null
   private var eventList: List<EventCongratulations>? = null

   @Inject
   lateinit var context: Context
   private val contact: Contact
   private val template: Template

   init {
      instance.appComponent.inject(this)
      contact = Contact()
      template = Template()
   }

   fun getContactList(position: Int): List<Contact> {
      val db_contactDao = db.contactDao()
      try {
         when (position) {
            0 -> return db_contactDao.all().also { contactsList = it }
            1 -> return db_contactDao.getFavoriteContact().also { contactsList = it }
         }
      } catch (e: Exception) {
         Log.e(TAG, "getContactList: $e")
      }
      return emptyList()
   }

   fun getTemplateList(position: Int): List<Template> {
      val db_templateDao = db.templateDao()
      try {
         when (position) {
            0 -> return db_templateDao.all.also { templateList = it }
            1 -> return db_templateDao.favorite.also { templateList = it }
         }
      } catch (e: Exception) {
         Log.e(TAG, "getTemplateList: $e")
      }
      return emptyList()
   }

   fun getCongratulationsList(position: Int): List<EventCongratulations> {
      val db_eventCongratulationsDao = db.eventDao()
      try {
         when (position) {
            0 -> return db_eventCongratulationsDao.getAll().also {
               eventList = it
            }
            1 -> return db_eventCongratulationsDao.getActiveEvent().also { eventList = it }
         }
      } catch (e: Exception) {
         Log.e(TAG, "getCongratulationsList: $e")
      }
      return emptyList()
   }

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
   fun loadDB(): Observable<List<Contact>> {
      val contentResolver = context.contentResolver
      @SuppressLint("Recycle") val cursor = contentResolver.query(
         ContactsContract.Contacts.CONTENT_URI,
         null,
         null,
         null,
         null
      )
      try {
         val contactsListForUpdate = db.contactDao().all()
         val map: Map<String, Contact> = HashMap()
         if (cursor != null) {
            while (cursor.moveToNext()) {
               @SuppressLint("Range") val id =
                  cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
               contact.setId(id)
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
               db.contactDao().upsert(contact)
               contact.clear()
            }
            val countries = context.resources.getStringArray(R.array.congratulations_templates)
            if (db.templateDao().size == "0") {
               for (i in countries.indices) {
                  template.setId(i)
                  template.setTextTemplate(countries[i])
                  template.setFavorite(false)
                  db.templateDao().insert(template)
               }
            }
            return Observable.just(
               db.contactDao().all()
            )
         }
      } catch (exception: Exception) {
         Log.e(TAG, "loadDB: exception: ", exception)
      }
      return Observable.just(emptyList())
   }

   fun Filt(searchText: String): Single<List<Contact>> {
      contactsList = getContactList(0)
      return Observable.fromIterable(this.contactsList!!)
         .filter { contact: Contact ->
            val searchTextfinal = searchText.lowercase(Locale.getDefault())
            if (contact.getName().lowercase(Locale.getDefault())
                  .contains(searchTextfinal)
            ) {
               return@filter true
            }
            contact.getPhoneList()[0].lowercase(Locale.getDefault())
               .contains(searchTextfinal)
         }
         .toList()
   }

   companion object {
      private const val TAG = "DBManager"
   }
}