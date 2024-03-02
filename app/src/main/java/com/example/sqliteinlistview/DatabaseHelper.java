package com.example.sqliteinlistview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "userdata.db";
    private static final String TABLE_NAME = "userlist";
    private static final String ID = "Id";
    private static final String NAME = "Name";
    private static final String PHONE = "Phone";
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " VARCHAR NOT NULL, " + PHONE + " VARCHAR(15)); ";
    private static final String DROP_TABLE = "DROP TABLE " + TABLE_NAME;
    private static final int VERSION_NUMBER = 1;
    private Context context;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION_NUMBER);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(CREATE_TABLE);
            Toast.makeText(context, "onCreate is Called", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(context, "Exception : " + e, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try {
            Toast.makeText(context, "onUpgrade is Called", Toast.LENGTH_LONG).show();
            db.execSQL(DROP_TABLE);
            onCreate(db);
        } catch (Exception e) {
            Toast.makeText(context, "Exception : " + e, Toast.LENGTH_LONG).show();
        }
    }

    public long insertData(DataClass dataClass) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, dataClass.getName());
        contentValues.put(PHONE, dataClass.getPhone());
        return sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    public Cursor loadData() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return cursor;
    }

    public long deleteData(String id) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_NAME, ID + " = ?", new String[]{id});
    }

    public long updateData(DataClass dataClass) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, dataClass.getName());
        contentValues.put(PHONE, dataClass.getPhone());
        String id = dataClass.getId();
        return sqLiteDatabase.update(TABLE_NAME, contentValues, ID + " = ?", new String[]{id});
    }
}
