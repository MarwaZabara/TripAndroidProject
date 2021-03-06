package com.example.tripandroidproject.View.NavDrawer_UpComingTrip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tripandroidproject.Broadcast.NetworkChangeBroadcast.ControlNetworkChangeBroadcast;
import com.example.tripandroidproject.Model.Room.RoomRepeatedTripHistoryModel;
import com.example.tripandroidproject.POJOs.RepeatedTripHistory;
import com.example.tripandroidproject.Model.Room.RoomNoteModel;
import com.example.tripandroidproject.Model.Room.RoomTripModel;
import com.example.tripandroidproject.POJOs.Note;
import com.example.tripandroidproject.POJOs.Person;
import com.example.tripandroidproject.Presenter.User.UserPresenter;
import com.example.tripandroidproject.View.AddTrip.AddTripActivity;
import com.example.tripandroidproject.POJOs.Trip;

import com.example.tripandroidproject.R;
import com.example.tripandroidproject.View.History.HistoryFragment;
import com.example.tripandroidproject.View.Login.LoginActivity;
import com.example.tripandroidproject.View.Profile.Profile;
import com.example.tripandroidproject.View.Repeated_NonRepeated.Non_RepeatedFragment;
import com.example.tripandroidproject.View.Repeated_NonRepeated.RepeatedFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NavDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    TextView name,email,fragTitle;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    private CustomViewPager viewPager;
    Person person;
    UserPresenter userPresenter;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ControlNetworkChangeBroadcast.registerBroadcast(this);
        setContentView(R.layout.activity_nav_drawer);
        fragTitle = findViewById(R.id.frag_title);
//////////////////////////////////////////////////////////////////
        viewPager = (CustomViewPager) findViewById(R.id.container);
        viewPager.setPagingEnabled(false);
        fragTitle.setText("UpComing Trip");
/////////////////////////////////////////////////////////////////
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        setPersonInfo();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawerOpen,R.string.drawerClose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        userPresenter = new UserPresenter(this);
        person = userPresenter.getUser();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupViewPager(viewPager);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.profile:
                setViewPager(4);
                drawerLayout.closeDrawers();
                fragTitle.setText("");
                break;
            case R.id.upComingTrip:
                setViewPager(0);
                drawerLayout.closeDrawers();
                fragTitle.setText("UpComing Trip");
                break;
            case R.id.history:
                Toast.makeText(NavDrawer.this, "History us Selected", Toast.LENGTH_SHORT).show();
                setViewPager(1);
                fragTitle.setText("History");
                drawerLayout.closeDrawers();
                break;
            case R.id.logout:
                Toast.makeText(NavDrawer.this, "Logout Selected", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                clearRoom();
                mGoogleSignInClient.signOut()
                        .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                // ...
                            }
                        });
                Intent intentLoginActivity = new Intent(this, LoginActivity.class);
                startActivity(intentLoginActivity);
                break;
            default:
                break;
        }
        return false;
    }

    private void clearRoom() {
        if(person == null)
        {
            person = userPresenter.getUser();
        }

        RoomTripModel roomTripModel = new RoomTripModel(this);
        RoomNoteModel roomNoteModel = new RoomNoteModel(this);
        RoomRepeatedTripHistoryModel roomRepeatedTripHistoryModel = new RoomRepeatedTripHistoryModel(this);
        List<Trip> trips = roomTripModel.getAllOfflineTrip();
        for (int i = 0 ; i<trips.size();i++)
        {
            roomTripModel.deleteOfflineTrip(trips.get(i));
            List<Note> notes = roomNoteModel.getNotes(trips.get(i).getId());
            for (int j=0;j<notes.size();j++) {
                roomNoteModel.deleteNote(notes.get(j));
            }
        }
        List<RepeatedTripHistory> repeatedTripHistories = roomRepeatedTripHistoryModel.getRepeatedHistory();
        for (int i = 0 ; i<repeatedTripHistories.size();i++)
        {
            roomRepeatedTripHistoryModel.deleteOfflineTrip(repeatedTripHistories.get(i));
        }
        userPresenter.deleteUser(person);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu_filter) {
        getMenuInflater().inflate(R.menu.menu_filter,menu_filter);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.allTrips:
                setViewPager(0);
                fragTitle.setText("UpComing Trip");
                break;
            case R.id.repeated:
                setViewPager(2);
                fragTitle.setText("Repeated Trip");
                break;
            case R.id.unrepeated:
                setViewPager(3);
                fragTitle.setText("UnRepeated Trip");
                break;
            default:
                break;
        }
        return true;
    }

    public void setPersonInfo(){
        View hView =  navigationView.getHeaderView(0);
        email = (TextView)hView.findViewById(R.id.navEmail);
        name  = (TextView) hView.findViewById(R.id.navName);
        final ImageView imageView = (ImageView) hView.findViewById(R.id.profilePic);
        UserPresenter userPresenter = new UserPresenter(this);
        Person person = userPresenter.getUser();
        Intent intent = getIntent();
        String pass = intent.getStringExtra("password");
        email.setText(person.getEmail());
        name.setText(person.getName());
        String imageUri = person.getImgUri();
        String imgPath = intent.getStringExtra("imgPath");
        if(person.getFirebasePhotoPath() != null) {
            if (person.getPassword() != null) {
                mStorageRef = FirebaseStorage.getInstance().getReference();
                Uri uri = Uri.parse(person.getFirebasePhotoPath());
                StorageReference ref = FirebaseStorage.getInstance().getReference().child("images/" + person.getFirebasePhotoPath());
                final long ONE_MEGABYTE = 1024 * 1024;
                ref.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytesPrm) {
                        Bitmap bmp = BitmapFactory.decodeByteArray(bytesPrm, 0, bytesPrm.length);
                        imageView.setImageBitmap(bmp);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        imageView.setImageResource(R.mipmap.ic_launcher);
                    }
                });
            } else {
                Picasso.get().load(person.getFirebasePhotoPath()).resize(120, 120).centerCrop().into(imageView);
            }
        }
    }


    public void GoToAddTrip(View view) {
        Intent intent = new Intent(NavDrawer.this, AddTripActivity.class);
        startActivity(intent);
    }

    private void setupViewPager(ViewPager viewPager1){
        AppAdapter adapter = new AppAdapter(getSupportFragmentManager());
        adapter.addFragment(new UpComingFragment());            //// fragmentNum --> 0
        adapter.addFragment(new HistoryFragment());            //// fragmentNum --> 1
        adapter.addFragment(new RepeatedFragment());          //// fragmentNum --> 2
        adapter.addFragment(new Non_RepeatedFragment());    //// fragmentNum --> 3
        adapter.addFragment(new Profile());                //// fragmentNum --> 4

        viewPager1.setAdapter(adapter);
    }

    private void setViewPager(int fragmentNum) {
        viewPager.setCurrentItem(fragmentNum);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ControlNetworkChangeBroadcast.unregisterReceiver(this);
    }
}
