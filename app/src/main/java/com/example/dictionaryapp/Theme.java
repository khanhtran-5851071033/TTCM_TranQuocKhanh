package com.example.dictionaryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

import android.content.Intent;
import android.database.Cursor;
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
    Database database = new Database(this, "Dictionary_db.sqlite", null, 1);

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
        addData();
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

    private void addData(){
        //database.QueryData("DROP TABLE Theme");
        database.QueryData("CREATE TABLE IF NOT EXISTS Theme(ID Integer PRIMARY KEY AUTOINCREMENT, ThemeName VARCHAR(50),Content VARCHAR(255))");
        Cursor cursor=database.GetData("SELECT * FROM Theme ");
        int count=cursor.getCount();
        if(count==0)
        {
            database.QueryData("INSERT INTO Theme VALUES(null,'Technology','Information technology (IT) is the use of computers to store, retrieve, transmit, and manipulate data or information.')");
            database.QueryData("INSERT INTO Theme VALUES(null,'Technology','A designer is a person who plans the look or workings of something prior to it being made, by preparing drawings or plans.')");
            database.QueryData("INSERT INTO Theme VALUES(null,'Technology','What do you think are the important things people need to learn when they start using computers?')");
            database.QueryData("INSERT INTO Theme VALUES(null,'Music','Music is an art form, and cultural activity, whose medium is sound.')");
            database.QueryData("INSERT INTO Theme VALUES(null,'Music','Making music is the process of putting sounds and tones in an order, often combining them to create a unified composition. P')");
            database.QueryData("INSERT INTO Theme VALUES(null,'Fashion','Fashion is a popular aesthetic expression at a particular time, place and in a specific context, especially in clothing, footwear, lifestyle, accessories, makeup, hairstyle, and body proportions.')");
            database.QueryData("INSERT INTO Theme VALUES(null,'Fashion','Designer clothing is expensive luxury clothing considered to be high quality and haute couture for the general public, made by, or carrying the label of, a well-known fashion designer.')");
            database.QueryData("INSERT INTO Theme VALUES(null,'Sports','A sport is commonly defined as an athletic activity that involves a degree of competition, such as netball or basketball. ')");
            database.QueryData("INSERT INTO Theme VALUES(null,'Sports','Football is a family of team sports that involve, to varying degrees, kicking a ball to score a goal. Unqualified, the word football normally means the form of football that is the most popular where the word is used. ')");
        }}

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
