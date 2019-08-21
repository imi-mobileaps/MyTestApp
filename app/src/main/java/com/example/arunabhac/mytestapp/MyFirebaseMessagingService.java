package com.example.arunabhac.mytestapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;


import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;



public class MyFirebaseMessagingService extends FirebaseMessagingService  {

    private static final String TAG = "FCM Service";
    final String JSON_NOTIFICATION_TITLE = "gcm.notification.title";
    final String JSON_NOTIFICATION_BODY = "gcm.notification.body";
    private NotificationBuilder notificationBuilder;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        try {

            notificationBuilder = NotificationBuilder.newInstance(this);

            Map<String,String> keys = remoteMessage.getData();
            JSONObject json = new JSONObject();

            for (Map.Entry<String, String> entry : keys.entrySet())
            {
                json.put(entry.getKey() , entry.getValue());
                //  System.out.println(entry.getKey() + "/" + entry.getValue());
            }


            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                notificationBuilder.sendBundledNotification(remoteMessage.getNotification().getBody(),remoteMessage.getNotification().getTitle(),json.toString(),System.currentTimeMillis());
            }
            else
                showNotification(this, remoteMessage.getNotification().getBody(),remoteMessage.getNotification().getTitle(),json.toString());

        } catch (Exception e) {

        }

    }

    private void showNotification(Context context, String title, String actualBody, String totalBody) {
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
        bigTextStyle.bigText(actualBody);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setContentTitle((!TextUtils.isEmpty(title) ? title : context.getResources().getString(R.string.app_name)))
                .setContentText(actualBody)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        notificationBuilder.setStyle(bigTextStyle);
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notfId, notificationBuilder.build());
    }
}
