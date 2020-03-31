package com.example.tripandroidproject.Presenter.Trip;

import android.content.Context;

import com.example.tripandroidproject.Contract.Note.INotePresenter;
import com.example.tripandroidproject.Contract.Trip.ITripPresenter;
import com.example.tripandroidproject.Model.Firebase.FirebaseNoteModel;
import com.example.tripandroidproject.Model.Firebase.FirebaseTripModel;
import com.example.tripandroidproject.Model.InternetConnection.Internetonnection;
import com.example.tripandroidproject.Model.Room.RoomTripModel;
import com.example.tripandroidproject.POJOs.Trip;
import com.example.tripandroidproject.Presenter.Note.SaveNotePresenter;

public class UpdateTripPresenter implements ITripPresenter, INotePresenter {
    Context context;

    public UpdateTripPresenter(Context context) {
        this.context = context;
    }

    public void updateTrip(Trip trip) {
        RoomTripModel roomTripModel = new RoomTripModel(this,context);
        FirebaseTripModel firebaseTripModel = new FirebaseTripModel(this);
        if(Internetonnection.isNetworkAvailable(context))
        {
            trip.setIsSync(1);
            firebaseTripModel.saveTrip(trip);
            roomTripModel.updateTrip(trip);
        }
        else {
            trip.setIsSync(0);
            roomTripModel.updateTrip(trip);
        }
        if (trip.getNotes().size() > 0)
        {
            if(Internetonnection.isNetworkAvailable(context))
            {
                FirebaseNoteModel firebaseNoteModel = new FirebaseNoteModel(this,trip.getId());
                firebaseNoteModel.deleteNote();
            }
            SaveNotePresenter saveNotePresenter = new SaveNotePresenter(context);
            saveNotePresenter.saveNote(trip.getNotes(),trip.getId());
        }
    }
}