package com.example.mohamedahmedgomaa.servieclyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.mohamedahmedgomaa.servieclyapp.Common.CommonData;
import com.example.mohamedahmedgomaa.servieclyapp.model.Change_pinNumber;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import static com.example.mohamedahmedgomaa.servieclyapp.R.string.navigation_drawer_close;
import static com.example.mohamedahmedgomaa.servieclyapp.R.string.navigation_drawer_open;

public class HealthHome extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigationView;
    private static final int MODE_DARK = 0;
    private static final int MODE_LIGHT = 1;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigationMyProfile:
                {
                    Intent intent =new Intent(HealthHome.this,ShowPlaces.class);
                    startActivity(intent);

                }
                case R.id.navigationMyCourses:
                    return true;
                case R.id.navigationHome:
                    return true;
                case  R.id.navigationSearch:
                    return true;
                case  R.id.navigationMenu:
                    Intent a = new Intent(HealthHome.this,Complaint.class);
                    startActivity(a);
                    return true;
            }
            return false;
        }
    };
    private Toolbar supportActionBar;
    Toolbar mtoolbar;
    DrawerLayout mdrawer;
    ActionBarDrawerToggle mtoggle;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_health_home);

        mtoolbar= (Toolbar) findViewById(R.id.toolbar) ;
        setSupportActionBar(mtoolbar);

        mdrawer=findViewById(R.id.drawer_layout);
        CommonData commonData=new CommonData();
        if(commonData.getLocale()!=null)
        {
            mdrawer.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        mtoggle=new ActionBarDrawerToggle( this,mdrawer, navigation_drawer_open, navigation_drawer_close);
        mdrawer.addDrawerListener(mtoggle);
        mtoggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        bottomNavigationView.setSelectedItemId(R.id.navigationHome);

        //handling floating action menu
        findViewById(R.id.floatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
                */
                onBackPressed();
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Intent intent =new Intent(HealthHome.this,ShowPlaces.class);
            startActivity(intent);

        }   else if (id == R.id.nav_home) {
            Intent intent =new Intent(this,Ministry.class);
            startActivity(intent);


        } else if (id == R.id.nav_changepin) {
            Change_pinNumber changepinNumber =new Change_pinNumber();
            changepinNumber.showChangePasswordDialog(HealthHome.this);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_logout) {

        }
        else if (id == R.id.nav_dashboard) {
            Intent intent =new Intent(this,DashboardHome.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void setSupportActionBar(Toolbar supportActionBar) {
        this.supportActionBar = supportActionBar;
    }


    public void gotoVaccation(View view) {
        Intent intentRequestService=new Intent(this, Vaccinations.class);
        startActivity(intentRequestService);
    }

    public void gotoDocAppointment(View view) {
        Intent ReservDocAppointment=new Intent(this, ReservDocAppointment.class);
        startActivity(ReservDocAppointment);
    }

    public void gotoVaccList(View view) {
        Intent ReservDocAppointment=new Intent(this, VaccinationList.class);
        startActivity(ReservDocAppointment);
    }

    public void gotoDocApp(View view) {
        Intent ReservDocAppointment=new Intent(this, ReservDocAppList.class);
        startActivity(ReservDocAppointment);
    }

    public void gotoRequestAmbulance(View view) {
        Intent ReservDocAppointment=new Intent(this, MapsActivity.class);
        startActivity(ReservDocAppointment);
    }

    public void gotoRequestAmbulanceList(View view) {
        Intent ReservDocAppointment=new Intent(this, RequestsAmlanceList.class);
        startActivity(ReservDocAppointment);
    }
}

