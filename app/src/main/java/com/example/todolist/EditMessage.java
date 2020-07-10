package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditMessage extends AppCompatActivity {
    String messageText;
    int position;
    Button saveMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do_layot);
        Intent intent = getIntent();
        messageText = intent.getStringExtra(Intent_Constraints.INTENT_MESSAGE_DATA);
        position = intent.getIntExtra(Intent_Constraints.INTENT_ITEM_POSITION,-1);
        EditText messageData = findViewById(R.id.newMessageHolder);
        messageData.setText(messageText);
        saveMessage = findViewById(R.id.saveNewMessageBtn);
        saveMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newMessage = ((EditText)findViewById(R.id.newMessageHolder)).getText().toString();
                Intent intent = new Intent();
                intent.putExtra(Intent_Constraints.INTENT_CHANGED_MESSAGE,newMessage);
                intent.putExtra(Intent_Constraints.INTENT_ITEM_POSITION,position);
                setResult(Intent_Constraints.INTENT_RESULT_CODE_TWO,intent);
                finish();
            }
        });
    }
}
