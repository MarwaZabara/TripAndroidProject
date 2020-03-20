package com.example.tripandroidproject.View.NavDrawer_UpComingTrip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tripandroidproject.POJOs.Trip;
import com.example.tripandroidproject.R;
import com.example.tripandroidproject.View.Login.LoginActivity;
import com.example.tripandroidproject.View.SaveUserLogIn;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class NavDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    TextView name,email;
    private FirebaseAuth mAuth;
    private SaveUserLogIn saveUserLogIn;
    private GoogleSignInClient mGoogleSignInClient;

    //    private RecyclerView recyclerView;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Trip> input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_drawer);

        mAuth = FirebaseAuth.getInstance();
        saveUserLogIn = new SaveUserLogIn(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        name  = findViewById(R.id.navName);
        email = findViewById(R.id.navEmail);
        Intent intent = getIntent();
        String e = intent.getStringExtra("Email");
        Toast.makeText(this, e, Toast.LENGTH_SHORT).show();
//        name.setText(intent.getStringExtra("Name"));
//        email.setText(intent.getStringExtra("Email"));

        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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
                break;
            case R.id.upComingTrip:
                Toast.makeText(NavDrawer.this, "UpComingTrip us Selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this,NavDrawer.class));
                break;
            case R.id.history:
                Toast.makeText(NavDrawer.this, "History us Selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.synch:
                Toast.makeText(NavDrawer.this, "Synch us Selected", Toast.LENGTH_SHORT).show();
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
                saveUserLogIn.clearUserData();
                saveUserLogIn.setUserLoggedIn(false);
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return false;
    }
}
