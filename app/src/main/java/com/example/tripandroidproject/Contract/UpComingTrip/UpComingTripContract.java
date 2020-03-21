package com.example.tripandroidproject.Contract.UpComingTrip;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tripandroidproject.Contract.Base.IBase;
import com.example.tripandroidproject.POJOs.Trip;

import java.util.List;

public class UpComingTripContract {
    public interface IUpComingTripView{
        void setAdapter(RecyclerView.Adapter myAdapter);
        RecyclerView.Adapter getAdapter();
    }
    public interface IUpComingTripPresenter extends IBase {
        List<Trip> getTripList ();
    }
    public interface IUpComingTripModel{
        void fetchData();
        List<Trip> returnData();
        RecyclerView.Adapter returnAdapter();
    }
}
