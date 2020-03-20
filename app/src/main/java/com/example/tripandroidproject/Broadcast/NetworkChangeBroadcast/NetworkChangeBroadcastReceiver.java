package com.example.tripandroidproject.Broadcast.NetworkChangeBroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.tripandroidproject.Model.InternetConnection.Internetonnection;
import com.example.tripandroidproject.Presenter.Trip.GetOfflineTripPresenter;

public class NetworkChangeBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(Internetonnection.isNetworkAvailable(context))
        {
            GetOfflineTripPresenter getOfflineTripPresenter = new GetOfflineTripPresenter(context);
            getOfflineTripPresenter.getOfflineTrip();
        }
    }
}
