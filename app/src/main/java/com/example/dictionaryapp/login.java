package com.example.dictionaryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.muddzdev.styleabletoastlibrary.StyleableToast;

import maes.tech.intentanim.CustomIntent;

public class login extends AppCompatActivity {

    EditText txt_user,txt_pass;
    Button btn_login;
    ImageView logo,line;
    CheckBox checkBox;
    TextView txt_toSignUp,lbl_signIn;
    Animation animation_top,animation_bottom,line_animation;
    Database database = new Database(this, "Dictionary_db.sqlite", null, 1);;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        getSupportActionBar().hide();
        animation_top= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        animation_bottom= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        line_animation= AnimationUtils.loadAnimation(this,R.anim.line_animation);
        addView();
        addAnimation();
        addEvent();

    }
    public void addEvent(){
        btn_login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String user=txt_user.getText().toString();
            String pass=txt_pass.getText().toString();
            if(user.matches(""))
            {
                StyleableToast.makeText(login.this,"Username null !",R.style.exampleToast).show();
            }
            else if(pass.matches("")) {
                StyleableToast.makeText(login.this,"Password null !",R.style.exampleToast).show();
            }
            else
            {
                try {
                    Cursor cursor=database.GetData("SELECT * FROM Accounts WHERE UserName='"+user+"' AND PassWord='"+pass+"'");
                    int count=cursor.getCount();
                    if(count>0)
                    {
                        StyleableToast.makeText(login.this,"Login accessfull !",R.style.SuccessToast).show();
                        toHome();
                    }
                    else {
                        StyleableToast.makeText(login.this,"Username or Password error !",R.style.exampleToast).show();
                    }
                }
                catch (Exception e)
                {
                    StyleableToast.makeText(login.this,"Username or Password error !",R.style.exampleToast).show();
                }
            }
        }
    });}
    private void addAnimation() {
        txt_user.setAnimation(animation_top);
        txt_pass.setAnimation(animation_top);
        txt_toSignUp.setAnimation(animation_bottom);
        logo.setAnimation(animation_top);
        checkBox.setAnimation(animation_bottom);
        btn_login.setAnimation(animation_bottom);
        lbl_signIn.setAnimation(animation_top);
        line.setAnimation(line_animation);
    }
    private void addView() {
        txt_pass=findViewById(R.id.txt_passSU);
        txt_user=findViewById(R.id.txt_userSU);
        logo=findViewById(R.id.logo_login);
        txt_toSignUp=findViewById(R.id.txt_toSignup);
        txt_toSignUp.setPaintFlags(txt_toSignUp.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        btn_login=findViewById(R.id.btn_signin);
        checkBox=findViewById(R.id.chk_remember);
        lbl_signIn=findViewById(R.id.lbl_signIn);
        line=findViewById(R.id.line1);
    }


    public void toHome() {
        Intent intent=new Intent(login.this, Home.class);
        login.this.startActivity(intent);
        CustomIntent.customType(this,"up-to-bottom");
        login.this.finish();
    }
    public void toSignUp(View view) {
        Intent intent=new Intent(login.this, signup.class);
        login.this.startActivity(intent);
        CustomIntent.customType(this,"left-to-right");
        //login.this.finish();
    }

}
