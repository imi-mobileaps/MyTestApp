package com.example.arunabhac.mytestapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by arunabha.c on 5/11/2017.
 */

public class InstallReferrerReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String referrer = intent.getStringExtra("referrer");
        Log.e("----Referrer Link is---",referrer);

        Uri uri= Uri.parse(referrer);

        String ctype = uri.getQueryParameter("ct");
        String cid = uri.getQueryParameter("cid");
        String pid = uri.getQueryParameter("pid");
        String event = uri.getQueryParameter("event");
        Log.e("Referrer query values--",ctype+"--"+cid+"--"+pid+"--"+event);
        //Toast.makeText(context,ctype+"--"+cid+"--"+pid+"--"+event, Toast.LENGTH_SHORT).show();
        Toast.makeText(context,"Installed from Play store", Toast.LENGTH_SHORT).show();
    }
}
