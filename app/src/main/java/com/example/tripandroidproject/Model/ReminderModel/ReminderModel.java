package com.example.tripandroidproject.Model.ReminderModel;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import com.example.tripandroidproject.Broadcast.ReminderService.ReminderReceiver;
import com.example.tripandroidproject.Contract.Reminder.Reminder;
import com.example.tripandroidproject.Service.FloatIcon.FloatingIconService;
import com.example.tripandroidproject.View.Reminder.ReminderActivity;
import com.example.tripandroidproject.View.UnderTest.TestReminder;


import java.util.Calendar;

//import static androidx.core.app.ActivityCompat.startActivityForResult;

public class ReminderModel implements Reminder.IReminderModel {
    Context context;
    Intent intent;
//    int requestCode;
    Reminder.IBaseReminder reminderPresenter;
    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;
    public ReminderModel(Reminder.IBaseReminder reminderPresenter, Context context) {
        this.context = context;
        this.reminderPresenter = reminderPresenter;
//        this.requestCode = requestCode;
    }
     public void startAlarmService(Calendar calendar, int requestCode) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderReceiver.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         intent.putExtra("requestCode", requestCode);
         PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
         if (calendar.before(Calendar.getInstance())) {
             calendar.add(Calendar.DATE, 1);
         }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
    public void stopAlarmService( int requestCode) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReminderReceiver.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("requestCode", requestCode);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        pendingIntent.cancel();
        alarmManager.cancel(pendingIntent);
    }
    @Override
    public void startTrip(String destinationPlaceName, String tripID,int requestCode) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("google.navigation:q=" + destinationPlaceName));
        context.startActivity(intent);
        startFloatIcon(tripID,requestCode);
    }

    public void openFloatIcon(String tripID,int requestCode) {
        intent = new Intent(context, FloatingIconService.class);
        context.stopService(intent);
        startFloatIcon(tripID,requestCode);
    }
    private void startFloatIcon(String tripID,int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(context)) {


            //If the draw over permission is not available open the settings screen
            //to grant the permission.
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + context.getPackageName()));
            intent.putExtra("tripID",requestCode);
            ((Activity) context).startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);

        } else {
            initializeView(tripID);
        }
    }
    public void initializeView(String tripID) {
        intent = new Intent(context, FloatingIconService.class);
        intent.putExtra("tripID",tripID);
        context.startService(intent);

    }
    public void stopService()
    {
        intent = new Intent(context, FloatingIconService.class);
        context.stopService(intent);
    }

}
