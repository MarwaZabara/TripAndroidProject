package com.example.tripandroidproject.View.NavDrawer_UpComingTrip;

import android.app.Activity;
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
import com.example.tripandroidproject.POJOs.Trip;
import com.example.tripandroidproject.Presenter.Trip.GetOfflineTripPresenter;
import com.example.tripandroidproject.Presenter.Trip.RetrieveTripPresenter;
import com.example.tripandroidproject.R;

import java.util.ArrayList;
import java.util.List;

public class UpComingFragment extends Fragment implements RetrieveTripContract.IRetrieveTripView {


    private RecyclerView.Adapter myAdapter;
    private RecyclerView recyclerView;
    private RetrieveTripPresenter retrieveTripPresenter;
    private GetOfflineTripPresenter getOfflineTripPresenter;
//    private CommunicatorFrag communicatorFrag;
    private Trip trip;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity a = getActivity();
//        communicatorFrag = (CommunicatorFrag) a;
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
//        presenter = new RetrieveTripPresenter(this.getContext(),this);

        getOfflineTripPresenter = new GetOfflineTripPresenter(this.getContext());
        List<Trip> trips = getOfflineTripPresenter.getTrips();
        if(trips.size() == 0)
        {
            retrieveTripPresenter = new RetrieveTripPresenter(this.getContext(),this);
            retrieveTripPresenter.retrieveUpcomingTrips();
        }
        myAdapter = new TripAdapter(this.getContext(),trips);
//        input = model.returnData();
//        myAdapter = returnAdapter();
        setAdapter(myAdapter);
        return view;
    }

    @Override
    public void setAdapter(RecyclerView.Adapter myAdapter) {
        this.myAdapter = myAdapter;
        this.myAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(myAdapter);
    }

}
