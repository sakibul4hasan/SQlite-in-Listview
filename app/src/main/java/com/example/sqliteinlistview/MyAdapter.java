package com.example.sqliteinlistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {

    private final Context context;
    private ArrayList<Data> list;
    public MyAdapter(Context context, ArrayList<Data> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item,parent, false);
            TextView id = convertView.findViewById(R.id.ID);
            TextView name = convertView.findViewById(R.id.nameId);
            TextView mobile = convertView.findViewById(R.id.mobileId);

            id.setText(list.get(position).id.toString());
            name.setText(list.get(position).name.toString());
            mobile.setText("Mobile : "+list.get(position).phone.toString());

        return convertView;
    }
}
