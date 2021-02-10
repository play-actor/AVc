package com.hfad.AVc.ui.main_fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import androidx.appcompat.widget.ShareActionProvider;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.hfad.AVc.Applications;
import com.hfad.AVc.R;
import com.hfad.AVc.ui.namelist.NameListFragment;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Db_Fragment extends Fragment {

    private ShareActionProvider shareActionProvider;
    private SQLiteDatabase db;
    private Cursor favoritesCursor;
    private CursorAdapter favoriteAdapter;

    public String TAG = "AVc";

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_db_, container, false);
        // Inflate the layout for this fragment
        view.findViewById(R.id.button2).setOnClickListener(v -> onShowBD());
        view.findViewById(R.id.button3).setOnClickListener(v -> onLoadBD());
        return view;
    }

    public void onShowBD() {
        NameListFragment nameListFragment = new NameListFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.root, nameListFragment, "nameListFragment")
                .addToBackStack("nameListFragment")
                .commit();

    }

    public void onLoadBD() {
        //new PreLoad();
        Snackbar.make(requireView(), "Загрузка БД", BaseTransientBottomBar.LENGTH_LONG).show();
        Applications.INSTANCE.getAVcDatabaseHelper().loadDB()
                //обрабатываем в потоке ввода.вывода
                //subscribeOn — это оператор, который задаёт поток выполнения работы
                .subscribeOn(Schedulers.io())
                //выводим в главном
                .observeOn(AndroidSchedulers.mainThread())
                //подписываемся
                .subscribe(list -> Snackbar.make(requireView(), "Загрузка БД завершена "
                                 + list.size(), BaseTransientBottomBar.LENGTH_LONG).show(),
                        Throwable::printStackTrace);
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
            Applications.INSTANCE.getAVcDatabaseHelper().getDb().close();
        } catch (Exception e) {
            Log.e("AVc", "База не открыта");
        }
    }
}