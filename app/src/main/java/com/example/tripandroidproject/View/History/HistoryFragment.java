package com.example.tripandroidproject.View.History;

import android.os.Bundle;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment implements RetrieveTripContract.IRetrieveTripView {

    private RecyclerView.Adapter myAdapter;
    private RecyclerView recyclerView;
    private List<Trip> trips ;
    private List<Trip> hTrips ;
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
        hTrips = new ArrayList<>();
        myAdapter = new HistoryAdapter(getContext(),trips);
        retrieveTripPresenter = new RetrieveTripPresenter(this.getContext(),this);
        retrieveTripPresenter.fetchData("Cancel","finished");
//        retrieveTripPresenter.retrieveFilteredTrips("Cancel");
//        myAdapter.notifyDataSetChanged();
//        renderData(trips);

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
        //////////get from table trips
        hTrips = trips;
        Log.d("History","------------------1-hTrips-before2ndTable-----------------");
        for (int i=0 ; i<hTrips.size(); i++){
            Log.d("History",hTrips.get(i).getName());
            Log.d("History",hTrips.get(i).getStatus());
        }
        Log.d("History","------------------2-trips-before2ndTable-----------------");
        for (int i=0 ; i<this.trips.size(); i++){
            Log.d("History",this.trips.get(i).getName());
            Log.d("History",this.trips.get(i).getStatus());
        }
        myAdapter = new TripAdapter(this.getContext(),hTrips);
        setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
        retrieveTripPresenter.retrieveRepeatedHistoryTrips();
    }


    public void returnAllHistory(List<Trip> historyTrips) {
        //////////get from table repeated history trips
        Log.d("History","------------------3-trips-after2ndTable------------------");
        for (int i=0 ; i<trips.size(); i++){
            Log.d("History",trips.get(i).getName());
            Log.d("History",trips.get(i).getStatus());
        }
        Log.d("History","------------------4-hTrips-after2ndTable------------------");
        for (int i=0 ; i<hTrips.size(); i++){
            Log.d("History",hTrips.get(i).getName());
            Log.d("History",hTrips.get(i).getStatus());
//        }
        Log.d("History","------------------5-comingData-after2ndTable------------------");
        for (int j=0 ; j<historyTrips.size(); j++){
            Log.d("History",historyTrips.get(j).getName());
            Log.d("History",historyTrips.get(j).getStatus());
        }
        trips = historyTrips;
        setMyAdapterAfterAllHistory();
        myAdapter = new TripAdapter(this.getContext(),trips);
        setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }

}
    private void setMyAdapterAfterAllHistory() {

        Log.d("History","------------------6-htrips-All-----------------");
        for (int i=0 ; i<hTrips.size(); i++){
            Log.d("History",hTrips.get(i).getName());
            Log.d("History",hTrips.get(i).getStatus());
        }
        Log.d("History","------------------7-trips-All-----------------");
        for (int i=0 ; i<trips.size(); i++){
            Log.d("History",trips.get(i).getName());
            Log.d("History",trips.get(i).getStatus());
        }
    }
}
