package com.example.tripandroidproject.View.AddTrip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.tripandroidproject.R;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;

public class AddTripActivity extends AppCompatActivity {
    String TAG = "Add";
    int AUTOCOMPLETE_REQUEST_CODE = 1;
    private EditText NameTxt,DescTxt,TripDateTxt,TripTimetxt,StartLocationTxt,DestinationTxt;
    private EditText RoundDateTxt,RoundTimeTxt,RepeatDayTxt,RepeatWeekTxt,RepeatMonthTxt;
    private boolean isRepeat,isRound;
    private Switch RepeatSwtch,RoundSwtch;
    String StartLong,StartLat,EndLong,EndLat;



//    private
//    SignInButton signInButton;
//    private UserDetails userDetails;
//    private GoogleSignInClient mGoogleSignInClient;
//    private static final int RC_SIGN_IN = 9001;
//    private LoginPresenter presenter;
//    private SaveUserLogIn saveUserLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        Places.initialize(getApplicationContext(), "AIzaSyCVOvMSNN18_AJKQjfKfoWKxsYNF5GNxK0");

    }
    // AutoComplete starts
    public void StartAutoCompleteActivity(View view) {
        Toast.makeText(getApplicationContext(),"in startAutoComplete",Toast.LENGTH_LONG).show();
        Intent i = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,
                Arrays.asList(Place.Field.ID,Place.Field.NAME,Place.Field.LAT_LNG))
                .setTypeFilter(TypeFilter.ADDRESS)
                .setCountries(Arrays.asList("EG"))
                .build(AddTripActivity.this);
        startActivityForResult(i,AUTOCOMPLETE_REQUEST_CODE);

    }

// after a place is selected or autocomplete cancelled
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
