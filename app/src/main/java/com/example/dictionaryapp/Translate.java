package com.example.dictionaryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Translate extends AppCompatActivity {

    EditText txt_input;
    ImageView img_delete_input;
    LinearLayout linear_output;
    Button btn_anh_viet,btn_viet_anh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        getSupportActionBar().hide();
        addView();
        addEvent();
    }

    private void addView() {
        txt_input=findViewById(R.id.text_input);
        img_delete_input=findViewById(R.id.img_delete_input);
        linear_output=findViewById(R.id.linear_translate);
        btn_anh_viet=findViewById(R.id.btn_anh_viet);
        btn_viet_anh=findViewById(R.id.btn_viet_anh);

    }

    private void addEvent() {
        txt_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if(!txt_input.getText().toString().matches("")){
                    img_delete_input.setVisibility(View.VISIBLE);
                }
                if(txt_input.getText().toString().matches(""))
                  img_delete_input.setVisibility(View.INVISIBLE);
            }
        });
        img_delete_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_input.setText("");
                linear_output.setVisibility(View.INVISIBLE);

            }
        });
        btn_anh_viet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_output.setVisibility(View.VISIBLE);
            }
        });
        btn_viet_anh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_output.setVisibility(View.VISIBLE);
            }
        });

    }
}
