package com.example.tripandroidproject.Presenter.Note;

import android.content.Context;

import com.example.tripandroidproject.Contract.Note.GetNoteContract;
import com.example.tripandroidproject.Model.Room.RoomNoteModel;
import com.example.tripandroidproject.Model.Room.RoomTripModel;
import com.example.tripandroidproject.POJOs.Note;
import com.example.tripandroidproject.POJOs.Trip;

import java.util.List;

public class GetNotePresenter implements GetNoteContract.IGetNotePresenter {
    Context context;
    public GetNotePresenter(Context context) {
        this.context = context;
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
}
