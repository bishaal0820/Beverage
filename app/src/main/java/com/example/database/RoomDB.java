package com.example.database;


//Add Database entry here

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

@Database(entities = {MainData.class},version=1,exportSchema = false)
@TypeConverters(DataConverter.class)
public abstract class RoomDB extends RoomDatabase {

    //Create database instance
    private static RoomDB database;

    //Define database name

    private static String DATABASE_NAME = "database";

    public synchronized static RoomDB getInstance(Context context)
    {
        //check condition

        if (database==null)
        {
            //When database is null
            //Initialize database
            database= Room.databaseBuilder(context.getApplicationContext(),
                    RoomDB.class,DATABASE_NAME).allowMainThreadQueries().fallbackToDestructiveMigration()
                    .build();
        }

        return database;
    }

    //create DAO
    public abstract MainDao mainDao();
}
