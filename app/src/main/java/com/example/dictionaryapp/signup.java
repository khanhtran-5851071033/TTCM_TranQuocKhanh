package com.example.dictionaryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.model.Account;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.ArrayList;

import maes.tech.intentanim.CustomIntent;

public class signup extends AppCompatActivity {

    EditText txt_user,txt_pass,txt_email,txt_phone;
    Button btn_signup;
    CheckBox checkBox;
    TextView txt_toSignIn,lbl_signup;
    Animation animation_top,line_animation;
    ImageView line,btn_back;

    Database database;
    ArrayList<Account> accountArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        getSupportActionBar().hide();
        animation_top= AnimationUtils.loadAnimation(this,R.anim.top_animation);
        line_animation= AnimationUtils.loadAnimation(this,R.anim.line_animation);
        addView();
        addAnimation();
        PrepareDB();
        addEvent();
        //GetData("SELECT * FROM Accounts");


    }
    public void addEvent(){
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UserName = txt_user.getText().toString();
                String PassWord = txt_pass.getText().toString();
                String Email = txt_email.getText().toString();
                String Phone = txt_phone.getText().toString();
                if(UserName.matches("")){ StyleableToast.makeText(signup.this,"Username null !",R.style.exampleToast).show();}
                else if(PassWord.matches("")){StyleableToast.makeText(signup.this,"Password null !",R.style.exampleToast).show();}
                else if(Email.matches("")){StyleableToast.makeText(signup.this,"Email null !",R.style.exampleToast).show();}
                else if(Phone.matches("")){StyleableToast.makeText(signup.this,"Phone number null !",R.style.exampleToast).show();}
                else if(!checkBox.isChecked()){StyleableToast.makeText(signup.this,"You should accept !",R.style.exampleToast).show();}
                else {
                    try {
                        Cursor cursor=database.GetData("SELECT * FROM Accounts WHERE UserName='"+UserName+"'");
                        int count=cursor.getCount();
                        if(count>0)
                        {
                            StyleableToast.makeText(signup.this,"Username exits !",R.style.exampleToast).show();
                        }
                        else {
                            database.QueryData("INSERT INTO Accounts VALUES(null,'"+UserName+"','"+PassWord+"','"+Email+"','"+Phone+"')");
                            StyleableToast.makeText(signup.this,"Sign Up accessfull !",R.style.SuccessToast).show();
                        }

                    }
                    catch (Exception e)
                    {
                        StyleableToast.makeText(signup.this,"Sign Up failed !",R.style.exampleToast).show();
                    }
                }
            }
        });
        txt_pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(txt_pass.length()>8)
                {
                    txt_pass.setTextColor(getResources().getColor(R.color.Red));
                    StyleableToast.makeText(signup.this,"More 8 character !",R.style.exampleToast).show();
                }
                else if(txt_pass.length()<=8)
                txt_pass.setTextColor(getResources().getColor(R.color.black));
            }
        });
    }
    private void GetData(String qr) {
        Cursor c = database.GetData(qr);
        accountArrayList.clear();
        while (c.moveToNext()) {
            int ID = c.getInt(0);
            String UserName = c.getString(1);
            String PassWord = c.getString(2);
            String Email = c.getString(3);
            String Phone = c.getString(4);
            accountArrayList.add(new Account(ID, UserName, PassWord, Email, Phone));
        }
    }
    private void PrepareDB() {
        database = new Database(this, "Dictionary_db.sqlite", null, 1);
       // database.GetData("DELETE FROM Accounts");
        database.QueryData("CREATE TABLE IF NOT EXISTS Accounts(ID Integer PRIMARY KEY AUTOINCREMENT, UserName VARCHAR(50),PassWord VARCHAR(50)" +
                ",Email VARCHAR(50),PhoneNumber VARCHAR(20))");
       // database.QueryData("INSERT INTO Accounts VALUES(null,'khanh','123','donbov79@gmail.com','0384742790')");
    }

    private void addAnimation() {
        txt_user.setAnimation(animation_top);
        txt_pass.setAnimation(animation_top);
        txt_toSignIn.setAnimation(animation_top);
        txt_phone.setAnimation(animation_top);
        txt_email.setAnimation(animation_top);
        checkBox.setAnimation(animation_top);
        btn_signup.setAnimation(animation_top);
        lbl_signup.setAnimation(animation_top);
        line.setAnimation(line_animation);
        btn_back.setAnimation(line_animation);
    }

    private void addView() {
        txt_pass=findViewById(R.id.txt_passSU);
        txt_user=findViewById(R.id.txt_userSU);
        txt_email=findViewById(R.id.txt_email);
        txt_phone=findViewById(R.id.txt_phone);
        btn_signup=findViewById(R.id.btn_signin);
        checkBox=findViewById(R.id.chk_accept);
        txt_toSignIn=findViewById(R.id.txt_toSigin);
        txt_toSignIn.setPaintFlags(txt_toSignIn.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        lbl_signup=findViewById(R.id.lbl_signup);
        btn_back=findViewById(R.id.btn_back);
        line=findViewById(R.id.line1);
    }
    public void toSignIn(View view) {
        signup.this.finish();
        CustomIntent.customType(this,"right-to-left");
    }

}
