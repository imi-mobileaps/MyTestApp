package com.example.arunabhac.mytestapp;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;


final class NotificationBuilder {

    private static final String GROUP_KEY = "MTNPlay";
    private static final String NOTIFICATION_ID = "com.mtnplay.app.NOTIFICATION_ID";
    private static final int SUMMARY_ID = 0;

    private final Context context;
    private final NotificationManagerCompat notificationManager;
    private final SharedPreferences sharedPreferences;

    static NotificationBuilder newInstance(Context context) {
        Context appContext = context.getApplicationContext();
        Context safeContext = ContextCompat.createDeviceProtectedStorageContext(appContext);
        if (safeContext == null) {
            safeContext = appContext;
        }
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(safeContext);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(safeContext);
        return new NotificationBuilder(safeContext, notificationManager, sharedPreferences);
    }

    private NotificationBuilder(Context context,
                                NotificationManagerCompat notificationManager,
                                SharedPreferences sharedPreferences) {
        this.context = context.getApplicationContext();
        this.notificationManager = notificationManager;
        this.sharedPreferences = sharedPreferences;
    }

    void sendBundledNotification(String title, String msg, String totalbody, long timestamp) {
        Notification notification = buildNotification(title,msg,totalbody,timestamp, GROUP_KEY);
        notificationManager.notify(getNotificationId(), notification);
        Notification summary = buildSummary(title,msg,totalbody,timestamp, GROUP_KEY);
        notificationManager.notify(SUMMARY_ID, summary);
    }

    private Notification buildNotification(String title, String msg, String totalBody, long timestamp, String groupKey) {
        int notfId = (int) System.currentTimeMillis();
        Intent intent = new Intent(context, PromoteContent.class);

        if (!TextUtils.isEmpty(totalBody)) {
            intent.putExtra("tagJson", totalBody);
        }
        intent.putExtra("uniqueId", notfId + "");
        intent.setAction("FromNotification");

        PendingIntent pendingIntent = PendingIntent.getActivity(context, notfId, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle((!TextUtils.isEmpty(title) ? title : context.getResources().getString(R.string.app_name)));
        bigTextStyle.bigText(msg);

        NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(context);
        notificationBuilder
                .setContentTitle(title)
                .setContentText(msg)
                .setWhen(timestamp)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setShowWhen(true)
                .setGroup(groupKey);

        notificationBuilder.setStyle(bigTextStyle);



        return notificationBuilder.build();
    }

    private Notification buildSummary(String title, String msg, String totalBody, long timestamp, String groupKey) {
        int notfId = (int) System.currentTimeMillis();
        Intent intent = new Intent(context, PromoteContent.class);

        if (!TextUtils.isEmpty(totalBody)) {
            intent.putExtra("tagJson", totalBody);
        }
        intent.putExtra("uniqueId", notfId + "");
        intent.setAction("FromNotification");

        PendingIntent pendingIntent = PendingIntent.getActivity(context, notfId, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle((!TextUtils.isEmpty(title) ? title : context.getResources().getString(R.string.app_name)));
        bigTextStyle.bigText(msg);

        NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(context);
        notificationBuilder
                .setContentTitle(title)
                .setContentText(msg)
                .setWhen(timestamp)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setShowWhen(true)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setGroup(groupKey)
                .setGroupSummary(true);

        notificationBuilder.setStyle(bigTextStyle);

        return notificationBuilder.build();
    }

    private int getNotificationId() {
        int id = sharedPreferences.getInt(NOTIFICATION_ID, SUMMARY_ID) + 1;
        while (id == SUMMARY_ID) {
            id++;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(NOTIFICATION_ID, id);
        editor.apply();
        return id;
    }
}
