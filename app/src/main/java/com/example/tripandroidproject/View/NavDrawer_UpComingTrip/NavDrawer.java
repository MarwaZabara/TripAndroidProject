package com.example.tripandroidproject.View.NavDrawer_UpComingTrip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tripandroidproject.AddTrip.AddTripActivity;
import com.example.tripandroidproject.R;
import com.example.tripandroidproject.View.History.HistoryFragment;
import com.example.tripandroidproject.View.Login.LoginActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.profile:
                Toast.makeText(NavDrawer.this, "Profile Selected", Toast.LENGTH_SHORT).show();
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
//                startActivity(new Intent(NavDrawer.this, HistoryActivity.class));
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
//                saveUserLogIn.clearUserData();
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
                break;
            case R.id.unrepeated:
                Toast.makeText(NavDrawer.this, "UnRepeated us Selected", Toast.LENGTH_SHORT).show();

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
        if (pass == null) {
            Picasso.get().load(imageUri).resize(120, 120).centerCrop().into(imageView);
        }else {
            imageView.setImageURI(Uri.parse(imageUri));
//            Picasso.get().load("http://www.google.co.in/"+imageUri).resize(120, 120).centerCrop().into(imageView);
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

        viewPager1.setAdapter(adapter);
    }

    private void setViewPager(int fragmentNum) {
        viewPager.setCurrentItem(fragmentNum);
    }
}
