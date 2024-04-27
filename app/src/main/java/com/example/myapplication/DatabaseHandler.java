package com.example.myapplication;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHandler {
    // Database metadata
    private final String DATABASE_NAME = "PASSWORDSAVERDB";
    private final int DATABASE_VERSION = 5;

    // Table to store users registered
    private final String TABLE_USERS = "USERS";

    // Table to store passwords of users
    private final String TABLE_PASSWORDS = "PASSWORDS";

    // Fields of TABLE_USERS
    private final String FIELD_USERS_USERNAME = "USERNAME";
    private final String FIELD_USERS_PASSWORD = "PASSWORD";

    // Fields of TABLE_PASSWORDS
    private final String FIELD_PASSWORDS_ID = "ID";
    private final String FIELD_PASSWORDS_USERNAME = "USERNAME";
    private final String FIELD_PASSWORDS_APP_NAME = "APP_NAME";
    private final String FIELD_PASSWORDS_APP_USERNAME = "APP_USERNAME";
    private final String FIELD_PASSWORDS_APP_PASSWORD = "APP_PASSWORD";

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
        cv.put(FIELD_USERS_USERNAME, username);
        cv.put(FIELD_USERS_PASSWORD,password);

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
                FIELD_USERS_USERNAME + " = ? AND " + FIELD_USERS_PASSWORD + " = ?";

        Cursor cursor = database.rawQuery(query, new String[]{username, password});

        boolean userExists = cursor.moveToFirst();

        if (userExists){
            SharedPreferences sharedPreferences = context.getSharedPreferences("LoginCredPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("loggedInUsername", username);
            editor.apply();
        }
        cursor.close();

        return userExists;
    }

    public void addNewPassword(String username, String appName, String appUsername, String appPassword){

        ContentValues values = new ContentValues();
        values.put(FIELD_PASSWORDS_USERNAME, username);
        values.put(FIELD_PASSWORDS_APP_NAME, appName);
        values.put(FIELD_PASSWORDS_APP_USERNAME, appUsername);
        values.put(FIELD_PASSWORDS_APP_PASSWORD, appPassword);

        // Insert the values into the TABLE_PASSWORDS
        long records = database.insert(TABLE_PASSWORDS, null, values);
        if(records == -1)
        {
            Toast.makeText(context, "Cannot save new password!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Password saved successful!", Toast.LENGTH_SHORT).show();
        }
    }

    public void deletePassword(int appId){
        String query = "DELETE FROM " + TABLE_PASSWORDS + " WHERE " + FIELD_PASSWORDS_ID + " = ?" + ";";
        database.execSQL(query, new Object[]{appId});
    }

    public ArrayList<Password> getUserPasswords(){
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginCredPrefs", Context.MODE_PRIVATE);
        String loggedInUsername = sharedPreferences.getString("loggedInUsername", "");

        String query = "SELECT * FROM " + TABLE_PASSWORDS +
                " WHERE " + FIELD_PASSWORDS_USERNAME + " =? " + ";";

        Cursor cursor = database.rawQuery(query, new String[]{loggedInUsername});

        int appNameIndex = cursor.getColumnIndex(FIELD_PASSWORDS_APP_NAME);
        int appUsernameIndex = cursor.getColumnIndex(FIELD_PASSWORDS_APP_USERNAME);
        int appPasswordIndex = cursor.getColumnIndex(FIELD_PASSWORDS_APP_PASSWORD);
        int appId = cursor.getColumnIndex(FIELD_PASSWORDS_ID);

        ArrayList<Password> userPasswords = new ArrayList<>();


        if (cursor.moveToFirst()) { // if any record exists
            while(cursor.moveToNext()){
                userPasswords.add(new Password(cursor.getInt(appId), cursor.getString(appNameIndex), cursor.getString(appUsernameIndex), cursor.getString(appPasswordIndex)));
            }
        }

        cursor.close();

        return userPasswords;
    }

    public void editPassword(int appId, String appName, String appUsername, String appPassword){
        ContentValues values = new ContentValues();
        values.put(FIELD_PASSWORDS_APP_NAME, appName);
        values.put(FIELD_PASSWORDS_APP_USERNAME, appUsername);
        values.put(FIELD_PASSWORDS_APP_PASSWORD, appPassword);

        int affectedRows = database.update(TABLE_PASSWORDS, values, "ID=?", new String[]{String.valueOf(appId)});

        database.close();

        if (affectedRows > 0) {
            Toast.makeText(context, "Password updated successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Failed to update password!", Toast.LENGTH_SHORT).show();
        }
    }

    private class CreateDatabase extends SQLiteOpenHelper {

        public CreateDatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // creating tables of users
            String query = "CREATE TABLE " + TABLE_USERS + "(" +
                    FIELD_USERS_USERNAME + " TEXT NOT NULL," +
                    FIELD_USERS_PASSWORD  + " TEXT NOT NULL" + ");";

            db.execSQL(query);

            // creating table for storing passwords
            query = "CREATE TABLE " + TABLE_PASSWORDS + "(" +
                    FIELD_PASSWORDS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FIELD_PASSWORDS_USERNAME + " TEXT NOT NULL, " +
                    FIELD_PASSWORDS_APP_NAME + " TEXT NOT NULL, " +
                    FIELD_PASSWORDS_APP_USERNAME + " TEXT NOT NULL," +
                    FIELD_PASSWORDS_APP_PASSWORD + " TEXT NOT NULL" + ");";

            db.execSQL(query);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            String query = "DROP TABLE IF EXISTS " + TABLE_USERS;
            db.execSQL(query);

            query = "DROP TABLE IF EXISTS " + TABLE_PASSWORDS;

            db.execSQL(query);

            onCreate(db);
        }
    }



}
