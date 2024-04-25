package com.example.myapplication;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHandler {
    private final String DATABASE_NAME = "PASSWORDSAVERDB";
    private final int DATABASE_VERSION = 1;

    private final String TABLE_USERS = "USERS";

    // Fields of TABLE_USERS
    private final String FIELD_USERNAME = "USERNAME";
    private final String FIELD_PASSWORD = "PASSWORD";

    CreateDatabase helper;
    SQLiteDatabase database;
    Context context;

    public DatabaseHandler (Context context){
        this.context = context;
    }

    public void open () {
        helper = new CreateDatabase(context, DATABASE_NAME, null, DATABASE_VERSION);
        database = helper.getWritableDatabase();
    }

    public void close (){
        database.close();
        helper.close();
    }

    public void signupUser (String username, String password) {
        ContentValues cv = new ContentValues();
        cv.put(FIELD_USERNAME, username);
        cv.put(FIELD_PASSWORD,password);

        long records = database.insert(TABLE_USERS, null, cv);
        if(records == -1)
        {
            Toast.makeText(context, "Cannot signup new user", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Signup Successful, Login now!", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean verifyUser(String username, String password) {
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " +
                FIELD_USERNAME + " = ? AND " + FIELD_PASSWORD + " = ?";

        Cursor cursor = database.rawQuery(query, new String[]{username, password});

        boolean userExists = cursor.moveToFirst();

        cursor.close();

        return userExists;
    }


    private class CreateDatabase extends SQLiteOpenHelper {

        public CreateDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + TABLE_USERS + "(" +
                    FIELD_USERNAME + " TEXT NOT NULL," + FIELD_PASSWORD  + " TEXT NOT NULL" + ");";

            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            String query = "DROP TABLE " + TABLE_USERS + " IF EXISTS";
            db.execSQL(query);
            onCreate(db);
        }
    }



}
