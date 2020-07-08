package com.example.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dictionaryapp.R;
import com.example.dictionaryapp.RemindWord;
import com.example.dictionaryapp.findword;
import com.example.dictionaryapp.login;
import com.example.dictionaryapp.viewWord;
import com.example.model.words;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RemindAdapter extends BaseAdapter {

    private RemindWord remindWord;
    private int layout;
    private List<words> wordsList;
    private TextToSpeech mTTS;
    private Set<String>ds_set = new HashSet<>();
    public RemindAdapter(RemindWord remindWord, int layout, List<words> wordsLists)
    {
        this.remindWord=remindWord;
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
        CheckBox checkBox_time;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final RemindAdapter.ViewHolder holder;
        if(convertView==null)
        {
            holder =new RemindAdapter.ViewHolder();
            LayoutInflater inflater=(LayoutInflater) remindWord.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(layout,null);
            holder.txt_spell=convertView.findViewById(R.id.txt_spell);
            holder.txt_Name=convertView.findViewById(R.id.txt_name);
            holder.txt_mean=convertView.findViewById(R.id.txt_a);
            holder.checkBox_time=convertView.findViewById(R.id.check_time);
            convertView.setTag(holder);
        }
        else {
            holder=(RemindAdapter.ViewHolder) convertView.getTag();
        }
        final words w=wordsList.get(position);
        holder.txt_Name.setText(w.getName());
        holder.txt_mean.setText(w.getMean());
        holder.txt_spell.setText(w.getSpell());
        holder.checkBox_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(holder.checkBox_time.isChecked())
                    {
                        SharedPreferences sharedPreferences = remindWord.getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        ds_set.add(w.getID()+"");
                        editor.putStringSet("set",ds_set);
                        editor.apply();
                        remindWord.Toast_remind();
                    }
                    else {
                        SharedPreferences sharedPreferences = remindWord.getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("set");
                        editor.apply();
                        ds_set.remove(w.getID()+"");
                        editor.putStringSet("set",ds_set);
                        editor.apply();
                        remindWord.Toast_unremind();
                    }
            }
        });
        return convertView;
    }

}
