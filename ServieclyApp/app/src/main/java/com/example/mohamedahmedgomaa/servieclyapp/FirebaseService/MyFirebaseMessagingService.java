package com.example.mohamedahmedgomaa.servieclyapp.FirebaseService;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.mohamedahmedgomaa.servieclyapp.Ministry;
import com.example.mohamedahmedgomaa.servieclyapp.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static com.android.volley.VolleyLog.TAG;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
public  static  int NOTIFICATION_ID=1;
   @Override
   public void onMessageReceived(RemoteMessage remoteMessage) {
       // ...

       // TODO(developer): Handle FCM messages here.
       // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
       Log.d(TAG, "From: " + remoteMessage.getFrom());

       // Check if message contains a data payload.
       if (remoteMessage.getData().size() > 0) {
           Log.d(TAG, "Message data payload: " + remoteMessage.getData());

           if (/* Check if data needs to be processed by long running job */ true) {
               // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
               //scheduleJob();
           } else {
               // Handle message within 10 seconds
              // handleNow();
           }

       }

       // Check if message contains a notification payload.
       if (remoteMessage.getNotification() != null) {
           Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
       }

       // Also if you intend on generating your own notifications as a result of a received FCM
       // message, here is where that should be initiated. See sendNotification method below.
       generateNotifcation(remoteMessage.getNotification().getBody(),remoteMessage.getNotification().getTitle());

   }

    private void generateNotifcation(String body, String title) {
        Intent intent =new Intent(this, Ministry.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        Uri   sondUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notifcationBulider=new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_notifications_none_black_24dp)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(sondUri)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if(NOTIFICATION_ID>1073741824)
        {
            NOTIFICATION_ID=0;
        }
        notificationManager.notify(NOTIFICATION_ID++,notifcationBulider.build());
    }


    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
       // sendRegistrationToServer(token);
    }
}
