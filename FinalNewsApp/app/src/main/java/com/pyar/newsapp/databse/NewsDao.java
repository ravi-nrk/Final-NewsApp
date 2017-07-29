package com.pyar.newsapp.databse;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.pyar.newsapp.NewsItem;

import java.util.ArrayList;

/**
 * Created by HARI on 7/24/2017.
 */

public class NewsDao {

    public SQLiteDatabase db;
    public DBHelper dbHelper;
    public Context context;

    public NewsDao(Context context) {
        //Here we are intializing database helper
        this.context = context;
        this.dbHelper = new DBHelper(context);
    }

    //Method to get All the news items
    public ArrayList<NewsItem> getAllNews() {
        ArrayList<NewsItem> newsItems = new ArrayList<>();
        db = dbHelper.getReadableDatabase();
        Cursor mCursor = null;
        try {
            mCursor = db.query(Contract.TABLE_NEWSITEM.TABLE_NAME, null, null, null, null, null, Contract.TABLE_NEWSITEM.PUBLISHED_AT + " DESC");
            if (mCursor != null && mCursor.moveToFirst()) {
                do {
                    //Getting individual news items
                    NewsItem newsItem = new NewsItem();
                    newsItem.setAuthor(mCursor.getString(mCursor.getColumnIndex(Contract.TABLE_NEWSITEM.AUTHOR)));
                    newsItem.setDescription(mCursor.getString(mCursor.getColumnIndex(Contract.TABLE_NEWSITEM.DESCRIPTION)));
                    newsItem.setTitle(mCursor.getString(mCursor.getColumnIndex(Contract.TABLE_NEWSITEM.TITLE)));
                    newsItem.setUrl(mCursor.getString(mCursor.getColumnIndex(Contract.TABLE_NEWSITEM.URL)));
                    newsItem.setUrlToImage(mCursor.getString(mCursor.getColumnIndex(Contract.TABLE_NEWSITEM.IMAGE_URL)));
                    newsItem.setPublishedAt(mCursor.getString(mCursor.getColumnIndex(Contract.TABLE_NEWSITEM.PUBLISHED_AT)));

                    newsItems.add(newsItem);//Adding news items one by one
                } while (mCursor.moveToNext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCursor != null) {
                mCursor.close();
            }
            dbHelper.close();
        }
        return newsItems;//Returing all news items
    }

    //Inserting all the news items
    public void bulkInsert(ArrayList<NewsItem> newsItems) {
        db = dbHelper.getWritableDatabase();
        db.beginTransaction();

        try {
            for (NewsItem a : newsItems) {
                ContentValues cv = new ContentValues();
                cv.put(Contract.TABLE_NEWSITEM.TITLE, a.getTitle());
                cv.put(Contract.TABLE_NEWSITEM.AUTHOR, a.getAuthor());
                cv.put(Contract.TABLE_NEWSITEM.DESCRIPTION, a.getDescription());
                cv.put(Contract.TABLE_NEWSITEM.URL, a.getUrl());
                cv.put(Contract.TABLE_NEWSITEM.IMAGE_URL, a.getUrlToImage());
                cv.put(Contract.TABLE_NEWSITEM.PUBLISHED_AT, a.getPublishedAt());
                db.insert(Contract.TABLE_NEWSITEM.TABLE_NAME, null, cv);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            dbHelper.close();
        }

    }

    //deleting news articles already synced
    public void deleteAll() {
        db = dbHelper.getWritableDatabase();
        db.delete(Contract.TABLE_NEWSITEM.TABLE_NAME, null, null);
        dbHelper.close();
    }


}
