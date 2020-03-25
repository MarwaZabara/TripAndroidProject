package com.example.tripandroidproject.Model.Firebase;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.tripandroidproject.Contract.Note.INotePresenter;
import com.example.tripandroidproject.Contract.Note.SaveNoteContract;
import com.example.tripandroidproject.Contract.Trip.ITripPresenter;
import com.example.tripandroidproject.POJOs.Note;
import com.example.tripandroidproject.POJOs.Trip;
import com.example.tripandroidproject.Presenter.Note.GetNotePresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseNoteModel implements SaveNoteContract.ISaveNoteOnlineModel {
    FirebaseDatabase database;
    DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private INotePresenter notePresenter;
    private String tripID;

    public FirebaseNoteModel(String tripID) {
        this.tripID = tripID;
        initializeVariables(tripID);
    }

    public FirebaseNoteModel(INotePresenter notePresenter, String tripID) {
        this.tripID = tripID;
        this.notePresenter = notePresenter;
        initializeVariables(tripID);
    }

    public FirebaseNoteModel(INotePresenter notePresenter) {
        this.notePresenter = notePresenter;

    }

    private void initializeVariables(String tripID) {
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Note").child(mAuth.getCurrentUser().getUid()).child(tripID);
    }

    @Override
    public void saveNote(Note note) {
        myRef.child(note.getId()).setValue(note);
    }

    @Override
    public String generateKey() {
        return myRef.push().getKey();
    }

    public void getUpcomingNotesForSpecificTrip(String tripId) {
        if(myRef == null)
        {
            initializeVariables(tripId);
        }
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Note> notes = new ArrayList<>();
                for (DataSnapshot noteSnapShot : dataSnapshot.getChildren()){
                    Note note = noteSnapShot.getValue(Note.class);
                    notes.add(note);
                }
//                myAdapter = new TripAdapter(context,input);
                GetNotePresenter getNotePresenter = (GetNotePresenter) notePresenter;
                getNotePresenter.onSucessUpcomingNotes(notes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
