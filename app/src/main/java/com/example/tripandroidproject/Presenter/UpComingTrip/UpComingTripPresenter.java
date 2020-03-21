package com.example.tripandroidproject.Presenter.UpComingTrip;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tripandroidproject.Contract.UpComingTrip.UpComingTripContract;
import com.example.tripandroidproject.Model.UpComingTrip.UpComingTripModel;
import com.example.tripandroidproject.POJOs.Trip;

import java.util.List;

public class UpComingTripPresenter implements UpComingTripContract.IUpComingTripPresenter {
    List<Trip> input;
    UpComingTripContract.IUpComingTripModel model;
    UpComingTripContract.IUpComingTripView view;
    Context context;
    RecyclerView.Adapter myAdapter;

    public UpComingTripPresenter(Context context, UpComingTripContract.IUpComingTripView view, String id){
        this.context = context;
        this.view = view;
        model = new UpComingTripModel(context,this,id);
        model.fetchData();
    }

    @Override
    public void onSucess() {
        input = model.returnData();
        myAdapter = model.returnAdapter();
        view.setAdapter(myAdapter);
    }

    @Override
    public void onFail() {

    }

    @Override
    public List<Trip> getTripList() {
        return input;
    }
}
