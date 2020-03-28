package com.example.tripandroidproject.InternetConnection;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;


public class CheckInternetConnection {

    public CheckInternetConnection() {
    }

    public Boolean getConnectivityStatusString(Context context) {
        Boolean status = true;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                return status;
            }
        } else {
            status = false;
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show();
            return status;
        }
        return status;
    }


}
