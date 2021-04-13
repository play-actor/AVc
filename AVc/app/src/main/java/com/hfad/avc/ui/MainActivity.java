package com.hfad.avc.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.hfad.avc.Applications;
import com.hfad.avc.BackButtonListener;
import com.hfad.avc.ChainHolder;
import com.hfad.avc.R;
import com.hfad.avc.interactor.LoadDBInteractor;
import com.hfad.avc.ui.database.AppDatabase;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import ru.terrakok.cicerone.Navigator;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;
import ru.terrakok.cicerone.commands.Command;

public class MainActivity extends MvpAppCompatActivity implements ChainHolder, IMainActivityViewModel {

    private List<WeakReference<Fragment>> chain = new ArrayList<>();
    @Inject
    NavigatorHolder navigatorHolder;
    LoadDBInteractor interactorLoad;

    @InjectPresenter
    MainActivityPresenter presenter;

    @ProvidePresenter
    MainActivityPresenter ProvidePresenterMainActivityPresenter() {
        return new MainActivityPresenter();
    }

    private Navigator navigator = new SupportAppNavigator(this, R.id.root) {
        @Override
        public void applyCommands(Command[] commands) {
            super.applyCommands(commands);
            getSupportFragmentManager().executePendingTransactions();
        }
    };
    public AppDatabase db = Applications.getInstance().getDatabase();
    String TAG = "AVc";
    private final int PERMISSION_REQUEST_CODE = 666;
    public static final String PERMISSION_STRING = android.Manifest.permission.READ_CONTACTS;
    public CoordinatorLayout coordLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        coordLayout = findViewById(R.id.mainFragApp);
        Applications.INSTANCE.getContactCompanent().inject(this);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        if (intent != null) {
            String textTemplate = intent.getStringExtra("TextTemplate");
            String phone = intent.getStringExtra("Phone");
            if (textTemplate != null & phone != null) {
                smsSend(phone, textTemplate);
            }
        }
        updateDB();
    }

    @SuppressLint("IntentReset")
    private void smsSend(String toSms, String messageText) {
        try {
            Intent sms = new Intent(Intent.ACTION_SEND, Uri.parse("smsto:" + art(toSms)));
            sms.setType("text/plain");
            sms.putExtra(Intent.EXTRA_TEXT, messageText);
            startActivity(Intent.createChooser(sms, "Отправить"));
        } catch (Exception ex) {
            Log.e("AVc", "", ex);
        }
    }

    private String art(String st) {
        String taboo = "+-";
        for (char c : taboo.toCharArray()) {
            st = st.replace(c, ' ');
            st = st.replaceAll(" ", "");
        }
        return st;
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
    @SuppressWarnings({"UnnecessaryReturnStatement", "ConditionCoveredByFurtherCondition"})
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
        this.presenter.openTemplateWrite();
    }

    public void onClickWorkBase(MenuItem item) {
        this.presenter.openDbContact();
    }

    public void onClickTemplateBase(MenuItem item) {
        this.presenter.openTemplateList();
    }

    @Override
    public List<WeakReference<Fragment>> getChain() {
        return chain;
    }


    public void updateDB() {
        String[] countries = getResources().getStringArray(R.array.congratulations_templates);
        this.interactorLoad = Applications.INSTANCE.getHelperInteractors().getContactInteractor();
        if (ContextCompat.checkSelfPermission(this, PERMISSION_STRING)
                == PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS}, PERMISSION_REQUEST_CODE);
            if (ContextCompat.checkSelfPermission(this, PERMISSION_STRING)
                    == PackageManager.PERMISSION_GRANTED) {
                updateDB();
            }
        }
        this.interactorLoad.loadDB(countries)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list ->
                                Log.i(TAG, "Обновление базы данных завершено. " +
                                        "Количество записей: " + list.size()),
                        Throwable::printStackTrace);
    }



}