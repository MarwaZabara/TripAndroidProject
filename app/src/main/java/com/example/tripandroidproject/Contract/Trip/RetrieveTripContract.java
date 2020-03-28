package com.example.tripandroidproject.Contract.Trip;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tripandroidproject.Contract.Base.IBase;
import com.example.tripandroidproject.POJOs.Trip;

import java.util.List;

public class RetrieveTripContract {
    public interface IRetrieveTripView{
        void setAdapter(RecyclerView.Adapter myAdapter);
        void renderData(List<Trip> trips);
    }
    public interface IRetrieveTripPresenter extends IBase {
        List<Trip> getTripList ();
        void retrieveUpcomingTrips();
        void retrieveFilteredTrips(String filter);
        void onSuccessGetUpcomingTrips(List<Trip> trips);
    }
    public interface IRetrieveTripModel{
        void fetchData();
        void fetchFilteredData(String filter);
        List<Trip> returnData();
        List<Trip> returnRepeatedData();
        List<Trip> returnNonRepeatedData();
    }
}
