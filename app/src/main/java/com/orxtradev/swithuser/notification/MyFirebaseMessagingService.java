/**
 * Copyright Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.orxtradev.swithuser.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.orxtradev.swithuser.R;
import com.orxtradev.swithuser.activities.MainActivity;
import com.orxtradev.swithuser.utils.SharedPrefDueDate;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFMService";

    // firebase
    private DatabaseReference mFirebaseDatabaseReference;
    private static String sender_id;

    private static String trip_id;
    private static String response;


    private static String sender_name;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Handle data payload of FCM messages.
        Log.d("hazem", "FCM Message Id: " + remoteMessage.toString());
        Log.d("hazem", "FCM Notification Message: " +
                remoteMessage.getData());

//        Log.d("hazem", "FCM Data Message: " + remoteMessage.getData().get("id"));


        final Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("notification_", "true");





        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.ic_stat_name);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
        notificationBuilder
                .setContentTitle(remoteMessage.getData().get("title"))
                .setContentText(remoteMessage.getData().get("body"))
                .setSmallIcon(R.drawable.ic_stat_name)
                .setLargeIcon(image)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        };
//        mFirebaseDatabaseReference.addValueEventListener(postListener);


        // here the oreo notification
        // Sets an ID for the notification, so it can be updated.

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = getString(R.string.default_notification_channel_id);
            NotificationChannel channel = new NotificationChannel(channelId, remoteMessage.getData().get("title"),
                    NotificationManager.IMPORTANCE_HIGH);

            channel.setDescription(remoteMessage.getData().get("body"));
            notificationManager.createNotificationChannel(channel);
            notificationBuilder.setChannelId(channelId);
            notificationManager.notify(1, notificationBuilder.build());
        } else {
            notificationManager.notify(0, notificationBuilder.build());
        }

    }


    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);


        SharedPrefDueDate pre = new SharedPrefDueDate(getApplicationContext());


        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();


        DatabaseReference ref = mFirebaseDatabaseReference.child("Tokens");


        if (pre.getUserId() ==  null || pre.getUserId().isEmpty())
            return;
        ref.child(pre.getUserId()).child("token").setValue(s);
        ref.child(pre.getUserId()).child("id").setValue(pre.getUserId());

//        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
//        DatabaseReference ref =   mFirebaseDatabaseReference.child("Tokens");
//
//        ref.child(user.getUid()).child("token").setValue(token);

        // Once a token is generated, we subscribe to topic.
//        FirebaseMessaging.getInstance()
//                .subscribeToTopic("MainTopic");

    }
}