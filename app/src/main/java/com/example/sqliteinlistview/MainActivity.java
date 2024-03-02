package com.example.sqliteinlistview;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    private ImageView add;
    private ListView listView;
    ArrayList<Data> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        new Handler().postDelayed(new Runnable() {@Override public void run() {
                setContentView(R.layout.activity_main);
                add = findViewById(R.id.add);
                listView = findViewById(R.id.listView);


                //SQLITE OBJECT AND CALL
                databaseHelper = new DatabaseHelper(MainActivity.this);
                SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();


                //DISPLAY DATA FROM DATABASE
                loadData();

                //DATA ADD BUTTON
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, InputActivity.class));
                    }
                });


            }}, 3000);
    }//onCreate end

    public void loadData() {

        list = new ArrayList<>();
        Cursor cursor = databaseHelper.loadData();
        if (cursor.getCount() == 0)
            Toast.makeText(this, "You have no data!", Toast.LENGTH_SHORT).show();
        else {
            while (cursor.moveToNext()) {
                String id = cursor.getString(0);
                String name = cursor.getString(1);
                String phone = cursor.getString(2);

                Data data = new Data(id, name, phone);
                list.add(data);
            }
            MyAdapter adapter = new MyAdapter(MainActivity.this, list);
            listView.setAdapter(adapter);

            //CLICK EVENT>>>>>>
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String ID = list.get(position).id.toString();
                    String NAME = list.get(position).name.toString();
                    String PHONE = list.get(position).phone.toString();

                    //BOTTOMSHEET DIALOG
                    Dialog dialog = new Dialog(MainActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.bottomshetelayout);
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.getWindow().setGravity(Gravity.BOTTOM);
                    dialog.getWindow().getAttributes().windowAnimations = android.R.anim.fade_in;

                    LinearLayout updateButton = dialog.findViewById(R.id.updateDialogId);
                    LinearLayout deleteButton = dialog.findViewById(R.id.deleteDialogId);
                    updateButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            updateData(ID, NAME, PHONE);
                            dialog.dismiss();
                        }
                    });
                    deleteButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            deleteData(ID);
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            });
        }
    }

    private void deleteData(String id) {
        long rowId = databaseHelper.deleteData(id);
        if (rowId > 0) Toast.makeText(this, "Delete Successful!", Toast.LENGTH_SHORT).show();
        else Toast.makeText(this, "Delete operation failed!", Toast.LENGTH_SHORT).show();
        loadData();
    }

    private void updateData(String id, String name, String phone) {
        Intent intent = new Intent(MainActivity.this, InputActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("name", name);
        intent.putExtra("phone", phone);
        startActivity(intent);
    }
}