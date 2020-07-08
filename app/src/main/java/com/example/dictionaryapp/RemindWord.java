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
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.adapter.RemindAdapter;
import com.example.adapter.WordsAdapter;
import com.example.model.words;
import com.example.notification.AlertReceiver;
import com.example.notification.TimePickerFragment;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

public class RemindWord extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{

    private static final int REQUEST_CODE_SPEECH_INPUT = 1;
    ListView lv_words;
    AutoCompleteTextView txt_find;
    Database database = new Database(this, "Dictionary_db.sqlite", null, 1);
    ArrayList<words> wordsArrayList;
    ArrayList<words> words;
    RemindAdapter remindAdapter;
    ImageView img_void,btn_setTime,btn_canle;
    RadioButton rdio_allWord,rdio_favourite,rdio_onlyDay,rdio_everyDay;
    TextView txt_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remind_word);
        getSupportActionBar().hide();
        addView();
        GetData("SELECT * FROM Wordss ORDER BY WordName ASC");
        addEvent();
    }

    private void addView() {
        lv_words=findViewById(R.id.lv_words);
        txt_find=findViewById(R.id.txt_find);
        img_void=findViewById(R.id.img_voice);
        btn_canle=findViewById(R.id.btn_cancel);
        btn_setTime=findViewById(R.id.btn_setTime);
        txt_time=findViewById(R.id.txt_time);
        rdio_allWord=findViewById(R.id.rdio_allWord);
        rdio_favourite=findViewById(R.id.rdio_favourite);
        rdio_everyDay=findViewById(R.id.rdio_everyDay);
        rdio_onlyDay=findViewById(R.id.rdio_onlyDay);
        words = new ArrayList<>();
        wordsArrayList=new ArrayList<>();
        remindAdapter=new RemindAdapter(RemindWord.this,R.layout.custom_row_remind,wordsArrayList);
        lv_words.setAdapter(remindAdapter);

    }
    private void addEvent() {
            img_void.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    speak();
                }
            });
            rdio_favourite.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) { GetData("SELECT * FROM Wordss WHERE favourite=1 ORDER BY WordName ASC"); }});
            rdio_allWord.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) { GetData("SELECT * FROM Wordss ORDER BY WordName ASC"); }});
            txt_find.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    if(rdio_favourite.isChecked())
                    {
                        GetData("SELECT * FROM Wordss WHERE WordName LIKE '%"+txt_find.getText().toString()+"%' AND favourite=1 ORDER BY WordName ASC");
                        //afterTextChanged();
                    }
                    else {GetData("SELECT * FROM Wordss WHERE WordName LIKE '%"+txt_find.getText().toString()+"%' ORDER BY WordName ASC");}
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }
                @Override
                public void afterTextChanged(Editable s) {
                    if(rdio_favourite.isChecked())
                    {
                        GetData("SELECT * FROM Wordss WHERE WordName LIKE '%"+txt_find.getText().toString()+"%' AND favourite=1 ORDER BY WordName ASC");
                        //afterTextChanged();
                    }
                    else {GetData("SELECT * FROM Wordss WHERE WordName LIKE '%"+txt_find.getText().toString()+"%' ORDER BY WordName ASC");}
                }
            });
            btn_setTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogFragment timePicker = new TimePickerFragment();
                    timePicker.show(getSupportFragmentManager(), "time picker");

                }
            });
            btn_canle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancelAlarm();
                }
            });
    }
    private void speak(){
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.ENGLISH);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Hi speaking something");
        try {
            startActivityForResult(intent,REQUEST_CODE_SPEECH_INPUT);
        }
        catch (Exception e)
        {
            Toast.makeText(this,""+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    private void GetData(String qr) {
        Cursor c= database.GetData(qr);
        wordsArrayList.clear();
        while (c.moveToNext()){
            int ID=c.getInt(0);
            String WordName =c.getString(1);
            String WordSpell=c.getString(2);
            String WordType=c.getString(3);
            String WordMean=c.getString(4);
            String WordSynonym=c.getString(5);
            String WordExample=c.getString(6);
            int WordFavourite=c.getInt(7);
            // byte[] WordImage=c.getBlob(7);
            wordsArrayList.add(new words(ID,WordName,WordType,WordSpell, WordMean, WordExample,WordSynonym,WordFavourite));
        }
        remindAdapter.notifyDataSetChanged();
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        updateTimeText(c);
        startAlarm(c);
    }
    private void updateTimeText(Calendar c) {
        String timeText = "";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        txt_time.setText(timeText);
    }
    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        Set<String>ds = preferences.getStringSet("set",null);
        ArrayList<String> danhsach = new ArrayList<>();
        for (String i :
                ds) {
            int ma = Integer.parseInt(i);
            com.example.model.words w = getRemind(ma);
            String a = ma+"-"+w.getName()+"-"+w.getSpell()+"-"+w.getMean();
            danhsach.add(a);
        }
        intent.putExtra("ds",danhsach);
        Toast.makeText(this, danhsach.get(0), Toast.LENGTH_SHORT).show();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }
    public words getRemind(int ma)
    {
        Cursor c= database.GetData("SELECT * FROM Wordss where ID="+ma);
        words w = new words();
        while (c.moveToNext()) {
            int ID = c.getInt(0);
            String WordName = c.getString(1);
            String WordSpell = c.getString(2);
            String WordMean = c.getString(3);
            w.setID(ID);
            w.setName(WordName);
            w.setSpell(WordSpell);
            w.setMean(WordMean);
        }
        return w;
    }
    
    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.cancel(pendingIntent);
        txt_time.setText("00:00");
    }
}
