package com.hfad.avc.managers;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.work.PeriodicWorkRequest;

import com.hfad.avc.R;
import com.hfad.avc.dagger.ComponentManager;
import com.hfad.avc.data.database.AppDatabase;
import com.hfad.avc.data.database.ContactDao;
import com.hfad.avc.data.model.Contact;
import com.hfad.avc.data.model.Template;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class DBManager {
   private PeriodicWorkRequest myWorkRequest;
   private static final String TAG = "DBManager";
   @Inject
   AppDatabase db;
   private List<Contact> contactsList;
   private List<Template> templateList;
   @Inject
   Context context;
   private final Contact contact;
   private final Template template;

   @Singleton
   public DBManager() {
      ComponentManager.Companion.getInstance().appComponent.inject(this);
      contact = new Contact();
      template = new Template();
   }

   public List<Contact> getContactList(int position) {
      ContactDao db_contactDao = db.contactDao();
      try {
         switch (position) {
            case 0:
               return this.contactsList = db_contactDao.all();
            case 1:
               //return this.contactsList = db_contactDao.getAllCongratulations();
            case 2:
               return this.contactsList = db_contactDao.getFavoriteContact();
         }
      } catch (Exception e) {
         Log.e(TAG, "getContactList: " + e);
      }
      return this.contactsList;
   }

   public List<Template> getTemplateList(int position) {
      try {
         switch (position) {
            case 0:
               return this.templateList = db.templateDao().getAll();
            case 1:
               return this.templateList = db.templateDao().getFavorite();
         }
      } catch (Exception e) {
         Log.e(TAG, "getContactList: " + e);
      }
      return this.templateList;
   }

   @SuppressWarnings({"unused", "RedundantSuppression"})
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

   public Observable<List<Contact>> loadDB() {
      ContentResolver contentResolver = context.getContentResolver();
      @SuppressLint("Recycle") Cursor cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            null
      );
      try {
         List<Contact> contactsListForUpdate = db.contactDao().all();
         Map<String, Contact> map = new HashMap<>();
         if (cursor != null) {
            while (cursor.moveToNext()) {
               @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
               contact.setId(id);
               @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(
                     ContactsContract.Contacts.DISPLAY_NAME));
               contact.setName(name != null ? name : "");
               @SuppressLint("Range") String uriThumbnail = cursor.getString(cursor.getColumnIndex(
                     ContactsContract.Contacts.PHOTO_THUMBNAIL_URI));
               contact.setUriThumbnail(uriThumbnail != null ? uriThumbnail : "");
               @SuppressLint("Range") String uriFull = cursor.getString(cursor.getColumnIndex(
                     ContactsContract.Contacts.PHOTO_URI));
               contact.setUriFull(uriFull != null ? uriFull : "");
               @SuppressLint("Range") String has_phone = cursor.getString(cursor.getColumnIndex(
                     ContactsContract.Contacts.HAS_PHONE_NUMBER));
               if (Integer.parseInt(has_phone) > 0) {
                  Cursor pCur;
                  pCur = context.getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        new String[]{id},
                        null);
                  while (pCur.moveToNext()) {
                     @SuppressLint("Range") String phone = pCur.getString(pCur.getColumnIndex(
                           ContactsContract.CommonDataKinds.Phone.NUMBER));
                     contact.addToPhoneList(phone);
                  }
                  pCur.close();
               }
               db.contactDao().upsert(contact);
               contact.clear();
            }
            String[] countries = context.getResources().getStringArray(R.array.congratulations_templates);
            if (db.templateDao().getSize().equals("0")) {
               for (int i = 0; i < countries.length; i++) {
                  this.template.setId(String.valueOf(i));
                  template.setTextTemplate(countries[i]);
                  template.setFavorite(false);
                  db.templateDao().insert(template);
               }
            }
            return Observable.just(db.contactDao().all());
         }
      } catch (Exception exception) {
         Log.e(TAG, "loadDB: exception: ", exception);
      }
      return Observable.just(Collections.emptyList());
   }

   public @NonNull Single<List<Contact>> Filt(String searchText) {
      contactsList = getContactList(0);
      return Observable.fromIterable(this.contactsList)
            .filter(contact -> {
               String searchTextfinal = searchText.toLowerCase();
               if (contact.getName().toLowerCase().contains(searchTextfinal)) {
                  return true;
               }
               return contact.getPhoneList().get(0).toLowerCase().contains(searchTextfinal);
            })
            .toList();

   }
}
