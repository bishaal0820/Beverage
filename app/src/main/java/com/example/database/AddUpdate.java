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
    public static final String EXTRA_STYLE = "com.example.database.EXTRA_STYLE";
    public static final String EXTRA_VOLUME = "com.example.database.EXTRA_VOLUME";
    public static final String EXTRA_BREWED = "com.example.database.EXTRA_BREWED";
    public static final String EXTRA_BEST = "com.example.database.EXTRA_BEST";


    EditText editText,editName,eStyle,eVolume,eBrewed,eBest;
    List<MainData> dataList = new ArrayList<>();
    RoomDB database;
    MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update);

        editText = findViewById(R.id.edit_text);
        editName = findViewById(R.id.edit_name);
        eStyle = findViewById(R.id.etStyle);
        eVolume = findViewById(R.id.etVolume);
        eBrewed = findViewById(R.id.etBrewed);
        eBest = findViewById(R.id.etBestBefore);

        Intent intent = getIntent();

        //Set Name for the menu and update
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");
            editText.setText(intent.getStringExtra(EXTRA_TEXT));
            editName.setText(intent.getStringExtra(EXTRA_NAME));
            eStyle.setText(intent.getStringExtra(EXTRA_STYLE));
            eVolume.setText(intent.getStringExtra(EXTRA_VOLUME));
            eBrewed.setText(intent.getStringExtra(EXTRA_BREWED));
            eBest.setText(intent.getStringExtra(EXTRA_BEST));

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
        String style=eStyle.getText().toString();
        String volume=eVolume.getText().toString();
        String brewed=eBrewed.getText().toString();
        String best=eBest.getText().toString();



        if (text.trim().isEmpty()||name.trim().isEmpty()||style.trim().isEmpty()||volume.trim().isEmpty()||brewed.trim().isEmpty()||best.trim().isEmpty()){
            Toast.makeText(this,"Please insert an text and Name",Toast.LENGTH_SHORT).show();
            return;
        }


        Intent data = new Intent();
        data.putExtra(EXTRA_TEXT,text);
        data.putExtra(EXTRA_NAME,name);
        data.putExtra(EXTRA_STYLE,style);
        data.putExtra(EXTRA_VOLUME,volume);
        data.putExtra(EXTRA_BREWED,brewed);
        data.putExtra(EXTRA_BEST,best);


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