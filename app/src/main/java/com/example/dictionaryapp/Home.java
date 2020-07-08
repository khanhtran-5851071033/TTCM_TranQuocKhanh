package com.example.dictionaryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import maes.tech.intentanim.CustomIntent;

public class Home extends AppCompatActivity {

    Animation animation_rigth,animation_left;
    LinearLayout l1,l2,l3,l4;
    TextView txt_intro,txt_intro1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
        animation_left= AnimationUtils.loadAnimation(this,R.anim.line_animation);
        animation_rigth= AnimationUtils.loadAnimation(this,R.anim.home_rigth_animation);
        addView();
        addAmimation();
    }

    private void addAmimation() {
        l1.setAnimation(animation_left);
        l2.setAnimation(animation_rigth);
        l3.setAnimation(animation_left);
        l4.setAnimation(animation_rigth);
        txt_intro.setAnimation(animation_left);
        txt_intro1.setAnimation(animation_rigth);
    }

    private void addView() {
        l1=findViewById(R.id.l1);
        l2=findViewById(R.id.l2);
        l3=findViewById(R.id.l3);
        l4=findViewById(R.id.l4);
        txt_intro=findViewById(R.id.txt_intro);
        txt_intro1=findViewById(R.id.txt_intro1);
    }


    public void findWord(View view) {
        Intent intent=new Intent(Home.this, findword.class);
        Home.this.startActivity(intent);
        CustomIntent.customType(this,"up-to-bottom");
    }

    public void test(View view) {
        Intent intent=new Intent(Home.this, test.class);
        Home.this.startActivity(intent);
        CustomIntent.customType(this,"up-to-bottom");
    }

    public void favourite(View view) {
        Intent intent=new Intent(Home.this, findword.class);
        Bundle bundle =new Bundle();
        bundle.putInt("favourite",1);
        intent.putExtra("my",bundle);
        Home.this.startActivity(intent);
        CustomIntent.customType(this,"up-to-bottom");
    }

    public void remindWord(View view) {
        Intent intent=new Intent(Home.this, RemindWord.class);
        Home.this.startActivity(intent);
        CustomIntent.customType(this,"up-to-bottom");
    }
    public void history(View view) {
        Intent intent=new Intent(Home.this, findword.class);
        Bundle bundle =new Bundle();
        bundle.putInt("history",1);
        intent.putExtra("my",bundle);
        Home.this.startActivity(intent);
        CustomIntent.customType(this,"up-to-bottom");
    }

    public void toSetting(View view) {
        Intent intent=new Intent(Home.this, Setting.class);
        Home.this.startActivity(intent);
        CustomIntent.customType(this,"Right-to-left");
    }
}
