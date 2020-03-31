package com.example.tripandroidproject.View.NavDrawer_UpComingTrip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tripandroidproject.Broadcast.NetworkChangeBroadcast.ControlNetworkChangeBroadcast;
import com.example.tripandroidproject.Broadcast.NetworkChangeBroadcast.NetworkChangeBroadcastReceiver;
import com.example.tripandroidproject.Contract.Trip.RetrieveTripContract;
import com.example.tripandroidproject.View.AddTrip.AddTripActivity;
import com.example.tripandroidproject.POJOs.Trip;
import com.example.tripandroidproject.Presenter.Trip.RetrieveTripPresenter;

import com.example.tripandroidproject.R;
import com.example.tripandroidproject.View.History.HistoryFragment;
import com.example.tripandroidproject.View.Login.LoginActivity;
import com.example.tripandroidproject.View.Profile.Profile;
import com.example.tripandroidproject.View.Repeated_NonRepeated.Non_RepeatedFragment;
import com.example.tripandroidproject.View.Repeated_NonRepeated.RepeatedFragment;
import com.example.tripandroidproject.View.SaveUserLogIn;
import com.example.tripandroidproject.View.UnderTest.TestReminder;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NavDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    TextView name,email;
    private FirebaseAuth mAuth;
    private SaveUserLogIn saveUserLogIn;
    private GoogleSignInClient mGoogleSignInClient;

    private ViewPager viewPager;
    private NetworkChangeBroadcastReceiver networkChangeBroadcastReceiver;


    TextView mItemSelected;

    String[] listItems =  {"dfsd","fsdfds"};
    boolean[] checkedItems;
    ArrayList<Integer> mUserItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ControlNetworkChangeBroadcast.registerBroadcast(this);
        setContentView(R.layout.activity_nav_drawer);
//////////////////////////////////////////////////////////////////
        viewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(viewPager);
/////////////////////////////////////////////////////////////////
        mAuth = FirebaseAuth.getInstance();
        saveUserLogIn = new SaveUserLogIn(this);
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
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawerOpen,R.string.drawerClose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        if(getIntent().getBooleanExtra("isFloatingService",false)){
            checkedItems  = new boolean[listItems.length];
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(NavDrawer.this);
            mBuilder.setTitle("Notes");
            mBuilder.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
//                        if (isChecked) {
//                            if (!mUserItems.contains(position)) {
//                                mUserItems.add(position);
//                            }
//                        } else if (mUserItems.contains(position)) {
//                            mUserItems.remove(position);
//                        }
                    if(isChecked){
                        mUserItems.add(position);
                    }else{
                        mUserItems.remove((Integer.valueOf(position)));
                    }
                }
            });

            mBuilder.setCancelable(false);
            mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    String item = "";
                    for (int i = 0; i < mUserItems.size(); i++) {
                        item = item + listItems[mUserItems.get(i)];
                        if (i != mUserItems.size() - 1) {
                            item = item + ", ";
                        }
                    }
                    mItemSelected.setText(item);
                }
            });

            mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            mBuilder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    for (int i = 0; i < checkedItems.length; i++) {
                        checkedItems[i] = false;
                        mUserItems.clear();
                        mItemSelected.setText("");
                    }
                }
            });

            AlertDialog mDialog = mBuilder.create();
            mDialog.show();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.profile:
                Toast.makeText(NavDrawer.this, "Profile Selected", Toast.LENGTH_SHORT).show();
                setViewPager(4);
                drawerLayout.closeDrawers();
                break;
            case R.id.upComingTrip:
                Toast.makeText(NavDrawer.this, "UpComingTrip us Selected", Toast.LENGTH_SHORT).show();
                setViewPager(0);
                drawerLayout.closeDrawers();
                break;
            case R.id.history:
                Toast.makeText(NavDrawer.this, "History us Selected", Toast.LENGTH_SHORT).show();
                setViewPager(1);
                drawerLayout.closeDrawers();
                break;
            case R.id.synch:
                Intent intent = new Intent(this, TestReminder.class);
                startActivity(intent);
                break;
            case R.id.logout:
                Toast.makeText(NavDrawer.this, "Logout Selected", Toast.LENGTH_SHORT).show();
                mAuth.signOut();
                mGoogleSignInClient.signOut()
                        .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                // ...
                            }
                        });
                saveUserLogIn.setUserLoggedIn(false);
                Intent intentLoginActivity = new Intent(this, LoginActivity.class);
                startActivity(intentLoginActivity);
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu_filter) {
        getMenuInflater().inflate(R.menu.menu_filter,menu_filter);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.repeated:
                Toast.makeText(NavDrawer.this, "Repeated Selected", Toast.LENGTH_SHORT).show();
                setViewPager(2);
                break;
            case R.id.unrepeated:
                Toast.makeText(NavDrawer.this, "UnRepeated us Selected", Toast.LENGTH_SHORT).show();
                setViewPager(3);
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
        ImageView imageView = (ImageView) hView.findViewById(R.id.profilePic);

        Intent intent = getIntent();
        String pass = intent.getStringExtra("password");
        email.setText(intent.getStringExtra("Email"));
        name.setText(intent.getStringExtra("Name"));
        String imageUri = intent.getStringExtra("imgUri");
        String imgPath = intent.getStringExtra("imgPath");
//        String userImageUri = intent.getStringExtra("userImgUri");
        if (pass == null & imageUri!=null) {
            ///// it's image url
//            Picasso.get().load(imageUri).resize(120, 120).centerCrop().into(imageView);
        }else if(imageUri != null) {
            imageView.setImageURI(Uri.parse(imageUri));
//            imageView.setImageBitmap(BitmapFactory.decodeFile(imgPath));
//            imageView.setImageURI(Uri.parse(imgPath));
        }
    }


    public void GoToAddTrip(View view) {
        Intent intent = new Intent(NavDrawer.this, AddTripActivity.class);
        startActivity(intent);
    }

    private void setupViewPager(ViewPager viewPager1){
        AppAdapter adapter = new AppAdapter(getSupportFragmentManager());
        adapter.addFragment(new UpComingFragment(),"UpComingTrips");   //// fragmentNum --> 0
        adapter.addFragment(new HistoryFragment(),"History");          //// fragmentNum --> 1
        adapter.addFragment(new RepeatedFragment(),"RepeatedTrips");   //// fragmentNum --> 2
        adapter.addFragment(new Non_RepeatedFragment(),"NonRepeatedTrips");          //// fragmentNum --> 3
        adapter.addFragment(new Profile(),"Profile");           //// fragmentNum --> 4

        viewPager1.setAdapter(adapter);
    }

    private void setViewPager(int fragmentNum) {
        viewPager.setCurrentItem(fragmentNum);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        ControlNetworkChangeBroadcast.unregisterReceiver(this);
    }
}
