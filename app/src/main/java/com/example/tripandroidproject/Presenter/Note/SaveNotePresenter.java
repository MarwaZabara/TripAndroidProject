package com.example.tripandroidproject.Presenter.Note;

import android.content.Context;

import com.example.tripandroidproject.Contract.Note.SaveNoteContract;
import com.example.tripandroidproject.Model.Firebase.FirebaseNoteModel;
import com.example.tripandroidproject.Model.Firebase.FirebaseTripModel;
import com.example.tripandroidproject.Model.InternetConnection.Internetonnection;
import com.example.tripandroidproject.Model.Room.RoomNoteModel;
import com.example.tripandroidproject.Model.Room.RoomTripModel;
import com.example.tripandroidproject.POJOs.Note;

import java.util.List;

public class SaveNotePresenter implements SaveNoteContract.ISaveNotePresenter {
    Context context;
    public SaveNotePresenter(Context context)  {
        this.context = context;
    }

    @Override
    public void saveNote(List<Note> notes, String tripid) {
        RoomNoteModel roomNoteModel = new RoomNoteModel(this,context);
        FirebaseNoteModel firebaseNoteModel = new FirebaseNoteModel(tripid);

        if(Internetonnection.isNetworkAvailable(context))
        {
            for (int i = 0 ; i < notes.size() ; i++)
            {
                notes.get(i).setId(firebaseNoteModel.generateKey());
                notes.get(i).setTripID(tripid);
                firebaseNoteModel.saveNote(notes.get(i));
                roomNoteModel.saveNote(notes.get(i));
            }
        }
        else {
            for (int i = 0 ; i < notes.size() ; i++)
            {
                notes.get(i).setId(firebaseNoteModel.generateKey());
                notes.get(i).setTripID(tripid);
                roomNoteModel.saveNote(notes.get(i));
            }
        }
    }

    @Override
    public void onSucess() {

    }

    @Override
    public void onFail() {

    }
}