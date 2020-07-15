package com.example.dictionaryapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.transition.Fade;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.model.Theme;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;


public class viewTechnology extends AppCompatActivity {


    ArrayList<Theme> themeArrayList;
    private static final int REQUEST_CODE_SPEECH_INPUT = 1;
    LinearLayout linearLayout,line_menu;
    ImageView img_theme,show_theme,img_speak,img_nexxt,img_previous,img_Delete_text,img_void,img_stran,btn_edit,btn_add;
    TextView txt_them,txt_content,txt_spech_totext,txt_page,txt_total_pages,txt_speed;
    SeekBar mSeekBarSpeed;
    Database database = new Database(this, "Dictionary_db.sqlite", null, 1);
    private int theme=0;
    private TextToSpeech mTTS;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_technology);
        getSupportActionBar().hide();
        addView();
        addEvent();
    }

    @Override
    protected void onDestroy() {
        if(mTTS!=null)
        {
            mTTS.stop();
            mTTS.shutdown();
        }
        super.onDestroy();
    }

    private void addView() {
        themeArrayList=new ArrayList<>();
        linearLayout=findViewById(R.id.linear_theme);
        mSeekBarSpeed=findViewById(R.id.mSeekBarSpeed);
        line_menu=findViewById(R.id.lineear_menu);
        txt_them=findViewById(R.id.txt_theme);
        img_theme=findViewById(R.id.img_theme);
        show_theme=findViewById(R.id.n_show_theme);
        txt_content=findViewById(R.id.txt_content);
        img_speak=findViewById(R.id.img_speak);
        img_nexxt=findViewById(R.id.img_nexxt);
        img_previous=findViewById(R.id.img_previous);
        img_Delete_text=findViewById(R.id.img_delete_Text);
        txt_spech_totext=findViewById(R.id.txt_speech_to_text);
        txt_page=findViewById(R.id.txt_page);
        txt_total_pages=findViewById(R.id.txt_total_pages);
        img_void=findViewById(R.id.img_void);
        txt_speed=findViewById(R.id.txt_speed);
        img_stran=findViewById(R.id.img_strans);
        btn_add=findViewById(R.id.btn_add);
        btn_edit=findViewById(R.id.btn_edit);

        Intent intent = getIntent();
        Bundle bundle=intent.getBundleExtra("my");
        if (bundle != null) {
            theme=bundle.getInt("theme");
        }
        if(theme==1) {linearLayout.setBackgroundResource(R.drawable.custom_line_setting);txt_them.setText("Technology");img_theme.setImageResource(R.drawable.tech);
        show_theme.setImageResource(R.drawable.next);img_previous.setImageResource(R.drawable.previous1);img_nexxt.setImageResource(R.drawable.next);GetData("SELECT * FROM Theme WHERE ThemeName='"+txt_them.getText().toString()+"'");}
        else if(theme==2){linearLayout.setBackgroundResource(R.drawable.custom_btn_login);txt_them.setText("Fashion");img_theme.setImageResource(R.drawable.fashion);
        show_theme.setImageResource(R.drawable.n2);img_previous.setImageResource(R.drawable.previous2);img_nexxt.setImageResource(R.drawable.n2);GetData("SELECT * FROM Theme WHERE ThemeName='Fashion'");}
        else if(theme==3){linearLayout.setBackgroundResource(R.drawable.custom_row_theme);txt_them.setText("Sports");img_theme.setImageResource(R.drawable.sp)
        ;show_theme.setImageResource(R.drawable.n3);img_previous.setImageResource(R.drawable.previous3);img_nexxt.setImageResource(R.drawable.n3);GetData("SELECT * FROM Theme WHERE ThemeName='Sports'");}
        else if (theme==4){linearLayout.setBackgroundResource(R.drawable.custom_row_theme2);txt_them.setText("Music");img_theme.setImageResource(R.drawable.mussic);
        show_theme.setImageResource(R.drawable.n1);img_previous.setImageResource(R.drawable.previous4);img_nexxt.setImageResource(R.drawable.n1);GetData("SELECT * FROM Theme WHERE ThemeName='Music'");}
    }

    boolean visible=false;
    private int i=0;
    private void addEvent() {
        show_theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionSet set = new TransitionSet()
                //.addTransition(new Scale(0.7f))
                .addTransition(new Fade())
                .setInterpolator(visible ? new LinearOutSlowInInterpolator() : new FastOutLinearInInterpolator());
        final ViewGroup transitionsContainer = (ViewGroup)findViewById(R.id.transitions_container1);
        TransitionManager.beginDelayedTransition(transitionsContainer, set);
        txt_content.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        line_menu.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
//        fashion.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        show_theme.setRotation(visible?0:90);
        if(visible)visible=false;
        else visible=true;
            }
        });

        img_speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                texttoSpeak();
            }
        });
        img_Delete_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txt_spech_totext.getText().equals("Speaking follow transcript")){txt_spech_totext.setText("Speaking follow transcript");}
            }
        });
        txt_content.setText(themeArrayList.get(i).getContent());
        txt_total_pages.setText("/"+themeArrayList.size());
        id=themeArrayList.get(i).getID();
        img_nexxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { if(i<themeArrayList.size()-1) {id=themeArrayList.get(i+1).getID(); txt_content.setText(themeArrayList.get(i+1).getContent());i++;txt_page.setText((i+1)+""); } }
        });
        img_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { if(i>0){i--;txt_page.setText((i+1)+""); txt_content.setText(themeArrayList.get(i).getContent());id=themeArrayList.get(i+1).getID();} }});
        img_void.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });
        mSeekBarSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txt_speed.setText(progress+"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        img_stran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { String tam=txt_content.getText().toString();String tam1=txt_spech_totext.getText().toString();txt_spech_totext.setText(tam);txt_content.setText(tam1); }});
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = txt_content.getText().toString();
                if(content.matches("")){ StyleableToast.makeText(viewTechnology.this,"Nội dung trống !",R.style.exampleToast).show();}
                else {
                    final AlertDialog.Builder alertDialog=new AlertDialog.Builder(viewTechnology.this);
                    alertDialog.setMessage("Bạn có muốn thêm nội dụng này : "+txt_content.getText().toString()+" ?");
                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            database.QueryData("INSERT INTO Theme VALUES(null,'"+txt_them.getText().toString()+"','"+txt_content.getText().toString()+"')");
                            StyleableToast.makeText(viewTechnology.this,"Thêm nội dụng thành công !",R.style.SuccessToast).show();
                            GetData("SELECT * FROM Theme WHERE ThemeName='"+txt_them.getText().toString()+"'");
                            txt_total_pages.setText("/"+themeArrayList.size());
                        }
                    });
                    alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog.show();
                }
            }
        });
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = txt_content.getText().toString();
                if(content.matches("")){ StyleableToast.makeText(viewTechnology.this,"Nội dung trống !",R.style.exampleToast).show();}
                else {
                    final AlertDialog.Builder alertDialog=new AlertDialog.Builder(viewTechnology.this);
                    alertDialog.setMessage("Bạn có sữa thành nội dụng này : "+txt_content.getText().toString()+" ?");
                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            database.QueryData("UPDATE Theme SET ThemeName='"+txt_them.getText().toString()+"',Content='"+txt_content.getText().toString()+"' WHERE ID="+id+"");
                            StyleableToast.makeText(viewTechnology.this,"Sửa nội dụng thành công !",R.style.SuccessToast).show();
                            GetData("SELECT * FROM Theme WHERE ThemeName='"+txt_them.getText().toString()+"'");
                        }
                    });
                    alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog.show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode==RESULT_OK && requestCode==REQUEST_CODE_SPEECH_INPUT)
        {
            ArrayList<String> resutl=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if(txt_spech_totext.getText().equals("Speaking follow transcript")){
            txt_spech_totext.setText(resutl.get(0)); }
            else {String tam=txt_spech_totext.getText().toString();txt_spech_totext.setText(tam+" "+resutl.get(0));}
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void speak(){
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.ENGLISH);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Hi speaking something");
        try {
            startActivityForResult(intent,REQUEST_CODE_SPEECH_INPUT);
        }
        catch (Exception e)
        {
            Toast.makeText(this,""+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    private void texttoSpeak()
    {
        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTS.setLanguage(Locale.ENGLISH);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    } else {
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
                float speed = (float) mSeekBarSpeed.getProgress() / 50;
                if (speed < 0.1) speed = 0.1f;
                mTTS.setSpeechRate(speed);
                mTTS.speak(txt_content.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });
    }

    private void GetData(String qr) {
        Cursor c= database.GetData(qr);
        themeArrayList.clear();
        while (c.moveToNext()){
            int ID=c.getInt(0);
            String themeName =c.getString(1);
            String themeContent=c.getString(2);
            themeArrayList.add(new Theme(ID,themeName,themeContent));
        }
    }

}
