package com.pyar.newsapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by RAVI on 30-Jun-17.
 */

public class NetworkUtils {
    public static final String BASE_URL = "https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest&apiKey=";
    public static final String API_KEY = "f9d4bdcf932a443f933423f897906f54";

    public static String getUrl() {
        /*Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("newsapi.org")
                .appendPath("v1")
                .appendPath("articles")
                .appendQueryParameter("source", "the-next-web")
                .appendQueryParameter("sortBy", "latest")
                .appendQueryParameter("apiKey",API_KEY );*/
        String myUrl = BASE_URL + API_KEY;
        Log.d("complete_java_url", "" + myUrl);
        return myUrl;
    }

    //Connecting to the respective uRL for data
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner input = new Scanner(in);

            input.useDelimiter("\\A");
            String result = (input.hasNext()) ? input.next() : null;
            return result;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return null;
    }

    //Parsing json data of news items
    public static ArrayList<NewsItem> parseJSON(String json) throws JSONException {
        ArrayList<NewsItem> result = new ArrayList<>();
        JSONObject main = new JSONObject(json);
        Log.d("JSON-Response", json);
        if (main.get("status").toString().equalsIgnoreCase("ok")) {
            JSONArray items = main.getJSONArray("articles");

            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                String author = item.getString("author");
                String title = item.getString("title");
                String description = item.getString("description");
                String url = item.getString("url");
                String urlToImage = item.getString("urlToImage");
                String publishedAt = item.getString("publishedAt");
                result.add(new NewsItem(author, title, description, url, urlToImage, publishedAt));
            }
        }
        Log.d("NetworkUtils", "final articles size: " + result.size());
        return result;
    }

    //INternet connection checking
    public static boolean isInternetAvailable(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;

    }

}
