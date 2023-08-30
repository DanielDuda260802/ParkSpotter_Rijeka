package com.example.parkspotter_rijeka;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBhelper extends SQLiteOpenHelper {
    public DBhelper (Context context) {
        super(context, "Accounts.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase myDB) {
        myDB.execSQL("create Table users(username Text primary key, email Text, password Text )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase myDB, int i, int i1) {
        myDB.execSQL("drop Table if exists users");
    }

    public Boolean updatePassword (String username, String password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", password);
        long result = myDB.update("users", contentValues, "username = ?", new String[] {username});

        if(result == -1){
            return false;
        }
        else {
            return true;
        }
    }
    public Boolean insertData(String email, String username, String password) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("email", email);
        contentValues.put("password", password);

        long result = myDB.insert("users", null, contentValues);
        if (result == -1) {
            Log.e("InsertData", "Insert failed");
            return false;
        } else {
            Log.d("InsertData", "Insert successful");
            return true;
        }
    }

    public boolean checkUsername (String username){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from users where username == ?", new String[] {username});
        if(cursor.getCount() > 0){
            return true;
        }
        else {
            return false;
        }
    }
    public boolean checkUsernamePassword (String username, String password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from users where username == ? and password == ?", new String[] {username, password});
        if(cursor.getCount() > 0){
            return true;
        }
        else {
            return false;
        }
    }
    public boolean checkUsernameEmail(String username, String email) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT * FROM users WHERE username = ? AND email = ?", new String[]{username, email});

        if(cursor.getCount() > 0){
            return true;
        }
        else {
            return false;
        }
    }

}
