package com.hfad.avc.ui;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.hfad.avc.Applications;
import com.hfad.avc.BackButtonListener;
import com.hfad.avc.ChainHolder;
import com.hfad.avc.R;
import com.hfad.avc.Screens;
import com.hfad.avc.interactor.LoadDBInteractor;
import com.hfad.avc.interactor.SendIteractor;
import com.hfad.avc.ui.database.AppDatabase;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Forward;
import ru.terrakok.cicerone.commands.Replace;

public class MainActivity extends AppCompatActivity implements ChainHolder {

    private List<WeakReference<Fragment>> chain = new ArrayList<>();
    @Inject
    NavigatorHolder navigatorHolder;
    SendIteractor interactor;
    LoadDBInteractor interactorLoad;
    //private boolean send = false;
    private Navigator navigator = new SupportAppNavigator(this, R.id.root) {
        @Override
        public void applyCommands(Command[] commands) {
            super.applyCommands(commands);
            getSupportFragmentManager().executePendingTransactions();
        }
    };
    public AppDatabase db = Applications.getInstance().getDatabase();
    String TAG = "AVc";
    private final int NOTIFICATION_ID = 423;
    private final String IFICATION_ID = "423";
    private final int PERMISSION_REQUEST_CODE = 666;
    String textTemplate,phone;
    public static final String PERMISSION_STRING = android.Manifest.permission.READ_CONTACTS;
    SharedPreferences sPref;
    String FIRST_LOAD_OK = "";

    public CoordinatorLayout coordLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Applications.INSTANCE.getContactCompanent().inject(this);
        setContentView(R.layout.activity_main);
        navigator.applyCommands(new Command[]{new Replace(new Screens.MainScreen())});
        Intent intent = getIntent();
        this.textTemplate = intent.getStringExtra("TextTemplate");
        this.phone = intent.getStringExtra("Phone");
        this.interactor = Applications.INSTANCE.getHelperInteractors().getSendIteractor();
        if (textTemplate != null & phone != null) {
            //this.send = true;
            this.interactor.smsSend(this, phone, textTemplate);
            try {
                TimeUnit.SECONDS.sleep(5);
                finishAndRemoveTask();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Log.d(TAG, String.valueOf(e));
            }
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        this.navigatorHolder.setNavigator(this.navigator);
    }

    @Override
    protected void onPause() {
        this.navigatorHolder.removeNavigator();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.root);
        if (fragment != null
                && fragment instanceof BackButtonListener
                && ((BackButtonListener) fragment).onBackPressed()) {
            return;
        } else {
            super.onBackPressed();
        }
    }

    public void OnMenuItemClickListener(View view) {
        this.navigator.applyCommands(new Command[]{new Forward(new Screens.TemplateWriteScreen(-1))});
    }

    public void onClickWorkBase(MenuItem item) {
        this.navigator.applyCommands(new Command[]{new Forward(new Screens.DbScreen())});
    }

    public void onClickTemplateBase(MenuItem item) {
        this.navigator.applyCommands(new Command[]{new Forward(new Screens.TemplateScreen())});
    }

    public void onClickLoad(MenuItem item) {
        onLoadBD();
    }

    @Override
    public List<WeakReference<Fragment>> getChain() {
        return chain;
    }

    public void onLoadBD() {
        if (ContextCompat.checkSelfPermission(this, PERMISSION_STRING)
                == PackageManager.PERMISSION_GRANTED) {
            forLoadBD();
            sPref = this.getSharedPreferences("MyPref", MODE_PRIVATE);
            SharedPreferences.Editor ed = sPref.edit();
            ed.putBoolean(FIRST_LOAD_OK, true).apply();
            Log.i(TAG, "FIRST_LOAD_OK = " + sPref.getBoolean(FIRST_LOAD_OK, false));
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS}, PERMISSION_REQUEST_CODE);
        }
    }

    private void forLoadBD() {
        coordLayout = findViewById(R.id.mainFragApp);
        String[] countries = getResources().getStringArray(R.array.congratulations_templates);
        this.interactorLoad = Applications.INSTANCE.getHelperInteractors().getContactInteractor();
        sPref = this.getSharedPreferences("MyPref", MODE_PRIVATE);
        Log.i(TAG, "FIRST_LOAD_OK: " + sPref.getBoolean(FIRST_LOAD_OK, false));
        if (!sPref.getBoolean(FIRST_LOAD_OK, false)) {
            Snackbar.make(coordLayout, "Загрузка БД", BaseTransientBottomBar.LENGTH_LONG).show();
            this.interactorLoad.AddTemplateFirstInsert(countries);
            this.interactorLoad.loadDB()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(list -> Snackbar.make(coordLayout, "Загрузка БД завершена "
                                    + list.size(), BaseTransientBottomBar.LENGTH_LONG).show(),
                            Throwable::printStackTrace);
        } else {
            Snackbar.make(coordLayout, "Загрузка была произведена ранее", BaseTransientBottomBar.LENGTH_LONG).show();
        }
    }
}