package com.hfad.AVc;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hfad.starbuzz.R;

//Помощники SQLite должны расширять класс SQLiteOpenHelper.
class StarbuzzDatabaseHelper extends SQLiteOpenHelper{
    private static final String DB_NAME = "starbuzz"; // Имя базы данных
    private static final int DB_VERSION = 2; // Версия базы данных
    StarbuzzDatabaseHelper(Context context){
        //Вызываем конструктор суперкласса SQLiteOpenHelper и передаем ему имя и версию базы данных
        super(context, DB_NAME, null, DB_VERSION);
        //  параметр null -  параметр используется для работы с курсорами.
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        updateMyDatabase(db, 0, DB_VERSION);

    }

    /**
     * Метод onUpgrade() вызывается тогда, когда возникнет необходимость в обновлении базы данных.
     * Мы рассмотрим его на следующей странице.
     * @param db - бд
     * @param oldVersion - номер старой версии
     * @param newVersion - номер новой версии
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    /**
     Необходимо вставить данные нескольких напитков, поэтому мы создали для вставки отдельный метод.
     */
    private static void insertDrink(SQLiteDatabase db, String name,
                                    String description, int resourceId) {
        ContentValues drinkValues = new ContentValues();
        drinkValues.put("NAME", name);
        drinkValues.put("DESCRIPTION", description);
        drinkValues.put("IMAGE_RESOURCE_ID", resourceId);
        db.insert("DRINK", null, drinkValues);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            //Создание БД
            //execSQL(String sql) - Выполнить команду SQL, заданную в строковом виде.
            db.execSQL("CREATE TABLE DRINK (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "DESCRIPTION TEXT, "
                    + "IMAGE_RESOURCE_ID INTEGER);");
            //Заполнение БД
            insertDrink(db, "Latte", "Espresso and steamed milk", R.drawable.latte);
            insertDrink(db, "Cappuccino", "Espresso, hot milk and steamed-milk foam",
                    R.drawable.cappuccino);
            insertDrink(db, "Filter", "Our best drip coffee", R.drawable.filter);
        }
        if (oldVersion < 2) {
            //Добавить числовой столбец FAVORITE в таблицу DRINK.
            db.execSQL("ALTER TABLE DRINK ADD COLUMN FAVORITE NUMERIC;");
        }
    }
}

