package com.example.tripandroidproject.Presenter.Trip;

import android.content.Context;

import com.example.tripandroidproject.Contract.Trip.ITripPresenter;
import com.example.tripandroidproject.Custom.Calendar.GenerateCalendarObject;
import com.example.tripandroidproject.Model.Firebase.FirebaseRepeatedTripHistory;
import com.example.tripandroidproject.Model.Firebase.FirebaseTripModel;
import com.example.tripandroidproject.Model.InternetConnection.Internetonnection;
import com.example.tripandroidproject.Model.ReminderModel.ReminderModel;
import com.example.tripandroidproject.Model.Room.RoomRepeatedTripHistoryModel;
import com.example.tripandroidproject.Model.Room.RoomTripModel;
import com.example.tripandroidproject.POJOs.RepeatedTripHistory;
import com.example.tripandroidproject.POJOs.Trip;

import java.lang.reflect.Field;
import java.util.Calendar;

public class FinishTripPresenter implements ITripPresenter, com.example.tripandroidproject.Contract.Trip.FinishTripPresenter.IFinishTripPresenter {
    Context context;
    ReminderModel reminderModel;
    public FinishTripPresenter(Context context) {
        this.context = context;
        reminderModel = new ReminderModel(this,context);
    }

    @Override
    public void finishTrip(String tripID) {
        RoomTripModel roomTripModel = new RoomTripModel(this,context);
        RoomRepeatedTripHistoryModel roomRepeatedTripHistoryModel = new RoomRepeatedTripHistoryModel(this,context);

        FirebaseRepeatedTripHistory firebaseRepeatedTripHistory = new FirebaseRepeatedTripHistory();
        FirebaseTripModel firebaseTripModel = new FirebaseTripModel();
        Trip trip = roomTripModel.getTripForSpecificID(tripID);
        trip.setStatus("upcoming");
        if (trip.getRepeatEvery() > 0)
        {

            RepeatedTripHistory repeatedTripHistory = setObject(trip);
            if(Internetonnection.isNetworkAvailable(context))
            {
                trip.setIsSync(1);
                repeatedTripHistory.setIsSync(1);
                repeatedTripHistory.setId(firebaseRepeatedTripHistory.generateKey());
                firebaseRepeatedTripHistory.saveTrip(repeatedTripHistory);
            }
            else {
                trip.setIsSync(0);
                repeatedTripHistory.setIsSync(0);
                roomRepeatedTripHistoryModel.saveTrip(repeatedTripHistory);
            }
            Calendar calendarHome = GenerateCalendarObject.generateCalendar(trip.getDate(),"0-0");
//            calendarHome.add(Calendar.DAY_OF_YEAR, (int) trip.getRepeatEvery());
            calendarHome.set(Calendar.DAY_OF_YEAR,GenerateCalendarObject.dayOfYear + (int) trip.getRepeatEvery());
//            calendarHome.set(Calendar.HOUR_OF_DAY, Integer.parseInt(trip.getTime().split("-")[0]));
//            calendarHome.set(Calendar.MINUTE, Integer.parseInt(trip.getTime().split("-")[1]));
//            trip.setDate(GenerateCalendarObject.generateStringDate(calendarHome));
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, GenerateCalendarObject.dayOfMonth);
            calendar.set(Calendar.MONTH, GenerateCalendarObject.month);
            calendar.set(Calendar.YEAR, GenerateCalendarObject.year);
//            String x= String.valueOf(calendarHome.get(Calendar.DAY_OF_MONTH));
            trip.setDate(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) + "-" + String.valueOf(calendar.get(Calendar.MONTH) + 1) + "-" + String.valueOf(calendar.get(Calendar.YEAR)));
//            trip.setTime(GenerateCalendarObject.generateStringTme(calendarHome));
            reminderModel.startAlarmService(GenerateCalendarObject.generateCalendar(trip.getDate(),trip.getTime()),trip.getRequestCodeHome());

//                if(trip.getIsRound() == 1)
//                {
//                    Calendar calendarRound = GenerateCalendarObject.generateCalendar(trip.getRoundDate(),"0-0");
//                    calendarRound.add(Calendar.DAY_OF_MONTH, trip.getRepeatEvery());
//                    calendarRound.set(Calendar.HOUR_OF_DAY, Integer.parseInt(trip.getRoundTime().split("-")[0]));
//                    calendarRound.set(Calendar.MINUTE, Integer.parseInt(trip.getRoundTime().split("-")[1]));
//                    trip.setRoundDate(GenerateCalendarObject.generateStringDate(calendarHome));
//                    trip.setRoundTime(GenerateCalendarObject.generateStringTme(calendarHome));
//                }

        }
        if(Internetonnection.isNetworkAvailable(context))
        {
            trip.setIsSync(1);
            firebaseTripModel.saveTrip(trip);
        }
        else {
            trip.setIsSync(0);
        }
        roomTripModel.updateTrip(trip);


    }

    private RepeatedTripHistory setObject(Trip trip) {
        RepeatedTripHistory repeatedTripHistory = new RepeatedTripHistory(trip.getId(), trip.getUserID(), trip.getName(),
                trip.getDescription(), "finished", trip.getDate(), trip.getTime(),
                trip.getRepeatEvery(), trip.getRequestCodeHome(), trip.getStartLongitude(),
                trip.getStartLatitude(), trip.getEndLongitude(), trip.getEndLatitude(), trip.getIsSync(),
                trip.getNotes());
        return repeatedTripHistory;
    }

    @Override
    public void onSucess() {

    }

    @Override
    public void onFail() {

    }
    public static void copyObject(Object src, Object dest)
            throws IllegalArgumentException, IllegalAccessException,
            NoSuchFieldException, SecurityException {
        for (Field field : src.getClass().getFields()) {
            dest.getClass().getField(field.getName()).set(dest, field.get(src));
        }
    }
}
