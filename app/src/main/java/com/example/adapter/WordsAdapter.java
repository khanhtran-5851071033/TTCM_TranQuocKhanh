package com.example.adapter;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dictionaryapp.R;
import com.example.dictionaryapp.findword;
import com.example.model.words;

import java.util.List;
import java.util.Locale;

public class WordsAdapter extends BaseAdapter {
//    private Activity mainActivity;
    private findword mainActivity;
    private int layout;
    private List<words> wordsList;
    private TextToSpeech mTTS;
    public WordsAdapter(findword mainActivity, int layout, List<words> wordsLists)
    {
        this.mainActivity=mainActivity;
        this.layout=layout;
        this.wordsList=wordsLists;
    }
    @Override
    public int getCount() {
        return wordsList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public static class ViewHolder{
        TextView txt_Name,txt_spell,txt_mean;
        ImageView img_sound,img;
        //TextToSpeech mTTS;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        WordsAdapter.ViewHolder holder;
        if(convertView==null)
        {
            holder =new WordsAdapter.ViewHolder();
            LayoutInflater inflater=(LayoutInflater) mainActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(layout,null);
            holder.txt_spell=convertView.findViewById(R.id.txt_spell);
            holder.txt_Name=convertView.findViewById(R.id.txt_name);
            holder.txt_mean=convertView.findViewById(R.id.txt_a);
            holder.img_sound=convertView.findViewById(R.id.img_sound);
            convertView.setTag(holder);
        }
        else {
            holder=(WordsAdapter.ViewHolder) convertView.getTag();
        }
        final words w=wordsList.get(position);
        holder.txt_Name.setText(w.getName());
        holder.txt_mean.setText(w.getMean());
        holder.txt_spell.setText(w.getSpell());
        holder.img_sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              mTTS=new TextToSpeech(mainActivity, new TextToSpeech.OnInitListener() {
                  @Override
                  public void onInit(int status) {
                      if(status==TextToSpeech.SUCCESS)
                      {
                          int result= mTTS.setLanguage(Locale.ENGLISH);
                          if(result==TextToSpeech.LANG_MISSING_DATA || result==TextToSpeech.LANG_NOT_SUPPORTED){
                              Log.e("TTS","Language not supported");
                          }
                          else {

                          }
                      }
                      else {
                          Log.e("TTS","Initialization failed");
                      }
                      mTTS.setSpeechRate(1);
                      mTTS.speak(w.getName(),TextToSpeech.QUEUE_FLUSH,null);
                  }
              });
            }
        });
        //imag
//        byte[] wordImage = w.getImage();
//        Bitmap bitmap = BitmapFactory.decodeByteArray(foodImage, 0, wordImage.length);
//        holder.img.setImageBitmap(bitmap);

        return convertView;
    }
}
