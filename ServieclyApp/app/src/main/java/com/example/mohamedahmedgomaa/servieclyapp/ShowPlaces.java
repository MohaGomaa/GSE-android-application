package com.example.mohamedahmedgomaa.servieclyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.google.android.gms.location.LocationListener;
import com.example.mohamedahmedgomaa.servieclyapp.Common.CommonData;
import com.example.mohamedahmedgomaa.servieclyapp.Maps.GetDirectionsData;
import com.example.mohamedahmedgomaa.servieclyapp.Maps.GetNearbyPlacesData;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class ShowPlaces extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMarkerDragListener
        {
         LinearLayout linearLayout;
         private GoogleMap mMap;
        LocationManager locationManager;
        private static final int REQUEST_LOCATION_PERMISSION = 1;
        GoogleApiClient mGoogleApiClient;
        Location mLastLocation;
        Marker mCurrLocationMarker;
         private static  final int REQUEST_LOCATION=1;
        LocationRequest mLocationRequest;
        int PROXIMITY_RADIUS = 10000;
        double latitude, longitude;
        double end_latitude, end_longitude;
            Marker marker;
            LocationListener locationListener;
            CommonData commonData;
@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_places);
    locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    commonData=new CommonData();
    linearLayout=findViewById(R.id.ShowPlaces);
    if(commonData.getLocale()!=null)
    {
        linearLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
    }
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        checkLocationPermission();
        }

        //Check if Google Play Services Available or not
        if (!CheckGooglePlayServices()) {
        Log.d("onCreate", "Finishing test case since Google Play Services are not available");
        finish();
        }
        else {
        Log.d("onCreate","Google Play Services available.");
        }
    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
    {
        //Write Function To enable gps
        OnGPS();
    }
    else
    {
        //GPS is already On then
        getLocation();
    }


    // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        }



private boolean CheckGooglePlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if(result != ConnectionResult.SUCCESS) {
        if(googleAPI.isUserResolvableError(result)) {
        googleAPI.getErrorDialog(this, result,
        0).show();
        }
        return false;
        }
        return true;
        }

@Override
public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if (ContextCompat.checkSelfPermission(this,
        Manifest.permission.ACCESS_FINE_LOCATION)
        == PackageManager.PERMISSION_GRANTED) {
        buildGoogleApiClient();
        mMap.setMyLocationEnabled(true);
        }
        } else {
        buildGoogleApiClient();
        mMap.setMyLocationEnabled(true);
        }

        mMap.setOnMarkerDragListener(this);
        mMap.setOnMarkerClickListener(this);

        }



protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(LocationServices.API)
        .build();
        mGoogleApiClient.connect();
        }

public void onClick(View v)
        {
        Object dataTransfer[] = new Object[2];
       GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();


        switch(v.getId()) {
        case R.id.B_search: {
        EditText tf_location = (EditText) findViewById(R.id.TF_location);
        String location = tf_location.getText().toString();

        List<Address> addressList = null;
        MarkerOptions markerOptions = new MarkerOptions();
        Log.d("location = ", location);

        if (!location.equals("")) {
        Geocoder geocoder = new Geocoder(this);
        try {
        addressList = geocoder.getFromLocationName(location, 5);

        } catch (IOException e) {
        e.printStackTrace();
        }
        if (addressList != null) {
        for (int i = 0; i < addressList.size(); i++) {
        Address myAddress = addressList.get(i);
        LatLng latLng = new LatLng(myAddress.getLatitude(), myAddress.getLongitude());
        markerOptions.position(latLng);
        mMap.addMarker(markerOptions);
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        }
        }
        else
        {
            Toast.makeText(ShowPlaces.this,getString(R.string.TRYAgain),Toast.LENGTH_SHORT).show();

        }

        }
        else
        {
            tf_location.setError(getString(R.string.EnterAddress));
        }
        }
        break;
        case R.id.B_hospital:
        mMap.clear();
        String hospital = "hospital";
        String url = getUrl(latitude, longitude, hospital);

        dataTransfer[0] = mMap;
        dataTransfer[1] = url;
            if (dataTransfer==null)
            {
                Toast.makeText(ShowPlaces.this,getString(R.string.tryAgainlater),Toast.LENGTH_SHORT).show();

            }
        getNearbyPlacesData.execute(dataTransfer);


        break;

        case R.id.B_restaurant:
        mMap.clear();
        dataTransfer = new Object[2];
        String restaurant = "restaurant";
        url = getUrl(latitude, longitude, restaurant);
        getNearbyPlacesData = new GetNearbyPlacesData();
        dataTransfer[0] = mMap;
        dataTransfer[1] = url;

            if (dataTransfer==null)
            {
                Toast.makeText(ShowPlaces.this,getString(R.string.tryAgainlater),Toast.LENGTH_SHORT).show();

            }
        getNearbyPlacesData.execute(dataTransfer);

        break;
        case R.id.B_school:
        mMap.clear();
        String school = "school";
        dataTransfer = new Object[2];
        url = getUrl(latitude, longitude, school);
        getNearbyPlacesData = new GetNearbyPlacesData();
        dataTransfer[0] = mMap;
        dataTransfer[1] = url;
            if (dataTransfer==null)
            {
                Toast.makeText(ShowPlaces.this,getString(R.string.tryAgainlater),Toast.LENGTH_SHORT).show();

            }
        getNearbyPlacesData.execute(dataTransfer);

        break;


        }
        }

private String getDirectionsUrl()
        {
        StringBuilder googleDirectionsUrl = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
        googleDirectionsUrl.append("origin="+latitude+","+longitude);
        googleDirectionsUrl.append("&destination="+end_latitude+","+end_longitude);
        googleDirectionsUrl.append("&key="+"AIzaSyCAcfy-02UHSu2F6WeQ1rhQhkCr51eBL9g");

        return googleDirectionsUrl.toString();
        }

private String getUrl(double latitude, double longitude, String nearbyPlace)
        {
        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=" + latitude + "," + longitude);
        googlePlacesUrl.append("&radius=" + PROXIMITY_RADIUS);
        googlePlacesUrl.append("&type=" + nearbyPlace);
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + "AIzaSyBj-cnmMUY21M0vnIKz0k3tD3bRdyZea-Y");
        Log.d("getUrl", googlePlacesUrl.toString());
        return (googlePlacesUrl.toString());
        }



@Override
public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
        Manifest.permission.ACCESS_FINE_LOCATION)
        == PackageManager.PERMISSION_GRANTED) {
       LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);
        }
        }



@Override
public void onConnectionSuspended(int i) {

        }

@Override
public void onLocationChanged(Location location) {
        Log.d("onLocationChanged", "entered");

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
        mCurrLocationMarker.remove();
        }

        latitude = location.getLatitude();
        longitude = location.getLongitude();


        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.draggable(true);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));




        //stop location updates
        if (mGoogleApiClient != null) {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (com.google.android.gms.location.LocationListener) this);
        Log.d("onLocationChanged", "Removing Location Updates");
        }

        }


            @Override
public void onConnectionFailed(ConnectionResult connectionResult) {

        }

public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
        Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {

        // Asking user if explanation is needed
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
        Manifest.permission.ACCESS_FINE_LOCATION)) {

        // Show an explanation to the user *asynchronously* -- don't block
        // this thread waiting for the user's response! After the user
        // sees the explanation, try again to request the permission.

        //Prompt the user once explanation has been shown
        ActivityCompat.requestPermissions(this,
        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
        MY_PERMISSIONS_REQUEST_LOCATION);


        } else {
        // No explanation needed, we can request the permission.
        ActivityCompat.requestPermissions(this,
        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
        MY_PERMISSIONS_REQUEST_LOCATION);
        }
        return false;
        } else {
        return true;
        }
        }

@Override
public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
    if (ActivityCompat.checkSelfPermission(this,
            Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED){

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                //get the location name from latitude and longitude
                Geocoder geocoder = new Geocoder(getApplicationContext());
                try {
                    List<Address> addresses =
                            geocoder.getFromLocation(latitude, longitude, 1);
                    String result = addresses.get(0).getLocality()+":";
                    result += addresses.get(0).getCountryName();
                    LatLng latLng = new LatLng(latitude, longitude);
                    if (marker != null){
                        marker.remove();
                        marker = mMap.addMarker(new MarkerOptions().position(latLng).title(result));
                        mMap.setMaxZoomPreference(30);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));
                    }
                    else{
                        marker = mMap.addMarker(new MarkerOptions().position(latLng).title(result));
                        mMap.setMaxZoomPreference(30);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        };
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (android.location.LocationListener) locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (android.location.LocationListener) locationListener);
    }

        switch (requestCode) {
        case MY_PERMISSIONS_REQUEST_LOCATION: {
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0
        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        // permission was granted. Do the
        // contacts-related task you need to do.
        if (ContextCompat.checkSelfPermission(this,
        Manifest.permission.ACCESS_FINE_LOCATION)
        == PackageManager.PERMISSION_GRANTED) {

        if (mGoogleApiClient == null) {
        buildGoogleApiClient();
        }
        mMap.setMyLocationEnabled(true);
        }

        } else {

        // Permission denied, Disable the functionality that depends on this permission.
        Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
        }
        return;
        }

        // other 'case' lines to check for other permissions this app might request.
        // You can add here other case statements according to your requirement.
        }
        }


@Override
public boolean onMarkerClick(Marker marker) {
        marker.setDraggable(true);
        return false;
        }

@Override
public void onMarkerDragStart(Marker marker) {

        }

@Override
public void onMarkerDrag(Marker marker) {

        }

@Override
public void onMarkerDragEnd(Marker marker) {
        end_latitude = marker.getPosition().latitude;
        end_longitude =  marker.getPosition().longitude;

        Log.d("end_lat",""+end_latitude);
        Log.d("end_lng",""+end_longitude);
        }
            private void getLocation() {


                if (ActivityCompat.checkSelfPermission(ShowPlaces.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ShowPlaces.this,

                        Manifest.permission.ACCESS_COARSE_LOCATION) !=PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(this,new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
                }
                else
                {
                    Location LocationGps= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    Location LocationNetwork=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    Location LocationPassive=locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

                    if (LocationGps !=null)
                    {
                        double lat=LocationGps.getLatitude();
                        double longi=LocationGps.getLongitude();

                        latitude=lat;
                        longitude=longi;

                        //showLocationTxt.setText("Your Location:"+"\n"+"Latitude= "+latitude+"\n"+"Longitude= "+longitude);
                    }
                    else if (LocationNetwork !=null)
                    {
                        double lat=LocationNetwork.getLatitude();
                        double longi=LocationNetwork.getLongitude();

                        latitude=lat;
                        longitude=longi;

                        //     showLocationTxt.setText("Your Location:"+"\n"+"Latitude= "+latitude+"\n"+"Longitude= "+longitude);
                    }
                    else if (LocationPassive !=null)
                    {
                        double lat=LocationPassive.getLatitude();
                        double longi=LocationPassive.getLongitude();

                        latitude=lat;
                        longitude=longi;

                        //     showLocationTxt.setText("Your Location:"+"\n"+"Latitude= "+latitude+"\n"+"Longitude= "+longitude);
                    }
                    else
                    {
                        Toast.makeText(this, R.string.CantGetYourLocation, Toast.LENGTH_SHORT).show();
                    }

                    //Thats All Run Your App
                }

            }


            private void OnGPS() {
                final AlertDialog.Builder builder= new AlertDialog.Builder(this);

                builder.setMessage(R.string.EnableGPS).setCancelable(false).setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                }).setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                        onBackPressed();

                    }
                });
                final AlertDialog alertDialog=builder.create();
                alertDialog.show();



            }


}
