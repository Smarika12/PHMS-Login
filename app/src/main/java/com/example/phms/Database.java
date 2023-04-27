package com.example.phms;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper
{
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        String query1 = "create table users(username text, email text, password text)";
        sqLiteDatabase.execSQL(query1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i , int i1)
    {

    }
    public void register(String username , String email, String password)
    {
        ContentValues contentVal = new ContentValues();
        contentVal. put("name", username);
        contentVal. put("mail", email);
        contentVal. put("pass", password);

        SQLiteDatabase db = getWritableDatabase();
//        db.insert("users",null, contentVal); (giving error)
        db.close();
    }

 /*   public int login(String name , String pass)
    {
        int result = 0;
        String strVal[] = new String[2];
        strVal[0] = name;
        strVal[1] = pass;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select * from users where name=? and pass=?", strVal);

        if (c.moveToFirst())
        {
            result = 1;
        }
        return  result;
    }*/              //giving error

}
