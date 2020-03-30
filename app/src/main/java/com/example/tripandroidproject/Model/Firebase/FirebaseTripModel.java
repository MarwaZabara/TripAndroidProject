package com.example.tripandroidproject.Model.Firebase;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripandroidproject.Contract.Trip.DeleteTripContract;
import com.example.tripandroidproject.Contract.Trip.ITripPresenter;
import com.example.tripandroidproject.Contract.Trip.RetrieveTripContract;
import com.example.tripandroidproject.Contract.Trip.SaveTripContract;
import com.example.tripandroidproject.Contract.Trip.UpdateTripContract;
import com.example.tripandroidproject.POJOs.Trip;
import com.example.tripandroidproject.View.NavDrawer_UpComingTrip.TripAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseTripModel implements SaveTripContract.ISaveTripOnlineModel , UpdateTripContract.IUpdateTripModel,RetrieveTripContract.IRetrieveTripModel, DeleteTripContract.IDeleteTripModel {
    FirebaseDatabase database;
    DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private ITripPresenter tripPresenter;
    //////////////////////////////////////////////////////
    private RetrieveTripContract.IRetrieveTripPresenter retrieveTripPresenter;
    List<Trip> input;
//    List<Trip> repeatedList,nonRepeatedList;
    Context context;
//    RecyclerView.Adapter myAdapter;

    public FirebaseTripModel(){
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        String userID = mAuth.getCurrentUser().getUid();
        myRef = database.getReference("Trip").child(userID);
    }

    public FirebaseTripModel(ITripPresenter tripPresenter) {
        this.tripPresenter = tripPresenter;
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Trip").child(mAuth.getCurrentUser().getUid());
    }
    public FirebaseTripModel(Context context,RetrieveTripContract.IRetrieveTripPresenter retrieveTripPresenter) {
        this.retrieveTripPresenter = retrieveTripPresenter;
        this.context = context;
        input = new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Trip").child(mAuth.getCurrentUser().getUid());
    }

    @Override
    public void saveTrip(Trip trip) {
        trip.setUserID(FirebaseUserModel.getUserID());
        myRef.child(trip.getId()).setValue(trip);
    }

    @Override
    public String generateKey() {
        return myRef.push().getKey();
    }
//    @Override
//    public String getUserID() {
//        return mAuth.getCurrentUser().getUid();
//    }


    @Override
    public void fetchData() {
        Query query = myRef.orderByChild("status").equalTo("upcoming");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                input.clear();
                for (DataSnapshot tripSnapShot : dataSnapshot.getChildren()){
                    Trip trip = tripSnapShot.getValue(Trip.class);
                    input.add(trip);
                }
                retrieveTripPresenter.onSuccessGetUpcomingTrips(input);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void fetchFilteredData(String filter) {
        Query query = myRef.orderByChild("status").equalTo(filter);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                input.clear();
                for (DataSnapshot tripSnapShot : dataSnapshot.getChildren()){
                    Trip trip = tripSnapShot.getValue(Trip.class);
                    input.add(trip);
                }
                retrieveTripPresenter.onSuccessGetUpcomingTrips(input);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void fetchData(String filter1, String filter2) {
//        Query query = myRef.orderByChild("status_status").equalTo(filter1+"_"+filter2);
//        Query query1 = myRef.orderByChild("status").equalTo(filter1);
        Query q = myRef.orderByChild("status").startAt(filter1).endAt(filter2);
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                input.clear();
                for (DataSnapshot tripSnapShot : dataSnapshot.getChildren()){
                    Trip trip = tripSnapShot.getValue(Trip.class);
                    input.add(trip);
                }
//                retrieveTripPresenter.onSuccessGetUpcomingTrips(input);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
//        Query query2 = myRef.orderByChild("status").equalTo(filter2);
//        query2.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                input.clear();
//                for (DataSnapshot tripSnapShot : dataSnapshot.getChildren()){
//                    Trip trip = tripSnapShot.getValue(Trip.class);
//                    input.add(trip);
//                }
//                retrieveTripPresenter.onSuccessGetUpcomingTrips(input);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//            }
//        });
    }

    @Override
    public List<Trip> returnData() {
        return input;
    }
//    @Override
//    public List<Trip> returnRepeatedData() {
//        return input;
//    }
//    @Override
//    public List<Trip> returnNonRepeatedData() {
//        return input;
//    }

    @Override
    public void deleteTrip(Trip trip) {
        DatabaseReference databaseReference = myRef.child(trip.getId());
        databaseReference.removeValue();
        Log.d("TAG","Delete from firebase");
    }

    @Override
    public void updateTrip(Trip trip) {
        myRef.child(trip.getId()).setValue(trip);
    }

}
