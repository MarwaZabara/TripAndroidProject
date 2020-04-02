package com.example.tripandroidproject.View.AddTrip;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;

import com.example.tripandroidproject.Broadcast.NetworkChangeBroadcast.ControlNetworkChangeBroadcast;
import com.example.tripandroidproject.Contract.RequestCode.RequestCodeContract;
import com.example.tripandroidproject.Custom.Calendar.GenerateCalendarObject;
import com.example.tripandroidproject.Model.InternetConnection.Internetonnection;
import com.example.tripandroidproject.POJOs.Note;
import com.example.tripandroidproject.POJOs.SemiCalendar;
import com.example.tripandroidproject.POJOs.Trip;
import com.example.tripandroidproject.Presenter.Note.GetNotePresenter;
import com.example.tripandroidproject.Presenter.Note.UpdateNotePresenter;
import com.example.tripandroidproject.Presenter.Reminder.ReminderPresenter;
import com.example.tripandroidproject.Presenter.RequestCode.RequestCodePresenter;
import com.example.tripandroidproject.Presenter.Trip.SaveTripPresenter;
import com.example.tripandroidproject.Presenter.Trip.UpdateTripPresenter;
import com.example.tripandroidproject.R;
import com.example.tripandroidproject.SwipeDeleteTVItem.SwipeDismissListViewTouchListener;
import com.example.tripandroidproject.TimePicker.TimePickerFragment;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

//import com.example.tripandroidproject.Custom.TimePicker.TimePickerFragment;

public class AddTripActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener , RequestCodeContract.IRequestCodeView {
    String TAG = "Add";
    int AUTOCOMPLETE_REQUEST_CODE = 1;
    public static int requestCode;
    //    int hourOfDay = 0; int minute = 0;int year = 0; int month = 0; int dayOfMonth = 0;
//    int hourOfDayRound = 0; int minuteRound = 0;int yearRound = 0; int monthRound = 0; int dayOfMonthRound = 0;
    SemiCalendar semiCalendarHome,semiCalendarRound;
    Calendar calendarMain,calendarRound;
    RequestCodePresenter requestCodePresenter;
    public static final String DATE_FORMAT_1 = "hh:mm a";
    private EditText NameTxt,DescTxt,TripDateTxt,TripTimetxt,StartLocationTxt,DestinationTxt;
    private EditText RoundDateTxt,RoundTimeTxt;
    private int RepeatEvery=0;
    private int isRound=0;
    private long RepeatRound = 0;
    double StartLong;
    double StartLat;
    double EndLong;
    double EndLat;
    String TripDate,RoundDate="None";
    String TripTime,RoundTime="None";
    String TripName,TripDesc;
    String status = "upcoming";
    String SelectedLocation,SelectedTime;
    long chosenTripDate;
    Spinner Repeatspinner;
    Button Next;
    AlertDialog dialog = null;
    ArrayAdapter<String> adapter;
    ArrayList<String> NotesAL =new ArrayList<String>();//Creating arraylist
    ListView lv;
    Trip trip;
    private boolean isEdit;
    private String tripID = null;
    Switch IsRound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ControlNetworkChangeBroadcast.registerBroadcast(this);
        //NotesAL.add("Ravi");//Adding object in arraylist
        setContentView(R.layout.activity_add_trip);
        requestCodePresenter = new RequestCodePresenter(this); // i want to change it single tone
        checkRequestCode();
        Places.initialize(getApplicationContext(), "AIzaSyCVOvMSNN18_AJKQjfKfoWKxsYNF5GNxK0");
        IsRound = findViewById(R.id.RoundSwitch);
        TripDateTxt = findViewById(R.id.TripDateTxt);
        RoundDateTxt = findViewById(R.id.RoundDateTxt);
        RoundTimeTxt = findViewById(R.id.RoundTimeTxt);
        RoundDateTxt.setEnabled(false);
        RoundTimeTxt.setEnabled(false);
        StartLocationTxt = findViewById(R.id.StartLocationTxt);
        DestinationTxt = findViewById(R.id.DestinationTxt);
        TripTimetxt = findViewById(R.id.TripTimeTxt);
        NameTxt = findViewById(R.id.NameTxt);
        DescTxt = findViewById(R.id.DescTxt);
        Next = findViewById(R.id.NextBtn);
        Repeatspinner = findViewById(R.id.Repeatspinner);
        calendarMain = Calendar.getInstance();
        calendarRound = Calendar.getInstance();
        Repeatspinner.setSelection(3);
        semiCalendarHome = new SemiCalendar();
        semiCalendarRound = new SemiCalendar();
        Repeatspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //String selectedItemText = (String) parent.getItemAtPosition(position);
                switch ((String) parent.getItemAtPosition(position)){
                    case "Daily":
                        RepeatEvery = 1;
                        break;
                    case "Weekly":
                        RepeatEvery = 7;
                        break;
                    case "Monthly":
                        RepeatEvery = 30;
                        break;
                    case "Never":
                        RepeatEvery = 0;
                }
                // Toast.makeText(getApplicationContext(),""+RepeatEvery,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                parent.setSelection(3);
            }
        });

        FloatingActionButton ShowDialogBtn = findViewById(R.id.ShowAddNoteDialog);
        ShowDialogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogListView();
            }
        });


        IsRound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //Toast.makeText(getApplicationContext(), "Rounded",Toast.LENGTH_LONG).show();
                    isRound =1;

                    RoundDateTxt.setEnabled(true);
                    RoundTimeTxt.setEnabled(true);

                    // The toggle is enabled
                } else {
                    //   Toast.makeText(getApplicationContext(), " NOT Rounded",Toast.LENGTH_LONG).show();
                    isRound = 0;
                    RepeatRound = 0;

                    RoundDateTxt.setEnabled(false);
                    RoundTimeTxt.setEnabled(false);
                    RoundTimeTxt.setText("");
                    RoundDateTxt.setText("");
                    RoundDate = "None";
                    RoundTime = "None";
                    // The toggle is disabled
                }
            }
        });

        TripTimetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectedTime = "Trip";
                openTime();
//                Toast.makeText(getApplicationContext(), TripTime,Toast.LENGTH_LONG).show();
            }
        });

        RoundTimeTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectedTime = "Round";
                openTime();
            }
        });

        TripDateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(TripDateTxt,"Trip");
            }
        });
        RoundDateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(RoundDateTxt,"Round");
            }
        });
        StartLocationTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectedLocation = "Start";
                StartAutoCompleteActivity();
//                Toast.makeText(getApplicationContext(), "lat = " + StartLat,Toast.LENGTH_LONG).show();

            }
        });
        DestinationTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectedLocation = "Dest";
                StartAutoCompleteActivity();
                //   Toast.makeText(getApplicationContext(),"lat="+ EndLat,Toast.LENGTH_LONG).show();
            }
        });

//        if(savedInstanceState != null && savedInstanceState.getBoolean("alertShown",true)) {
//    showDialogListView();
//        }


//        NotesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ViewGroup vg =(ViewGroup) view;
//                TextView txt =(TextView) vg.findViewById(R.id.NoteListTxt);
//                Toast.makeText(AddTripActivity.this, txt.getText().toString(),Toast.LENGTH_LONG).show();
//                m.add("plzzzz");
//                adapter.notifyDataSetChanged();

//
//            }
//        });
        trip = new Trip();
        /////////////////////////////////////////// edit //////////////////////////////////////////
        isEdit = getIntent().getBooleanExtra("isEdit",false);
        if(isEdit){
            trip = (Trip) getIntent().getSerializableExtra("trip");
            fillTripData();
        }

    }

    private void fillTripData() {
        tripID = trip.getId();
        TripName = trip.getName();
        TripDesc = trip.getDescription();
        //trip.setIsRound(isRound);
        TripDate = trip.getDate();
        TripTime = trip.getTime();
        status = trip.getStatus();
        EndLat = trip.getEndLatitude();
        EndLong = trip.getEndLongitude();
        StartLat = trip.getStartLatitude();
        StartLong = trip.getStartLongitude();
        RepeatEvery = (int) trip.getRepeatEvery();

        NameTxt.setText(TripName);
        DescTxt.setText(TripDesc);
        TripDateTxt.setText(TripDate);
        TripTimetxt.setText(TripTime);
        StartLocationTxt.setText(getRegionName(StartLat,StartLong));
        DestinationTxt.setText(getRegionName(EndLat,EndLong));
        trip.setDate(TripDate);
        trip.setTime(TripTime);
        trip.setStatus(status);

        switch (RepeatEvery)
        {
            case 0:
                Repeatspinner.setSelection(3);
                break;
            case 30:
                Repeatspinner.setSelection(2);
                break;
            case 7:
                Repeatspinner.setSelection(1);
                break;
            case 1:
                Repeatspinner.setSelection(0);
                break;
        }
        RoundTimeTxt.setVisibility(View.GONE);
        RoundDateTxt.setVisibility(View.GONE);
        IsRound.setVisibility(View.GONE);
        calendarMain = GenerateCalendarObject.generateCalendar(TripDate,TripTime);
        GetNotePresenter getNotePresenter = new GetNotePresenter(this,false);
        trip.setNotes(getNotePresenter.getNotes(trip.getId()));
        for (int i = 0 ; i<trip.getNotes().size() ; i++)
        {
            NotesAL.add(trip.getNotes().get(i).getName());
        }
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
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        NotesAL = savedInstanceState.getStringArrayList("SavedNotes");
        if (savedInstanceState.getBoolean("alertShown",false)){
            showDialogListView();
        }
    }

    public void showDialogListView() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogLayout = getLayoutInflater().inflate(R.layout.notes_dialog,null);
        lv = (ListView) dialogLayout.findViewById(R.id.NotesListView);
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,NotesAL);
        lv.setAdapter(adapter);
        SwipeDismissListViewTouchListener touchListener =
                new SwipeDismissListViewTouchListener(
                        lv,
                        new SwipeDismissListViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {

                                    NotesAL.remove(position);
                                    adapter.notifyDataSetChanged();

                                }


                            }
                        });
        lv.setOnTouchListener(touchListener);

        if (getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            // Toast.makeText(getApplicationContext(),"in startAutoComplete",Toast.LENGTH_LONG).show();

            ViewGroup.LayoutParams params = lv.getLayoutParams();
            params.height = 100 ;
            lv.setLayoutParams(params);
            lv.requestLayout();
        }
        else if (getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            ViewGroup.LayoutParams params = lv.getLayoutParams();
            // Toast.makeText(getApplicationContext(),"portrait",Toast.LENGTH_LONG).show();

            params.height = 1000;
            lv.setLayoutParams(params);
            lv.requestLayout();
        }

        adapter.notifyDataSetChanged();
        builder.setView(dialogLayout);
        dialog = builder.create();
        dialog.setCancelable(true);
        final Button AddNoteBtn = dialogLayout.findViewById(R.id.AddNoteBtn);
        final EditText AddNoteTxt= dialogLayout.findViewById(R.id.AddNoteTxt);
        AddNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!AddNoteTxt.getText().toString().equals("")){
                    NotesAL.add(AddNoteTxt.getText().toString());
                    AddNoteTxt.setText("");
                    adapter.notifyDataSetChanged();
                }}
        });
        Button DismissBtn = dialogLayout.findViewById(R.id.DismissBtn);
        DismissBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


//        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
//            ViewGroup.LayoutParams params = lv.getLayoutParams();
//            params.height = 1000;
//            lv.setLayoutParams(params);
//            lv.requestLayout();
//        }
//        else {
//            ViewGroup.LayoutParams params = lv.getLayoutParams();
//            params.height = 250;
//            lv.setLayoutParams(params);
//            lv.requestLayout();
//
//        }

        dialog.show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(dialog != null && dialog.isShowing()) {
            //dialog.dismiss();
            outState.putBoolean("alertShown", true);
        }
        outState.putStringArrayList("SavedNotes",NotesAL);

    }
    // AutoComplete starts
    public void StartAutoCompleteActivity() {
        // Toast.makeText(getApplicationContext(),"in startAutoComplete",Toast.LENGTH_LONG).show();
        Intent i = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,
                Arrays.asList(Place.Field.ID,Place.Field.NAME,Place.Field.LAT_LNG))
                .setTypeFilter(TypeFilter.ADDRESS)
                .setCountries(Arrays.asList("EG"))
                .build(AddTripActivity.this);
        startActivityForResult(i,AUTOCOMPLETE_REQUEST_CODE);

    }
    public long daysBetween(Calendar startDate, Calendar endDate) {
        startDate.set(Calendar.HOUR_OF_DAY,0);
        endDate.set(Calendar.HOUR_OF_DAY,0);
        startDate.set(Calendar.MINUTE,0);
        endDate.set(Calendar.MINUTE,0);

        long end = endDate.getTimeInMillis();
        long start = startDate.getTimeInMillis();
        startDate.set(Calendar.HOUR_OF_DAY,semiCalendarHome.hourOfDay);
        endDate.set(Calendar.HOUR_OF_DAY,semiCalendarRound.hourOfDay);
        startDate.set(Calendar.MINUTE,semiCalendarHome.minute);
        endDate.set(Calendar.MINUTE,semiCalendarRound.minute);
        return TimeUnit.MILLISECONDS.toDays(Math.abs(end - start));

    }
    // after a place is selected or autocomplete cancelled
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Toast.makeText(getApplicationContext(), "in activity result", Toast.LENGTH_LONG).show();
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);

                if (SelectedLocation.equals("Start")){
                    StartLat = place.getLatLng().latitude;
                    StartLong = place.getLatLng().longitude;
//                    StartLocationTxt.setText(place.getName());
                    StartLocationTxt.setText(getRegionName(StartLat,StartLong));
                }
                else if (SelectedLocation.equals("Dest")){
                    EndLat = place.getLatLng().latitude;
                    EndLong = place.getLatLng().longitude;
//                    DestinationTxt.setText(place.getName());
                    DestinationTxt.setText(getRegionName(EndLat,EndLong));

                }
                //  Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                //  Log.i(TAG, "longLat =  " + place.getLatLng().latitude);
                // Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_LONG).show();
//                Toast.makeText(getApplicationContext(), "Place: " + place.getName() , Toast.LENGTH_LONG).show();
//

//                Toast.makeText(getApplicationContext(), "LAT = " + place.getLatLng().latitude, Toast.LENGTH_LONG).show();
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
//                Toast.makeText(getApplicationContext(), "cancelled", Toast.LENGTH_LONG).show();

                // The user canceled the operation.
            }
        }
    }

    public void openTime() {
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(),"time picker");
    }

    private void showDateDialog(final EditText Txt,final String Date) {
        final Calendar calendar=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

//                calendar.set(Calendar.YEAR,year);
//                calendar.set(Calendar.MONTH,month);
//                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

                if (Date.equals("Round")){
                    //RoundDate = simpleDateFormat.format(calendarRound.getTime());
                    //SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    //  Date Round = simpleDateFormat.parse(RoundDate);
                    //Date Trip = simpleDateFormat.parse(TripDate);
                    //RepeatRound = TimeUnit.DAYS.convert((Round.getTime() - Trip.getTime()),TimeUnit.MILLISECONDS)+"";
                    // Toast.makeText(getApplicationContext(), RepeatRound, Toast.LENGTH_LONG).show();

                    semiCalendarRound.year = year;
                    semiCalendarRound.month = month;
                    semiCalendarRound.dayOfMonth = dayOfMonth;
                    if(semiCalendarRound.hourOfDay > 0 && semiCalendarRound.minute > 0) {
                        fillCalenderObj(calendarRound,semiCalendarRound);
                    }
                    RoundDate = String.valueOf(semiCalendarRound.dayOfMonth) + "-" + String.valueOf(semiCalendarRound.month + 1) + "-" + String.valueOf(semiCalendarRound.year);
                    Txt.setText(RoundDate);
                }
                else if (Date.equals("Trip")) {

                    semiCalendarHome.year = year;
                    semiCalendarHome.month = month;
                    semiCalendarHome.dayOfMonth = dayOfMonth;
                    if(semiCalendarHome.hourOfDay > 0 && semiCalendarHome.minute > 0) {

                        fillCalenderObj(calendarMain,semiCalendarHome);
                        chosenTripDate = calendarMain.getTimeInMillis();
                    }
                    TripDate = String.valueOf(semiCalendarHome.dayOfMonth) + "-" + String.valueOf(semiCalendarHome.month + 1) + "-" + String.valueOf(semiCalendarHome.year);
                    Txt.setText(TripDate);

//                    TripDate = simpleDateFormat.format(chosenTripDate);
//                    Toast.makeText(getApplicationContext(), "Trip data = "+TripDate, Toast.LENGTH_LONG).show();
                }



            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddTripActivity.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        if (Date.equals("Trip")){
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        }
        else if  (Date.equals("Round")) {
//            datePickerDialog.getDatePicker().setMinDate(chosenTripDate+(24*60*60*1000));
            datePickerDialog.getDatePicker().setMinDate(chosenTripDate);
            switch (RepeatEvery) {
                case 0:
//                    datePickerDialog.getDatePicker().setMaxDate(chosenTripDate+(24*60*60*1000));
                    break;
                case 1:
                    datePickerDialog.getDatePicker().setMaxDate(chosenTripDate+(24*60*60*1000));
                    break;
                case 7:
                    datePickerDialog.getDatePicker().setMaxDate(chosenTripDate+(24*60*60*1000*7));
                    break;
                case 30:
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.MONTH, 1);
                    long afterOneMonthinMilli = cal.getTimeInMillis();
                    datePickerDialog.getDatePicker().setMaxDate(afterOneMonthinMilli);
                    break;
            }
        }

        datePickerDialog.show();
        //new DatePickerDialog(AddTripActivity.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (SelectedTime.equals("Trip")){
//            TripTime = hourOfDay + ":" + minute;
//
            semiCalendarHome.hourOfDay = hourOfDay;
            semiCalendarHome.minute = minute;
            if(semiCalendarHome.month > 0) {
                fillCalenderObj(calendarMain,semiCalendarHome);
                chosenTripDate = calendarMain.getTimeInMillis();
            }
            TripTime = String.valueOf(semiCalendarHome.hourOfDay) + "-" + String.valueOf(semiCalendarHome.minute);
            TripTimetxt.setText(TripTime);
        }
        else if (SelectedTime.equals("Round")){
//            RoundTime =  hourOfDay + ":" + minute;
//            RoundTimeTxt.setText(RoundTime);
            semiCalendarRound.hourOfDay = hourOfDay;
            semiCalendarRound.minute = minute;
            if(semiCalendarRound.month > 0) {
                fillCalenderObj(calendarRound,semiCalendarRound);
            }
            RoundTime = String.valueOf(semiCalendarRound.hourOfDay) + "-" + String.valueOf(semiCalendarRound.minute);
            RoundTimeTxt.setText(RoundTime);
        }

    }
    private void fillCalenderObj(Calendar calendar,SemiCalendar semiCalendar) {
//        calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, semiCalendar.year);
        calendar.set(Calendar.MONTH, semiCalendar.month);
        calendar.set(Calendar.DAY_OF_MONTH, semiCalendar.dayOfMonth);
        calendar.set(Calendar.HOUR_OF_DAY, semiCalendar.hourOfDay);
        calendar.set(Calendar.MINUTE, semiCalendar.minute);
        calendar.set(Calendar.SECOND, 0);
    }

    public void saveTrip(View view) {
        ReminderPresenter reminderPresenter = new ReminderPresenter(this);
        SaveTripPresenter saveTripPresenter = new SaveTripPresenter(this);
        if (ValidateName(NameTxt)&& ValidateName(DescTxt)&& ValidateName(TripDateTxt)&&
        ValidateName(TripTimetxt)&& ValidateName(StartLocationTxt)&& ValidateName(DestinationTxt))
        {
        if (RepeatEvery == 0)
        {
            status = "upcoming";
        }
        else {
            status = "repeated";
        }

        TripName = NameTxt.getText().toString();

        TripDesc = DescTxt.getText().toString();

        trip.setName(TripName);
        trip.setDescription(TripDesc);
        //trip.setIsRound(isRound);

                /*trip.setRoundDate(RoundDate);
                trip.setRoundTime(RoundTime);
                trip.setRoundRepeatEvery(String.valueOf(RepeatRound));*/
        trip.setEndLatitude(EndLat);
        trip.setEndLongitude(EndLong);
        trip.setStartLatitude(StartLat);
        trip.setStartLongitude(StartLong);
        trip.setRepeatEvery(RepeatEvery);
        trip.setRequestCodeHome(isEdit? requestCode: requestCode++);
        calendarMain = GenerateCalendarObject.generateCalendar(TripDate,TripTime);
        reminderPresenter.startReminderService(calendarMain,trip.getRequestCodeHome());
        //trip.setRoundRepeatEvery(String.valueOf(RepeatRound));
        List<Note> notes = new ArrayList<>();
        for (int i=0;i<NotesAL.size();i++){
            Note note = new Note();
            note.setName(NotesAL.get(i));
            note.setStatus("unchecked");
            notes.add(note);
        }
        trip.setNotes(notes);
        if(!isEdit) {

            saveTripPresenter.saveTrip(trip, false);
            if (isRound == 1) {
                if (RepeatEvery == 0)
                    RepeatRound = 0;
                else
                    RepeatRound = daysBetween(calendarMain, calendarRound);
                Trip tripRound = new Trip();
                tripRound.setName(TripName + "- Round");
                tripRound.setDescription(TripDesc);
                //trip.setIsRound(isRound);
                tripRound.setDate(RoundDate);
                tripRound.setTime(RoundTime);
                tripRound.setStatus(status);
                tripRound.setEndLatitude(StartLat);
                tripRound.setEndLongitude(StartLong);
                tripRound.setStartLatitude(EndLat);
                tripRound.setStartLongitude(EndLong);
                tripRound.setRepeatEvery(RepeatRound);
                tripRound.setRequestCodeHome(requestCode++);
                reminderPresenter.startReminderService(calendarRound, tripRound.getRequestCodeHome());
                tripRound.setNotes(notes);
                saveTripPresenter.saveTrip(tripRound, false);
            }
            setRequestCodeInSharedPreference(requestCode);
            if (Internetonnection.isNetworkAvailable(this))
                requestCodePresenter.updateRequestCode(requestCode);
        }
        else {
            UpdateTripPresenter updateTripPresenter = new UpdateTripPresenter(this);
            updateTripPresenter.updateTrip(trip);
        }
        finish();

    }
    }
    @Override
    public void setRequestCodeInSharedPreference(int requestCode) {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("requestCode", requestCode);
        editor.commit();
        this.requestCode = requestCode;
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
    protected void onDestroy() {
        super.onDestroy();
        ControlNetworkChangeBroadcast.unregisterReceiver(this);

    }

    private boolean ValidateName(EditText editText){
        boolean result;
        String str = editText.getText().toString().trim();
        if (str.isEmpty()){
            editText.setError("Field can't be empty");
            result = false;
        } else {
            editText.setError(null);
            result = true;
        }
        return result;
    }
}