package com.example.tripandroidproject.View.Repeated_NonRepeated;

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
import com.example.tripandroidproject.Presenter.Trip.GetOfflineTripPresenter;
import com.example.tripandroidproject.Presenter.Trip.RetrieveTripPresenter;
import com.example.tripandroidproject.R;
import com.example.tripandroidproject.View.NavDrawer_UpComingTrip.TripAdapter;

import java.util.List;


public class RepeatedFragment extends Fragment implements RetrieveTripContract.IRetrieveTripView{

    private RecyclerView.Adapter myAdapter;
    private RecyclerView recyclerView;
    private RetrieveTripPresenter retrieveTripPresenter;
    private GetOfflineTripPresenter getOfflineTripPresenter;

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

        //////get data by select
        getOfflineTripPresenter = new GetOfflineTripPresenter(this.getContext());
        List<Trip> trips = getOfflineTripPresenter.getOfflineFilteredTrip("repeated");
        if(trips.size() == 0) {
            retrieveTripPresenter = new RetrieveTripPresenter(this.getContext(), this);
            retrieveTripPresenter.retrieveFilteredTrips("repeated");
        }
        renderData(trips);
        return view;
    }

    @Override
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
