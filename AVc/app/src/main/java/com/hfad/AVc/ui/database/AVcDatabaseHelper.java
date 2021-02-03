package com.hfad.AVc.ui.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

import com.hfad.AVc.Applications;

import java.util.ArrayList;
import java.util.List;


//Помощники SQLite должны расширять класс SQLiteOpenHelper.
public class AVcDatabaseHelper extends SQLiteOpenHelper {

    public String TAG = "AVc";
    private static final String DB_NAME = "AVcDatabase"; // Имя базы данных
    private static final int DB_VERSION = 4; // Версия базы данных
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
        // loadDB(db);
        //Log.i(TAG, "loadDB: ok");
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
    }

    //Метод для заполнения БД
    public void insertDB(SQLiteDatabase db, String NAME, String Phone, boolean Activate) {
        ContentValues ContactValues = new ContentValues();
        ContactValues.put("NAME", NAME);
        ContactValues.put("Phone", Phone);
        ContactValues.put("Activate", Activate);
        db.insert("PhoneDB", null, ContactValues);
        Log.i("Контакт добавлен в БД", String.valueOf(ContactValues));
    }

    //Метод обновления БД
    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            //Создание БД
            //execSQL(String sql) - Выполнить команду SQL, заданную в строковом виде.
            db.execSQL("CREATE TABLE PhoneDB (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "Phone TEXT, "
                    + "Activate NUMERIC);");
         }
    }



    public void loadDB() {
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
            while (cursor.moveToNext()) {

                Contact contact = new Contact();
                String id = cursor.getString(
                        cursor.getColumnIndex(
                                ContactsContract.Contacts._ID));
                /*contact.setId(id);*/

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
                        ContactValues.put("Phone", phone);
                        contact.setPhone(phone);
                    }
                    pCur.close();

                    contacts.add(contact);
                }

            }

            for (Contact contact : contacts) {
                insertDB(db, contact.getName(), contact.getPhone(), false);
                Log.i(TAG, contact.getName());
            }

        }


    }

}
