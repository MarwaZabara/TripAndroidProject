package com.example.tripandroidproject.Service.SnoozeNotification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.tripandroidproject.R;
import com.example.tripandroidproject.View.Reminder.ReminderActivity;
import com.example.tripandroidproject.View.UnderTest.TestReminder;

public class SnoozeNotificationForegroundService extends Service {
    public static final String CHANNEL_ID = "SnoozeNotificationForegroundServiceChannel";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int requestCode = intent.getIntExtra("requestCode",0);
        String name = intent.getStringExtra("tripName");
        String description = intent.getStringExtra("tripDescription");
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, ReminderActivity.class);
        notificationIntent.putExtra("requestCodeFromNotification",requestCode);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                requestCode, notificationIntent, 0);

        //Build a notification
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(name)
                .setContentText(description)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(description))
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
                .build();
        //A notifcation HAS to be passed for the foreground service to be started.
        startForeground(requestCode, notification);;

        return START_NOT_STICKY;
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Example Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
