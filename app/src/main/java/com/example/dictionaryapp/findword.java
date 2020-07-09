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
import android.widget.AdapterView;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findword);
        getSupportActionBar().hide();
        addView();
       // PrepareDB();
        normal_or_favourite_or_history();
        viewWord();
        addEvent();
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
    private void PrepareDB() {

        database.QueryData("DROP TABLE wordss");
        database.QueryData("CREATE TABLE IF NOT EXISTS Wordss(ID Integer PRIMARY KEY AUTOINCREMENT, WordName VARCHAR(50),WordSpell VARCHAR(50)" +
                ",WordType VARCHAR(50),WordMean VARCHAR(100),WordSynonym VARCHAR(100),WordExample VARCHAR(200),favourite INT,history INT)");
        database.QueryData("INSERT INTO Wordss VALUES(null,'Hello','[helou]','Động từ','Xin chào','Hi','Hello Khánh',0,0)");
        database.QueryData("INSERT INTO Wordss VALUES(null,'Design','/dizain/','Danh từ','Bản thiết kế, kế hoạch','Plan, scheme','The design of machine',0,0)");
        database.QueryData("INSERT INTO Wordss VALUES(null,'Information',' /ˌɪn.fəˈmeɪ.ʃən/','Danh từ','Thông tin','Info,Data',' I would like some information about your flights to the USA',0,0)");
        database.QueryData("INSERT INTO Wordss VALUES(null,'Technology','/tekˈnɒl.ə.dʒi/','Danh từ','Công nghệ, khoa học','knowledge, machinery','The technology of computers',0,0)");
        database.QueryData("INSERT INTO Wordss VALUES(null,'Condition','/kənˈdiʃn/','Danh từ','Điều kiện','State,form,order,..','A man of condition',0,0)");
        database.QueryData("INSERT INTO Wordss VALUES(null,'Positive','/ˈpɔzətiv/','Tính từ','Xác thực, rõ ràng','Sure, certain','A positive proof',0,0)");
        database.QueryData("INSERT INTO Wordss VALUES(null,'Negative','/ˈnegətiv/','Tính từ','Phủ định, phủ nhận, ','unenthusiastic, pessimistic','To give a negative answers',0,0)");
        database.QueryData("INSERT INTO Wordss VALUES(null,'Technical','/ˈteknikəl/','Tính từ','Kỹ thuật',' technological, technical foul','technical school, technical terms',0,0)");
        database.QueryData("INSERT INTO Wordss VALUES(null,'Computer','/kəmˈpju:tə/','Danh từ','Máy tính',' computing device','electronic computer',0,0)");
        database.QueryData("INSERT INTO Wordss VALUES(null,'Reservation','/rezəˈveiʃn/','Danh từ','Sự hạn chế, điều kiện hạn chế','mental reservation,  arriere pensee','mental reservation',0,0)");
        database.QueryData("INSERT INTO Wordss VALUES(null,'Book','/buk/','Danh từ','Sách','Bible','old book, to writer a book',0,0)");
        database.QueryData("INSERT INTO Wordss VALUES(null,'Install','/inˈstɔ:l/','Ngoại động từ','Cài đặt','set up, put in','installing sofwares',0,0)");
        database.QueryData("INSERT INTO Wordss VALUES(null,'Instruction','/insˈtrʌkʃn/','Danh từ','kiến thức truyền cho, tài liệu cung cấp cho','education, statement','course of instruction',0,0)");
        database.QueryData("INSERT INTO Wordss VALUES(null,'Network','/ˈnetwə:k/','Danh từ','mạng lưới, hệ thống','electronic network','a network of railways',0,0)");
        database.QueryData("INSERT INTO Wordss VALUES(null,'Database','/ˈdeitəbeis/','Danh từ','Cơ sở dữ liệu','database service,','database manager, database processor,',0,0)");
        database.QueryData("INSERT INTO Wordss VALUES(null,'Developer','/diˈveləpə[r]/','Danh từ','Nhà phát triển','Creator, maker','Sofware deverloper',0,0)");
    }
}
