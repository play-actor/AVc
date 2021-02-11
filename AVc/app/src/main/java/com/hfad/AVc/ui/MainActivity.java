package com.hfad.avc.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.hfad.avc.R;
import com.hfad.avc.ui.main_fragment.Db_Fragment;
import com.hfad.avc.ui.main_fragment.MainFragment;
import com.hfad.avc.ui.namelist.NameListFragment;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainFragment mainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.root, mainFragment)
                .commit();



    }

    public void OnMenuItemClickListener(View view) {
        /*Db_Fragment db_fragment = new Db_Fragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.root, db_fragment,"db_fragment")
                .addToBackStack("db_fragment")
                .commit();*/
        Snackbar.make(view,"Здесь могла быть ваша реклама", BaseTransientBottomBar.LENGTH_LONG).show();
    }

    public void onClickWorkBase(MenuItem item) {
        Db_Fragment db_fragment = new Db_Fragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.root, db_fragment,"db_fragment")
                .addToBackStack("db_fragment")
                .commit();
    }



}