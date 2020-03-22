package com.example.tripandroidproject.Presenter.Trip;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tripandroidproject.Contract.Trip.RetrieveTripContract;
import com.example.tripandroidproject.Model.Firebase.FirebaseTripModel;
import com.example.tripandroidproject.POJOs.Trip;
import java.util.List;

public class RetrieveTripPresenter implements RetrieveTripContract.IRetrieveTripPresenter {
    List<Trip> input;
    RetrieveTripContract.IRetrieveTripModel model;
    RetrieveTripContract.IRetrieveTripView view;
    Context context;
    RecyclerView.Adapter myAdapter;

    public RetrieveTripPresenter(Context context, RetrieveTripContract.IRetrieveTripView view){
        this.context = context;
        this.view = view;
        model = new FirebaseTripModel(context,this);
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
