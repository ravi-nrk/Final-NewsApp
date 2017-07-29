package com.pyar.newsapp;

import android.content.Context;

import com.pyar.newsapp.databse.NewsDao;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by RAVI on 7/25/2017.
 */

public class RefreshNews {

    private static NewsDao newsDao;

    //Loading fresh news ,calling from mainactivity and JOB
    public static void syncLatestNews(Context context) {
        ArrayList<NewsItem> newsItems = new ArrayList<>();
        String url = NetworkUtils.getUrl();
        newsDao = new NewsDao(context);
        try {
            newsDao.deleteAll();
            String json = NetworkUtils.getResponseFromHttpUrl(new URL(url));
            newsItems = NetworkUtils.parseJSON(json);
            newsDao.bulkInsert(newsItems);
        } catch (IOException e) {
            e.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
