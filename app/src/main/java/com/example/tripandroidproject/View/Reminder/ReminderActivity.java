package com.example.tripandroidproject.View.Reminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.example.tripandroidproject.POJOs.Trip;
import com.example.tripandroidproject.Presenter.Trip.CancelTripPresenter;
import com.example.tripandroidproject.Presenter.Trip.StartTripPresenter;
import com.example.tripandroidproject.Presenter.Trip.GetOfflineTripPresenter;
import com.example.tripandroidproject.R;
import com.example.tripandroidproject.Service.SnoozeNotification.SnoozeNotificationForegroundService;
import com.example.tripandroidproject.View.UnderTest.TestReminder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ReminderActivity extends AppCompatActivity {
    private final String CHANNEL_ID = "ForegroundServiceChannel";
    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;
    boolean isServiceRunning = false;
    StartTripPresenter startTripPresenter;
    private int tripRequestCode;
    Trip trip;
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isServiceRunning",isServiceRunning);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int requestCodeFromNotification = intent.getIntExtra("requestCodeFromNotification", 0);
        if(requestCodeFromNotification > 0){
            tripRequestCode =requestCodeFromNotification;
            stopService(new Intent(ReminderActivity.this, SnoozeNotificationForegroundService.class));
        }
        getTripInfoUsingRequestCode();
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        isServiceRunning = sharedPref.getBoolean("isServiceRunning",false);
        //if (isServiceRunning == true) {


        setContentView(R.layout.activity_reminder);
        final MediaPlayer mp = MediaPlayer.create(this, com.example.tripandroidproject.R.raw.remind);
        mp.start();
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(trip.getName());
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                "Start",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        StartTrip();
                        dialog.cancel();
                        mp.stop();
                        finish();

                    }
                });
        builder1.setNeutralButton(
                "Snooze",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        snooze();
                        mp.stop();
                        finish();

                    }
                }
        );
        builder1.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        cancelTrip();
                        mp.stop();
                        finish();

                    }
                });


        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void cancelTrip() {
        CancelTripPresenter cancelTripPresenter = new CancelTripPresenter(this);
        cancelTripPresenter.cancelTrip(trip);
    }

    private void getTripInfoUsingRequestCode() {
        if(tripRequestCode == 0) {
            Intent intent = getIntent();
            tripRequestCode = intent.getIntExtra("requestCode", 0);
        }
        GetOfflineTripPresenter getOfflineTripPresenter = new GetOfflineTripPresenter(this);
        trip = getOfflineTripPresenter.getTripInfo(tripRequestCode);

    }

    private void StartTrip() {
        String destination = getRegionName(trip.getEndLatitude(),trip.getEndLongitude());
//        String destination = "1+محمود+سلامة،+كوم+الدكة+غرب،+العطارين،+الإسكندرية";
        startTripPresenter = new StartTripPresenter(this);
        startTripPresenter.startTrip(destination,trip.getId(),tripRequestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {
            //Check if the permission is granted or not.
            if (resultCode == RESULT_OK) {
                startTripPresenter.initializeView("-M3SXR-SAqbNODQuujOw");
            } else { //Permission is not available
                Toast.makeText(this,
                        "Draw over other app permission not available. Closing the application",
                        Toast.LENGTH_SHORT).show();

                finish();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void snooze() {
        Intent intent = new Intent(ReminderActivity.this, SnoozeNotificationForegroundService.class);
        intent.putExtra("requestCode",tripRequestCode);
        intent.putExtra("tripName",trip.getName());
        intent.putExtra("tripDescription",trip.getDescription());

        startService(intent);
        isServiceRunning = true;
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("isServiceRunning", isServiceRunning);
        editor.commit();
    }

    @Override
    public void onBackPressed() {
//        moveTaskToBack(true);
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
    private void openNotification() {
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, TestReminder.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Foreground Service")
                .setContentText("get Image Now")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
                .build();

//        startForeground(1, notification);
    }
    private String getRegionName(Double lat,Double lon) {
        String region = "";
        List<Address> addresses;
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            if (lat>0 & lon>0) {
                addresses = geocoder.getFromLocation(lat, lon, 1);
                String address = addresses.get(0).getAddressLine(0);
                region = addresses.get(0).getLocality();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return region;
    }
}
