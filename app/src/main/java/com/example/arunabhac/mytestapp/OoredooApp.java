package com.example.arunabhac.mytestapp;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.crashlytics.android.Crashlytics;
import com.example.arunabhac.mytestapp.common.AnalyticsStandardExceptionParser;
import com.google.android.gms.analytics.ExceptionReporter;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;


import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;

public class OoredooApp extends Application {

    private Tracker mTracker;

    private static boolean isInterestingActivityVisible;

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        try {

            ArrayList<String> packages = new ArrayList<>();
            packages.add(this.getPackageName());

            Thread.UncaughtExceptionHandler mUEHandler = new ExceptionReporter(
                    getDefaultTracker(),
                    Thread.getDefaultUncaughtExceptionHandler(),
                    this);

            ((ExceptionReporter) mUEHandler).setExceptionParser(new AnalyticsStandardExceptionParser(this, packages));

            Thread.setDefaultUncaughtExceptionHandler(mUEHandler);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    synchronized public Tracker getDefaultTracker() throws Exception {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);

            mTracker = analytics.newTracker(R.xml.global_tracker);
        }
        return mTracker;
    }

}
