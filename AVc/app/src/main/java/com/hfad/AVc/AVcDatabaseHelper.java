package com.hfad.AVc;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

//Помощники SQLite должны расширять класс SQLiteOpenHelper.
public class AVcDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "AVcDatabase"; // Имя базы данных
    private static final int DB_VERSION = 1; // Версия базы данных

    public AVcDatabaseHelper(Context context) {
        //Вызываем конструктор суперкласса SQLiteOpenHelper и передаем ему имя и версию базы данных
        super(context, DB_NAME, null, DB_VERSION);
        //  параметр null -  параметр используется для работы с курсорами.
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
    }

    /**
     * Метод onUpgrade() вызывается тогда, когда возникнет необходимость в обновлении базы данных.
     *
     * @param db - бд
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
        /*if (oldVersion < 2) {
            //Добавить числовой столбец FAVORITE в таблицу DRINK.
            db.execSQL("ALTER TABLE DRINK ADD COLUMN FAVORITE NUMERIC;");
        }*/
    }
}
