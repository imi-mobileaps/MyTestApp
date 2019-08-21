package com.example.arunabhac.mytestapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.appinvite.AppInvite;
import com.google.android.gms.appinvite.AppInviteInvitationResult;
import com.google.android.gms.appinvite.AppInviteReferral;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;


public class PromoteContent extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "PromoteContent";
    String deepLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        if (intent == null) {
            return;
        }

        /*GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(PromoteContent.this, this)
                .addApi(AppInvite.API)
                .build();*/

        /*AppInvite.AppInviteApi.getInvitation(googleApiClient, this, false)
                .setResultCallback(
                        new ResultCallback<AppInviteInvitationResult>() {
                            @Override
                            public void onResult(@NonNull AppInviteInvitationResult result) {
                                if (result.getStatus().isSuccess()) {
                                    Intent intent = result.getInvitationIntent();
                                    deepLink = AppInviteReferral.getDeepLink(intent);

                                    Log.e("Deep link is---", deepLink);
                                    Uri uri = Uri.parse(deepLink);

                                    Intent promoteIntent = new Intent(PromoteContent.this, MainActivity.class);


                                    promoteIntent.setData(uri);
                                    promoteIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                                    promoteIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    promoteIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    startActivity(promoteIntent);
                                    PromoteContent.this.finish();
                                } else {
                                    Log.e("Failed", "Oops, looks like there was no deep link found!");
                                }
                            }
                        });*/

        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();
                           // Uri uri = Uri.parse(deepLink);*/

                            Intent promoteIntent = new Intent(PromoteContent.this, MainActivity.class);


                            promoteIntent.setData(deepLink);
                            promoteIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                            promoteIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            promoteIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(promoteIntent);
                            PromoteContent.this.finish();
                        }


                        // Handle the deep link. For example, open the linked
                        // content, or apply promotional credit to the user's
                        // account.
                        // ...

                        // ...
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "getDynamicLink:onFailure", e);
                    }
                });

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, connectionResult.getErrorCode(), Toast.LENGTH_LONG).show();
    }
}
