package com.example.tripandroidproject.Broadcast.BootBroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.tripandroidproject.View.Login.LoginActivity;

public class BootBroadcastReeiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Intent intent1 = new Intent(context, LoginActivity.class);
            context.startService(intent1);
        }
    }
}
