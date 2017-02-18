package com.grizzzwalk.mjschmidt.grizzwalk;

/**
 * Created by Nick on 10/24/16.
 */

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.content.Context;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.app.PendingIntent;
import android.app.NotificationManager;
import android.graphics.BitmapFactory;

public class AlarmReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Calendar alarmTime = Calendar.getInstance();
        long courseTime = intent.getLongExtra("time", 0);
        String course = intent.getStringExtra("course");
        alarmTime.setTimeInMillis(courseTime);
        SimpleDateFormat format = new SimpleDateFormat("h:mm a");


        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                                R.drawable.scheduling_app_candidate))
                        .setContentTitle("You have an exam in " + course + " soon!")
                        .setContentText("You have an exam in " + course + " at " + format.format(alarmTime.getTime()) + ".");
        Intent resultIntent = new Intent(context, Month_View.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(Month_View.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_CANCEL_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify((int) alarmTime.getTimeInMillis(), mBuilder.build());
    }
}
