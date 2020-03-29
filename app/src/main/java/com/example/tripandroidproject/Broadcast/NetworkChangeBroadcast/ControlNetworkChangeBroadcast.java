package com.example.tripandroidproject.Broadcast.NetworkChangeBroadcast;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

public class ControlNetworkChangeBroadcast {
    static NetworkChangeBroadcastReceiver networkChangeBroadcastReceiver;
    public static void registerBroadcast(Context context) {
        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        networkChangeBroadcastReceiver = new NetworkChangeBroadcastReceiver();
        context.registerReceiver(networkChangeBroadcastReceiver,intentFilter);
    }
    public static void unregisterReceiver(Context context){
        context.unregisterReceiver(networkChangeBroadcastReceiver);
    }
}
