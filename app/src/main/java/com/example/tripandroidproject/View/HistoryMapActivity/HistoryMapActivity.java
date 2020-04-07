package com.example.tripandroidproject.View.HistoryMapActivity;

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

    // List<Integer> colors = Arrays.asList(Color.RED, Color.BLUE, Color.GREEN, Color.MAGENTA, Color.BLACK, Color.CYAN, Color.DKGRAY, Color.GRAY, Color.LTGRAY, Color.YELLOW, Color.WHITE);
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
                for (Trip trip : trips) {
                    origin = new LatLng(trip.getStartLatitude(), trip.getStartLongitude());
                    destination = new LatLng(trip.getEndLatitude(), trip.getEndLongitude());
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
                    new FetchURL(context).execute(getUrl(origin, destination, "driving"), "driving");
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


//    public int colorsReturnARandomElement(List<Integer> colors) {
//        Random rand = new Random();
//        int randomElement = colors.get(rand.nextInt(colors.size()));
//        return randomElement;
//    }

    @Override
    public void onTaskDone(Object... values) {
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
        currentPolyline.setColor(colorsReturnARandomElement());
        currentPolyline.setWidth(5);

    }

    public int colorsReturnARandomElement() {

        Random rnd = new Random();
        int r = rnd.nextInt(256);
        int g = rnd.nextInt(256);
        int b = rnd.nextInt(256);
        int rndColor= Color.argb(255, r,g,b);
        return rndColor;
    }
}