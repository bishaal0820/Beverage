package com.example.database;


import android.widget.ListAdapter;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MainDao {

    //Insert query
    @Insert(onConflict = REPLACE)
    void insert(MainData mainData);

    //Delete query
    @Delete
    void delete(MainData mainData);

    //Delete all query
    @Delete
    void reset(List<MainData> mainData);

    //Search query
   @Query("SELECT * FROM table_name where name = :sText")
   List<MainData> searchName(String sText);

    //Update query
    @Query("SELECT * FROM table_name where style = :sText")
    List<MainData> searchStyle(String sText);

    //Update query
    @Query("SELECT * FROM table_name where volume = :sText")
    List<MainData> searchVolume(String sText);

    //Update query
    @Query("SELECT * FROM table_name where brewed = :sText")
    List<MainData> searchBrew(String sText);

    //Update query
    @Query("SELECT * FROM table_name where expdate = :sText")
    List<MainData> searchBest(String sText);

    //Update query
    @Query("SELECT * FROM table_name where text = :sText")
    List<MainData> searchStorage(String sText);

    //Update
    @Update
    void update(MainData mainData);

    //Get all data query
    @Query("SELECT * FROM table_name")
    List<MainData> getAll();

    //Get all data query
    @Query("SELECT * FROM table_name ORDER BY lower(name) ASC")
    List<MainData> SortByName();

    //Get all data query
    @Query("SELECT * FROM table_name ORDER BY volume ASC")
    List<MainData> SortByVolume();




}
