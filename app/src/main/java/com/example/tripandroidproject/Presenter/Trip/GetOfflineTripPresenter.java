package com.example.tripandroidproject.Presenter.Trip;

import android.content.Context;

import com.example.tripandroidproject.Contract.Trip.GetOfflineTripContract;
import com.example.tripandroidproject.Contract.Trip.ITripPresenter;
import com.example.tripandroidproject.Model.Firebase.FirebaseNoteModel;
import com.example.tripandroidproject.Model.Firebase.FirebaseTripModel;
import com.example.tripandroidproject.Model.Room.RoomNoteModel;
import com.example.tripandroidproject.Model.Room.RoomTripModel;
import com.example.tripandroidproject.POJOs.Note;
import com.example.tripandroidproject.POJOs.Trip;
import com.example.tripandroidproject.Presenter.Note.GetNotePresenter;
import com.example.tripandroidproject.Presenter.Note.SaveNotePresenter;

import java.util.List;

public class GetOfflineTripPresenter implements GetOfflineTripContract.IGetOfflineTripPresenter {
    Context context;
    public GetOfflineTripPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void getOfflineTrip() {
        RoomTripModel roomTripModel = new RoomTripModel(this,context);
        List<Trip> trips = roomTripModel.getOfflineTrip();
        FirebaseTripModel firebaseTripModel = new FirebaseTripModel(this);
        for (int i = 0; i < trips.size(); i++) {
            firebaseTripModel.saveTrip(trips.get(i));
            getOfflineNoteWithSpecificTrip(trips.get(i).getId());
        }

    }
    public List<Trip> getTrips() {
        RoomTripModel roomTripModel = new RoomTripModel(this,context);
        List<Trip> trips = roomTripModel.getTrips();
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

    @Override
    public void onSucess() {

    }

    @Override
    public void onFail() {

    }
}
