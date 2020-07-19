package com.example.dictionaryapp;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.UserDictionary;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.adapter.WordsAdapter;
import com.example.model.words;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Locale;

public class findword extends AppCompatActivity {
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;
    ListView lv_words;
    AutoCompleteTextView txt_find;
    Database database = new Database(this, "Dictionary_db.sqlite", null, 1);
    ArrayList<words> wordsArrayList;
    WordsAdapter wordsAdapter;
    ImageView img_void;
    private int favourite=0;
    private int history=0;
    private TextToSpeech mTTS;
    private static final ArrayList<String> NAME=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findword);
        getSupportActionBar().hide();
        addView();
        normal_or_favourite_or_history();
        viewWord();
        addEvent();
        autoComplete();
        ArrayAdapter<String> stringArrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,NAME);
        txt_find.setAdapter(stringArrayAdapter);

    }

    private void addEvent() {
        img_void.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });
        txt_find.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(favourite==1)
                { GetData("SELECT * FROM Wordss WHERE WordName LIKE '%"+txt_find.getText().toString()+"%' AND favourite=1 ORDER BY WordName ASC"); }
                else if(history==1) { GetData("SELECT * FROM Wordss WHERE WordName LIKE '%"+txt_find.getText().toString()+"%' AND history=1 ORDER BY WordName ASC"); }
                else {GetData("SELECT * FROM Wordss WHERE WordName LIKE '%"+txt_find.getText().toString()+"%' ORDER BY WordName ASC");}
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if(favourite==1)
                { GetData("SELECT * FROM Wordss WHERE WordName LIKE '%"+txt_find.getText().toString()+"%' AND favourite=1 ORDER BY WordName ASC"); }
                else if(history==1) { GetData("SELECT * FROM Wordss WHERE WordName LIKE '%"+txt_find.getText().toString()+"%' AND history=1 ORDER BY WordName ASC"); }
                else {GetData("SELECT * FROM Wordss WHERE WordName LIKE '%"+txt_find.getText().toString()+"%' ORDER BY WordName ASC");}
            }
        });
        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTS.setLanguage(Locale.ENGLISH);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    } else {
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });
    }


    public void addVoid(String a) {


        mTTS.setSpeechRate(1);
        mTTS.speak(a, TextToSpeech.QUEUE_FLUSH, null);
    }
    private void normal_or_favourite_or_history()
    {
        Intent intent = getIntent();
        Bundle bundle=intent.getBundleExtra("my");
        if (bundle != null) {
            favourite=bundle.getInt("favourite");
            history=bundle.getInt("history");
        }

        if(favourite==1)
        {
            GetData("SELECT * FROM Wordss WHERE favourite=1 ORDER BY WordName ASC");
        }
        else if(history==1)
        {
            GetData("SELECT * FROM Wordss WHERE history=1 ORDER BY WordName ASC");
        }
        else {GetData("SELECT * FROM Wordss ORDER BY WordName ASC");}

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

                if(resultCode==RESULT_OK && requestCode==REQUEST_CODE_SPEECH_INPUT)
                {
                    ArrayList<String> resutl=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txt_find.setText(resutl.get(0).toString());
                }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void addView() {
        lv_words=findViewById(R.id.lv_words);
        txt_find=findViewById(R.id.txt_find);
        img_void=findViewById(R.id.img_voice);
        wordsArrayList=new ArrayList<>();
        wordsAdapter=new WordsAdapter(this,R.layout.custom_row_words,wordsArrayList);
        lv_words.setAdapter(wordsAdapter);
    }

    private String readFromInternal(){
        try {
            File sdcard = Environment.getExternalStorageDirectory();
            File f = new File(sdcard,"auto.txt");
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = br.readLine()) != null) {
                content.append(line);
            }
            return content.toString();
        }catch (Exception ex){
            Log.e("lỗi ",ex.getMessage() );
        }
        return "";
    }
    public void viewWord(){
        lv_words.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                words w=wordsArrayList.get(position);
                if(readFromInternal().trim().equals("1"))
                {
                    addVoid(w.getName());
                }
                database.QueryData("UPDATE Wordss SET history=1 WHERE ID="+w.getID()+"");
                Intent intent=new Intent(findword.this, viewWord.class);
                Bundle bundle =new Bundle();
                bundle.putInt("ID",w.getID());
                bundle.putString("name",w.getName());
                bundle.putString("spell",w.getSpell());
                bundle.putString("type",w.getWordType());
                bundle.putString("mean",w.getMean());
                bundle.putString("example",w.getExample());
                bundle.putString("synonym",w.getSynonym());
                bundle.putInt("favourite",w.getFavourite());
                intent.putExtra("my",bundle);
                findword.this.startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        if(favourite==1)
        {
            GetData("SELECT * FROM Wordss WHERE favourite=1 ");
        }
        else if(history==1)
        {
            GetData("SELECT * FROM Wordss WHERE history=1 ORDER BY WordName ASC");
        }
        else {GetData("SELECT * FROM Wordss");}
        super.onResume();
    }

    private void autoComplete(){
        Cursor c= database.GetData("SELECT * FROM Wordss");
        wordsArrayList.clear();
        while (c.moveToNext()){
            String WordName =c.getString(1);
            NAME.add(WordName);
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
            int WordHistory=c.getInt(8);
           // byte[] WordImage=c.getBlob(7);
            wordsArrayList.add(new words(ID,WordName,WordType,WordSpell, WordMean, WordExample,WordSynonym,WordFavourite,WordHistory));
        }
        wordsAdapter.notifyDataSetChanged();

    }
    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    //tạo databasse
}
