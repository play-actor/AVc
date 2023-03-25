package com.hfad.avc.managers;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.work.Data;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.hfad.avc.dagger.ComponentManager;
import com.hfad.avc.data.database.AppDatabase;
import com.hfad.avc.data.database.ContactDao;
import com.hfad.avc.data.model.Contact;
import com.hfad.avc.data.model.Template;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class DBManager {
   private PeriodicWorkRequest myWorkRequest;
   private static final String TAG = "DBManager";
   @Inject
   AppDatabase db;
   private List<Contact> contactsList;
   private List<Template> templateList;
   private final List<Contact> contacts;
   @Inject
   Context context;
   private final Contact contact;
   private final ContentValues contentValues;
   private final Template template;

   @Singleton
   public DBManager() {
      ComponentManager.Companion.getInstance().appComponent.inject(this);
      contact = new Contact();
      contentValues = new ContentValues();
      contacts = new ArrayList<>();
      template = new Template();
   }

   public List<Contact> getContactList(int position) {
      ContactDao db_contactDao = db.contactDao();
      try {
         switch (position) {
            case 0:
               return this.contactsList = db_contactDao.all();
            case 1:
               return this.contactsList = db_contactDao.getAllCongratulations();
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
   public Completable createWorkRequest(Data data, long seconds, String id) {
      return Observable.just(data)
            .flatMapCompletable(data1 -> {
               this.myWorkRequest = new PeriodicWorkRequest.Builder(WorkerManager.class, seconds, TimeUnit.MINUTES)
                     .setInputData(data1)
                     .setInitialDelay(10, TimeUnit.SECONDS)
                     .build();
               WorkManager.getInstance(context).enqueue(this.myWorkRequest);
               return Completable.complete();
            }).doFinally(() -> {
               updateListIDWorker(id);
            });
   }

   public Completable cancelWorkRequest(String id) {
      Contact contact = db.contactDao().getById(id);
      ArrayList<String> listIDWorker = new ArrayList<>(contact.getListIDWorker());
      return Observable.just(listIDWorker)
            .flatMapCompletable(listID -> {
               for (String idfromList : listID) {
                  UUID uuid = UUID.fromString(idfromList);
                  WorkManager.getInstance(context).cancelWorkById(uuid);
               }
               return Completable.complete();
            }).doFinally(() -> {
               clineListIDWorker(id);
            });
   }

   public void updateListIDWorker(String id) {
      //FIXME добавляет но медленно, лучше в RX
      Contact contact = db.contactDao().getById(id);
      ArrayList<String> listIDWorker = new ArrayList<>(contact.getListIDWorker());
      listIDWorker.add(this.myWorkRequest.getId().toString());
      contact.setListIDWorker(listIDWorker);
      db.contactDao().update(contact);
   }

   public void clineListIDWorker(String id) {
      //FIXME удаляет но медленно, лучше в RX
      Contact contact = db.contactDao().getById(id);
      contact.setListIDWorker(Collections.emptyList());
      db.contactDao().update(contact);
   }

   public Observable<List<Contact>> loadDB(String[] countries) {
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
         for (Contact i : contactsListForUpdate) map.put(i.getId(), i);
         if (cursor != null) {
            while (cursor.moveToNext()) {
               @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
               if (map.containsKey(id)) {
                  for (int i = 0; i < contactsListForUpdate.size(); i++) {
                     if (contactsListForUpdate.get(i).getId().equals(id)) {
                        @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(
                              ContactsContract.Contacts.DISPLAY_NAME));
                        contentValues.put("NAME", name);
                        contactsListForUpdate.get(i).setName(name);
                        @SuppressLint("Range") String uriThumbnail = cursor.getString(cursor.getColumnIndex(
                              ContactsContract.Contacts.PHOTO_THUMBNAIL_URI));
                        if (uriThumbnail != null) {
                           contentValues.put("URI_THUMBNAIL", uriThumbnail);
                           contact.setUriThumbnail(uriThumbnail);
                        } else {
                           contentValues.put("URI_THUMBNAIL", "");
                           contact.setUriThumbnail("");
                        }
                        @SuppressLint("Range") String uriFull = cursor.getString(cursor.getColumnIndex(
                              ContactsContract.Contacts.PHOTO_URI));
                        if (uriFull != null) {
                           contentValues.put("URI", uriFull);
                           contact.setUriFull(uriFull);
                        } else {
                           contentValues.put("URI", "");
                           contact.setUriFull("");
                        }
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
                              contentValues.put("PHONE", phone);
                              contactsListForUpdate.get(i).setPhone(phone);
                           }
                           pCur.close();
                           db.contactDao().update(contactsListForUpdate.get(i));
                           contacts.add(contactsListForUpdate.get(i));
                           break;
                        }
                     }
                  }
               } else {
                  contact.setId(id);
                  @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));
                  contentValues.put("NAME", name);
                  contact.setName(name);
                  @SuppressLint("Range") String uriThumbnail = cursor.getString(cursor.getColumnIndex(
                        ContactsContract.Contacts.PHOTO_THUMBNAIL_URI));
                  if (uriThumbnail != null) {
                     contentValues.put("URI_THUMBNAIL", uriThumbnail);
                     contact.setUriThumbnail(uriThumbnail);
                  } else {
                     contentValues.put("URI_THUMBNAIL", "");
                     contact.setUriThumbnail("");
                  }
                  @SuppressLint("Range") String uriFull = cursor.getString(cursor.getColumnIndex(
                        ContactsContract.Contacts.PHOTO_URI));
                  if (uriFull != null) {
                     contentValues.put("URI", uriFull);
                     contact.setUriFull(uriFull);
                  } else {
                     contentValues.put("URI", "");
                     contact.setUriFull("");
                  }
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
                        contentValues.put("PHONE", phone);
                        contact.setPhone(phone);
                     }
                     pCur.close();
                     db.contactDao().insert(contact);
                     contacts.add(contact);
                  }
               }
            }
            if (db.templateDao().getSize().equals("0")) {
               for (int i = 0; i < 13; i++) {
                  this.template.setId(String.valueOf(i));
                  template.setTextTemplate(countries[i]);
                  template.setFavorite(false);
                  db.templateDao().insert(template);
               }
            }
            return Observable.just(contacts);
            //позже смотрим на количество записей в листе

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
               return contact.getPhone().toLowerCase().contains(searchTextfinal);
            })
            .toList();

   }
}