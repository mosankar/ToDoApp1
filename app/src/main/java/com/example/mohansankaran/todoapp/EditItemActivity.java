package com.example.mohansankaran.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditItemActivity extends AppCompatActivity {

    public int intPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        String et2EditText = getIntent().getStringExtra("et2EditText");
        intPosition = getIntent().getExtras().getInt("intPosition");
        EditText editText = (EditText) findViewById(R.id.et2EditText);
        editText.setText(et2EditText, TextView.BufferType.EDITABLE);
        editText.setSelection(editText.getText().length());
    }

    public void onEditItem(View v) {
        EditText et2EditedText = (EditText) findViewById(R.id.et2EditText);
        Intent data = new Intent();
        data.putExtra("et2EditedText", et2EditedText.getText().toString());
        data.putExtra("intPosition", intPosition);
        data.putExtra("code", 200);
        setResult(RESULT_OK, data);
        finish();
    }
}
