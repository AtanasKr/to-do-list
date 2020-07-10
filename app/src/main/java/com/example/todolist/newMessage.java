package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class newMessage extends AppCompatActivity {
    Button saveMessage;
    EditText message;
    String getMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do_layot);
        saveMessage = findViewById(R.id.saveNewMessageBtn);
        message = findViewById(R.id.newMessageHolder);
        saveMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(newMessage.this,"Your message is empty",Toast.LENGTH_LONG);
                getMessage = message.getText().toString();
                if (getMessage.equals("")){
                    toast.show();
                }
                else {
                    Intent intent = new Intent();
                    intent.putExtra(Intent_Constraints.INTENT_MESSAGE_FIELD,getMessage);
                    setResult(Intent_Constraints.INTENT_RESULT_CODE,intent);
                    finish();
                }
            }
        });

    }
}
