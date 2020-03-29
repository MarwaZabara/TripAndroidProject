package com.example.tripandroidproject.Model.Firebase;

import com.example.tripandroidproject.Contract.Trip.ITripPresenter;
import com.example.tripandroidproject.POJOs.RepeatedTripHistory;
import com.example.tripandroidproject.POJOs.Trip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseRepeatedTripHistory {
    FirebaseDatabase database;
    DatabaseReference myRef;
    private FirebaseAuth mAuth;
    public FirebaseRepeatedTripHistory(){
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        String userID = mAuth.getCurrentUser().getUid();
        myRef = database.getReference("RepeatedTripHistory").child(userID);
    }
    public void saveTrip(RepeatedTripHistory repeatedTripHistory) {
        repeatedTripHistory.setUserID(FirebaseUserModel.getUserID());
        myRef.child(repeatedTripHistory.getId()).setValue(repeatedTripHistory);
    }
    public String generateKey() {
        return myRef.push().getKey();
    }
}
