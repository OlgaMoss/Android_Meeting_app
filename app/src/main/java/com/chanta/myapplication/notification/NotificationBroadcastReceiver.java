package com.chanta.myapplication.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


/**
 * Created by chanta on 12.12.17.
 */

public class NotificationBroadcastReceiver extends BroadcastReceiver {

    private final static String TAG = "NotificationBroadcastRe";

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent serviceIntent = new Intent(context, BackgroundNotificationService.class);
        serviceIntent.setAction(BackgroundNotificationService.LOAD_EVERY_10_MIN);
        Log.d(TAG, "onReceive: intent");

        context.startService(serviceIntent);
        Log.d(TAG, "onReceive: start");

        PendingIntent pendingIntent = PendingIntent.getService(context, 0,
                serviceIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 60 * 1000 * 10, pendingIntent);
    }


}
