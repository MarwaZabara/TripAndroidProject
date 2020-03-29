package com.example.tripandroidproject.Presenter.Trip;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tripandroidproject.Contract.Trip.RetrieveTripContract;
import com.example.tripandroidproject.Model.Firebase.FirebaseTripModel;
import com.example.tripandroidproject.POJOs.Trip;
import com.example.tripandroidproject.Presenter.Note.GetNotePresenter;

import java.util.List;

public class RetrieveTripPresenter implements RetrieveTripContract.IRetrieveTripPresenter {
    List<Trip> input;
    RetrieveTripContract.IRetrieveTripModel model;
    RetrieveTripContract.IRetrieveTripView view;
    GetNotePresenter getNotePresenter;
    Context context;

    public RetrieveTripPresenter(Context context, RetrieveTripContract.IRetrieveTripView view){
        this.context = context;
        this.view = view;
        model = new FirebaseTripModel(context,this);
    }

    @Override
    public void retrieveUpcomingTrips() {
        model.fetchData();
    }

    @Override
    public void retrieveFilteredTrips(String filter) {
        model.fetchFilteredData(filter);
    }

    @Override
    public void fetchData(String filter1, String filter2) {
        model.fetchData(filter1,filter2);
    }

    @Override
    public void onSuccessGetUpcomingTrips(List<Trip> trips) {
        view.renderData(trips);
        SaveTripPresenter saveTripPresenter = new SaveTripPresenter(context);

        for (int i = 0 ; i < trips.size() ; i++){
            saveTripPresenter.saveTrip(trips.get(i),true);
            getNotePresenter = new GetNotePresenter(context,true);
            getNotePresenter.getUpComingNotes(trips.get(i).getId(),context);
        }
    }

    @Override
    public void onSucess() {
    }

    @Override
    public void onFail() {

    }

    @Override
    public List<Trip> getTripList() {
        return input;
    }
}
