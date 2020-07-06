package com.example.dictionaryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RemindWord extends AppCompatActivity {

    Button btnDate,btn_canle;
    EditText text;
    TimePicker timePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind_word);
        btnDate = findViewById(R.id.btn_set);
        btn_canle = findViewById(R.id.btn_canle) ;
        text = findViewById(R.id.txt_text) ;
        timePicker=findViewById(R.id.dateTimePic);

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RemindWord.this,AlarmReceiver.class);
                intent.putExtra("notificationid",1);
                intent.putExtra("todo",text.getText().toString());

                PendingIntent pendingIntent=PendingIntent.getBroadcast(RemindWord.this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);

                int h=timePicker.getCurrentHour();
                int m=timePicker.getCurrentMinute();

                Calendar startTime=Calendar.getInstance();
                startTime.set(Calendar.HOUR_OF_DAY,h);
                startTime.set(Calendar.MINUTE,m);
                startTime.set(Calendar.SECOND,0);
                long alamStartTime=startTime.getTimeInMillis();
                alarmManager.set(AlarmManager.RTC_WAKEUP,alamStartTime,pendingIntent);

                Toast.makeText(RemindWord.this,"done",Toast.LENGTH_LONG).show();

            }
        });
        btn_canle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }

}
