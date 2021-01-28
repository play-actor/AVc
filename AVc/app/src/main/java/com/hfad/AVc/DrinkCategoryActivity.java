package com.hfad.AVc;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.ListView;
import android.view.View;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.hfad.starbuzz.R;

public class DrinkCategoryActivity extends Activity {
    /**
     * Эти приватные переменные добавляются для того, чтобы базу данных и курсор можно было закрыть
     * в методе onDestroy()
     */
    private SQLiteDatabase db;
    private Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_category);

       /* Адаптер массива с бд не используется
       *//**
         * Списковое представление заполняется данными из массива drinks
         *//*
        ArrayAdapter<Drink> listAdapter = new ArrayAdapter<>(
                this,
                *//**
                 * Встроенный ресурсмакета. Он приказывает адаптеру массива отображать каждый
                 * элемент массива в виде надписи
                 *//*
                android.R.layout.simple_list_item_1,
                Drink.drinks);*/
        ListView listDrinks = (ListView) findViewById(R.id.list_drinks);
        //Получить ссылку на базу данных и создать курсор
        SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
        try {
            db = starbuzzDatabaseHelper.getReadableDatabase();
            cursor = db.query("DRINK",
                    new String[]{"_id", "NAME"},
                    null, null, null, null, null);
            //Создание адаптера курсора
            SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"NAME"},
                    new int[]{android.R.id.text1},
                    0);
            listDrinks.setAdapter(listAdapter);
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }




        //Создание слушателя
        AdapterView.OnItemClickListener itemClickListener =
                new AdapterView.OnItemClickListener(){
                    public void onItemClick(AdapterView<?> listDrinks,
                                            View itemView,
                                            int position,
                                            long id) {


                        //Передача напитка, выбранного пользователем, DrinkActivity
                        Intent intent = new Intent(DrinkCategoryActivity.this,
                                DrinkActivity.class);
                        /**
                         * Имя дополнительной информации в интенте обозначается константой, чтобы
                         * DrinkCategoryActivity и DrinkActivity заведомо использовали одну строку.
                         * Константа будет добавлена в DrinkActivity при создании активности.
                         */
                        intent.putExtra(DrinkActivity.EXTRA_DRINKID, (int) id);
                        startActivity(intent);
                    }
                };
        //Назначение слушателя для спискового представления
        listDrinks.setOnItemClickListener(itemClickListener);

    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        cursor.close();
        db.close();
    }
}