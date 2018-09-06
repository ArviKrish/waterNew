package com.practice.aravind.wahter.service;

import android.content.Context;
import android.content.Intent;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.practice.aravind.wahter.LoginActivity;
import com.practice.aravind.wahter.util.NotificationUtils;

/**
 * Created by Ravi Tamada on 08/08/16.
 * www.androidhive.info
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage == null)
            return;

        if (remoteMessage.getData().size() > 0) {
            try {
                handleDataMessage(remoteMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

     private void handleDataMessage(RemoteMessage remoteMessage) {
        try {
            String title = remoteMessage.getData().get("title");
            String body = remoteMessage.getData().get("body");
            String channel_id = remoteMessage.getData().get("channel_id");
            String timestamp = remoteMessage.getData().get("timestamp");

            //todo check if app running in background and broadcast accordingly
            Intent resultIntent = new Intent(getApplicationContext(), LoginActivity.class);
            resultIntent.putExtra("message", body);
            showNotificationMessage(getApplicationContext(), title, body, timestamp, resultIntent, channel_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showNotificationMessage(Context context, String title, String message, String timeStamp, Intent intent, String channel_id) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, channel_id);
    }

    private void showNotificationMessageWithBigImage(Context context, String title, String message, String timeStamp, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        notificationUtils.showNotificationMessage(title, message, timeStamp, intent, imageUrl);
    }
}


