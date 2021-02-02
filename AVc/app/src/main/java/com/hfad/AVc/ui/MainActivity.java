package com.hfad.AVc.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.hfad.AVc.R;
import com.hfad.AVc.ui.main_fragment.MainFragment;


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
}