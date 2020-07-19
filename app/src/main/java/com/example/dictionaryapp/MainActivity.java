package com.example.dictionaryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import maes.tech.intentanim.CustomIntent;

public class MainActivity extends AppCompatActivity {

    Animation animation,animation_bottom,rotato,line_animation;
    TextView textView,textView1;
    ImageView logo,btn_get,c1,c2,c3,c4,c5,line,path;
   // Button btn_get;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        animation= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        animation_bottom= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        rotato=AnimationUtils.loadAnimation(this,R.anim.rotate_animation);
        line_animation=AnimationUtils.loadAnimation(this,R.anim.line_animation);
        textView=findViewById(R.id.t2);
        textView1=findViewById(R.id.t);
        logo=findViewById(R.id.logo);
        btn_get=findViewById(R.id.btn_get);
        line=findViewById(R.id.line1);
        c1=findViewById(R.id.c1);
        c2=findViewById(R.id.c2);
        c3=findViewById(R.id.c3);
        c4=findViewById(R.id.c4);
        c5=findViewById(R.id.c5);
        path=findViewById(R.id.path);


        textView1.setAnimation(animation_bottom);
        textView.setAnimation(animation_bottom);
        logo.setAnimation(animation);
        logo.setAnimation(rotato);
        line.setAnimation(line_animation);
        btn_get.setAnimation(animation_bottom);
        btn_get.setAnimation(rotato);
        c1.setAnimation(animation_bottom);
        c2.setAnimation(animation);
        c3.setAnimation(animation);
        c4.setAnimation(animation);
        c5.setAnimation(animation_bottom);
        path.setAnimation(animation);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(MainActivity.this, login.class);
                MainActivity.this.startActivity(intent);
                CustomIntent.customType(MainActivity.this,"left-to-right");
                MainActivity.this.finish();
            }
        },4000);
    }
}
