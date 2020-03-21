package com.example.tripandroidproject.Broadcast.ReminderService;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import com.example.tripandroidproject.View.Reminder.ReminderActivity;

public class ReminderReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        alarmSound = ringtoneMgr.getRingtoneUri(alarmChosen);
//        Ringtone r = RingtoneManager.getRingtone(getActivity(), alarmSound);
//        r.play();
        Log.i("Done","Recieved");

        Intent intent1 = new Intent(context, ReminderActivity.class);
        intent1.putExtra("requestCode",intent.getIntExtra("requestCode",0));
        context.startActivity(intent1);
    }
}
