package com.example.mohamedahmedgomaa.servieclyapp;

import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsRequest extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_request);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
               double lat=getIntent().getDoubleExtra("lat",-34);
               double lon=getIntent().getDoubleExtra("lon",151);
        // Add a marker in Sydney and move the camera
     /*   LatLng latLng = new LatLng(lat, lon);

        mMap.addMarker(new MarkerOptions().position(latLng).title( getString(R.string.MarkerinRequestPlace)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
*/
        //AIzaSyAGzQCN3eJ7APDoTxIoAywCuK_dfhUk_ZE
        Geocoder geocoder = new Geocoder(getApplicationContext());


            LatLng latLng = new LatLng(lat, lon);
            mMap.addMarker(new MarkerOptions().position(latLng).title(getString(R.string.MarkerinRequestPlace)));
     //   Toast.makeText(this,lat+"  |   "+lon,Toast.LENGTH_LONG).show();
                mMap.setMaxZoomPreference(30);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));



    }
}
