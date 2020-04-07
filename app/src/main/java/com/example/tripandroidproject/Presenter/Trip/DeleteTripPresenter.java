package com.example.tripandroidproject.Presenter.Trip;

import android.content.Context;

import com.example.tripandroidproject.Contract.Reminder.Reminder;
import com.example.tripandroidproject.Contract.Trip.DeleteTripContract;
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

import java.util.Calendar;

public class DeleteTripPresenter implements ITripPresenter, DeleteTripContract.IDeleteTripPresenter, Reminder.IBaseReminder {

    DeleteTripContract.IDeleteTripModel model;
    Context context;

    public DeleteTripPresenter(Context context) {
        model = new FirebaseTripModel();
        this.context = context;
    }

    @Override
    public void deleteTrip(Trip trip) {
        //    model.deleteTrip(trip);
        RoomTripModel roomTripModel = new RoomTripModel(this, context);
        RoomRepeatedTripHistoryModel roomRepeatedTripHistoryModel = new RoomRepeatedTripHistoryModel(this, context);
        ReminderModel reminderModel = new ReminderModel(this, context);

        FirebaseRepeatedTripHistory firebaseRepeatedTripHistory = new FirebaseRepeatedTripHistory();
        FirebaseTripModel firebaseTripModel = new FirebaseTripModel();
        if (Internetonnection.isNetworkAvailable(context)) {
            firebaseTripModel.deleteTrip(trip);    ///////////delete from firebase
//            deleteOfflineTripPresenter.deleteOfflineTrip(trip); /////delete from room
//            trip.setIsSync(1);
            roomTripModel.deleteOfflineTrip(trip);/////delete from room

        } else if (!Internetonnection.isNetworkAvailable(context) && trip.getIsSync() == 1) {
            // isSync = 1 mean it stored in firebase allready and need to delete it from firebase & room
            trip.setIsSync(0);
            trip.setStatus("delete");
            roomTripModel.updateTrip(trip);
            /////// -> here send trip to Hassan

        } else {
            //////// isSync = 0 mean it didn't store in firebase so need to delete it from room only
//            trip.setIsSync(1);
//            updateTripPresenter.updateTrip(trip);
            roomTripModel.deleteOfflineTrip(trip); /////delete from room
            /////// -> here send trip to Hassan
        }
        reminderModel.stopAlarmService(trip.getRequestCodeHome());
        reminderModel.stopService();
    }

    public void deleteRepeatedHistoryTrip(Trip trip) {
        RoomRepeatedTripHistoryModel roomRepeatedTripHistoryModel = new RoomRepeatedTripHistoryModel(this, context);
        RepeatedTripHistory repeatedTripHistory = setObject(trip);
        if (Internetonnection.isNetworkAvailable(context)) {
            FirebaseRepeatedTripHistory firebaseRepeatedTripHistory = new FirebaseRepeatedTripHistory();
            firebaseRepeatedTripHistory.deleteTrip(repeatedTripHistory);
        }
        roomRepeatedTripHistoryModel.deleteOfflineTrip(repeatedTripHistory);
    }

    private RepeatedTripHistory setObject(Trip trip) {
        RepeatedTripHistory repeatedTripHistory = new RepeatedTripHistory(trip.getId(), trip.getUserID(), trip.getName(),
                trip.getDescription(), trip.getStatus(), trip.getDate(), trip.getTime(),
                trip.getRepeatEvery(), trip.getRequestCodeHome(), trip.getStartLongitude(),
                trip.getStartLatitude(), trip.getEndLongitude(), trip.getEndLatitude(), trip.getIsSync(),
                trip.getNotes());
        return repeatedTripHistory;
    }
}