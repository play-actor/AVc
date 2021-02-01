package com.hfad.AVc;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Этот код нужен для работы с БД
 */
public class ContactActivity extends Activity {
    public static final String EXTRA_DRINKID = "drinkId";
    private CheckBox favorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        favorite = (CheckBox) findViewById(R.id.favorite);
        //Получение напитка из интента
        int drinkId = (Integer)getIntent().getExtras().get(EXTRA_DRINKID);
        //Создание курсора
        SQLiteOpenHelper AVcDatabaseHelper = new AVcDatabaseHelper(this);
        try {
            //Получение ссылки на базу данных
            SQLiteDatabase db = AVcDatabaseHelper.getReadableDatabase();
            //Создаем курсор для получения названия, описания и идентификатора ресурса выбранного
            //пользователем напитка.
            Cursor cursor = db.query ("PhoneDB",
                    new String[] {"NAME", "Phone", "Activate"},
                    "_id = ?",
                    new String[] {Integer.toString(drinkId)},
                    null, null, null);
            //Переход к первой записи в курсоре
            //moveToFirst - возвращает true, если запись успешно найдена
            Log.d("Cursor ", "ok");
            if (cursor.moveToFirst()) {
                /**
                 * Получение данных напитка из курсора и расфасовка их по переменным
                 *
                 * Название напитка хранится в первом столбце курсора, описание — во втором, а
                 * идентификатор ресурса изображения — в третьем. Вспомните, что столбцы NAME,
                 * DESCRIPTION и IMAGE_RESOURCE_ID базы данных были включены в курсор именно
                 * в таком порядке
                 */
                String nameText = cursor.getString(0);
                String descriptionText = cursor.getString(1);


                //Если столбец FAVORITE содержит 1, это соответствуетзначению true
                boolean isFavorite = (cursor.getInt(2) == 1);

                //Заполнение названия напитка
                TextView name = (TextView)findViewById(R.id.name);
                name.setText(nameText);

                //Заполнение описания напитка
                TextView description = (TextView)findViewById(R.id.description);
                description.setText(descriptionText);

                //Заполнение флажка любимого напитка
                CheckBox favorite = (CheckBox)findViewById(R.id.favorite);
                favorite.setChecked(isFavorite);

            }
            //Эти строки закрывают курсор и базу данных.
            cursor.close();
            db.close();
        } catch(SQLiteException e) {//При выдаче исключенияSQLiteException выводится уведомление.
            Toast toast = Toast.makeText(this,
                    "Database unavailable",
                    Toast.LENGTH_SHORT);
            //Если выдается исключение SQLiteException,значит, с базой данных возникли проблемы.
            //В этом случае уведомление используется для вывода сообщения для пользователя.
            toast.show();
        }
    }

    //Обновление базы данных по щелчку на флажке
    public void onFavoriteClicked(View view){
        int drinkId = (Integer) getIntent().getExtras().get(EXTRA_DRINKID);
        /**
         * Код далее использовался при выполнении в главном потоке, при использовании нескольких
         * потоков заменен на UpdateDrinkTask
         */
        //Получение значения флажка
        /*CheckBox favorite = (CheckBox) findViewById(R.id.favorite);
        ContentValues drinkValues = new ContentValues();
        drinkValues.put("FAVORITE", favorite.isChecked());
        //Получение ссылки на базу данных и обновление столбца FAVORITE
        SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
        try {
            SQLiteDatabase db = starbuzzDatabaseHelper.getWritableDatabase();
            //Обновить столбец FAVORITE текущим значением флажка.
            db.update("DRINK",
                    drinkValues,
                    "_id = ?",
                    new String[] {Integer.toString(drinkId)});
            db.close();
        } catch(SQLiteException e) {
            //Вывести сообщение при возникновении проблемы с базой данных
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }*/
        new UpdateDrinkTask().execute(drinkId);
    }


    //Реализация AsyncTask добавляется в активность в виде внутреннего класса.
    private class UpdateDrinkTask extends AsyncTask<Integer, Void, Boolean> {
        private ContentValues drinkValues;

        /**
         * Мы определили drinkValues как приватную  переменную, так как она используется только
         * в методах onExecute() и doInBackground().
         */
        protected void onPreExecute() {
            //Перед выполнением кода базы данных значение флажка помещается в объект
            //drinkValues типа ContentValues.

            drinkValues = new ContentValues();
            //Прежде чем выполнять код базы данных, значение флажка favorite помещается в объект
            // drinkValues типа ContentValues.
            drinkValues.put("Activate", favorite.isChecked());
        }
        //Код базы данных содержится в методе doInBackground() и выполняется в фоновом потоке.
        protected Boolean doInBackground(Integer... drinks) {
            int drinkId = drinks[0];
            SQLiteOpenHelper AVcDatabaseHelper =
                    Applications.INSTANCE.getAVcDatabaseHelper();
            try {
                SQLiteDatabase db = AVcDatabaseHelper.getWritableDatabase();
                db.update("PhoneDB", drinkValues,//Обновление значениястолбца FAVORITE.
                        "_id= ?", new String[] {Integer.toString(drinkId)});
                db.close();
                return true;
            } catch(SQLiteException e) {
                return false;
            }
        }


        //После выполнения кода базы данных в фоновом режиме следует проверить, успешно ли он
        //был выполнен. Если при выполнении произошла ошибка, выводится сообщение об ошибке.
        protected void onPostExecute(Boolean success) {
            if (!success) {
                Toast toast = Toast.makeText(ContactActivity.this,
                        "Database unavailable", Toast.LENGTH_SHORT);
                //Код вывода сообщения включается в метод onPostExecute(), так как он должен
                //выполняться в основном потоке событий для обновления экрана.
                toast.show();
            }
        }
    }

}