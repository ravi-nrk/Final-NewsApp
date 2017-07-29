package com.pyar.newsapp.job;


import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.pyar.newsapp.RefreshNews;

/**
 * Created by HARI on 7/26/2017.
 */

public class NewsSyncJob extends JobService {

    //This jobservice is from firebase dispatcher
    private AsyncTask backGroundJobAsync;

    @Override
    public boolean onStartJob(final JobParameters job) {
        //starting the job in asynk task
        backGroundJobAsync = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                Log.d("Job-News","doinbackground called");
                RefreshNews.syncLatestNews(NewsSyncJob.this);//syncing new news items
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                jobFinished(job, false);//Job gets fininshed
                super.onPostExecute(o);
                Toast.makeText(NewsSyncJob.this, "News refreshed", Toast.LENGTH_SHORT).show();
            }
        };

        backGroundJobAsync.execute();



        return true;
    }

    @Override
    public boolean onStopJob(final JobParameters job) {
        if(backGroundJobAsync!=null)
        {
            backGroundJobAsync.cancel(false);
        }

        return true;
    }
}
