package com.example.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.RoomDatabase;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST=1;
    public static final int EDIT_NOTE_REQUEST=2;
    RecyclerView recyclerView;
    List<MainData> dataList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    MainAdapter adapter;
    private Activity context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Adding the close button on the menu
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        //Adding the Plus button that looks like it's floating
        FloatingActionButton buttonAddNote = findViewById(R.id.button_add_note);

        //Starting the activity after the button is clicked
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddUpdate.class);
                startActivityForResult(intent,ADD_NOTE_REQUEST);
            }
        });


        //Displaying all the data in the list
        recyclerView = findViewById(R.id.recycler_view);
        //Initialize database
        database = RoomDB.getInstance(this);
        //Store database value in data list
        dataList = database.mainDao().getAll();
        //Initialize linear layout manager
        linearLayoutManager = new LinearLayoutManager(this);
        //set layout manager
        recyclerView.setLayoutManager(linearLayoutManager);
        //Initialize adapter
        adapter = new MainAdapter(MainActivity.this,dataList);
        //Set adapter
        recyclerView.setAdapter(adapter);


        //Adding Swipe to delete functionality
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //Initialize main data
                MainData d = dataList.get(viewHolder.getAdapterPosition());
                //Delete text from database
                database.mainDao().delete(d);
                //Notify when data is deleted
                int position = viewHolder.getAdapterPosition();
                dataList.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position,dataList.size());
                Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        //Update when the item is clicked
        adapter.setOnItemClickListener(new MainAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(MainData mainData) {
                Intent intent = new Intent(MainActivity.this,AddUpdate.class);
                intent.putExtra(AddUpdate.EXTRA_ID,mainData.getID());
                intent.putExtra(AddUpdate.EXTRA_TEXT,mainData.getText());
                intent.putExtra(AddUpdate.EXTRA_NAME,mainData.getName());
                startActivityForResult(intent,EDIT_NOTE_REQUEST);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            String sText = data.getStringExtra(AddUpdate.EXTRA_TEXT);
            String sName = data.getStringExtra(AddUpdate.EXTRA_NAME);

            //Initialize main data
            MainData mainData = new MainData(sText, sName);
            //Insert text in database
            database.mainDao().insert(mainData);
            //view();
            //Notify when the data is inserted
            dataList.clear();
            dataList.addAll(database.mainDao().getAll());
            adapter.notifyDataSetChanged();

            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();
        }
        else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK)
        {
            int id = data.getIntExtra(AddUpdate.EXTRA_ID,-1);

            if (id==-1){
                Toast.makeText(this, "Notes Can't Be Updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String sText = data.getStringExtra(AddUpdate.EXTRA_TEXT);
            String sName = data.getStringExtra(AddUpdate.EXTRA_NAME);

            //Initialize main data
            MainData mainData = new MainData(sText, sName);
            mainData.setID(id);

            //Insert text in database
            database.mainDao().update(mainData);

            //Notify when the data is updated
            dataList.clear();
            dataList.addAll(database.mainDao().getAll());
            adapter.notifyDataSetChanged();

            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();



        }
        else {
            Toast.makeText(this, "Note Not Saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.delete_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.delete_all:
                //Delete all data from the database
                database.mainDao().reset(dataList);
                //Notify when all data is deleted
                dataList.clear();
                dataList.addAll(database.mainDao().getAll());
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void DialogBox(){
        //Create dialog
        final Dialog dialog = new Dialog(context);
        //Set content view
        dialog.setContentView(R.layout.dialog_update);
        //Initialize width
        int width = WindowManager.LayoutParams.MATCH_PARENT;
        //Initialize height
        int height = WindowManager.LayoutParams.WRAP_CONTENT;
        //Set Layout
        dialog.getWindow().setLayout(width,height);
        //show dialog
        dialog.show();
        Button btUpdate = dialog.findViewById(R.id.bt_update);

        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss dialog
                dialog.dismiss();
            }
        });

    }

}