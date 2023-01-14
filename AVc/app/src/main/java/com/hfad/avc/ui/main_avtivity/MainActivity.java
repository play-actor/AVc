package com.hfad.avc.ui.main_avtivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.terrakok.cicerone.Command;
import com.github.terrakok.cicerone.Navigator;
import com.github.terrakok.cicerone.NavigatorHolder;
import com.hfad.avc.ChainHolder;
import com.hfad.avc.R;
import com.hfad.avc.bus.RxBus;
import com.hfad.avc.dagger.ComponentManager;
import com.hfad.avc.dagger.ExtSupportAppNavigator;
import com.hfad.avc.managers.DBManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;
import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

public class MainActivity extends MvpAppCompatActivity implements ChainHolder, IMainViewModel {

   private List<WeakReference<Fragment>> chain = new ArrayList<>();
   @Inject
   NavigatorHolder navigatorHolder;
   @Inject
   DBManager dbManager;
   @Inject
   RxBus rxBus;

   @InjectPresenter
   MainPresenter presenter;

   @ProvidePresenter
   MainPresenter ProvidePresenterMainActivityPresenter() {
      return new MainPresenter();
   }

   private Navigator navigator = new ExtSupportAppNavigator(this,R.id.root) {
      @Override
      public void applyCommands(Command[] commands) {
         super.applyCommands(commands);
         getSupportFragmentManager().executePendingTransactions();
      }
   };
   String TAG = "MainActivity";
   private final int PERMISSION_REQUEST_CODE_READ_CONTACTS = 1;
   public CoordinatorLayout coordLayout;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      ComponentManager.Companion.getInstance().appComponent.inject(this);
      super.onCreate(savedInstanceState);
      coordLayout = findViewById(R.id.mainFragApp);
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

   @Override
   public List<WeakReference<Fragment>> getChain() {
      return chain;
   }


   public boolean updateDB() {
      String[] countries = getResources().getStringArray(R.array.congratulations_templates);
      if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
            == PackageManager.PERMISSION_GRANTED) {
         this.dbManager.loadDB(countries)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(list -> {
                        //toolbar.setSubtitle("Количество записей: "+list.size());
                        Log.i(TAG, "Обновление базы данных завершено. " +
                              "Количество записей: " + list.size());
                     },
                     Throwable::printStackTrace);
      return true;
      } else {
         ActivityCompat.requestPermissions(this,
               new String[]{Manifest.permission.READ_CONTACTS}, PERMISSION_REQUEST_CODE_READ_CONTACTS);
         return false;
      }
   }

   @Override
   public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
      if (requestCode == PERMISSION_REQUEST_CODE_READ_CONTACTS) {
         updateDB();
      }
      rxBus.send(requestCode);
      super.onRequestPermissionsResult(requestCode, permissions, grantResults);
   }
}