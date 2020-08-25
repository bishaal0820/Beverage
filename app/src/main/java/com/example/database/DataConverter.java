package com.example.database;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.TypeConverter;

import java.io.ByteArrayOutputStream;
import java.util.Date;

public class DataConverter {
@TypeConverter
    public static Long toTime(Date date)
{
    return date==null? null: date.getTime();
}

@TypeConverter
    public static Date toDate(Long timestamp)
{
    return timestamp==null? null: new Date(timestamp);
}

}
