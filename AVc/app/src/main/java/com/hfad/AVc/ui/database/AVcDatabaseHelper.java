package com.hfad.avc.ui.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

import com.hfad.avc.Applications;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.core.Observable;


//Помощники SQLite должны расширять класс SQLiteOpenHelper.
public class AVcDatabaseHelper extends SQLiteOpenHelper {

    public String TAG = "AVc";
    private static final String DB_NAME = "AVcDBase10"; // Имя базы данных
    private static final int DB_VERSION = 1; // Версия базы данных
    private final SQLiteDatabase db;

    public AVcDatabaseHelper(Context context) {
        //Вызываем конструктор суперкласса SQLiteOpenHelper и передаем ему имя и версию базы данных
        super(context, DB_NAME, null, DB_VERSION);
        this.db = getWritableDatabase();
        //  параметр null -  параметр используется для работы с курсорами.
        /*SQLiteOpenHelper AVcDatabaseHelper = Applications.INSTANCE.getAVcDatabaseHelper();
        db = AVcDatabaseHelper.getWritableDatabase();
        loadDB(db);
        Log.i(TAG, "loadDB: ok");*/
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
        Log.i(TAG, "onCreate DB: ok");
    }

    /**
     * Метод onUpgrade() вызывается тогда, когда возникнет необходимость в обновлении базы данных.
     *
     * @param db         - бд
     * @param oldVersion - номер старой версии
     * @param newVersion - номер новой версии
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
        Log.i(TAG, "onUpgrade DB: ok");
    }

    //Метод для заполнения БД
    public void insertDB(SQLiteDatabase db, String NAME, String Phone, boolean Activate, String Date_con) {
        ContentValues ContactValues = new ContentValues();
        ContactValues.put("NAME", NAME);
        ContactValues.put("PHONE", Phone);
        ContactValues.put("ACTIVATE", Activate);
        ContactValues.put("DATE_CONGRATULATIONS", Date_con);
        db.insert("CONTACT_TABLE", null, ContactValues);
        Log.i(TAG, String.valueOf(ContactValues));
    }

    //Метод обновления БД
    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            //Создание БД
            //execSQL(String sql) - Выполнить команду SQL, заданную в строковом виде.
            db.execSQL("CREATE TABLE CONTACT_TABLE (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "PHONE TEXT, "
                    + "ACTIVATE NUMERIC, "
                    + "DATE_CONGRATULATIONS TEXT);");
            insertDB(db, "", "", false, "");
            insertDB(db, "Lucifer", "666", true, "0000-00-00");
            insertDB(db, "Satan", "666", true, "0");
        }

        if (oldVersion < 5) {
            db.execSQL("CREATE TABLE CONGRATULATIONS_TEXT (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "CONGRATULATIONS_TEXT TEXT);");
        }


    }

    public void updateContact(boolean check, String date, int id) {
        ContentValues activateValues = new ContentValues();
        activateValues.put("ACTIVATE", check);
        this.db.update(
                "CONTACT_TABLE",
                activateValues,//Обновление значениястолбца ACTIVATE.
                "_id= ?",
                new String[]{Integer.toString(id)}
        );

        ContentValues dateValues = new ContentValues();
        dateValues.put("DATE_CONGRATULATIONS", date);
        db.update(
                "CONTACT_TABLE",
                dateValues,//Обновление значениястолбца FAVORITE.
                "_id= ?",
                new String[]{Integer.toString(id)}
        );
    }


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

                        contacts.add(contact);
                    }

                }

                for (Contact contact : contacts) {
                    insertDB(db, contact.getName(), contact.getPhone(), false, "0");
                    Log.i(TAG, contact.getName());
                }

                return Observable.just(contacts);
                //тут возврат мне не нужен, но он должен быть в синтаксисе, поэтому возвращаем
            });
        }

        return Observable.just(Collections.emptyList());
        //нужен лишь на тот случай если у нас лист пустой (его и возвращаем)
    }

}
