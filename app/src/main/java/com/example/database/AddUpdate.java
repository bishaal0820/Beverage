package com.example.database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddUpdate extends AppCompatActivity {

    public static final String EXTRA_ID = "com.example.database.EXTRA_ID";
    public static final String EXTRA_TEXT = "com.example.database.EXTRA_TEXT";
    public static final String EXTRA_NAME = "com.example.database.EXTRA_NAME";


    EditText editText,editName;
    List<MainData> dataList = new ArrayList<>();
    RoomDB database;
    MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update);

        editText = findViewById(R.id.edit_text);
        editName = findViewById(R.id.edit_name);

        Intent intent = getIntent();

        //Set Name for the menu and update
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");
            editText.setText(intent.getStringExtra(EXTRA_TEXT));
            editName.setText(intent.getStringExtra(EXTRA_NAME));

        } else {
            setTitle("Add Note"); }


        //Initialize database
        database = RoomDB.getInstance(this);
        //Store database value in data list
        dataList = database.mainDao().getAll();
    }

    private void saveNote()
    {
        String text=editText.getText().toString();
        String name=editName.getText().toString();


        if (text.trim().isEmpty()||name.trim().isEmpty()){
            Toast.makeText(this,"Please insert an text and Name",Toast.LENGTH_SHORT).show();
            return;
        }


        Intent data = new Intent();
        data.putExtra(EXTRA_TEXT,text);
        data.putExtra(EXTRA_NAME,name);

        int id =getIntent().getIntExtra(EXTRA_ID,-1);

        if (id!=-1) {
            data.putExtra(EXTRA_ID,id);
        }

        setResult(RESULT_OK,data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu_item,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}