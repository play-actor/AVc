package com.hfad.AVc.ui.database;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.hfad.AVc.Applications;
import com.hfad.AVc.R;
import com.hfad.AVc.interactor.LoadDBInteractor;
import com.hfad.AVc.ui.namelist.NameListFragment;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Db_Fragment extends Fragment {
    public AppDatabase db;
    private LoadDBInteractor interactor;
    public String TAG = "AVc";

    public Db_Fragment() {
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_db_, container, false);
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
        Snackbar.make(requireView(), "Загрузка БД", BaseTransientBottomBar.LENGTH_LONG).show();
        this.interactor = Applications.INSTANCE.getHelperInteractors().getContactInteractor();
        this.interactor.loadDB()
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
}