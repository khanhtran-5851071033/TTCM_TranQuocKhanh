package com.example.dictionaryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeechService;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.model.words;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.Locale;

public class viewWord extends AppCompatActivity {
    TextView txt_name,txt_spell,txt_exam,txt_type,txt_mean,txt_synoym;
    ImageView img_sound,img_like,img1,img2,img3,img4,img5;
    private words words;
    Animation animation_line,animation_rotato;
    private TextToSpeech mTTS;
    private Boolean check_like=false;
    private int id;
    Database database=new Database(this, "Dictionary_db.sqlite", null, 1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_word);
        getSupportActionBar().hide();
        animation_line= AnimationUtils.loadAnimation(this,R.anim.line_animation);
        animation_rotato=AnimationUtils.loadAnimation(this,R.anim.rotate_animation);
        addView();
        receiverData();
        addAnimation();
        addEvent();
    }
    public void addEvent(){
        mTTS=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status==TextToSpeech.SUCCESS)
                {
                    int result= mTTS.setLanguage(Locale.ENGLISH);
                    if(result==TextToSpeech.LANG_MISSING_DATA || result==TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("TTS","Language not supported");
                    }
                    else { }
                }
                else {
                    Log.e("TTS","Initialization failed");
                }
            }
        });
        img_sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTTS.setSpeechRate(1);
                mTTS.speak(txt_name.getText().toString(),TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        //update like
        img_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check_like)
                {
                    img_like.setImageResource(R.drawable.hearts);
                    database.QueryData("UPDATE Wordss SET favourite=1 WHERE ID="+id+"");
                    StyleableToast.makeText(viewWord.this,"Đã thích !",R.style.SuccessToast).show();
                    check_like=false;
                }
                else {
                    img_like.setImageResource(R.drawable.unhearts);
                    database.QueryData("UPDATE Wordss SET favourite=0 WHERE ID="+id+"");
                    StyleableToast.makeText(viewWord.this,"Bỏ thích !",R.style.SuccessToast).show();
                    check_like=true;
                }
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

    private void receiverData() {
        Intent intent = getIntent();
        Bundle bundle=intent.getBundleExtra("my");
        if (bundle != null) {
            id=bundle.getInt("ID");
            String name =bundle.getString("name");
            String spell=bundle.getString("spell");
            String type=bundle.getString("type");
            String mean=bundle.getString("mean");
            String example=bundle.getString("example");
            String synonym=bundle.getString("synonym");
            int favourite=bundle.getInt("favourite");
            //words=(words) bundle.getSerializable("word");
            //textView.setText(String.valueOf(a)+"\n"+b);
            txt_spell.setText(spell);
            txt_name.setText(name);
            txt_mean.setText(mean);
            txt_synoym.setText(synonym);
            txt_exam.setText(example);
            txt_type.setText(type);
            if(favourite==1)
            {img_like.setImageResource(R.drawable.hearts);check_like=false;}
            else{ img_like.setImageResource(R.drawable.unhearts);check_like=true;}
        }
    }

    private void addAnimation() {
        img_sound.setAnimation(animation_rotato);
        img_like.setAnimation(animation_rotato);
        img1.setAnimation(animation_line);
        img2.setAnimation(animation_line);
        img3.setAnimation(animation_line);
        img4.setAnimation(animation_line);
        img5.setAnimation(animation_line);
    }
    private void addView() {
        txt_name=findViewById(R.id.txt_name);
        txt_exam=findViewById(R.id.txt_exampe);
        txt_spell=findViewById(R.id.txt_spell);
        txt_mean=findViewById(R.id.txt_mean);
        txt_synoym=findViewById(R.id.txt_synoym);
        txt_type=findViewById(R.id.txt_type);
        img_sound=findViewById(R.id.img_sound);
        img_like=findViewById(R.id.img_like);
        img1=findViewById(R.id.avv2);
        img2=findViewById(R.id.avv);
        img3=findViewById(R.id.avv3);
        img4=findViewById(R.id.avv4);
        img5=findViewById(R.id.avv5);
    }
}
