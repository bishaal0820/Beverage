package com.example.database;


//Define table name

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "table_name")
public class MainData implements Serializable {
    //Create Primary Key column

    @PrimaryKey(autoGenerate = true)
    private int ID;

    //Create text column
    @ColumnInfo(name="text")
    private String text;

    private String name;

    private String style;

    private String volume;
    private String brewed;
    private String best;

    public MainData(String text, String name,String style,String volume,String brewed,String best) {
        this.text = text;
        this.name = name;
        this.style = style;
        this.volume = volume;
        this.brewed = brewed;
        this.best = best;
    }

//Getter and Setter

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getBrewed() {
        return brewed;
    }

    public void setBrewed(String brewed) {
        this.brewed = brewed;
    }

    public String getBest() {
        return best;
    }

    public void setBest(String best) {
        this.best = best;
    }
}
