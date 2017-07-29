package com.pyar.newsapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ProtocolException;
import java.util.ArrayList;

import com.pyar.newsapp.databse.NewsDao;
import com.pyar.newsapp.databse.SharedPref;
import com.pyar.newsapp.job.ScheduleJobUtil;

import static com.pyar.newsapp.NetworkUtils.isInternetAvailable;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Void> {
    private static final int NEWS_LOADER = 1;

    ProgressDialog progressDialog;
    ArrayList<NewsItem> newsItems;
    RecyclerView newsItemRecyclerView;
    LinearLayoutManager newsItemLayoutManager;
    NewsItemAdapter news_adapter;
    ProgressBar progressBar;
    NewsDao newsDao;
    Context context;
    boolean isAppFirstTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        newsDao = new NewsDao(context); //intializing newsdao whichi is responsible for performing news operation
        newsItems = new ArrayList<>();
        isAppFirstTime = SharedPref.getSharedPref(context); //Getting app status here

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        newsItemRecyclerView = (RecyclerView) findViewById(R.id.rv_news_item_search);
        newsItemLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        newsItemRecyclerView.setLayoutManager(newsItemLayoutManager);

//If app is first time opening then it loads news from internet
        if (isAppFirstTime) {
            load();
        }

        //Scheduling job here
        ScheduleJobUtil.scheduleRefresh(context);

    }

    @Override
    protected void onStart() {
        super.onStart();

        //if the app already openned then the news has to load from local db
        if (!isAppFirstTime) {
            loadNewsItems();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void loadNewsItems() {
        //Loading this news items to the ADAPTER
        newsItems = newsDao.getAllNews();
        news_adapter = new NewsItemAdapter(MainActivity.this, newsItems, "news_item");
        newsItemRecyclerView.setAdapter(news_adapter);
        news_adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.refresh:
                //Here we are checking internet cpnnection
                if (isInternetAvailable(context)) {
                    load();
                } else {
                    Toast.makeText(context, "Please ensure that your data connection is enabled", Toast.LENGTH_LONG).show();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void load() {
        //Loader manager for loading items from internet
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.restartLoader(NEWS_LOADER, null, this).forceLoad();

    }

    @Override
    public Loader<Void> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<Void>(this) {

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public Void loadInBackground() {
                SharedPref.setSharedPref(context);//setting app status
                RefreshNews.syncLatestNews(context);//syncing  news
                return null;
            }

        };
    }

    @Override
    public void onLoadFinished(Loader<Void> loader, Void data) {
        progressBar.setVisibility(View.GONE);
        loadNewsItems();//after loading finished loading items from db
    }

    @Override
    public void onLoaderReset(Loader<Void> loader) {

    }


}
