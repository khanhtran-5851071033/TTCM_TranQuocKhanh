package com.example.dictionaryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.transition.Fade;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;


public class viewTechnology extends AppCompatActivity {

    LinearLayout linearLayout,line_menu;
    ImageView img_theme,show_theme,img_speak;
    TextView txt_them,txt_content;
    SeekBar mSeekBarSpeed;
    private int theme=0;
    private TextToSpeech mTTS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_technology);
        getSupportActionBar().hide();
        addView();
        addEvent();

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

    private void addView() {
        linearLayout=findViewById(R.id.linear_theme);
        mSeekBarSpeed=findViewById(R.id.mSeekBarSpeed);
        line_menu=findViewById(R.id.lineear_menu);
        txt_them=findViewById(R.id.txt_theme);
        img_theme=findViewById(R.id.img_theme);
        show_theme=findViewById(R.id.n_show_theme);
        txt_content=findViewById(R.id.txt_content);
        img_speak=findViewById(R.id.img_speak);

        Intent intent = getIntent();
        Bundle bundle=intent.getBundleExtra("my");
        if (bundle != null) {
            theme=bundle.getInt("theme");
        }
        if(theme==1) {linearLayout.setBackgroundResource(R.drawable.custom_line_setting);txt_them.setText("Technology");img_theme.setImageResource(R.drawable.tech);show_theme.setImageResource(R.drawable.next);}
        else if(theme==2){linearLayout.setBackgroundResource(R.drawable.custom_btn_login);txt_them.setText("Fashion");img_theme.setImageResource(R.drawable.fashion);show_theme.setImageResource(R.drawable.n2);}
        else if(theme==3){linearLayout.setBackgroundResource(R.drawable.custom_row_theme);txt_them.setText("Sports");img_theme.setImageResource(R.drawable.sp);show_theme.setImageResource(R.drawable.n3);}
        else if (theme==4){linearLayout.setBackgroundResource(R.drawable.custom_row_theme2);txt_them.setText("Music");img_theme.setImageResource(R.drawable.mussic);show_theme.setImageResource(R.drawable.n1);}
    }

    boolean visible=false;
    private void addEvent() {
        show_theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionSet set = new TransitionSet()
                //.addTransition(new Scale(0.7f))
                .addTransition(new Fade())
                .setInterpolator(visible ? new LinearOutSlowInInterpolator() : new FastOutLinearInInterpolator());
        final ViewGroup transitionsContainer = (ViewGroup)findViewById(R.id.transitions_container1);
        TransitionManager.beginDelayedTransition(transitionsContainer, set);
        txt_content.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        line_menu.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
//        fashion.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        show_theme.setRotation(visible?0:90);
        if(visible)visible=false;
        else visible=true;
            }
        });

        img_speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                texttoSpeak();
            }
        });
    }
    private void texttoSpeak()
    {
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
                float speed = (float) mSeekBarSpeed.getProgress() / 50;
                if (speed < 0.1) speed = 0.1f;
                mTTS.setSpeechRate(speed);
                mTTS.speak(txt_content.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }

}
