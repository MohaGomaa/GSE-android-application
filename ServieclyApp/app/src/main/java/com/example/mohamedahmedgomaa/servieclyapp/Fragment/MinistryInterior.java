package com.example.mohamedahmedgomaa.servieclyapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.mohamedahmedgomaa.servieclyapp.BottomNavigationBehavior;
import com.example.mohamedahmedgomaa.servieclyapp.CitizenRequests;
import com.example.mohamedahmedgomaa.servieclyapp.Citizen_Profile;
import com.example.mohamedahmedgomaa.servieclyapp.DarkModePrefManager;
import com.example.mohamedahmedgomaa.servieclyapp.MainActivity;
import com.example.mohamedahmedgomaa.servieclyapp.Ministry;
import com.example.mohamedahmedgomaa.servieclyapp.R;
import com.example.mohamedahmedgomaa.servieclyapp.RequestService;
import com.example.mohamedahmedgomaa.servieclyapp.ReservListCivilRegist;
import com.example.mohamedahmedgomaa.servieclyapp.Reservation;
import com.example.mohamedahmedgomaa.servieclyapp.model.PasswordModel;
import com.google.android.material.navigation.NavigationView;

import static com.example.mohamedahmedgomaa.servieclyapp.R.string.navigation_drawer_close;
import static com.example.mohamedahmedgomaa.servieclyapp.R.string.navigation_drawer_open;

public class MinistryInterior extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    public MinistryInterior() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MinistryInterior newInstance(String param1, String param2) {
        MinistryInterior fragment = new MinistryInterior();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ministry_interior, container, false);





    }


}



