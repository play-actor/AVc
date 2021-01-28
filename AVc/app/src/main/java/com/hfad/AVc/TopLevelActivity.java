package com.hfad.AVc;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.hfad.starbuzz.R;

public class TopLevelActivity extends Activity {

    private SQLiteDatabase db;
    private Cursor favoritesCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);
        setupOptionsListView();
        setupFavoritesListView();
    }
    private void setupOptionsListView() {
        //Создать OnItemClickListener
        AdapterView.OnItemClickListener itemClickListener =
                new AdapterView.OnItemClickListener(){
                    /**
                     * Представление, на котором был сделан щелчок (списковое представление в данном
                     * случае).
                     */
                    public void onItemClick(AdapterView<?> listView,
                                            /**
                                             * Дополнительная информация о варианте спискового
                                             * представления — например, представление и его позиция
                                             */
                                            View itemView,
                                            int position,
                                            long id) {
                        /**
                         * Drinks — первый вариант в списковом представлении —
                         * находится в позиции 0
                         */
                        if (position == 0) {
                            /**
                             * Интент выдается TopLevelActivity.
                             * Должен запускать DrinkCategoryActivity.
                             */
                            Intent intent = new Intent(TopLevelActivity.this,
                                    DrinkCategoryActivity.class);
                            startActivity(intent);
                        }
                    }
                };
        //Добавить слушателя к списковому представлению
        ListView listView = (ListView) findViewById(R.id.list_options);
        listView.setOnItemClickListener(itemClickListener);
    }

    private void setupFavoritesListView() {
        //Заполнение списка list_favorites по данным курсора
        ListView listFavorites = (ListView) findViewById(R.id.list_favorites);
        try{
            SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
            db = starbuzzDatabaseHelper.getReadableDatabase();
            favoritesCursor = db.query("DRINK",
                    new String[] { "_id", "NAME"},
                    "FAVORITE = 1",
                    null, null, null, null);
            CursorAdapter favoriteAdapter =
                    new SimpleCursorAdapter(TopLevelActivity.this,
                            android.R.layout.simple_list_item_1,
                            //Использовать курсор в адаптере курсора.
                            favoritesCursor,
                            //Вывести названия напитков в списковом представлении.
                            new String[]{"NAME"},
                            new int[]{android.R.id.text1}, 0);
            listFavorites.setAdapter(favoriteAdapter);
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        //Переход к DrinkActivity при выборе напитка
        //Списковое представление list_favorites реагирует на щелчки.
        listFavorites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View v, int position, long id) {
                Intent intent = new Intent(TopLevelActivity.this, DrinkActivity.class);
                //Запустить DrinkActivity и передать идентификатор выбранного напитка.
                intent.putExtra(DrinkActivity.EXTRA_DRINKID, (int)id);
                startActivity(intent);
            }
        });
    }

    /**
     * Добавление метода onRestart(). Этот  метод вызывается при возврате пользователя к TopLevelActivity.
     */
    @Override
    public void onRestart() {
        super.onRestart();
        Cursor newCursor = db.query("DRINK",
                new String[] { "_id", "NAME"},
                "FAVORITE = 1",
                null, null, null, null);
        ListView listFavorites = (ListView) findViewById(R.id.list_favorites);
        //Для получения адаптера ListView используется метод getAdapter().
        CursorAdapter adapter = (CursorAdapter) listFavorites.getAdapter();
        //Курсор, используемый list_favorites,заменяется новым курсором.
        adapter.changeCursor(newCursor);
        //Значение favoritesCursor заменяется новым курсором, чтобы его можно было закрыть в методе
        //onDestroy() активности.
        favoritesCursor = newCursor;
    }

    /**
     * Метод onDestroy() вызывается непосредственно перед уничтожением активности. Мы  закрываем
     * курсор и базу данных  в этом методе, так как после  уничтожения активности они
     * уже понадобятся.
     */
    //Закрытие курсора и базы данных в методе onDestroy() и закрытие БД
    @Override
    public void onDestroy(){
        super.onDestroy();
        favoritesCursor.close();
        db.close();
    }
}