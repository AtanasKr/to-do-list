package com.example.todolist;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> dataModel = new ArrayList<String>();
    CustomAdapter adapter;
    Button newMessageBtn;
    String messageText;
    int position;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new CustomAdapter(this,dataModel);
        listView = (ListView) findViewById(R.id.taskHolder);
        listView.setAdapter(adapter);
        newMessageBtn = findViewById(R.id.addMessageBtn);
        newMessageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,newMessage.class);
                startActivityForResult(intent,Intent_Constraints.INTENT_REQUEST_CODE);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditMessage.class);
                intent.putExtra(Intent_Constraints.INTENT_MESSAGE_DATA, dataModel.get(position).toString());
                intent.putExtra(Intent_Constraints.INTENT_ITEM_POSITION,position);
                startActivityForResult(intent,Intent_Constraints.INTENT_REQUEST_CODE_TWO);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                dataModel.remove(position);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
        try {
            Scanner sc = new Scanner(openFileInput("Todo.txt"));
            while (sc.hasNextLine()){
                String data1 = sc.nextLine();
                adapter.add(data1);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==Intent_Constraints.INTENT_REQUEST_CODE){
            messageText = data.getStringExtra(Intent_Constraints.INTENT_MESSAGE_FIELD);
            dataModel.add(messageText);
            myRef = database.getReference(messageText);
            myRef.setValue(messageText);
            adapter.notifyDataSetChanged();
        }
        else if(requestCode==Intent_Constraints.INTENT_REQUEST_CODE_TWO){
            if(messageText!=null && !messageText.isEmpty()) {
                messageText = data.getStringExtra(Intent_Constraints.INTENT_CHANGED_MESSAGE);
                position = data.getIntExtra(Intent_Constraints.INTENT_ITEM_POSITION, -1);
                dataModel.remove(position);
                dataModel.add(position, messageText);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            PrintWriter PW = new PrintWriter(openFileOutput("Todo.txt", Context.MODE_PRIVATE));
            for (String data : dataModel) {
                PW.println(data);
            }
            PW.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finish();
    }



}
