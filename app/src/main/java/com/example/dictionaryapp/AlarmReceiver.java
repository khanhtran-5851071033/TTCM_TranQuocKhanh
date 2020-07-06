package com.example.dictionaryapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int id=intent.getIntExtra("notificationid",0);
        String mes=intent.getStringExtra("todo");
        Intent remindIntent=new Intent(context,RemindWord.class);
        PendingIntent contentIntent=PendingIntent.getActivity(context,0,remindIntent,0);
        NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder=new Notification.Builder(context);
        builder.setSmallIcon(R.drawable.delete_sign_filled_26px)
                .setContentTitle("REMIND YOU")
                .setContentText(mes)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentIntent(contentIntent)
                .setPriority(Notification.PRIORITY_MAX)
                .setDefaults(Notification.DEFAULT_ALL);

        notificationManager.notify(id,builder.build());

    }
}
