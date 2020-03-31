package com.example.tripandroidproject.View.HistoryMapActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tripandroidproject.Contract.Trip.RetrieveTripContract;
import com.example.tripandroidproject.POJOs.Trip;
import com.example.tripandroidproject.Presenter.Trip.RetrieveTripPresenter;
import com.example.tripandroidproject.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class HistoryMapActivity extends AppCompatActivity implements OnMapReadyCallback, RetrieveTripContract.IRetrieveTripView {
    GoogleMap map;
    Polyline polyline = null;
    Button mapBtn;
    MarkerOptions place1,place2;
    List<LatLng> latLngList = new ArrayList<>();
    List<Marker> markerList = new ArrayList<>();
    private RetrieveTripPresenter retrieveTripPresenter;
    List<Trip> finishedTrips;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_map);
        retrieveTripPresenter = new RetrieveTripPresenter(this, this);
        retrieveTripPresenter.retrieveFilteredTrips("finished");
//        for (int i = 0; i < finishedTrips.size(); i++){
//            Toast.makeText(getApplicationContext(), finishedTrips.get(i).getName(), Toast.LENGTH_LONG).show();
//    }

        mapBtn = findViewById(R.id.mapBtn);
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.mapFrag);
        mapFragment.getMapAsync(this);
//        place1 = new MarkerOptions().position(new LatLng(31.06391939999999,29.8144185)).title("place1");
//        place2 = new MarkerOptions().position(new LatLng(30.0609444,31.2234375)).title("place2");






        mapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(polyline != null){
//                    polyline.remove();}
//                    PolylineOptions polyLineOptions = new PolylineOptions()
//                            .addAll(latLngList).clickable(true);
                Polyline polyline1 = map.addPolyline(new PolylineOptions()
                        .clickable(true)
                        .add(
                                new LatLng(31.06391939999999,29.8144185),
//                                new LatLng(-34.747, 145.592),
//                                new LatLng(-34.364, 147.891),
//                                new LatLng(-33.501, 150.217),
//                                new LatLng(-32.306, 149.248),
                                new LatLng(30.0609444,31.2234375)));
                Polyline polyline2 = map.addPolyline(new PolylineOptions()
                        .clickable(true)
                        .add(
                                new LatLng(31.06391939999999,29.8144185),
//                                new LatLng(-34.747, 145.592),
//                                new LatLng(-34.364, 147.891),
//                                new LatLng(-33.501, 150.217),
//                                new LatLng(-32.306, 149.248),
                                new LatLng(29.991216,31.128639)));


                Polyline polyline3 = map.addPolyline(new PolylineOptions()
                        .clickable(true)
                        .add(
                                new LatLng(30.0602316,31.3373878),
//                                new LatLng(-34.747, 145.592),
//                                new LatLng(-34.364, 147.891),
//                                new LatLng(-33.501, 150.217),
//                                new LatLng(-32.306, 149.248),
                                new LatLng(31.06391939999999,29.8144185)));

// Store a data object with the polyline, used here to indicate an arbitrary type.
                polyline1.setTag("A");

                LatLngBounds latLngBounds = new LatLngBounds.Builder()
                        .include(new LatLng(26.8205528, 30.8024979))
                        .include(new LatLng(30.0609444,31.2234375))
                        .build();
                 LatLngBounds AUSTRALIA = new LatLngBounds(
                        new LatLng(26.8205528, 30.8024979), new LatLng(30.0609444,31.2234375));

                map.moveCamera(CameraUpdateFactory.newLatLngZoom(AUSTRALIA.getCenter(), 5));

               // map.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 10));

              //  map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(30.0609444,31.2234375), 10));
//                CameraPosition cameraPosition = new CameraPosition.Builder()
//                        .target(MOUNTAIN_VIEW)      // Sets the center of the map to Mountain View
//                        .zoom(17)                   // Sets the zoom
//                        .bearing(90)                // Sets the orientation of the camera to east
//                        .tilt(30)                   // Sets the tilt of the camera to 30 degrees
//                        .build();                   // Creates a CameraPosition from the builder
//                map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));




            }
        });

//        if (polyline != null)
//            polyline.remove();
//        for (Marker marker : markerList) marker.remove();
//        latLngList.clear();
//        markerList.clear();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
        @Override
        public void onMapClick(LatLng latLng) {
            MarkerOptions markerOptions = new MarkerOptions().position(latLng);
            Marker marker = map.addMarker(markerOptions);
            latLngList.add(latLng);
            markerList.add(marker);


        }
    });

    }

    public void setAdapter(RecyclerView.Adapter myAdapter) {

    }

    @Override
    public void renderData(List<Trip> trips) {
        finishedTrips = trips;
        for (int i = 0; i < trips.size(); i++){
            Toast.makeText(getApplicationContext(), trips.get(i).getName(), Toast.LENGTH_LONG).show();
        }

    }
}
