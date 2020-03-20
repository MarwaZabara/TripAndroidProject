package com.example.tripandroidproject.Model.Firebase;

import com.example.tripandroidproject.Contract.Trip.ITripPresenter;
import com.example.tripandroidproject.Contract.Trip.SaveTripContract;
import com.example.tripandroidproject.POJOs.Trip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseTripModel implements SaveTripContract.ISaveTripOnlineModel {
    FirebaseDatabase database;
    DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private ITripPresenter tripPresenter;

    public FirebaseTripModel(ITripPresenter tripPresenter) {
        this.tripPresenter = tripPresenter;
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Trip").child(mAuth.getCurrentUser().getUid());
    }

    @Override
    public void saveTrip(Trip trip) {
        //trip.setId(generateKey());
        myRef.child(trip.getId()).setValue(trip);
    }

    @Override
    public String generateKey() {
        return myRef.push().getKey();
    }
}
