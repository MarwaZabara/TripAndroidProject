package com.example.tripandroidproject.Contract.Trip;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tripandroidproject.Contract.Base.IBase;
import com.example.tripandroidproject.POJOs.Trip;

import java.util.List;

public class RetrieveTripContract {
    public interface IRetrieveTripView{
        void renderData(List<Trip> trips);
    }
    public interface IRetrieveTripPresenter extends IBase {
        List<Trip> getTripList ();
        void retrieveUpcomingTrips();
        void retrieveFilteredTrips(String filter);
        void fetchData(String filter1,String filter2);
        void onSuccessGetUpcomingTrips(List<Trip> trips);
    }
    public interface IRetrieveTripModel{
        void fetchData();
        void fetchFilteredData(String filter);
        void fetchData(String filter1,String filter2);
        List<Trip> returnData();
//        List<Trip> returnRepeatedData();
//        List<Trip> returnNonRepeatedData();
    }
}
