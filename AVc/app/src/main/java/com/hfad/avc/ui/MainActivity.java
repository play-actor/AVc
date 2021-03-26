package com.hfad.avc.ui;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.hfad.avc.Applications;
import com.hfad.avc.BackButtonListener;
import com.hfad.avc.ChainHolder;
import com.hfad.avc.R;
import com.hfad.avc.Screens;
import com.hfad.avc.interactor.SendIteractor;
import com.hfad.avc.ui.database.AppDatabase;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

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
    private boolean send = false;

    private Navigator navigator = new SupportAppNavigator(this, R.id.root) {
        @Override
        public void applyCommands(Command[] commands) {
            super.applyCommands(commands);
            getSupportFragmentManager().executePendingTransactions();
        }
    };

    public AppDatabase db = Applications.getInstance().getDatabase();
    //private Router router;
    String TAG = "AVc";
    private final int NOTIFICATION_ID = 423;
    private final String IFICATION_ID = "423";
    private final int PERMISSION_REQUEST_CODE = 666;
    DialogInterface.OnClickListener myClickListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Applications.INSTANCE.getContactCompanent().inject(this);
        setContentView(R.layout.activity_main);
        navigator.applyCommands(new Command[]{new Replace(new Screens.MainScreen())});
        myClickListener = (dialog, which) -> {
            switch (which) {
                // положительная кнопка
                case Dialog.BUTTON_POSITIVE:
                    break;
                // негативная кнопка
                case Dialog.BUTTON_NEGATIVE:
                    break;
                // нейтральная кнопка
                case Dialog.BUTTON_NEUTRAL:
                    break;
            }
        };
        Intent intent = getIntent();
        String TextTemplate = intent.getStringExtra("TextTemplate");
        String phone = intent.getStringExtra("Phone");
        //Log.i(TAG, name+" "+phone);
        this.interactor = Applications.INSTANCE.getHelperInteractors().getSendIteractor();
        if (TextTemplate != null & phone != null) {
            this.send = true;
            //String s =
            this.interactor.smsSend(this, phone, TextTemplate);

        //finish();
        try {
            TimeUnit.SECONDS.sleep(5);
            finishAndRemoveTask();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d(TAG, String.valueOf(e));
        }
        }
        //}
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
/*        Contact сontact = new Contact();
        сontact.setId("665");
        сontact.setName("Satana");
        сontact.setPhone("4545");
        сontact.setFavorite(true);
        db.contactDao().insert(сontact);
        Snackbar.make(view, сontact.toString(), BaseTransientBottomBar.LENGTH_LONG).show();
        Log.i(TAG, "OnMenuItemClickListener: " + сontact.toString());*/
/*        Template template = new Template();
        template.setId("0");
        template.setTextTemplate("С днем рождения! Пусть жизнь будет беспрерывным потоком счастливых дней и прекрасных мгновений. Желаю назад оглядываться только лишь с хорошими воспоминаниями, вперёд смотреть с уверенностью в собственных силах и доброй надеждой, а в настоящем всегда оставаться замечательным человеком с любящим сердцем и открытой душой!");
        template.setFavorite(true);
        db.templateDao().insert(template);
        Snackbar.make(view, template.toString(), BaseTransientBottomBar.LENGTH_LONG).show();*/
        //Log.i(TAG, "OnMenuItemClickListener: " + template.toString());
  /*      ReminderFragment reminderFragment = new ReminderFragment();
                getSupportFragmentManager().beginTransaction()
                .replace(R.id.root, reminderFragment, "nameListFragment")
                .addToBackStack("nameListFragment")
                .commit();*/
/*        CustomDialogFragment dialog = new CustomDialogFragment();
        dialog.show(getSupportFragmentManager(), "custom");*/

    }


    public void onClickWorkBase(MenuItem item) {
        this.navigator.applyCommands(new Command[]{new Forward(new Screens.DbScreen())});
//        Db_Fragment db_fragment = new Db_Fragment();
//        getSupportFragmentManager().beginTransaction()
//                .replace(R.id.root, db_fragment, "db_fragment")
//                .addToBackStack("db_fragment")
//                .commit();
    }

    public void onClickTemplateBase(MenuItem item) {
        this.navigator.applyCommands(new Command[]{new Forward(new Screens.TemplateScreen())});
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {

        Log.i(TAG, "onRequestPermissionsResult - начало");
        Log.i(TAG, String.valueOf(requestCode));
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE: {//Проверить, совпадает ли код с тем, который был использован в методе requestPermissions().
                Log.i(TAG, String.valueOf(grantResults.length));
                if (grantResults.length > 0 //Если запрос был отменен, результаты не возвращаются.
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "onRequestPermissionsResult: +");
                    //делаем повторну загрузку
                } else {
                    NotificationChannel channel = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        channel = new NotificationChannel(IFICATION_ID, "asff", NotificationManager.IMPORTANCE_HIGH);
                        NotificationManagerCompat.from(this).createNotificationChannel(channel);
                    }
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(this, IFICATION_ID)
                            //Эти настройки необходимы для всех уведомлений.
                            .setSmallIcon(android.R.drawable.ic_menu_compass)
                            .setContentTitle("Приложение " + getResources().getString(R.string.app_name) + " не получило разрешений")
                            //А эти — только для всплывающих уведомлений.
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setVibrate(new long[]{1000, 1000})
                            .setAutoCancel(true);//Благодаря этой строке уведомление исчезает после щелчка.
                    //Выдача уведомления
                    NotificationManager notificationManager =
                            (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    notificationManager.notify(NOTIFICATION_ID, builder.build());
                }
            }
        }
    }

    @Override
    public List<WeakReference<Fragment>> getChain() {
        return chain;
    }

/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult: +");
//        finishAndRemoveTask();
//        Log.i(TAG, "finishAndRemoveTask: +");

    }

    @Override
    protected void onResume() {
        super.onResume();
//        Log.i(TAG, "onResume: +");
//        Intent intent = getIntent();
//        String TextTemplate = intent.getStringExtra("TextTemplate");
//        String phone = intent.getStringExtra("Phone");
//        //Log.i(TAG, name+" "+phone);
//        this.interactor = Applications.INSTANCE.getHelperInteractors().getSendIteractor();
//        if (TextTemplate != null & phone != null) {
//            String s = this.interactor.smsSend(this, phone, TextTemplate);
        //finish();
            *//*try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Log.d(TAG, String.valueOf(e));
            }*//*
        if (this.send) {
            this.send = false;
//            finishAndRemoveTask();
        }
//        }
        Log.i(TAG, "onResume: +");
    }*/
}