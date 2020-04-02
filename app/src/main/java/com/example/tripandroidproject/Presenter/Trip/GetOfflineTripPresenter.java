package com.example.tripandroidproject.Presenter.Trip;

import android.content.Context;

import com.example.tripandroidproject.Contract.Trip.GetOfflineTripContract;
import com.example.tripandroidproject.Contract.Trip.ITripPresenter;
import com.example.tripandroidproject.Model.Firebase.FirebaseNoteModel;
import com.example.tripandroidproject.Model.Firebase.FirebaseTripModel;
import com.example.tripandroidproject.Model.Room.RoomNoteModel;
import com.example.tripandroidproject.Model.Room.RoomRepeatedTripHistoryModel;
import com.example.tripandroidproject.Model.Room.RoomTripModel;
import com.example.tripandroidproject.POJOs.Note;
import com.example.tripandroidproject.POJOs.RepeatedTripHistory;
import com.example.tripandroidproject.POJOs.Trip;
import com.example.tripandroidproject.Presenter.Note.GetNotePresenter;
import com.example.tripandroidproject.Presenter.Note.SaveNotePresenter;

import java.util.ArrayList;
import java.util.List;

public class GetOfflineTripPresenter implements GetOfflineTripContract.IGetOfflineTripPresenter {
    Context context;
    List<RepeatedTripHistory> repeatedTripHistories;
    public GetOfflineTripPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void getOfflineTrip() {
        RoomTripModel roomTripModel = new RoomTripModel(this,context);

        List<Trip> trips = roomTripModel.getOfflineTrip();
        FirebaseTripModel firebaseTripModel = new FirebaseTripModel(this);
        for (int i = 0; i < trips.size(); i++) {
            trips.get(i).setIsSync(1);
            firebaseTripModel.saveTrip(trips.get(i));
            getOfflineNoteWithSpecificTrip(trips.get(i).getId());
        }

    }

    @Override
    public List<Trip> getOfflineFilteredTrip(String filter) {
        RoomTripModel roomTripModel = new RoomTripModel(this,context);
        List<Trip> trips = roomTripModel.getOfflineFilteredTrip(filter);
//        FirebaseTripModel firebaseTripModel = new FirebaseTripModel(this);
        for (int i = 0; i < trips.size(); i++) {
//            firebaseTripModel.saveTrip(trips.get(i));
            getOfflineNoteWithSpecificTrip(trips.get(i).getId());
        }
        return trips;
    }

    @Override
    public List<Trip> getOfflineFilteredTrip(String filter1, String filter2) {
        RoomTripModel roomTripModel = new RoomTripModel(this,context);
        List<Trip> trips = roomTripModel.getOfflineFilteredTrip(filter1, filter2);
        for (int i = 0; i < trips.size(); i++) {
            getOfflineNoteWithSpecificTrip(trips.get(i).getId());
        }
        return trips;
    }

    public List<Trip> getTrips() {
        RoomTripModel roomTripModel = new RoomTripModel(this,context);
        List<Trip> trips = roomTripModel.getTrips();
        return trips;
    }

    public List<Trip> getAllOfflineTrips() {
        RoomTripModel roomTripModel = new RoomTripModel(this,context);
        List<Trip> trips = roomTripModel.getAllOfflineTrip();
        return trips;
    }

    @Override
    public Trip getTripInfo(int requestCode) {
        RoomTripModel roomTripModel = new RoomTripModel(this,context);
        Trip trip = roomTripModel.getTripForSpecificCode(requestCode);
        return trip;
    }

    @Override
    public void getOfflineNoteWithSpecificTrip(String tripID) {
        GetNotePresenter getNotePresenter = new GetNotePresenter(context);
        List<Note> notes = getNotePresenter.getNotes(tripID);
        FirebaseNoteModel firebaseTripModel = new FirebaseNoteModel(tripID);
        for (int i = 0; i < notes.size() ; i++)
        {
            firebaseTripModel.saveNote(notes.get(i));
        }
    }
    public  List<Trip> getRepeatedHistory()
    {
        RoomRepeatedTripHistoryModel roomRepeatedTripHistoryModel = new RoomRepeatedTripHistoryModel(this,context);
        repeatedTripHistories = roomRepeatedTripHistoryModel.getRepeatedHistory();
        List<Trip> trips = new ArrayList<>();
        for(int i = 0; i < repeatedTripHistories.size()  ; i++) {

            Trip trip = setObject(repeatedTripHistories.get(i));
            trips.add(trip);
        }
        return trips;
    }
    @Override
    public void onSucess() {

    }

    @Override
    public void onFail() {

    }
    public Trip setObject(RepeatedTripHistory repeatedTripHistory) {

        Trip trip = new Trip(repeatedTripHistory.getId(), repeatedTripHistory.getUserID(), repeatedTripHistory.getName(),
                repeatedTripHistory.getDescription(), "finished", repeatedTripHistory.getDate(), repeatedTripHistory.getTime(),
                repeatedTripHistory.getRepeatEvery(), repeatedTripHistory.getRequestCodeHome(), repeatedTripHistory.getStartLongitude(),
                repeatedTripHistory.getStartLatitude(), repeatedTripHistory.getEndLongitude(), repeatedTripHistory.getEndLatitude(), repeatedTripHistory.getIsSync(),
                repeatedTripHistory.getNotes());
        return trip;
    }
}
