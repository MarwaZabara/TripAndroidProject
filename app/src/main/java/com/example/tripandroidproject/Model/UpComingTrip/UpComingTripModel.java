package com.example.tripandroidproject.Model.UpComingTrip;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripandroidproject.Contract.UpComingTrip.UpComingTripContract;
import com.example.tripandroidproject.POJOs.Trip;
import com.example.tripandroidproject.View.UpComingTrips.TripAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UpComingTripModel implements UpComingTripContract.IUpComingTripModel {

    DatabaseReference databaseReference;
    List<Trip> input;
    Context context;
    RecyclerView.Adapter myAdapter;
    UpComingTripContract.IUpComingTripPresenter presenter;

    public UpComingTripModel(Context context, UpComingTripContract.IUpComingTripPresenter presenter, String id){
        this.context = context;
        this.presenter = presenter;
        input = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Trip").child(id);
//        fetchData();
    }

    @Override
    public void fetchData(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                input.clear();
                for (DataSnapshot tripSnapShot : dataSnapshot.getChildren()){
                    Trip trip = tripSnapShot.getValue(Trip.class);
                    input.add(trip);
                }
                myAdapter = new TripAdapter(context,input);
                presenter.onSucess();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public List<Trip> returnData() {
        return input;
    }

    @Override
    public RecyclerView.Adapter returnAdapter() {
        return myAdapter;
    }
}
