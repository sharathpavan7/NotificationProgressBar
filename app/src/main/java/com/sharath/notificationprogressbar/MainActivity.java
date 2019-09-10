package com.sharath.notificationprogressbar;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getClass().getSimpleName();

    private int notificationId = 45667;
    private Notification notification;
    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startProgress(View view) {
        Log.i(TAG, "button clicked");

        String NOTIFICATION_CHANNEL_ID = "ivicatechnologies.com.famegear";
        final NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        assert notificationManager != null;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.journaldev.com/"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        builder.setSmallIcon(R.drawable.notification_clip_art)
                                            .setContentTitle("Notifications Title")
                .setContentText("Your notification content here.")
                .setContentIntent(pendingIntent);
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        int incr;
                        // Do the "lengthy" operation 20 times
                        for (incr = 0; incr <= 100; incr+=5) {
                            // Sets the progress indicator to a max value, the current completion percentage and "determinate" state
                            builder.setProgress(100, incr, false);
                            // Displays the progress bar for the first time.
                            notificationManager.notify(notificationId, builder.build());
                            // Sleeps the thread, simulating an operation
                            try {
                                // Sleep for 1 second
                                Thread.sleep(1*1000);
                            } catch (InterruptedException e) {
                                Log.d("TAG", "sleep failure");
                            }
                        }
                        // When the loop is finished, updates the notification
                        builder.setContentText("Download completed")
                                // Removes the progress bar
                                .setProgress(0,0,false);
                        notificationManager.notify(notificationId, builder.build());
                    }
                }
                 //Starts the thread by calling the run() method in its Runnable
        ).start();

        // Will display the notification in the notification bar
        //notificationManager.notify(1, builder.build());


        /*String NOTIFICATION_CHANNEL_ID = "ivicatechnologies.com.famegear";
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        notification = notificationBuilder
                .setSmallIcon(R.drawable.notification_clip_art)
                .setContentTitle("TEST")
                .setContentText("DESC")
                .setContentIntent(pendingIntent)
                .build();
        manager.notify(notificationId, notificationBuilder.build());*/
    }

    //@RequiresApi(Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        String NOTIFICATION_CHANNEL_ID = "ivicatechnologies.com.famegear";
        String channelName = "AdNewPostService";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                channelName, NotificationManager.IMPORTANCE_DEFAULT);
        chan.setSound(null, null);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.drawable.notification_clip_art)
                .setContentTitle("TEST")
                .setContentText("DESC")
                .setPriority(NotificationManager.IMPORTANCE_DEFAULT)
                .setContentIntent(pendingIntent)
                .setColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(getString(R.string.app_name)))
                .setStyle(new NotificationCompat.BigTextStyle().bigText("new post"))
                .setCategory(Notification.CATEGORY_SERVICE)
                //.setProgress(100, 0, true)
                .setAutoCancel(true)
                /*  .setDefaults(0)*/
                .setOngoing(false)
                .build();
        manager.notify(notificationId, notificationBuilder.build());
    }

    public void printLog(View view) {
        Log.i(TAG, "Button is working");
    }
}
