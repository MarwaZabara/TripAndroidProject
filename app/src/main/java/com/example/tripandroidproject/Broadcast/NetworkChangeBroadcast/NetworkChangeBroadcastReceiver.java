package com.example.tripandroidproject.Broadcast.NetworkChangeBroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.tripandroidproject.Contract.RequestCode.RequestCodeContract;
import com.example.tripandroidproject.Model.InternetConnection.Internetonnection;
import com.example.tripandroidproject.Presenter.RequestCode.RequestCodePresenter;
import com.example.tripandroidproject.Presenter.Trip.GetOfflineTripPresenter;
import com.example.tripandroidproject.View.AddTrip.AddTripActivity;
import com.example.tripandroidproject.View.UnderTest.TestReminder;

public class NetworkChangeBroadcastReceiver extends BroadcastReceiver implements RequestCodeContract.IRequestCodeView {
    @Override
    public void onReceive(Context context, Intent intent) {

        if(Internetonnection.isNetworkAvailable(context))
        {
            if(AddTripActivity.requestCode > 0) {
                RequestCodePresenter requestCodePresenter;
                requestCodePresenter = new RequestCodePresenter(this);
                requestCodePresenter.updateRequestCode(AddTripActivity.requestCode);
            }
            GetOfflineTripPresenter getOfflineTripPresenter = new GetOfflineTripPresenter(context);
            getOfflineTripPresenter.getOfflineTrip();
            getOfflineTripPresenter.getNotSyncRepeatedHistoryTrip();
        }
    }

    @Override
    public void setRequestCodeInSharedPreference(int requestCode) {

    }
}
