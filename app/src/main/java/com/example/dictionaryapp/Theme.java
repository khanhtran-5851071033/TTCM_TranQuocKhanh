package com.example.dictionaryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import maes.tech.intentanim.CustomIntent;


public class Theme extends AppCompatActivity {

    LinearLayout tech,mussic,sport,fashion;
    Animation animation,fade_in;
    ImageView n1,n2,n3,n4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
        getSupportActionBar().hide();
        animation=AnimationUtils.loadAnimation(this,R.anim.line_animation);
        fade_in=AnimationUtils.loadAnimation(this,R.anim.rotate_animation);
        addView();
        addAnimation();
        addEvent();
    }


    private void addAnimation() {
        tech.setAnimation(animation);
        sport.setAnimation(animation);
        mussic.setAnimation(animation);
        fashion.setAnimation(animation);
        n1.setAnimation(fade_in);
        n2.setAnimation(fade_in);
       n3.setAnimation(fade_in);
       n4.setAnimation(fade_in);
    }

    private void addEvent() {

    }


    private void addView(){
        tech=findViewById(R.id.btn_technology);
        sport=findViewById(R.id.btn_sport);
        mussic=findViewById(R.id.btn_music);
        fashion=findViewById(R.id.btn_fashion);
        n1=findViewById(R.id.n1);
        n2=findViewById(R.id.n2);
        n3=findViewById(R.id.n3);
        n4=findViewById(R.id.n4);

    }
    boolean visible=false;
    public void openTech(View view) {
        Intent intent=new Intent(Theme.this, viewTechnology.class);

//        TransitionSet set = new TransitionSet()
//                //.addTransition(new Scale(0.7f))
//                .addTransition(new Fade())
//                .setInterpolator(visible ? new LinearOutSlowInInterpolator() : new FastOutLinearInInterpolator());
//        final ViewGroup transitionsContainer = (ViewGroup)findViewById(R.id.transitions_container);
//        TransitionManager.beginDelayedTransition(transitionsContainer, set);
//        sport.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
//        mussic.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
//        fashion.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
//        n1.setRotation(visible?0:90);
//        if(visible)visible=false;
//        else visible=true;
        Bundle bundle =new Bundle();
        bundle.putInt("theme",1);
        intent.putExtra("my",bundle);
        ActivityOptionsCompat opitions= ActivityOptionsCompat.makeSceneTransitionAnimation(Theme.this,tech, ViewCompat.getTransitionName(tech));
        Theme.this.startActivity(intent,opitions.toBundle());
        CustomIntent.customType(this,"left-to-right");
    }

    public void openMusic(View view) {
        Intent intent=new Intent(Theme.this, viewTechnology.class);
        Bundle bundle =new Bundle();
        bundle.putInt("theme",4);
        intent.putExtra("my",bundle);
        ActivityOptionsCompat opitions= ActivityOptionsCompat.makeSceneTransitionAnimation(Theme.this,mussic, ViewCompat.getTransitionName(tech));
        Theme.this.startActivity(intent,opitions.toBundle());
        CustomIntent.customType(this,"left-to-right");
    }

    public void openFashion(View view) {
        Intent intent=new Intent(Theme.this, viewTechnology.class);
        Bundle bundle =new Bundle();
        bundle.putInt("theme",2);
        intent.putExtra("my",bundle);
        ActivityOptionsCompat opitions= ActivityOptionsCompat.makeSceneTransitionAnimation(Theme.this,fashion, ViewCompat.getTransitionName(tech));
        Theme.this.startActivity(intent,opitions.toBundle());
        CustomIntent.customType(this,"left-to-right");
    }

    public void onpendSport(View view) {
        Intent intent=new Intent(Theme.this, viewTechnology.class);
        Bundle bundle =new Bundle();
        bundle.putInt("theme",3);
        intent.putExtra("my",bundle);
        ActivityOptionsCompat opitions= ActivityOptionsCompat.makeSceneTransitionAnimation(Theme.this,sport, ViewCompat.getTransitionName(tech));
        Theme.this.startActivity(intent,opitions.toBundle());
        CustomIntent.customType(this,"left-to-right");
    }
}
