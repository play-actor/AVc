package com.lastaurus.automatic_congratulations.managers

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.lastaurus.automatic_congratulations.R
import com.lastaurus.automatic_congratulations.ui.main_avtivity.MainActivity

class WorkerManager(
   private val context: Context,
   params: WorkerParameters?,
) : Worker(context, params!!) {
   private val NOTIFICATION_ID = 466
   private val IFICATION_ID: String = NOTIFICATION_ID.toString()
   override fun doWork(): Result {
      val inputData = inputData
      val channel: NotificationChannel
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
         channel = NotificationChannel(
            IFICATION_ID,
            "Automatic congratulations",
            NotificationManager.IMPORTANCE_HIGH
         )
         NotificationManagerCompat.from(context).createNotificationChannel(channel)
      }
      val actionIntent = Intent(context, MainActivity::class.java)
      actionIntent.putExtra("Name", inputData.getString("Name"))
      actionIntent.putExtra("Phone", inputData.getString("Phone"))
      try {
         actionIntent.putExtra("TextTemplate", inputData.getString("TextTemplate"))
      } catch (e: Exception) {
         Log.w("AVC", "WorkerManager Exception: ", e)
      }
      var flag = 0
      flag = flag or PendingIntent.FLAG_IMMUTABLE
      val actionPendingIntent =
         PendingIntent.getActivity( // Интент используется для создания отложенного интента.
            context,  //Контекст (в данном случае текущая служба).
            0,  //Этот флаг должен использоваться в том случае, если понадобится получить отложенный интент. Нам это не нужно, поэтому передается 0.
            actionIntent,  //Интент, созданный выше
            flag
         ) //Если соответствующий отложенный интент существует, он будет обновлен
      val builder: NotificationCompat.Builder = NotificationCompat.Builder(
         context, IFICATION_ID
      ) //Эти настройки необходимы для всех уведомлений.
         .setSmallIcon(R.mipmap.ic_launcher_round)
         .setContentTitle(
            "Запланировано поздравить абонента: "
                  + inputData.getString("Name") + " ("
                  + inputData.getString("Phone") + ")"
         ) //А эти — только для всплывающих уведомлений.
         .setPriority(NotificationCompat.PRIORITY_HIGH)
         .setVibrate(longArrayOf(1000, 1000))
         .setAutoCancel(true) //Благодаря этой строке уведомление исчезает после щелчка.
         /*      .addAction(R.drawable.ic_add_24dp, "Да",
                          this.interactor.smsSend(this.context, inputData.getString("Phone"), inputData.getString("TextTemplate")))*/
         .setContentIntent(actionPendingIntent)
      //Выдача уведомления
      val notificationManager = NotificationManagerCompat.from(context)
      if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
         ) != PackageManager.PERMISSION_GRANTED
      ) {
         return Result.failure()
      }
      notificationManager.notify(NOTIFICATION_ID, builder.build())
      return Result.success()
   }
}