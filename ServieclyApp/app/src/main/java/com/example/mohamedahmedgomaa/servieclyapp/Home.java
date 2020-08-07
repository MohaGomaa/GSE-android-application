package com.example.mohamedahmedgomaa.servieclyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.mohamedahmedgomaa.servieclyapp.Common.CommonData;
import com.example.mohamedahmedgomaa.servieclyapp.model.Change_pinNumber;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import static com.example.mohamedahmedgomaa.servieclyapp.R.string.*;
import static com.example.mohamedahmedgomaa.servieclyapp.R.string.navigation_drawer_open;

public class Home  extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
private BottomNavigationView bottomNavigationView;
private static final int MODE_DARK = 0;
private static final int MODE_LIGHT = 1;
        TextView name,nId;

private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
        = new BottomNavigationView.OnNavigationItemSelectedListener() {

@Override
public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment;
        switch (item.getItemId()) {
        case R.id.navigationMyProfile:
        {
                Intent intent =new Intent(Home.this,ShowPlaces.class);
                startActivity(intent);
        }
        case R.id.navigationMyCourses:
        return true;
        case R.id.navigationHome:
        return true;
        case  R.id.navigationSearch:
        return true;
        case  R.id.navigationMenu:
                Intent a = new Intent(Home.this,Complaint.class);
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


        setContentView(R.layout.activity_home);

        mtoolbar= (Toolbar) findViewById(R.id.toolbar) ;
        setSupportActionBar(mtoolbar);
        mdrawer=findViewById(R.id.drawer_layout);
        mtoggle=new ActionBarDrawerToggle( this,mdrawer, navigation_drawer_open, navigation_drawer_close);
        mdrawer.addDrawerListener(mtoggle);
        CommonData commonData=new CommonData();
        if(commonData.getLocale()!=null)
        {
            mdrawer.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        mtoggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        bottomNavigationView.setSelectedItemId(R.id.navigationHome);



        View headerview=navigationView.getHeaderView(0);
        name=headerview.findViewById(R.id.textName);
        nId=headerview.findViewById(R.id.textNId);

        SharedPreferences sharedPreferences = getSharedPreferences("CitizenData", Context.MODE_PRIVATE);
        nId.setText( sharedPreferences.getString("NId","").toString());
        String Name=sharedPreferences.getString("CFirstName","")+" "+sharedPreferences.getString("CSecondName","")
                +" "+sharedPreferences.getString("CThirdName","")+" "+sharedPreferences.getString("CFourthName","");

        name.setText(Name);
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
                 BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                        = new BottomNavigationView.OnNavigationItemSelectedListener() {

                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                                Fragment fragment;
                                switch (item.getItemId()) {
                                        case R.id.navigationMyProfile:
                                        {
                                                Intent intent =new Intent(Home.this,Citizen_Profile.class);
                                                startActivity(intent);
                                        }
                                        case R.id.navigationMyCourses:
                                                return true;
                                        case R.id.navigationHome:
                                                return true;
                                        case  R.id.navigationSearch:
                                                return true;
                                        case  R.id.navigationMenu:
                                                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                                                drawer.openDrawer(GravityCompat.START);
                                                return true;
                                }
                                return false;
                        }
                };
        }
        }


@SuppressWarnings("StatementWithEmptyBody")
@Override
public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
                Intent intent =new Intent(Home.this,ShowPlaces.class);
                startActivity(intent);

        }   else if (id == R.id.nav_home) {
                Intent intent =new Intent(this,Ministry.class);
                startActivity(intent);



        } else if (id == R.id.nav_changepin) {
                Change_pinNumber changepinNumber =new Change_pinNumber();
                changepinNumber.showChangePasswordDialog(Home.this);

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




    public void gotoservices(View view) {
            Intent intentRequestService=new Intent(this, MainActivity.class);
            startActivity(intentRequestService);
    }

        public void gotoRequestService(View view) {
                Intent intentRequestService=new Intent(this, RequestService.class);
                startActivity(intentRequestService);
        }

        public void gotoReservation(View view) {
                Intent intentReservation=new Intent(this, Reservation.class);
                startActivity(intentReservation);
        }

        public void gotoRequestsList(View view) {
                Intent intentCitizenRequests=new Intent(this, CitizenRequests.class);
                startActivity(intentCitizenRequests);
        }

        public void gotoReservationList(View view) {
                Intent intentReservation=new Intent(this, ReservListCivilRegist.class);
                startActivity(intentReservation);
        }

    public void gotoTrafficViolation(View view) {
            Intent intentRequestService=new Intent(this, TrafficViolationsList.class);
            startActivity(intentRequestService);
    }
}


  /*  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    */

