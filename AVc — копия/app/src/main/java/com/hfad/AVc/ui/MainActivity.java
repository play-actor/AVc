package com.hfad.AVc.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.hfad.AVc.Applications;
import com.hfad.AVc.R;
import com.hfad.AVc.ui.database.AppDatabase;
import com.hfad.AVc.ui.database.Contact;
import com.hfad.AVc.ui.database.Db_Fragment;
import com.hfad.AVc.ui.main_fragment.MainFragment;


public class MainActivity extends AppCompatActivity {
    public AppDatabase db= Applications.getInstance().getDatabase();
    //private Router router;
    String TAG = "AVc";
    //private Navigator navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainFragment mainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.root, mainFragment)
                .commit();
        //router = Applications.INSTANCE.getRouter();
    }

/*    public void onBackCommandClick() {
        router.exit();
    }

    public void onForwardCommandClick() {
        //router.navigateTo(new SomeScreen());
    }*/


    public void OnMenuItemClickListener(View view) {
        Contact сontact = new Contact();
        сontact.setId("665");
        сontact.setName("Satana");
        сontact.setPhone("4545");
        сontact.setFavorite(true);
        db.contactDao().insert(сontact);
        Snackbar.make(view,сontact.toString(), BaseTransientBottomBar.LENGTH_LONG).show();
        Log.i(TAG, "OnMenuItemClickListener: " + сontact.toString());
    }

    public void onClickWorkBase(MenuItem item) {
        Db_Fragment db_fragment = new Db_Fragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.root, db_fragment, "db_fragment")
                .addToBackStack("db_fragment")
                .commit();
    }
}