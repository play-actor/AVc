package com.hfad.avc.interactor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import com.hfad.avc.Applications;
import com.hfad.avc.ui.database.AppDatabase;
import com.hfad.avc.ui.database.Contact;
import com.hfad.avc.ui.database.Template;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class LoadDBInteractor {

    private static final String TAG = "AVc";
    @Inject AppDatabase db;
    private ArrayList<Contact> contactsList;
    private ArrayList<Template> templateList;
    private List<Contact> contacts;
    private Contact contact;
    private Context context;
    private ContentValues ContactValues;
    private Template template;


    public LoadDBInteractor() {
        Applications.INSTANCE.getContactCompanent().inject(this);
        contact = new Contact();
        context = Applications.INSTANCE;
        ContactValues = new ContentValues();
        contacts = new ArrayList<>();
        template = new Template();
    }



    public ArrayList<Contact> getContactList(int position) {
        switch (position) {
            case 0:
                return this.contactsList = (ArrayList<Contact>) db.contactDao().getAll();
            case 1:
                return this.contactsList = (ArrayList<Contact>) db.contactDao().getAllCongratulations();
            case 2:
                return this.contactsList = (ArrayList<Contact>) db.contactDao().getComingCongratulations();
        }
        return this.contactsList = (ArrayList<Contact>) db.contactDao().getAll();
    }

    public ArrayList<Template> getTemplateList(int position) {
        switch (position) {
            case 0:
                return this.templateList = (ArrayList<Template>) db.templateDao().getAll();
            }
        return this.templateList = (ArrayList<Template>) db.templateDao().getAll();
    }


    public Observable<List<Contact>> loadDB() {
        Cursor cursor = context.getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        if (cursor.getCount() > 0) {
            //defer - не создаётся Observable, пока наблюдатель не подпишется, и создаётся новый
            // Observable для каждого наблюдателя
            return Observable.defer(() -> {
                while (cursor.moveToNext()) {
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
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
                return Observable.just(contacts);
                //тут возврат мне не особо нужен, но он должен быть в синтаксисе, поэтому возвращаем
            });
        }
        return Observable.just(Collections.emptyList());
        //нужен лишь на тот случай если у нас лист пустой (его и возвращаем)
    }
    public void AddTemplateFirstInsert(String[] countries) {
        for (int i = 0; i < 13; i++) {
            this.template.setId(String.valueOf(i));
            template.setTextTemplate(countries[i]);
            template.setFavorite(false);
            Log.i(TAG, "В БД загружается поздавление: "+String.valueOf(template));
            db.templateDao().insert(template);

        }
    }
}
