package com.example.dictionaryapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.model.Language;
import com.example.model.TranslateAPI;

import java.util.ArrayList;
import java.util.Locale;

public class Translate extends AppCompatActivity {

    EditText txt_input;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;
    ImageView img_delete_input,img_void,img_speak,img_trans;
    LinearLayout linear_output;
    SeekBar seekBar_seed;
    TextView txt_speed,txt_output;
    Button btn_anh_viet,btn_viet_anh;
    private TextToSpeech mTTS;
    private static final String TAG = "Translate";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        getSupportActionBar().hide();
        addView();
        addEvent();
    }

    private void addView() {
        txt_input=findViewById(R.id.text_input);
        txt_output=findViewById(R.id.txt_output);
        img_delete_input=findViewById(R.id.img_delete_input);
        linear_output=findViewById(R.id.linear_translate);
        btn_anh_viet=findViewById(R.id.btn_anh_viet);
        btn_viet_anh=findViewById(R.id.btn_viet_anh);
        seekBar_seed=findViewById(R.id.mSeekBarSpeed);
        txt_speed=findViewById(R.id.txt_speed2);
        img_speak=findViewById(R.id.img_speak);
        img_void=findViewById(R.id.img_void);
        img_trans=findViewById(R.id.img_strans);

    }

    private void addEvent() {
        txt_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if(!txt_input.getText().toString().matches("")){
                    img_delete_input.setVisibility(View.VISIBLE);
                }
                if(txt_input.getText().toString().matches(""))
                  img_delete_input.setVisibility(View.INVISIBLE);
            }
        });
        img_delete_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_input.setText("");
                linear_output.setVisibility(View.INVISIBLE);

            }
        });
        btn_anh_viet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_output.setVisibility(View.VISIBLE);
                TranslateAPI translateAPI = new TranslateAPI(
                        Language.AUTO_DETECT,
                        Language.VIETNAMESE,
                        txt_input.getText().toString());
                translateAPI.setTranslateListener(new TranslateAPI.TranslateListener() {
                    @Override
                    public void onSuccess(String translatedText) {
                        Log.d(TAG, "onSuccess: "+translatedText);
                        txt_output.setText(translatedText);
                    }

                    @Override
                    public void onFailure(String ErrorText) {
                        Log.d(TAG, "onFailure: "+ErrorText);

                    }
                });
            }
        });
        btn_viet_anh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_output.setVisibility(View.VISIBLE);
                TranslateAPI translateAPI = new TranslateAPI(
                        Language.AUTO_DETECT,
                        Language.ENGLISH,
                        txt_input.getText().toString());
                translateAPI.setTranslateListener(new TranslateAPI.TranslateListener() {
                    @Override
                    public void onSuccess(String translatedText) {
                        Log.d(TAG, "onSuccess: "+translatedText);
                        txt_output.setText(translatedText);
                    }

                    @Override
                    public void onFailure(String ErrorText) {
                        Log.d(TAG, "onFailure: "+ErrorText);

                    }
                });
            }
        });
        seekBar_seed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txt_speed.setText(progress+"%"); }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
        img_void.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });
        img_speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                texttoSpeak();
            }
        });
        img_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tam=txt_input.getText().toString();
                String tam1=txt_output.getText().toString();
                txt_output.setText(tam);
                txt_input.setText(tam1);
            }
        });

    }
    private void speak(){
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Hi speaking something");
        try {
            startActivityForResult(intent,REQUEST_CODE_SPEECH_INPUT);
        }
        catch (Exception e)
        {
            Toast.makeText(this,""+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    private void texttoSpeak() {
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
                float speed = (float) seekBar_seed.getProgress() / 50;
                if (speed < 0.1) speed = 0.1f;
                mTTS.setSpeechRate(speed);
                mTTS.speak(txt_input.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }

    @Override
    protected void onDestroy() {
        if(mTTS!=null)
        {
            mTTS.stop();
            mTTS.shutdown();
        }
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode==RESULT_OK && requestCode==REQUEST_CODE_SPEECH_INPUT)
        {
            ArrayList<String> resutl=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            txt_input.setText(resutl.get(0));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
