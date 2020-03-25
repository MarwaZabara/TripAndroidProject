package com.example.tripandroidproject.Contract.Trip;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tripandroidproject.Contract.Base.IBase;
import com.example.tripandroidproject.POJOs.Trip;

import java.util.List;

public class RetrieveTripContract {
    public interface IRetrieveTripView{
        void setAdapter(RecyclerView.Adapter myAdapter);
        public void renderData(List<Trip> trips);
//        RecyclerView.Adapter getAdapter();
    }
    public interface IRetrieveTripPresenter extends IBase {
        List<Trip> getTripList ();
        public void retrieveUpcomingTrips();
        public void onSuccessGetUpcomingTrips(List<Trip> trips);
    }
    public interface IRetrieveTripModel{
        void fetchData();
        List<Trip> returnData();
        RecyclerView.Adapter returnAdapter();
    }
}
