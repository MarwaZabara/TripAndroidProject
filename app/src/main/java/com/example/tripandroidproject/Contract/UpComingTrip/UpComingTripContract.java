package com.example.tripandroidproject.Contract.UpComingTrip;

import android.content.Context;

import com.example.tripandroidproject.Contract.Base.IBase;
import com.example.tripandroidproject.POJOs.Trip;

import java.util.List;

public class UpComingTripContract {
    public interface IUpComingTripView{
        void getTripList (List<Trip> tripList);
    }
    public interface IUpComingTripPresenter extends IBase {

    }
    public interface IUpComingTripModel{
        void fetchData();
        List<Trip> returnData();
    }
}
