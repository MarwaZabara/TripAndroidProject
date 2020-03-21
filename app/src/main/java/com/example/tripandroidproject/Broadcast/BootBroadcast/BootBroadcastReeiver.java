package com.example.tripandroidproject.Broadcast.BootBroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.tripandroidproject.POJOs.Trip;
import com.example.tripandroidproject.Presenter.Trip.GetOfflineTripPresenter;
import com.example.tripandroidproject.View.Login.LoginActivity;

import java.util.List;

public class BootBroadcastReeiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.i("AfterReboot","Done");
        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                GetOfflineTripPresenter getOfflineTripPresenter = new GetOfflineTripPresenter(context);
                List<Trip> trips = getOfflineTripPresenter.getTrips();

            }
        });
        th.start();
//        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
//
//
//        }
    }
}
