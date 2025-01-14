package com.example.specurator;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        Log.d("Firebase Notification", "Notification received");

        if(message.getNotification() != null){
            String title = message.getNotification().getTitle();
            String body = message.getNotification().getBody();
            Log.d("Firebase Notification", "There is message");

            if (!message.getData().isEmpty()){
                Log.d("Firebase Notification", "Have payload data");
                Map<String, String> data = message.getData();
                handleDataPayload(data, title, body);
            }else{
                Log.d("Firebase Notification", "No payload data");
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("title", title);
                intent.putExtra("body", body);

                sendNotification(title, body, intent);
            }
        }
    }

    private void handleDataPayload(Map<String, String> data, String title, String body){
        String actionType = data.get("action_type");
        Intent intent;

//        switch (actionType){
//            case "open_activity_one":
//                intent = new Intent(this, FirstActivity.class);
//                break;
//            case "open_activity_two":
//                intent = new Intent(this, SecondActivity.class);
//                break;
//            default:
//                intent = new Intent(this, MainActivity.class);
//                break;
//        }

        intent = new Intent(this, HomeActivity.class);

        for (Map.Entry<String, String> entry : data.entrySet()) {
            intent.putExtra(entry.getKey(), entry.getValue());
        }

        intent.putExtra("title", title);
        intent.putExtra("body", body);

        sendNotification(title, body, intent);
    }

    private void sendNotification(String title, String body, Intent intent){
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "NotifChannel";

        NotificationChannel channel = new NotificationChannel(
                channelId,
                "Push Notification Channel",
                NotificationManager.IMPORTANCE_DEFAULT
        );
        manager.createNotificationChannel(channel);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(this, channelId)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        manager.notify(0, notifBuilder.build());
    }
}
