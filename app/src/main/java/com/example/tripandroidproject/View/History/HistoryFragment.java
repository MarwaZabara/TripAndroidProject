package com.example.tripandroidproject.View.History;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tripandroidproject.Contract.Trip.RetrieveTripContract;
import com.example.tripandroidproject.POJOs.Trip;
import com.example.tripandroidproject.Presenter.Trip.RetrieveTripPresenter;
import com.example.tripandroidproject.R;
import com.example.tripandroidproject.View.HistoryMapActivity.HistoryMapActivity;
import com.example.tripandroidproject.View.NavDrawer_UpComingTrip.TripAdapter;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HistoryFragment extends Fragment implements RetrieveTripContract.IRetrieveTripView {

    private RecyclerView.Adapter myAdapter;
    private RecyclerView recyclerView;
    private List<Trip> trips;
    private Trip trip;
    List<LatLng> startList = new ArrayList<>();
    List<LatLng> endList = new ArrayList<>();

    RetrieveTripContract.IRetrieveTripPresenter retrieveTripPresenter;
    FloatingActionButton OpenMapBtn;



    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.historyfragment, container, false);
        OpenMapBtn = (FloatingActionButton) view.findViewById(R.id.OpenMapBtn);
        OpenMapBtn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
//                  new Thread( new Runnable() {
//                      @Override
//                      public void run() {
//                          for (int i = 0; i < trips.size(); i++) {
////                              startList.add
////                              startList.add(i,new LatLng(trips.get(i).getStartLatitude(),trips.get(i).getStartLongitude()));
////                              endList.add(i,new LatLng(trips.get(i).getEndLatitude(),trips.get(i).getEndLongitude()));
//                          }
//                      }
//                  }).start();
                  Intent intent = new Intent(getActivity(), HistoryMapActivity.class);
                  intent.putExtra("Trips",(Serializable)trips);
                  //intent.putExtra("EndList",(Serializable)endList);

                  startActivity(intent);

              }

          });
        recyclerView = view.findViewById(R.id.recycleViewHistory);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        trips = new ArrayList<>();
        myAdapter = new HistoryAdapter(getContext(),trips);
        retrieveTripPresenter = new RetrieveTripPresenter(getContext(),this);
        retrieveTripPresenter.fetchData("Cancel","finished");
//        retrieveTripPresenter.retrieveFilteredTrips("Cancel");
        myAdapter.notifyDataSetChanged();
        renderData(trips);

        return view;
    }

    public void setAdapter(RecyclerView.Adapter myAdapter) {
        this.myAdapter = myAdapter;
        this.myAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(myAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void renderData(List<Trip> trips) {
        myAdapter = new TripAdapter(this.getContext(),trips);
        setAdapter(myAdapter);
        this.trips=trips;
    }

}
