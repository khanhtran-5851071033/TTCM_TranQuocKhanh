package com.example.dictionaryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

public class Setting extends AppCompatActivity {

    Switch auto;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        auto=findViewById(R.id.s_autoSpeak);

        auto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(auto.isChecked())
                {
                    SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("auto",true);
                    editor.apply();

                }
                else
                {
                    SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("auto");
                    editor.apply();
                }
            }
        });
    }


}
