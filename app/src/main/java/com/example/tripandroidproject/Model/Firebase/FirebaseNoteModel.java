package com.example.tripandroidproject.Model.Firebase;

import com.example.tripandroidproject.Contract.Note.INotePresenter;
import com.example.tripandroidproject.Contract.Note.SaveNoteContract;
import com.example.tripandroidproject.Contract.Trip.ITripPresenter;
import com.example.tripandroidproject.POJOs.Note;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class FirebaseNoteModel implements SaveNoteContract.ISaveNoteOnlineModel {
    FirebaseDatabase database;
    DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private ITripPresenter tripPresenter;
    private String tripID;

    public FirebaseNoteModel(String tripID) {
        initializeVariables(tripID);
    }

    public FirebaseNoteModel(INotePresenter notePresenter, String tripID) {
        this.tripPresenter = tripPresenter;
        initializeVariables(tripID);
    }

    private void initializeVariables(String tripID) {
        this.tripID = tripID;
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Note").child(mAuth.getCurrentUser().getUid()).child(this.tripID);
    }

    @Override
    public void saveNote(Note note) {
        myRef.child(note.getId()).setValue(note);
    }

    @Override
    public String generateKey() {
        return myRef.push().getKey();
    }
}
