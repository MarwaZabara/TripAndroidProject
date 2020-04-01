package com.example.tripandroidproject.View.NavDrawer_UpComingTrip;

import android.app.Activity;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripandroidproject.Contract.Trip.RetrieveTripContract;
import com.example.tripandroidproject.POJOs.Note;
import com.example.tripandroidproject.POJOs.Trip;
import com.example.tripandroidproject.Presenter.Note.GetNotePresenter;
import com.example.tripandroidproject.Presenter.Trip.GetOfflineTripPresenter;
import com.example.tripandroidproject.Presenter.Trip.RetrieveTripPresenter;
import com.example.tripandroidproject.Presenter.Trip.SaveTripPresenter;
import com.example.tripandroidproject.R;

import java.util.ArrayList;
import java.util.List;

public class UpComingFragment extends Fragment implements RetrieveTripContract.IRetrieveTripView {

    private RecyclerView.Adapter myAdapter;
    private RecyclerView recyclerView;
    private RetrieveTripPresenter retrieveTripPresenter;
    private GetOfflineTripPresenter getOfflineTripPresenter;
    private SaveTripPresenter saveTripOfflinePresenter;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.upcomingfragment, container, false);
        recyclerView = view.findViewById(R.id.recycleView);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

/////////////////////////////////////////////////////////////////////////////////
        getOfflineTripPresenter = new GetOfflineTripPresenter(this.getContext());
//        List<Trip> trips = getOfflineTripPresenter.getTrips();
        List<Trip> trips = getOfflineTripPresenter.getOfflineFilteredTrip("upcoming","repeated");
        if(trips.size() == 0){
            saveTripOfflinePresenter = new SaveTripPresenter(this.getContext());
            retrieveTripPresenter = new RetrieveTripPresenter(this.getContext(),this);
            retrieveTripPresenter.fetchData("upcoming","repeated");
            int i=0;
            for(i=0; i<trips.size();i++){
                Trip trip = trips.get(i);
                saveTripOfflinePresenter.saveTrip(trip, false);
            }
//            retrieveTripPresenter.retrieveUpcomingTrips();
        }
////////////////////////////////////////////////////////////////////////////////
        renderData(trips);
        return view;
    }

    public void setAdapter(RecyclerView.Adapter myAdapter) {
        this.myAdapter = myAdapter;
        this.myAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(myAdapter);
    }
    @Override
    public void renderData(List<Trip> trips) {
        myAdapter = new TripAdapter(this.getContext(),trips);
        setAdapter(myAdapter);

    }

}
