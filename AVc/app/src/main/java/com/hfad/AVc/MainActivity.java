package com.hfad.AVc;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CursorAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity {
    private ShareActionProvider shareActionProvider;
    private SQLiteDatabase db;
    private Cursor favoritesCursor;
    private CursorAdapter favoriteAdapter;

    public String TAG = "AVc";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SectionsPagerAdapter pagerAdapter =
                new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager pager = findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);

        //Связывание ViewPager с TabLayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);

    }

    public void onShowBD(View view) {
        Intent intent = new Intent(MainActivity.this,
                NameListActivity.class);
        startActivity(intent);
    }

    public void onLoadBD(View view) {
        //Applications.INSTANCE.getAVcDatabaseHelper().loadDB();
        new PreLoad();
        Snackbar.make(findViewById(R.id.root), "ddd", BaseTransientBottomBar.LENGTH_LONG).show();


    }

    private class PreLoad extends AsyncTask<Integer, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Integer... integers) {
            Applications.INSTANCE.getAVcDatabaseHelper().loadDB();
            return false;
        }
    }


    private class SectionsPagerAdapter extends FragmentPagerAdapter {
        /**
         * Необходимо наличие конструктора, получающего параметр FragmentManager.
         */
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        /**
         * Необходимо переопределить метод getCount() для определения количества страниц в ViewPager.
         */
        public int getCount() {
            return 2;
        }

        @Override
        /**
         * Необходимо указать, какой фрагмент должен
         * выводиться на каждой странице. Позиция определяет номер страницы (начиная с 0).
         */
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new Db_Fragment();
                case 1:
                    return new CongratulationsFragment();
            }
            return null;
        }

        @Override
        /**
         * Новый метод в созданной ранее реализации FragmentPagerAdapter.
         */
        public CharSequence getPageTitle(int position) {
            switch (position) {
                /**
                 * В этих строках кода добавляются строковые ресурсы для вкладок.
                 */
                case 0:
                    return getResources().getText(R.string.base_work);
                case 1:
                    return getResources().getText(R.string.congratulations);
            }
            return null;
        }
    }

    /**
     * Метод onDestroy() вызывается непосредственно перед уничтожением активности. Мы  закрываем
     * курсор и базу данных  в этом методе, так как после  уничтожения активности они
     * уже понадобятся.
     */
    //Закрытие курсора и базы данных в методе onDestroy() и закрытие БД
    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            favoritesCursor.close();
            db.close();
        } catch (Exception e) {
            Log.e(TAG, "База не открыта");
        }
    }
}