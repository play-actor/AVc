package com.hfad.avc.managers;

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

import com.hfad.avc.R;
import com.hfad.avc.ui.main_avtivity.MainActivity;


public class WorkerManager extends Worker {
   static final String TAG = "WorkerManager";
   private final int NOTIFICATION_ID = 466;
   private final String IFICATION_ID = "466";
   private final Context context;

   public WorkerManager(
         Context context,
         WorkerParameters params) {
      super(context, params);
      this.context = context;
   }


   @Override
   public Result doWork() {
      Data inputData = getInputData();
      Log.i(TAG, "doWork: start");
      Log.i(TAG, String.valueOf(inputData));
      NotificationChannel channel;
      if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
         channel = new NotificationChannel(IFICATION_ID, "Avc", NotificationManager.IMPORTANCE_HIGH);
         NotificationManagerCompat.from(this.context).createNotificationChannel(channel);
      }
      Intent actionIntent = new Intent(this.context, MainActivity.class);
      actionIntent.putExtra("Name", inputData.getString("Name"));
      actionIntent.putExtra("Phone", inputData.getString("Phone"));
      try {
         actionIntent.putExtra("TextTemplate", inputData.getString("TextTemplate"));
      } catch (Exception e) {
         Log.w(TAG, "doWork: e", e);
      }
      int flag = 0;
      flag = flag | PendingIntent.FLAG_IMMUTABLE;
      PendingIntent actionPendingIntent = PendingIntent.getActivity(
            // Интент используется для создания отложенного интента.
            this.context,//Контекст (в данном случае текущая служба).
            0,//Этот флаг должен использоваться в том случае, если понадобится получить отложенный интент. Нам это не нужно, поэтому передается 0.
            actionIntent,//Интент, созданный выше
            flag);//Если соответствующий отложенный интент существует, он будет обновлен
      NotificationCompat.Builder builder = new NotificationCompat.Builder(this.context, IFICATION_ID)
            //Эти настройки необходимы для всех уведомлений.
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle("Запланировано поздравить абонента: "
                  + inputData.getString("Name") + " ("
                  + inputData.getString("Phone") + ")")
            //А эти — только для всплывающих уведомлений.
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(new long[]{1000, 1000})
            .setAutoCancel(true)//Благодаря этой строке уведомление исчезает после щелчка.
            /*      .addAction(R.drawable.ic_add_24dp, "Да",
                          this.interactor.smsSend(this.context, inputData.getString("Phone"), inputData.getString("TextTemplate")))*/
            .setContentIntent(actionPendingIntent);
      //Выдача уведомления
      NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this.context);
      notificationManager.notify(NOTIFICATION_ID, builder.build());
      Log.i(TAG, "doWork: end");
      return Result.success();
   }
}
