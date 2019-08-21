package com.example.arunabhac.mytestapp;


import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        String getInstanceId = FirebaseInstanceId.getInstance().getId();


        sendRegistrationToServer(refreshedToken);

    }

    public void sendRegistrationToServer(String aToken) {


    }

}
