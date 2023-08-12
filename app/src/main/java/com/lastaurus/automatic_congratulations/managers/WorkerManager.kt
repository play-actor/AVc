package com.lastaurus.automatic_congratulations.managers

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.lastaurus.automatic_congratulations.R
import com.lastaurus.automatic_congratulations.Util.Util.Companion.getDateTime
import com.lastaurus.automatic_congratulations.dagger.ComponentManager.Companion.instance
import com.lastaurus.automatic_congratulations.data.database.DBManager
import com.lastaurus.automatic_congratulations.ui.main_avtivity.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import javax.inject.Inject

class WorkerManager(
   private val context: Context,
   params: WorkerParameters?,
) : Worker(context, params!!) {
   private val NOTIFICATION_ID = 666
   private val scope: CoroutineScope by lazy { CoroutineScope(Job()) }

   @Inject
   lateinit var dbManager: DBManager

   init {
      instance.appComponent.inject(this)
   }

   override fun doWork(): Result {
      val channel: NotificationChannel
      val idCongratulation = inputData.getInt("idCongratulation", -1)

      dbManager.db.congratulationsDao().getById(idCongratulation)?.let { congratulation ->
         getDateTime(congratulation, congratulation.getDateTimeFuture())
         dbManager.db.congratulationsDao().update(congratulation)
         NotificationChannel(
            /* id = */ NOTIFICATION_ID.toString(),
            /* name = */ "Automatic congratulations",
            /* importance = */ NotificationManager.IMPORTANCE_HIGH
         ).also { channel = it }
         NotificationManagerCompat.from(context).createNotificationChannel(channel)
         val name = dbManager.db.contactDao().getById(congratulation.getIdContact())?.getName()
         val phone = congratulation.getPhone()
         val actionIntent = Intent(context, MainActivity::class.java)
         actionIntent.putExtra(
            "Name",
            name
               ?: return Result.failure()
         )
         actionIntent.putExtra("Phone", phone)
         actionIntent.putExtra(
            "TextTemplate",
            dbManager.db.templateDao().getById(congratulation.getIdTemplate())?.getTextTemplate()
               ?: return Result.failure()
         )
         var flag = 0
         flag = flag or PendingIntent.FLAG_IMMUTABLE
         val actionPendingIntent =
            PendingIntent.getActivity(
               context,
               0,
               actionIntent,
               flag
            )
         dbManager.createWorkRequest()
         create(actionPendingIntent, name, phone) { Result.failure() }

         return Result.success()
      }
      return Result.failure()
   }

   private inline fun create(
      actionPendingIntent: PendingIntent,
      name: String, phone: String,
      resultFail: () -> Result,
   ) {
      val builder: NotificationCompat.Builder = NotificationCompat.Builder(
         context,
         NOTIFICATION_ID.toString()
      )
         .setSmallIcon(R.mipmap.ic_launcher_round)
         .setContentTitle("Запланировано поздравить абонента: $name ($phone)")
         .setPriority(NotificationCompat.PRIORITY_HIGH)
         .setVibrate(longArrayOf(1000, 1000))
         .setAutoCancel(true)
         .setContentIntent(actionPendingIntent)

      val notificationManager = NotificationManagerCompat.from(context)
      if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
         ) != PackageManager.PERMISSION_GRANTED
      ) {
         resultFail.invoke()
      }
      notificationManager.notify(NOTIFICATION_ID, builder.build())
   }
}