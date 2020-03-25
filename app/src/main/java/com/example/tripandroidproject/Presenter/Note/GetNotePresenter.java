package com.example.tripandroidproject.Presenter.Note;

import android.content.Context;

import com.example.tripandroidproject.Contract.Note.GetNoteContract;
import com.example.tripandroidproject.Model.Firebase.FirebaseNoteModel;
import com.example.tripandroidproject.Model.Room.RoomNoteModel;
import com.example.tripandroidproject.Model.Room.RoomTripModel;
import com.example.tripandroidproject.POJOs.Note;
import com.example.tripandroidproject.POJOs.Trip;

import java.util.List;

public class GetNotePresenter implements GetNoteContract.IGetNotePresenter {
    Context context;
    FirebaseNoteModel firebaseNoteModel;
    public GetNotePresenter(Context context) {
        this.context = context;
    }

    public GetNotePresenter(Context context,boolean isFirebase) {
        firebaseNoteModel = new FirebaseNoteModel(this);

    }

    @Override
    public List<Note> getNotes(String tripID) {
        RoomNoteModel roomNoteModel = new RoomNoteModel(this,context);
        return roomNoteModel.getNotes(tripID);
    }

    @Override
    public void onSucess() {

    }

    @Override
    public void onFail() {

    }
    @Override
    public void onSucessUpcomingNotes(List<Note> notes) {
        SaveNotePresenter saveNotePresenter = new SaveNotePresenter(context);
        saveNotePresenter.saveNotesInRoomOnly(null,notes,null);
    }
    public void getUpComingNotes(String tripId, Context context) {
        this.context = context;
        firebaseNoteModel.getUpcomingNotesForSpecificTrip(tripId);
    }
}
