package com.example.tripandroidproject.Presenter.Note;

import android.content.Context;

import com.example.tripandroidproject.Contract.Note.UpdateNoteContract;
import com.example.tripandroidproject.Contract.Trip.ITripPresenter;
import com.example.tripandroidproject.Model.Firebase.FirebaseNoteModel;
import com.example.tripandroidproject.Model.InternetConnection.Internetonnection;
import com.example.tripandroidproject.Model.Room.RoomNoteModel;
import com.example.tripandroidproject.Model.Room.RoomTripModel;
import com.example.tripandroidproject.POJOs.Note;
import com.example.tripandroidproject.POJOs.Trip;

public class UpdateNotePresenter implements UpdateNoteContract.IUpdateNotePresenter, ITripPresenter {
    Context context;
    RoomNoteModel roomNoteModel;
    RoomTripModel roomTripModel;
    public UpdateNotePresenter(Context context) {
        this.context = context;
        roomNoteModel = new RoomNoteModel(context);
    }
    @Override
    public void updateNote(Note note) {
        FirebaseNoteModel firebaseNoteModel = new FirebaseNoteModel(note.getTripID());

        if(Internetonnection.isNetworkAvailable(context)) {
            firebaseNoteModel.saveNote(note); // save here make update also
        }
        else {
            // msh el tare2a el as7 mafrood a3tmd 3la el note bs
            roomTripModel = new RoomTripModel(this,context);
            Trip trip = roomTripModel.getTripForSpecificID(note.getTripID());
            trip.setIsSync(0);
            roomTripModel.updateTrip(trip);
        }
        roomNoteModel.updateNote(note);
    }
}
