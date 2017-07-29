package com.pyar.newsapp.databse;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by RAVI on 7/24/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME="news.db";
    public static final int DB_VERSION=1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creation of table
        String tableString="CREATE TABLE "+Contract.TABLE_NEWSITEM.TABLE_NAME+" ("
                +Contract.TABLE_NEWSITEM._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +Contract.TABLE_NEWSITEM.AUTHOR+" TEXT NOT NULL, "
                +Contract.TABLE_NEWSITEM.DESCRIPTION+" TEXT, "
                +Contract.TABLE_NEWSITEM.TITLE+" TEXT, "
                +Contract.TABLE_NEWSITEM.IMAGE_URL+" TEXT, "
                +Contract.TABLE_NEWSITEM.URL+" TEXT,"
                +Contract.TABLE_NEWSITEM.PUBLISHED_AT+" TEXT"+");";

        Log.d("DBHelper", "Create table SQL: " + tableString);
        db.execSQL(tableString);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
