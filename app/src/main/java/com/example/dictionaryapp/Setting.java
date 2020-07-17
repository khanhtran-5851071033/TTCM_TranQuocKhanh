package com.example.dictionaryapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.util.Set;

import javax.security.auth.login.LoginException;

import maes.tech.intentanim.CustomIntent;

public class Setting extends AppCompatActivity {

    Switch auto;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportActionBar().hide();
        auto=findViewById(R.id.s_autoSpeak);
        if(readFromInternal().trim().equals("1"))
            auto.setChecked(true);
        auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(auto.isChecked())
                {
                    writeExternal("1");
                }
                else
                {
                    writeExternal("0");
                }
            }
        });
    }
    private String readFromInternal(){
        try {
            File sdcard = Environment.getExternalStorageDirectory();
            File f = new File(sdcard,"auto.txt");
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = br.readLine()) != null) {
                content.append(line);
            }
            return content.toString();
        }catch (Exception ex){
            Log.e("lỗi ",ex.getMessage() );
        }
        return "";
    }
    public void writeExternal(String string) {
        try {
            File sdcard = Environment.getExternalStorageDirectory();
            File f = new File(sdcard,"auto.txt");
            OutputStream os = new FileOutputStream(f);
            os.write(string.getBytes());
            os.close();
            //StyleableToast.makeText(Setting.this,"Đã Thêm lời nhắc !",R.style.SuccessToast).show();
        } catch (Exception e) {
            Log.e("lỗi ",e.getMessage() );
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void openEmail(View view) {
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://gmail.com/"));
        startActivity(intent);
    }

    public void openFB(View view) {
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://Facebook.com/"));
        startActivity(intent);
    }

    public void openApstore(View view) {
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://www.apple.com/"));
        startActivity(intent);
    }

    public void openQuestion(View view) {
        Intent intent=new Intent(Setting.this, setting_questions.class);
        Setting.this.startActivity(intent);
        CustomIntent.customType(this,"left-to-right");
    }
}
