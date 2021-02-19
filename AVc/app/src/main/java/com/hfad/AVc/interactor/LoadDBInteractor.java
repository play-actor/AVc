package com.hfad.avc.interactor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.hfad.avc.Applications;
import com.hfad.avc.ui.database.AppDatabase;
import com.hfad.avc.ui.database.Contact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;



public class LoadDBInteractor {

    public AppDatabase db;

    public Observable<List<Contact>> loadDB() {
        List<Contact> contacts = new ArrayList<>();
        Context context = Applications.INSTANCE;
        ContentValues ContactValues = new ContentValues();
        Cursor cursor = context.getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        db = Applications.getInstance().getDatabase();

        if (cursor.getCount() > 0) {
            //defer - не создаётся Observable, пока наблюдатель не подпишется, и создаётся новый
            // Observable для каждого наблюдателя
            return Observable.defer(() -> {
                while (cursor.moveToNext()) {

                    Contact contact = new Contact();
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    contact.setId(id);

                    String name = cursor.getString(
                            cursor.getColumnIndex(
                                    ContactsContract.Contacts
                                            .DISPLAY_NAME));
                    ContactValues.put("NAME", name);
                    contact.setName(name);

                    String has_phone = cursor.getString(
                            cursor.getColumnIndex(
                                    ContactsContract.Contacts
                                            .HAS_PHONE_NUMBER));
                    if (Integer.parseInt(has_phone) > 0) {
                        // получаем контакты
                        Cursor pCur;
                        pCur = context.getContentResolver().query(
                                ContactsContract.CommonDataKinds
                                        .Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds
                                        .Phone.CONTACT_ID + " = ?",
                                new String[]{id},
                                null);
                        while (pCur.moveToNext()) {
                            String phone = pCur.getString(
                                    pCur.getColumnIndex(
                                            ContactsContract.
                                                    CommonDataKinds.
                                                    Phone.NUMBER));
                            ContactValues.put("PHONE", phone);
                            contact.setPhone(phone);
                        }
                        pCur.close();
                        db.contactDao().insert(contact);
                        contacts.add(contact);
                    }

                }
                return Observable.just(contacts);
                //тут возврат мне не нужен, но он должен быть в синтаксисе, поэтому возвращаем
            });
        }

        return Observable.just(Collections.emptyList());
        //нужен лишь на тот случай если у нас лист пустой (его и возвращаем)
    }
}
