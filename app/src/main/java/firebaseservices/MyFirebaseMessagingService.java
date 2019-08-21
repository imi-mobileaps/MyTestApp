package firebaseservices;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

import com.example.arunabhac.mytestapp.MainActivity;
import com.example.arunabhac.mytestapp.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by arunabha.c on 2/10/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";
    final String JSON_NOTIFICATION_TITLE = "gcm.notification.title";
    final String JSON_NOTIFICATION_BODY = "gcm.notification.body";

    private static final String GROUP_KEY = "Messenger";
    private static final String NOTIFICATION_ID = "com.stylingandroid.nougat.NOTIFICATION_ID";
    private static final int SUMMARY_ID = 0;
    private  NotificationManagerCompat notificationManager;
    private  SharedPreferences sharedPreferences;
    private NotificationBuilder notificationBuilder;

    private Context context;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        try {


            Map<String, String> keys = remoteMessage.getData();
            JSONObject json = new JSONObject();

            for (Map.Entry<String, String> entry : keys.entrySet()) {
                json.put(entry.getKey(), entry.getValue());
            }
            Intent intent = new Intent();
            intent.putExtra("transid",json.optString("transid"));
            intent.setAction("com.vodafone.appstore.sa.fcmpushrecieved");
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
           /* if (remoteMessage.getNotification() != null)
                showNotification(this, remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getTitle(), json.toString(),getBitmapfromUrl(remoteMessage.getData().get("image")));
            else
                showNotification(this, json.optString("title"), json.optString("message"), json.toString(),json.optString("type").equalsIgnoreCase("image")?getBitmapfromUrl(json.optString("imagepath")):null);*/

            notificationBuilder = NotificationBuilder.newInstance(this);
            notificationBuilder.sendBundledNotification(remoteMessage.getNotification().getBody(),remoteMessage.getNotification().getTitle(),System.currentTimeMillis());
        } catch (Exception e) {
        }

    }

    private void showNotification(Context context, String title, String actualBody, String totalBody,Bitmap image) {
        int notfId = (int) System.currentTimeMillis();
        Intent intent = new Intent(context, MainActivity.class);

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

        NotificationCompat.BigPictureStyle bigImageStyle = new NotificationCompat.BigPictureStyle();
        if(image!=null){
            bigImageStyle.bigPicture(image);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setContentTitle((!TextUtils.isEmpty(title) ? title : context.getResources().getString(R.string.app_name)))
                .setContentText(actualBody)
                .setSmallIcon(R.drawable.ic_stat_vodafoneicon_white)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);
        if(image!=null){
            notificationBuilder.setStyle(bigImageStyle);
        }
        else
            notificationBuilder.setStyle(bigTextStyle);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notfId, notificationBuilder.build());
    }

    public Bitmap getBitmapfromUrl(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }


}
