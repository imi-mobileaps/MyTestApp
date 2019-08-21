package com.example.arunabhac.mytestapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import io.fabric.sdk.android.Fabric;


public class MainActivity extends AppCompatActivity {
    private FirebaseRemoteConfig mFirebaseRemoteConfig;
    private TextView txt, txt1;
    private static final String WELCOME_MESSAGE_TEXT_SIZE = "text_size";
    private static final String WELCOME_MESSAGE_KEY = "welcome_message";
    private static final String WELCOME_MESSAGE_TEXT_COLOUR = "text_color";

    private String[] temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Bundle bn= new Bundle();
        bn.putInt("amount",7);
        mFirebaseAnalytics.logEvent("my_test",bn);

        mFirebaseAnalytics.logEvent("no_bundle",new Bundle());

        mFirebaseAnalytics.logEvent("onlyapp",new Bundle());

        findViewById(R.id.btn4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int i = 101;
                int j = 101/0;

            }
        });

        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt1.setText("India");
            }
        });

        findViewById(R.id.btn5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt1.setText("India");
            }
        });
        findViewById(R.id.btn_invite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                throw new IllegalArgumentException("This is a test app");
            }
        });
        findViewById(R.id.btn_newact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSec = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intentSec);
            }
        });
        /*int targetSdkVersion= 0;
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
             targetSdkVersion = packageInfo.applicationInfo.minSdkVersion;
             Toast.makeText(this,"The minimum SDK version is--"+targetSdkVersion,Toast.LENGTH_LONG).show();

        }
        catch (PackageManager.NameNotFoundException e) {

        }
        ((TextView)findViewById(R.id.text)).setText("The minimum SDK version is--"+targetSdkVersion+"");
       findViewById(R.id.btn4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              int i = 101;
                int j = 101/0;

            }
        });

        findViewById(R.id.btn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt1.setText("India");
            }
        });

        findViewById(R.id.btn5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt1.setText("India");
            }
        });

        final FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        mFirebaseRemoteConfig.setConfigSettings(configSettings);
        mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);


        txt=(TextView) findViewById(R.id.txt);

        mFirebaseRemoteConfig.fetch(0)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "Fetch Succeeded",
                                    Toast.LENGTH_SHORT).show();

                            // After config data is successfully fetched, it must be activated before newly fetched
                            // values are returned.
                            mFirebaseRemoteConfig.activateFetched();
                        } else {
                            Toast.makeText(MainActivity.this, "Fetch Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                        displayWelcomeMessage();
                    }
                });

        Intent intent = getIntent();
        try {
            if (intent != null && intent.getData() != null) {
                Uri uri = intent.getData();
                String pname = uri.getQueryParameter("pname");
                String cid = uri.getQueryParameter("cid");
                if (pname.equalsIgnoreCase("myapps")) {
                    Intent intentSec = new Intent(MainActivity.this, SecondActivity.class);
                    startActivity(intentSec);
                }
            }


        } catch (Exception e) {

        }
        ((Button) findViewById(R.id.btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onInviteClicked();
            }
        });
        ((Button) findViewById(R.id.btn2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent promoteIntent = new Intent(MainActivity.this, SecondActivity.class);

                startActivity(promoteIntent);
            }
        });*/

    }

    public static boolean isStoreVersion(Context context) {
        boolean result = false;

        try {
            String installer = context.getPackageManager()
                    .getInstallerPackageName(context.getPackageName());
            result = !TextUtils.isEmpty(installer);
        } catch (Throwable e) {
        }

        return result;
    }

    private void onInviteClicked() {
       try {
            DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                    .setLink(Uri.parse("https://www.imimobile.com/default.aspx?&pname=myapps"))
                    .setDynamicLinkDomain("r8ed9.app.goo.gl")
                    // Open links with this app on Android
                    .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                    // Open links with com.example.ios on iOS
                    .setIosParameters(new DynamicLink.IosParameters.Builder("com.example.ios").build())
                    .buildDynamicLink();

            Uri dynamicLinkUri = dynamicLink.getUri();

            Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                    .setLongLink(Uri.parse("https://r8ed9.app.goo.gl/?link=https://www.imimobile.com/default.aspx?%26pname%3Dmyapps&apn=com.imi.arunabhac.mytestapp&amv=2&afl=https://www.imimobile.com&st=Custom+Promotion&sd=This+is+a+custom+promotion+to+test+Dynamic+links+redirecting+user+to+custom+link+when+the+app+is+not+installed.&si=https://imimobile.com/apple-touch-icon.png"))
                    .buildShortDynamicLink()
                    .addOnCompleteListener(this, new OnCompleteListener<ShortDynamicLink>() {
                        @Override
                        public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                            if (task.isSuccessful()) {
                                // Short link created
                                Uri shortLink = task.getResult().getShortLink();
                                Uri flowchartLink = task.getResult().getPreviewLink();
                                Intent intent = new AppInviteInvitation.IntentBuilder("Dynamic Promotion 4")
                                        .setMessage("This is a dynamic links promotion testing for multiple contacts")
                                        .setDeepLink(shortLink)
                                        .setCustomImage(Uri.parse("https://static.pexels.com/photos/36764/marguerite-daisy-beautiful-beauty.jpg"))
                                        .setCallToActionText("This is test msg two")
                                        .build();
                                startActivityForResult(intent, 1001);
                            } else {
                                // Error
                                // ...
                            }
                        }
                    });


        } catch (Exception e) {
            e.printStackTrace();
        }


      Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
      .setLink(Uri.parse("https://imimobile.com/default.aspx?&pname=myapps"))
              .setDynamicLinkDomain("r8ed9.app.goo.gl")
              .setAndroidParameters(
                      new DynamicLink.AndroidParameters.Builder("com.imi.arunabhac.mytestapp")
                              .setMinimumVersion(2)
                              .build())
              .setSocialMetaTagParameters(
                      new DynamicLink.SocialMetaTagParameters.Builder()
                              .setTitle("Example of a Dynamic Link")
                              .setImageUrl(Uri.parse("http://northfieldartsguild.org/_file/Music.jpg"))
                              .setDescription("This link works whether the app is installed or not!")
                              .build())
              .buildShortDynamicLink()
              .addOnCompleteListener(this, new OnCompleteListener<ShortDynamicLink>() {
                  @Override
                  public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                      if (task.isSuccessful()) {
                          // Short link created
                          Uri shortLink = task.getResult().getShortLink();
                          Log.e("Short link",shortLink.toString());

Intent share=new Intent(Intent.ACTION_SEND);
                          share.setType("text/plain");
                          share.putExtra(Intent.EXTRA_TEXT, shortLink.toString());
                          share.setPackage("com.facebook.katana"); //Facebook App package
                          startActivity(Intent.createChooser(share, "Title of the dialog the system will open"));

                          Uri flowchartLink = task.getResult().getPreviewLink();
                          Intent intent = new AppInviteInvitation.IntentBuilder("Dynamic Promotion 5")
                                  .setMessage("This is a dynamic links promotion testing for multiple contacts")
                                  .setDeepLink(Uri.parse("https://imimobile.com/default.aspx?&pname=myapps&cid=12345"))
                                  .setCustomImage(Uri.parse("http://northfieldartsguild.org/_file/Music.jpg"))
                                  .setCallToActionText("This is test msg one")
                                  .build();
                          startActivityForResult(intent, 1001);
                      } else {
                          // Error
                          // ...
                      }
                  }
              });



        Intent intent = new AppInviteInvitation.IntentBuilder("Dynamic Promotion 6")
                .setMessage("This is a dynamic links promotion testing for multiple contacts")
                .setDeepLink(Uri.parse("https://imimobile.com/default.aspx?&pname=myapps&cid=12345"))
                .setCustomImage(Uri.parse("http://northfieldartsguild.org/_file/Music.jpg"))
                .setCallToActionText("This is test msg one")
                .build();
        startActivityForResult(intent, 1001);



    }

    public void shareFriends() {
        try {



            Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                    .setLongLink(Uri.parse("https://r8ed9.app.goo.gl/?link=https://www.imimobile.com/default.aspx?%26pname%3Dmyapps&apn=com.imi.arunabhac.mytestapp&amv=2&afl=https://www.imimobile.com&st=Custom+Promotion&sd=This+is+a+custom+promotion+to+test+Dynamic+links+redirecting+user+to+custom+link+when+the+app+is+not+installed.&si=https://imimobile.com/apple-touch-icon.png"))
                    .buildShortDynamicLink()
                    .addOnCompleteListener(this, new OnCompleteListener<ShortDynamicLink>() {
                        @Override
                        public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                            if (task.isSuccessful()) {
                                // Short link created
                                Uri shortLink = task.getResult().getShortLink();
                                Uri flowchartLink = task.getResult().getPreviewLink();
                                Intent intent = new AppInviteInvitation.IntentBuilder("Dynamic Promotion 4")
                                        .setMessage("This is a dynamic links promotion testing for multiple contacts")
                                        .setDeepLink(shortLink)
                                        .setCustomImage(Uri.parse("https://static.pexels.com/photos/36764/marguerite-daisy-beautiful-beauty.jpg"))
                                        .setCallToActionText("This is test msg two")
                                        .build();
                                startActivityForResult(intent, 1001);
                            } else {
                                // Error
                                // ...
                            }
                        }
                    });



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("MainActivity", "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);

      if (requestCode == 1001) {
            if (resultCode == RESULT_OK) {
                // Get the invitation IDs of all sent messages
                String[] ids = AppInviteInvitation.getInvitationIds(resultCode, data);
                for (String id : ids) {
                    Log.d("MainActivity", "onActivityResult: sent invitation " + id);
                }
            } else {
                // Sending failed or it was canceled, show failure message to the user
                // ...
            }
        }
    }
    private void displayWelcomeMessage() {
        String welcomeMessage = mFirebaseRemoteConfig.getString(WELCOME_MESSAGE_KEY);
        txt.setTextColor(Color.parseColor( mFirebaseRemoteConfig.getString(WELCOME_MESSAGE_TEXT_COLOUR)));
        txt.setTextSize(convertSpToPixels( mFirebaseRemoteConfig.getLong(WELCOME_MESSAGE_TEXT_SIZE) , getApplicationContext()));
        txt.setText(welcomeMessage);
    }
    public static float convertSpToPixels(float sp, Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }
}
