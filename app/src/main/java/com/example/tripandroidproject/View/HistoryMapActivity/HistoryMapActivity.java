package com.example.tripandroidproject.View.HistoryMapActivity;
//
//import android.graphics.Point;
//import android.os.Bundle;
//
//import com.ahmadrosid.lib.drawroutemap.DrawRouteMaps;
//import com.example.tripandroidproject.POJOs.Trip;
//import com.example.tripandroidproject.R;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.MapFragment;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.model.BitmapDescriptorFactory;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.LatLngBounds;
//import com.google.android.gms.maps.model.Marker;
//import com.google.android.gms.maps.model.MarkerOptions;
//
//import java.util.List;
//
//import androidx.appcompat.app.AppCompatActivity;
//
////TaskLoadedCallback
//public class HistoryMapActivity extends AppCompatActivity implements OnMapReadyCallback {
//    GoogleMap map;
//    LatLng origin, destination;
//    MarkerOptions originMO, destMO;
//    Marker originMarker, destMarker;
//    String c = "#ff3636";
//    //    List<LatLng> startList = new ArrayList<>();
////    List<LatLng> endList = new ArrayList<>();
////    List<Marker> markerList = new ArrayList<>();
//    List<Trip> finishedTrips;
//    double startLat, startLong, endLat, endLong;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_history_map);
//      //  Toast.makeText(getApplicationContext(), "in activity result", Toast.LENGTH_LONG).show();
//        finishedTrips = (List<Trip>) getIntent().getSerializableExtra("Trips");
//        // endList = (List<LatLng>) getIntent().getSerializableExtra("EndList");
//       // Toast.makeText(getApplicationContext(), finishedTrips.size() + "", Toast.LENGTH_LONG).show();
//       // for (int i = 0; i < finishedTrips.size(); i++) {
//          //  Toast.makeText(getApplicationContext(), finishedTrips.get(i).getStatus() + "", Toast.LENGTH_LONG).show();
//
//      //  }
//
//        MapFragment mapFragment = (MapFragment) getFragmentManager()
//                .findFragmentById(R.id.mapFrag);
//        mapFragment.getMapAsync(this);
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        map = googleMap;
//        if (finishedTrips != null) {
//            DrawRouteMaps drawRouteMaps = DrawRouteMaps.getInstance(HistoryMapActivity.this, "AIzaSyCVOvMSNN18_AJKQjfKfoWKxsYNF5GNxK0");
//            LatLngBounds bounds = null;
//            for (int i = 0; i < finishedTrips.size(); i++) {
//                startLat = finishedTrips.get(i).getStartLatitude();
//                startLong = finishedTrips.get(i).getStartLongitude();
//                endLat = finishedTrips.get(i).getEndLatitude();
//                endLong = finishedTrips.get(i).getEndLongitude();
//                final String Name = finishedTrips.get(i).getName();
//                LatLng origin = new LatLng(startLat, startLong);
//                LatLng destination = new LatLng(endLat, endLong);
//                originMO = new MarkerOptions().position(new LatLng(startLat, startLong)).title("origin");
//                destMO = new MarkerOptions().position(new LatLng(endLat, endLong)).title("dest");
//                originMarker = map.addMarker(originMO);
//                originMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
//                destMarker = map.addMarker(destMO);
//                destMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
//                drawRouteMaps.draw(origin, destination, map);
//                bounds = bounds.builder()
//                        .include(origin)
//                        .include(destination).build();
//                Point displaySize = new Point();
//                getWindowManager().getDefaultDisplay().getSize(displaySize);
//                map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, displaySize.x, 250, 30));
//
//            }
//        }
//
//
//
//    }
//
//    }
//

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;

import com.example.tripandroidproject.POJOs.Trip;
import com.example.tripandroidproject.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;



//import com.ahmadrosid.lib.drawroutemap.DrawMarker;
//import com.ahmadrosid.lib.drawroutemap.DrawRouteMaps;

public class HistoryMapActivity extends FragmentActivity implements OnMapReadyCallback,TaskLoadedCallback {

    private GoogleMap mMap;
    LatLng origin;
    LatLng destination;
    LatLngBounds bounds;
    public MutableLiveData<List<Trip>> trips = new MutableLiveData<>();
    Context context = this;
    MarkerOptions originMO, destMO;
    Marker originMarker, destMarker;
        List<Trip> finishedTrips;
//

    List<Integer> colors = Arrays.asList(Color.RED,Color.BLUE,Color.GREEN,Color.MAGENTA,Color.BLACK,Color.CYAN,Color.DKGRAY,Color.GRAY,Color.LTGRAY,Color.YELLOW,Color.WHITE);
    private Polyline currentPolyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFrag);
        mapFragment.getMapAsync(this);
        finishedTrips = (List<Trip>) getIntent().getSerializableExtra("Trips");
        trips.setValue(finishedTrips);



    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */




    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;


        trips.observe(this, new Observer<List<Trip>>() {
            @Override
            public void onChanged(List<Trip> trips) {
                for (Trip trip: trips) {
                    origin = new LatLng(trip.getStartLatitude(),trip.getStartLongitude());
                    destination = new LatLng(trip.getEndLatitude(),trip.getEndLongitude());
                    bounds = new LatLngBounds.Builder()
                            .include(origin)
                            .include(destination).build();
                    originMO = new MarkerOptions().position(origin).title("origin");
                destMO = new MarkerOptions().position(destination).title("dest");
                originMarker = mMap.addMarker(originMO);
                originMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                destMarker = mMap.addMarker(destMO);
                destMarker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
//                    DrawMarker.getInstance(context).draw(mMap, origin, R.drawable.marker_a, trip.getStartPoint());
//                    DrawMarker.getInstance(context).draw(mMap, destination, R.drawable.marker_b, trip.getEndPoint());
                    new FetchURL(context).execute(getUrl(origin,destination,"driving"),"driving");
                }
                Point displaySize = new Point();
                getWindowManager().getDefaultDisplay().getSize(displaySize);
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, displaySize.x, displaySize.y, 30));
            }
        });

    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + "AIzaSyCVOvMSNN18_AJKQjfKfoWKxsYNF5GNxK0";
        return url;
    }


    public int colorsReturnARandomElement(List<Integer> colors) {
        Random rand = new Random();
        int randomElement = colors.get(rand.nextInt(colors.size()));
        return randomElement;
    }

    @Override
    public void onTaskDone(Object... values) {
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
        currentPolyline.setColor(colorsReturnARandomElement(colors));
        currentPolyline.setWidth(5);

    }
}
