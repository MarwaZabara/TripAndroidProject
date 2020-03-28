package com.example.tripandroidproject.View.UnderTest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.room.Room;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.tripandroidproject.Broadcast.NetworkChangeBroadcast.NetworkChangeBroadcastReceiver;
import com.example.tripandroidproject.Broadcast.ReminderService.ReminderReceiver;
import com.example.tripandroidproject.Contract.RequestCode.RequestCodeContract;
import com.example.tripandroidproject.Custom.Calendar.GenerateCalendarObject;
import com.example.tripandroidproject.Custom.TimePicker.TimePickerFragment;
import com.example.tripandroidproject.Model.Firebase.FirebaseRequestCodeModel;
import com.example.tripandroidproject.Model.InternetConnection.Internetonnection;
import com.example.tripandroidproject.Model.Room.AppDatabase;
import com.example.tripandroidproject.Model.Room.TripDAO;
import com.example.tripandroidproject.POJOs.Note;
import com.example.tripandroidproject.POJOs.Trip;
import com.example.tripandroidproject.Presenter.Note.GetNotePresenter;
import com.example.tripandroidproject.Presenter.Reminder.ReminderPresenter;
import com.example.tripandroidproject.Presenter.RequestCode.RequestCodePresenter;
import com.example.tripandroidproject.Presenter.Trip.SaveTripPresenter;
import com.example.tripandroidproject.R;
import com.example.tripandroidproject.Service.SnoozeNotification.SnoozeNotificationForegroundService;
import com.example.tripandroidproject.View.Reminder.ReminderActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class TestReminder extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener , RequestCodeContract.IRequestCodeView {
    int hourOfDay = 0; int minute = 0;int year = 0; int month = 0; int dayOfMonth = 0;
    static int count = 1; // To be removed later
    public static int requestCode;
    RequestCodePresenter requestCodePresenter;
    TextView testLbl;
    TextView testTxt;
    AppDatabase database;
    TripDAO tripDAO;
    private NetworkChangeBroadcastReceiver networkChangeBroadcastReceiver;


    TextView mItemSelected;

    String[] listItems =  {"dfsd","fsdfds"};
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_reminder);
        requestCodePresenter = new RequestCodePresenter(this); // i want to change it single tone
        checkRequestCode();
        testLbl = findViewById(R.id.testLbl);
        testTxt = findViewById(R.id.testTxt);
        database = Room.databaseBuilder(this, AppDatabase.class, "db-trips")
                .allowMainThreadQueries()   //Allows room to do operation on main thread
                .build();
//        tripDAO = database.getTripDAO();
//        List<Trip> trip1 = tripDAO.getTrips();
//        testLbl.setText(trip1.get(0).getId());
        registerBroadcast();
//        openSenderBroadcast();


//        GetNotePresenter getNotePresenter = new GetNotePresenter(this,true);
//       List<Note> dasd = getNotePresenter.getNotes("-M2zmue57fQv3tGV5T_N");
//        int x = 6;
        checkedItems  = new boolean[listItems.length];
    }



    private void registerBroadcast() {
        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        networkChangeBroadcastReceiver = new NetworkChangeBroadcastReceiver();
        registerReceiver(networkChangeBroadcastReceiver,intentFilter);
    }
//    private void openSenderBroadcast() {
//        Intent intent = new Intent();
//        intent.setAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
//        intent.setAction(ConnectivityManager.CONNECTIVITY_ACTION);
//        intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
////        Uri contentUri = Uri.fromFile(new File(fileUri));
////        intent.setData(contentUri);
//        sendBroadcast(intent);
//    }
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
//        calendar.set(Calendar.MINUTE, minute);
//        calendar.set(Calendar.SECOND, 0);
        TestReminder.this.hourOfDay = hourOfDay;
        TestReminder.this.minute = minute;
        if(month > 0) {
            fillCalenderObj();
        }


//        startAlarmService(calendar);
    }
    private void showDateDialog() {
        final Calendar calendar=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                calendar.set(Calendar.YEAR,year);
//                calendar.set(Calendar.MONTH,month);
//                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
//                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yy-MM-dd");
                TestReminder.this.year = year;
                TestReminder.this.month = month;
                TestReminder.this.dayOfMonth = dayOfMonth;
                if(hourOfDay > 0 && minute > 0) {
                    fillCalenderObj();
                }
//                date_in.setText(simpleDateFormat.format(calendar.getTime()));

            }
        };

        new DatePickerDialog(TestReminder.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeBroadcastReceiver);
    }

    private void fillCalenderObj() {
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);


        Trip trip = new Trip();
        trip.setId(testTxt.getText().toString());
        trip.setName("Trip" + String.valueOf(requestCode));
        trip.setDescription("Description" + String.valueOf(requestCode));
        trip.setIsRound(0);
        String date = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) + "-" + String.valueOf(calendar.get(Calendar.MONTH)) + "-" + String.valueOf(calendar.get(Calendar.YEAR));
        trip.setDate(date);
        String time = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)) + "-" + String.valueOf(calendar.get(Calendar.MINUTE));
        trip.setTime(time);
        trip.setRequestCodeHome(requestCode++);
        trip.setStatus("upcoming");
        trip.setRoundDate("None");
        trip.setRoundTime("None");
        trip.setRoundRepeatEvery("None");
        Note note = new Note();
        note.setName("hello");
        note.setStatus("Done");
        Note note1 = new Note();
        note1.setName("hello");
        note1.setStatus("Done");
        List<Note> notes = new ArrayList<>();
        notes.add(note);
        notes.add(note1);
        trip.setNotes(notes);
        SaveTripPresenter saveTripPresenter = new SaveTripPresenter(this);
        saveTripPresenter.saveTrip(trip,false);
        retrieveRequestCode(requestCode);


        Calendar calendar2 = GenerateCalendarObject.generateCalendar(trip.getDate(),trip.getTime());

        ReminderPresenter reminderPresenter = new ReminderPresenter(this);
        reminderPresenter.startReminderService(calendar2,trip.getRequestCodeHome());


        if(Internetonnection.isNetworkAvailable(this))
            requestCodePresenter.updateRequestCode(requestCode);
//        tripDAO.insert(trip);
//        List<Trip> trip1 = tripDAO.getTrips();
//        testLbl.setText(trip1.get(0).getId());

    }

//    private Calendar generateCalendar(String date, String time) {
//
//        List<String> dateSp = Arrays.asList(date.split("-"));
//        List<String> timeSp = Arrays.asList(time.split("-"));
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateSp.get(0)));
//        calendar.set(Calendar.MONTH, Integer.parseInt(dateSp.get(1)));
//        calendar.set(Calendar.YEAR, Integer.parseInt(dateSp.get(2)));
//        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeSp.get(0)));
//        calendar.set(Calendar.MINUTE, Integer.parseInt(timeSp.get(1)));
//        calendar.set(Calendar.SECOND, 0);
//        return calendar;
//    }


    public void openTime(View view) {
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(),"time picker");
    }

    public void showDatePicker(View view) {
        showDateDialog();
//        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
//                Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345"));
//
//        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
//                Uri.parse("google.navigation:q=1+Mahmoud+Salamah"));
//        startActivity(intent);

    }
    private void checkRequestCode() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        int requestCode = sharedPref.getInt("requestCode", 0);
        if (requestCode == 0) {

            requestCodePresenter.getRequestCode();
        }
        else {
            this.requestCode = requestCode;
        }
    }
    @Override
    public void retrieveRequestCode(int requestCode) {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("requestCode", requestCode);
        editor.commit();
        this.requestCode = requestCode;
    }

    public void dialog(View view) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(TestReminder.this);
        mBuilder.setTitle("Notes");
        mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
//                        if (isChecked) {
//                            if (!mUserItems.contains(position)) {
//                                mUserItems.add(position);
//                            }
//                        } else if (mUserItems.contains(position)) {
//                            mUserItems.remove(position);
//                        }
                if(isChecked){
                    mUserItems.add(position);
                }else{
                    mUserItems.remove((Integer.valueOf(position)));
                }
            }
        });

        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                String item = "";
                for (int i = 0; i < mUserItems.size(); i++) {
                    item = item + listItems[mUserItems.get(i)];
                    if (i != mUserItems.size() - 1) {
                        item = item + ", ";
                    }
                }
                mItemSelected.setText(item);
            }
        });

        mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        mBuilder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                for (int i = 0; i < checkedItems.length; i++) {
                    checkedItems[i] = false;
                    mUserItems.clear();
                    mItemSelected.setText("");
                }
            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }
}
