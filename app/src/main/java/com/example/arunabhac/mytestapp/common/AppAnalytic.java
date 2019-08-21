package com.example.arunabhac.mytestapp.common;

import android.content.Context;

import com.example.arunabhac.mytestapp.OoredooApp;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.Map;


public class AppAnalytic {

    private static AppAnalytic app_analytic = null;

    private Tracker mTracker;

    private Context mContext;

    public static AppAnalytic getInstance(Context context) {
        if (app_analytic == null) {
            app_analytic = new AppAnalytic(context);
        }
        return app_analytic;
    }

    public AppAnalytic(Context context) {
        try {
            mContext = context;
            OoredooApp application = (OoredooApp) context.getApplicationContext();
            mTracker = application.getDefaultTracker();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void analyticAEvent(String category, String action, String label,
                               String value) {

        try {

            long val = 0;

            try {

                val = Long.parseLong(value);

            } catch (Exception e) {
                e.printStackTrace();
            }

            mTracker.send(new HitBuilders.EventBuilder().setCategory(category)
                    .setAction(action).setLabel(label).setValue(val).build());


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void analyticAEvent(String category, String action, String label) {

        try {

            mTracker.send(new HitBuilders.EventBuilder().setCategory(category)
                    .setAction(action).setLabel(label).build());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void analyticAScreenView(String value) {

        try {

            mTracker.setScreenName(value);

            mTracker.send(new HitBuilders.ScreenViewBuilder()
                    .build());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void analyticAException(Exception e, boolean isFatal) {

        try {

            mTracker.send(new HitBuilders.ExceptionBuilder()
                    .setDescription(
                            new AnalyticsExceptionParser().getDescription(
                                    Thread.currentThread().getName(), e))
                    .setFatal(isFatal).build());

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void analyticATime(long timeTaken, String event) {

        try {

            Map<String, String> build = new HitBuilders.TimingBuilder()
                    .setCategory("Request API")
                    .setValue(timeTaken)
                    .setVariable(event)
                    .setLabel(event + " - " + timeTaken)
                    .build();
            mTracker.send(build);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
