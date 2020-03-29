package com.example.tripandroidproject.View.History;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tripandroidproject.Contract.Trip.RetrieveTripContract;
import com.example.tripandroidproject.POJOs.Trip;
import com.example.tripandroidproject.Presenter.Trip.RetrieveTripPresenter;
import com.example.tripandroidproject.R;
import com.example.tripandroidproject.View.NavDrawer_UpComingTrip.TripAdapter;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment implements RetrieveTripContract.IRetrieveTripView {

    private RecyclerView.Adapter myAdapter;
    private RecyclerView recyclerView;
    private List<Trip> trips;
    private Trip trip;
    RetrieveTripContract.IRetrieveTripPresenter retrieveTripPresenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.historyfragment, container, false);
        recyclerView = view.findViewById(R.id.recycleViewHistory);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        trips = new ArrayList<>();
        myAdapter = new HistoryAdapter(getContext(),trips);
        retrieveTripPresenter = new RetrieveTripPresenter(getContext(),this);
        retrieveTripPresenter.retrieveFilteredTrips("Cancel");
        myAdapter.notifyDataSetChanged();
        renderData(trips);

        return view;
    }

    public void setAdapter(RecyclerView.Adapter myAdapter) {
        this.myAdapter = myAdapter;
        this.myAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(myAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void renderData(List<Trip> trips) {
        myAdapter = new TripAdapter(this.getContext(),trips);
        setAdapter(myAdapter);
    }

}
