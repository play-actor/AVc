package com.hfad.avc.ui;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.hfad.avc.Applications;

import com.hfad.avc.interactor.SendIteractor;

import java.util.Date;
import java.util.concurrent.TimeUnit;


public class SendWorker extends Worker {
    static final String TAG = "AVc workmng";
    Date dateCon = null;


    SendIteractor interactor;
    private final int NOTIFICATION_ID = 466;
    private final String IFICATION_ID = "466";
    private final int PERMISSION_REQUEST_CODE = 666;

    private final Context context;


    public SendWorker(
            Context context,
            WorkerParameters params) {
        super(context, params);
        this.context = context;
    }

    @Override
    public Result doWork() {
        Data inputData = getInputData();
        Log.d(TAG, "doWork: start");
        Log.i(TAG, String.valueOf(inputData));
        this.interactor = Applications.INSTANCE.getHelperInteractors().getSendIteractor();
        try {
/*            CustomDialogFragment dialog = new CustomDialogFragment();
            dialog.show(getSupportFragmentManager(), "custom");*/
            NotificationChannel channel = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                channel = new NotificationChannel(IFICATION_ID, "asff", NotificationManager.IMPORTANCE_HIGH);
                NotificationManagerCompat.from(this.context).createNotificationChannel(channel);
            }
            Intent actionIntent = new Intent(this.context, MainActivity.class);
            actionIntent.putExtra("Name", inputData.getString("Name"));
            actionIntent.putExtra("Phone", inputData.getString("Phone"));
            actionIntent.putExtra("TextTemplate", inputData.getString("TextTemplate"));
            PendingIntent actionPendingIntent = PendingIntent.getActivity(
//            PendingIntent actionPendingIntent = PendingIntent.getBroadcast(
                    // Интент используется для создания отложенного интента.
                    this.context,//Контекст (в данном случае текущая служба).
                    0,//Этот флаг должен использоваться в том случае, если понадобится получить отложенный интент. Нам это не нужно, поэтому передается 0.
                    actionIntent,//Интент, созданный выше
                    PendingIntent.FLAG_UPDATE_CURRENT);//Если соответствующий отложенный интент существует, он будет обновлен

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this.context, IFICATION_ID)
                //Эти настройки необходимы для всех уведомлений.
                .setSmallIcon(android.R.drawable.ic_menu_compass)
                .setContentTitle("Предлогаю поздравить абонента: " + inputData.getString("Name"))
                //А эти — только для всплывающих уведомлений.
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(new long[]{1000, 1000})
                .setAutoCancel(true)//Благодаря этой строке уведомление исчезает после щелчка.
                /*      .addAction(R.drawable.ic_add_24dp, "Да",
                              this.interactor.smsSend(this.context, inputData.getString("Phone"), inputData.getString("TextTemplate")))*/
                .setContentIntent(actionPendingIntent);
        ;

        //Выдача уведомления
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this.context);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
        TimeUnit.SECONDS.sleep(1);
    } catch(
    InterruptedException e)

    {
        e.printStackTrace();
        Log.d(TAG, String.valueOf(e));
    }
        Log.d(TAG,"doWork: end");
        return Result.success();
}
}
