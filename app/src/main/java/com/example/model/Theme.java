package com.example.model;

public class Theme {
    int ID;
    String themeName;
    String content;
    public Theme(int ID, String themeName, String content) {
        this.ID = ID;
        this.themeName = themeName;
        this.content = content;
    }
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getThemeName() {
        return themeName;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
