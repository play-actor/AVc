package com.hfad.avc.ui.database;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.hfad.avc.Applications;
import com.hfad.avc.R;
import com.hfad.avc.interactor.LoadDBInteractor;
import com.hfad.avc.ui.namelist.NameListFragment;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static android.content.Context.MODE_PRIVATE;

public class Db_Fragment extends Fragment {

    private LoadDBInteractor interactor;

    public String TAG = "AVc";
    public static final String PERMISSION_STRING = android.Manifest.permission.READ_CONTACTS;
    private final int PERMISSION_REQUEST_CODE = 666;
    private final int NOTIFICATION_ID = 423;
    private final String IFICATION_ID = "423";
    SharedPreferences sPref;
    String FIRST_LOAD_OK = "";


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

        // Log.i(TAG, String.valueOf(countries));
        Log.i(TAG, "onLoadBD: +");
        if (ContextCompat.checkSelfPermission(getActivity(), PERMISSION_STRING)
                == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "разрешение: +");
            forLoadBD();
            sPref = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
            SharedPreferences.Editor ed = sPref.edit();
            ed.putBoolean(FIRST_LOAD_OK, true).apply();

            Log.i(TAG, "FIRST_LOAD_OK = " + sPref.getBoolean(FIRST_LOAD_OK, false));
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.READ_CONTACTS}, PERMISSION_REQUEST_CODE);
            Log.i(TAG, "разрешение: -");
        }


    }

    private void forLoadBD() {
        String[] countries = getResources().getStringArray(R.array.congratulations_templates);
        //Log.i(TAG, "Загрузка countries"+countries);
        this.interactor = Applications.INSTANCE.getHelperInteractors().getContactInteractor();
        sPref = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
        Log.i(TAG, "FIRST_LOAD_OK: " + sPref.getBoolean(FIRST_LOAD_OK, false));
        if (!sPref.getBoolean(FIRST_LOAD_OK, false)) {
            Snackbar.make(requireView(), "Загрузка БД", BaseTransientBottomBar.LENGTH_LONG).show();
            this.interactor.AddTemplateFirstInsert(countries);
            this.interactor.loadDB()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(list -> Snackbar.make(requireView(), "Загрузка БД завершена "
                                    + list.size(), BaseTransientBottomBar.LENGTH_LONG).show(),
                            Throwable::printStackTrace);
            Log.i(TAG, "Загрузка БД: +");
        } else {
            Snackbar.make(requireView(), "Загрузка была произведена ранее", BaseTransientBottomBar.LENGTH_LONG).show();
        }
    }
}