package com.example.notification;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.UserDictionary;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.dictionaryapp.Database;
import com.example.dictionaryapp.R;
import com.example.dictionaryapp.RemindWord;
import com.example.model.words;

import java.util.ArrayList;
import java.util.List;

import static com.example.notification.App.CHANNEL_1_ID;
import static com.example.notification.App.CHANNEL_2_ID;

public class AlertReceiver extends BroadcastReceiver {

   @Override
   public void onReceive(Context context, Intent intent) {
       NotificationHelper notificationHelper = new NotificationHelper(context);

       ArrayList<String>danhsach = intent.getStringArrayListExtra("ds");
      // Log.e("aa :",danhsach.get(0));

           // or activity.getApplicationContext()
//        PackageManager packageManager = context.getPackageManager();
//        String packageName = context.getPackageName();
//        RemoteViews contentView = new RemoteViews(packageName,R.layout.activity_main);
//        //contentView.setImageViewResource(R.id.imageView, R.drawable.c);
////        contentView.setTextViewText(R.id.textView2, "adsa");
////        contentView.setTextViewText(R.id.textView3, "adsdsa");
       for(int i=0;i<danhsach.size();i++)
      {
          String [] arr = danhsach.get(i).split("-");
           NotificationCompat.Builder nb = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                   .setSmallIcon(R.drawable.dictionary_blue_100px)
                   .setContentTitle(arr[1])
                   .setContentText(arr[2]+"\n   "+arr[3])
                   .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                   .setPriority(NotificationCompat.PRIORITY_HIGH)
                   .setColor(Color.GREEN)
                   .setAutoCancel(true);
           //.setContent(contentView);
           SystemClock.sleep(2000);
           notificationHelper.getManager().notify(i, nb.build());
            }
        }
   }
