package com.example.tripandroidproject.Model.Firebase;

import com.example.tripandroidproject.Contract.Trip.SaveTripContract;
import com.example.tripandroidproject.POJOs.Trip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseTripModel implements SaveTripContract.ISaveTripOnlineModel {
    FirebaseDatabase database;
    DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private SaveTripContract.ISaveTripPresenter saveTripPresenter;

    public FirebaseTripModel(SaveTripContract.ISaveTripPresenter saveTripPresenter) {
        this.saveTripPresenter = saveTripPresenter;
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Trip").child(mAuth.getCurrentUser().getUid());
    }

    @Override
    public void saveTrip(Trip trip) {
        String genKey = myRef.push().getKey();
        trip.setId(genKey);
        myRef.child(genKey).setValue(trip);
    }
}
