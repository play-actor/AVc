package com.hfad.avc.interactor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import androidx.work.Data;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.hfad.avc.Applications;
import com.hfad.avc.ui.SendWorker;
import com.hfad.avc.ui.database.AppDatabase;
import com.hfad.avc.ui.database.Contact;
import com.hfad.avc.ui.database.Template;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class LoadDBInteractor {
    private PeriodicWorkRequest myWorkRequest;
    private static final String TAG = "AVc";
    @Inject
    AppDatabase db;
    private ArrayList<Contact> contactsList;
    private ArrayList<Contact> contactsListForUpdate;
    private ArrayList<Template> templateList;
    private List<Contact> contacts;
    private Contact contact;
    private Context context;
    private ContentValues ContactValues;
    private Template template;
    SimpleDateFormat format = new SimpleDateFormat();
    private final static String INPUT_PATERN = "EEE MMM dd HH:mm:ss z yyyy";
    private final static String OUTPUT_PATERN = "dd.MM.yyyy, HH:mm";


    public LoadDBInteractor() {
        Applications.INSTANCE.getContactCompanent().inject(this);
        contact = new Contact();
        context = Applications.INSTANCE;
        ContactValues = new ContentValues();
        contacts = new ArrayList<>();
        template = new Template();
    }


    public ArrayList<Contact> getContactList(int position) {
        Date myTime = java.util.Calendar.getInstance().getTime();
        long secondsDeadLine = myTime.getTime() + 604800000;
        switch (position) {
            case 0:
                return this.contactsList = (ArrayList<Contact>) db.contactDao().getAll();
            case 1:
                return this.contactsList = (ArrayList<Contact>) db.contactDao().getAllCongratulations();
            case 2:
                return this.contactsList = (ArrayList<Contact>) db.contactDao().getComingCongratulations(secondsDeadLine);
        }
        return this.contactsList = (ArrayList<Contact>) db.contactDao().getAll();
    }

    public ArrayList<Template> getTemplateList(int position) {
        switch (position) {
            case 0:
                return this.templateList = (ArrayList<Template>) db.templateDao().getAll();
            case 1:
                return this.templateList = (ArrayList<Template>) db.templateDao().getFavorite();
        }
        return this.templateList = (ArrayList<Template>) db.templateDao().getAll();
    }

    @SuppressWarnings({"unused", "RedundantSuppression"})
    public Completable myWork(Data data, long seconds) {
        return Observable.just(data)
                .flatMapCompletable(data1 -> {
                    this.myWorkRequest = new PeriodicWorkRequest.Builder(SendWorker.class, 31536000, TimeUnit.MINUTES)
                            .setInputData(data1)
                            .setInitialDelay(10, TimeUnit.SECONDS)
                            .build();
                    WorkManager.getInstance(Applications.INSTANCE).enqueue(this.myWorkRequest);

                    return Completable.complete();
                });
    }

    public Observable<List<Contact>> loadDB(String[] countries) {
        Cursor cursor = context.getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        this.contactsListForUpdate = (ArrayList<Contact>) db.contactDao().getAll();
        Map<String, Contact> map = new HashMap<>();
        for (Contact i : contactsListForUpdate) map.put(i.getId(), i);

        if (cursor.getCount() > 0) {
            //defer - не создаётся Observable, пока наблюдатель не подпишется, и создаётся новый
            // Observable для каждого наблюдателя
            return Observable.defer(() -> {
                while (cursor.moveToNext()) {
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    if (map.containsKey(id)) {
                        for (int i = 0; i < contactsListForUpdate.size(); i++) {
                            if (contactsListForUpdate.get(i).getId().equals(id)) {
                                String name = cursor.getString(cursor.getColumnIndex(
                                        ContactsContract.Contacts.DISPLAY_NAME));
                                ContactValues.put("NAME", name);
                                contactsListForUpdate.get(i).setName(name);
                                String has_phone = cursor.getString(cursor.getColumnIndex(
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
                                        String phone = pCur.getString(pCur.getColumnIndex(
                                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                                        ContactValues.put("PHONE", phone);
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
                        String name = cursor.getString(cursor.getColumnIndex(
                                ContactsContract.Contacts.DISPLAY_NAME));
                        ContactValues.put("NAME", name);
                        contact.setName(name);
                        String has_phone = cursor.getString(cursor.getColumnIndex(
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
                                String phone = pCur.getString(pCur.getColumnIndex(
                                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                                ContactValues.put("PHONE", phone);
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
                //тут возврат мне не особо нужен, но мы позже смотрим на количество записей в листе
            });
        }
        return Observable.just(Collections.emptyList());
        //нужен лишь на тот случай если у нас нет контактов и лист пустой (его и возвращаем)


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
