package com.example.mohansankaran.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 20;
    ArrayList<String> toDoItems;
    ArrayAdapter<String> aToDoAdapter;
    ListView lvItems;
    EditText etEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        lvItems = (ListView) findViewById(R.id.lvItems);
        lvItems.setAdapter(aToDoAdapter);
        etEditText = (EditText) findViewById(R.id.etEditText);
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String editData = toDoItems.get(position);
                launchComposeView(editData, position);
            }
        });


        lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                toDoItems.remove(position);
                aToDoAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });
    }

    public void populateArrayItems() {
        toDoItems = new ArrayList<String>();
        readItems();
        aToDoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, toDoItems);
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try {
            toDoItems = new ArrayList<String>(FileUtils.readLines(file));
        } catch (IOException e) {

        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(file, toDoItems);
        } catch (IOException e) {

        }
    }

    public void onAddItem(View view) {
        if(!(etEditText.getText().toString().equals(""))){
            aToDoAdapter.add(etEditText.getText().toString());
            writeItems();
            etEditText.setText("");
        }else{
            etEditText.setText("");
            Toast.makeText(this, "Added Item can't be empty!", Toast.LENGTH_SHORT).show();
        }
    }

    public void launchComposeView(String editData, int position) {
        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
        i.putExtra("et2EditText", editData);
        i.putExtra("intPosition", position);
        startActivityForResult(i, REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String et2EditedText = data.getExtras().getString("et2EditedText");
            int intPosition = data.getExtras().getInt("intPosition");
            if(!(et2EditedText.equals(""))){
                toDoItems.set(intPosition, et2EditedText);
                aToDoAdapter.notifyDataSetChanged();
                writeItems();
            }else{
                toDoItems.remove(intPosition);
                aToDoAdapter.notifyDataSetChanged();
                writeItems();
            }

        }
    }
}
