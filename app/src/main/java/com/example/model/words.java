package com.example.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class words {
    private int ID;
    private String name;
    private String wordType;
    private String spell;
    private String mean;
    private String example;
    private String synonym;
    private int favourite;


    private  byte [] image;

    public words() {
    }

    public words(int ID, String name, String spell, String mean){
        this.ID = ID;
        this.name = name;
        this.wordType = wordType;
        this.spell = spell;
    }
    public words(int ID, String name, String wordType, String spell, String mean, String example, String synonym,int favourite) {
        this.ID = ID;
        this.name = name;
        this.wordType = wordType;
        this.spell = spell;
        this.mean = mean;
        this.example = example;
        this.synonym = synonym;
        this.favourite=favourite;
    }
    public int getFavourite() {
        return favourite;
    }

    public void setFavourite(int favourite) {
        this.favourite = favourite;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWordType() {
        return wordType;
    }

    public void setWordType(String wordType) {
        this.wordType = wordType;
    }

    public String getSpell() {
        return spell;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getSynonym() {
        return synonym;
    }

    public void setSynonym(String synonym) {
        this.synonym = synonym;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name+"\n"+this.spell+"\n"+this.mean+"\n"+this.wordType+this.synonym+"\n"+this.synonym+this.ID;
    }
}
