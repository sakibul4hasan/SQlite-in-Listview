package com.example.sqliteinlistview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class InputActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText nameId, phoneId;
    private Button saveButtonId, updateButtonId;
    DataClass dataClass;
    DatabaseHelper databaseHelper;
    String idInt, nameInt, phoneInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
        nameId = findViewById(R.id.nameId);
        phoneId = findViewById(R.id.phoneId);
        saveButtonId = findViewById(R.id.saveButtonId);
        updateButtonId = findViewById(R.id.updateButtonId);


        // SQLITE OBJECT CREATE AND CALL
        databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();


        //INTENT RECEIVED
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            idInt = bundle.getString("id");
            nameInt = bundle.getString("name");
            phoneInt = bundle.getString("phone");
            saveButtonId.setVisibility(View.GONE);
            updateButtonId.setVisibility(View.VISIBLE);
            nameId.setText(nameInt);
            phoneId.setText(phoneInt);
        }


        //ON CLICK EVENT
        dataClass = new DataClass();
        saveButtonId.setOnClickListener(this);
        updateButtonId.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.saveButtonId) {

            String name = nameId.getText().toString().trim();
            String phone = phoneId.getText().toString().trim();
            dataClass.setName(name);
            dataClass.setPhone(phone);
            nameId.setText("");
            phoneId.setText("");

            if (name.isEmpty())
                Toast.makeText(InputActivity.this, "Enter your name and others!", Toast.LENGTH_LONG).show();
            else {
                long rowId = databaseHelper.insertData(dataClass);
                if (rowId > 0) {
                    //dialog
                    Toast.makeText(InputActivity.this, rowId + " row Insert Successful", Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finishAffinity();
                            startActivity(new Intent(InputActivity.this, MainActivity.class));
                        }
                    }, 1000);
                } else Toast.makeText(this, "Data insert failed!", Toast.LENGTH_SHORT).show();
            }
        }

        //UPDATE BUTTON CALLED>>
        else if (v.getId() == R.id.updateButtonId) {

            String name = nameId.getText().toString().trim();
            String phone = phoneId.getText().toString().trim();
            dataClass.setName(name);
            dataClass.setPhone(phone);
            dataClass.setId(idInt);
            nameId.setText("");
            phoneId.setText("");

            if (name.isEmpty())
                Toast.makeText(InputActivity.this, "Enter your name and others!", Toast.LENGTH_LONG).show();
            else {
                long rowId = databaseHelper.updateData(dataClass);
                if (rowId > 0) {
                    //dialog
                    Toast.makeText(InputActivity.this, "Update Successful", Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finishAffinity();
                            startActivity(new Intent(InputActivity.this, MainActivity.class));
                        }
                    }, 1000);

                } else Toast.makeText(this, "update operation failed!", Toast.LENGTH_LONG).show();
            }

        }

    }


}