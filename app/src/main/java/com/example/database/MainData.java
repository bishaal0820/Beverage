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

    public MainData(String text, String name) {
        this.text = text;
        this.name = name;
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
}
