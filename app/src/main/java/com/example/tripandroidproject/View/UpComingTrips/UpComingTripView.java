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
        presenter = new UpComingTripPresenter(context,this,"f4orss9YQbSRMaVpOOscKWYH7S13");
    }

    public List<Trip> getData(){
//        input = Arrays.asList(
//                new Trip("1","FirstTrip","Alexandria","UpComing","22/5/2020","09:00AM"),
//                new Trip("2","SecondTrip","Cairo","UpComing","20/5/2020","11:00AM"),
//                new Trip("3","ThirdTrip","Luxor","UpComing","19/5/2020","08:00pM"),
//                new Trip("4","ForthTrip","Aswan","UpComing","2/5/2020","10:00AM"));
        input = presenter.getTripList();
        return input;
    }


}
