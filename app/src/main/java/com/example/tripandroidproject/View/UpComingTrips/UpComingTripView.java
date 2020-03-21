package com.example.tripandroidproject.View.UpComingTrips;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tripandroidproject.Contract.UpComingTrip.UpComingTripContract;
import com.example.tripandroidproject.POJOs.Trip;
import com.example.tripandroidproject.Presenter.UpComingTrip.UpComingTripPresenter;

import java.util.Arrays;
import java.util.List;

public class UpComingTripView implements UpComingTripContract.IUpComingTripView {
    //    private RecyclerView recyclerView;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Trip> input;
    private Context context;
    private UpComingTripPresenter presenter;

    public UpComingTripView(Context context){
        this.context = context;
        presenter = new UpComingTripPresenter(this.context,this,"f4orss9YQbSRMaVpOOscKWYH7S13");
    }

    public List<Trip> getData(){
        input = presenter.getTripList();
        return input;
    }


    @Override
    public void setAdapter(RecyclerView.Adapter myAdapter) {
        this.myAdapter = myAdapter;
    }

    @Override
    public RecyclerView.Adapter getAdapter() {
        return myAdapter;
    }
}
