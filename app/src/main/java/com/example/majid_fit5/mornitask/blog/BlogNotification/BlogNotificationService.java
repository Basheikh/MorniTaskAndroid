package com.example.majid_fit5.mornitask.blog.BlogNotification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


import com.example.majid_fit5.mornitask.R;
import com.example.majid_fit5.mornitask.blog.blogDetails.BlogDetailsActivity;
import com.example.majid_fit5.mornitask.blog.bloglist.BlogListView;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Created by BASH on 12/19/2017.
 */

public class BlogNotificationService extends FirebaseMessagingService {
    private static final String TAG = "FCM Service";
    private static int notificationNumber=0;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        final Map<String, String> data = remoteMessage.getData();
        // Check if message contains a data payload.
        if (data.size() > 0) {
            //check if the title not empty and the blogID is greater then -1
            if (!data.get("title").trim().isEmpty() && Integer.parseInt(data.get("blogid")) > -1) {
                sendNotification(data.get("title"), data.get("smalldesc"), Integer.parseInt(data.get("blogid")));
            }
        }
    }

    //--------------------------------------------------------------------------------------------------------------------------
    private void sendNotification(String title, String smallDesc, int blogID) {
        long[] pattern = {500, 500, 500, 500, 500};
        //set the mobile default notification ringtone to our notification
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //convert the notification icon to bitmap
        Bitmap bitmapIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_blog_notification);
        //set the notification
        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_logo)
                .setLargeIcon(bitmapIcon)
                .setContentTitle(title)
                .setContentText(smallDesc)
                .setAutoCancel(true)
                .setVibrate(pattern)
                .setSound(defaultSoundUri)
                .setContentIntent(setNotificationTarget(blogID));
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationNumber++ /* ID of notification */, notificationBuilder.build());
    }

    //--------------------------------------------------------------------------------------------------------------------------
    //set the notification target to "BlogDetailsActivity"
    private PendingIntent setNotificationTarget(int blogID) {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, BlogDetailsActivity.class);
        bundle.putInt("KEY_OBJECT_ID", blogID);
        intent.putExtras(bundle);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return PendingIntent.getActivity(this, 0 /* request code */, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}


