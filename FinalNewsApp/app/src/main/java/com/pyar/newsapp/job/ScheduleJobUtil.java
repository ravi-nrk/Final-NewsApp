package com.pyar.newsapp.job;

import android.content.Context;
import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;
import com.pyar.newsapp.job.NewsSyncJob;

/**
 * Created by HARI on 7/26/2017.
 */

public class ScheduleJobUtil {
    private static final int SCHEDULE_INTERVAL_MINUTES = 1;
    private static final int SYNC_FLEXTIME_SECONDS = 60;
    private static final String NEWS_JOB_TAG = "news_job_tag";

    private static boolean sInitialized;
    synchronized public static void scheduleRefresh(@NonNull final Context context){
        if(sInitialized) return;

        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        //Scheduling job for 1minute
        Job constraintRefreshJob = dispatcher.newJobBuilder()
                .setService(NewsSyncJob.class)
                .setTag(NEWS_JOB_TAG)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(SCHEDULE_INTERVAL_MINUTES,
                        SCHEDULE_INTERVAL_MINUTES + SYNC_FLEXTIME_SECONDS))
                .setReplaceCurrent(true)
                .build();

        dispatcher.schedule(constraintRefreshJob);
        sInitialized = true;

    }

}
