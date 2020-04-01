package com.example.tripandroidproject.View.HistoryMapActivity;

import android.graphics.Point;
import android.os.Bundle;

import com.ahmadrosid.lib.drawroutemap.DrawRouteMaps;
import com.example.tripandroidproject.POJOs.Trip;
import com.example.tripandroidproject.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

//TaskLoadedCallback
public class HistoryMapActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap map;
    LatLng origin, destination;
    MarkerOptions originMO, destMO;
    Marker originMarker, destMarker;
    String c = "#ff3636";
    //    List<LatLng> startList = new ArrayList<>();
//    List<LatLng> endList = new ArrayList<>();
//    List<Marker> markerList = new ArrayList<>();
    List<Trip> finishedTrips;
    double startLat, startLong, endLat, endLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_map);
      //  Toast.makeText(getApplicationContext(), "in activity result", Toast.LENGTH_LONG).show();
        finishedTrips = (List<Trip>) getIntent().getSerializableExtra("Trips");
        // endList = (List<LatLng>) getIntent().getSerializableExtra("EndList");
       // Toast.makeText(getApplicationContext(), finishedTrips.size() + "", Toast.LENGTH_LONG).show();
       // for (int i = 0; i < finishedTrips.size(); i++) {
          //  Toast.makeText(getApplicationContext(), finishedTrips.get(i).getStatus() + "", Toast.LENGTH_LONG).show();

      //  }

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.mapFrag);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        if (finishedTrips != null) {
            DrawRouteMaps drawRouteMaps = DrawRouteMaps.getInstance(HistoryMapActivity.this, "AIzaSyCVOvMSNN18_AJKQjfKfoWKxsYNF5GNxK0");
            LatLngBounds bounds = null;
            for (int i = 0; i < finishedTrips.size(); i++) {
                startLat = finishedTrips.get(i).getStartLatitude();
                startLong = finishedTrips.get(i).getStartLongitude();
                endLat = finishedTrips.get(i).getEndLatitude();
                endLong = finishedTrips.get(i).getEndLongitude();
                final String Name = finishedTrips.get(i).getName();
                LatLng origin = new LatLng(startLat, startLong);
                LatLng destination = new LatLng(endLat, endLong);
                originMO = new MarkerOptions().position(new LatLng(startLat, startLong)).title("origin");
                destMO = new MarkerOptions().position(new LatLng(endLat, endLong)).title("dest");
                originMarker = map.addMarker(originMO);
                originMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                destMarker = map.addMarker(destMO);
                destMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                drawRouteMaps.draw(origin, destination, map);
                bounds = bounds.builder()
                        .include(origin)
                        .include(destination).build();
                Point displaySize = new Point();
                getWindowManager().getDefaultDisplay().getSize(displaySize);
                map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, displaySize.x, 250, 30));

            }
        }

    }

    @Override
    public void renderData(List<Trip> trips) {
        finishedTrips = trips;
        for (int i = 0; i < trips.size(); i++){
            Toast.makeText(getApplicationContext(), trips.get(i).getName(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void returnAllHistory(List<Trip> historyTrips) {

    }

}
