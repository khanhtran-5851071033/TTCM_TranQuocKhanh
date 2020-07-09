package com.example.dictionaryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

public class Theme extends AppCompatActivity {

    LinearLayout tech,mussic,sport,fashion;
    Animation animation,fade_in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
        getSupportActionBar().hide();
        animation=AnimationUtils.loadAnimation(this,R.anim.line_animation);
        fade_in=AnimationUtils.loadAnimation(this,R.anim.fade_in);
        addView();
        addAnimation();
        addEvent();
    }

    private void addAnimation() {
        tech.setAnimation(animation);
        sport.setAnimation(animation);
        mussic.setAnimation(animation);
        fashion.setAnimation(animation);

    }

    private void addEvent() {

    }
    private void addView(){
        tech=findViewById(R.id.btn_technology);
        sport=findViewById(R.id.btn_sport);
        mussic=findViewById(R.id.btn_music);
        fashion=findViewById(R.id.btn_fashion);
    }
}
