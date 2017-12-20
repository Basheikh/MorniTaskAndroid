package com.example.majid_fit5.mornitask.blog.BlogNotification;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by BASH on 12/19/2017.
 */

public class FirebaseTokenService extends FirebaseInstanceIdService {
    private static final String TAG = "BlogToken";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
    }
}
