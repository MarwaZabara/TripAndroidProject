package com.example.tripandroidproject.AddTrip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.tripandroidproject.R;
import com.example.tripandroidproject.View.UnderTest.TestReminder;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;

public class AddTripActivity extends AppCompatActivity {
    String TAG = "Add";
    int AUTOCOMPLETE_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        Places.initialize(getApplicationContext(), "AIzaSyCVOvMSNN18_AJKQjfKfoWKxsYNF5GNxK0");

    }

    public void StartAutoCompleteActivity(View view) {
        Toast.makeText(getApplicationContext(),"in startAutoComplete",Toast.LENGTH_LONG).show();
        Intent i = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,
                Arrays.asList(Place.Field.ID,Place.Field.NAME,Place.Field.LAT_LNG))
                .setTypeFilter(TypeFilter.ADDRESS)
                .setCountries(Arrays.asList("BR","SR","GY","EG"))
                .build(AddTripActivity.this);
        startActivityForResult(i,AUTOCOMPLETE_REQUEST_CODE);

    }


    @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(getApplicationContext(), "in activity result", Toast.LENGTH_LONG).show();
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                Log.i(TAG, "longLat =  " + place.getLatLng().latitude);
               // Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), "Place: " + place.getName() , Toast.LENGTH_LONG).show();

                Toast.makeText(getApplicationContext(), "LONG = " + place.getLatLng().longitude, Toast.LENGTH_LONG).show();


            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "cancelled", Toast.LENGTH_LONG).show();

                // The user canceled the operation.
            }
        }
    }
}
