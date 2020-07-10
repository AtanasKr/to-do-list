package com.example.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter  extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> values;

    public CustomAdapter(Context context, ArrayList<String> values){
        super(context,-1,values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.custom,parent,false);
        TextView textView = rowView.findViewById(R.id.holderFor);
        textView.setText(values.get(position));
        return rowView;
    }
}
