package com.example.mohamedahmedgomaa.servieclyapp;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.example.mohamedahmedgomaa.servieclyapp.Common.CommonData;
import com.example.mohamedahmedgomaa.servieclyapp.model.Change_pinNumber;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.theartofdev.edmodo.cropper.CropImage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import static com.example.mohamedahmedgomaa.servieclyapp.R.string.navigation_drawer_close;
import static com.example.mohamedahmedgomaa.servieclyapp.R.string.navigation_drawer_open;

public class DashboardHome extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView name,nId;
    private BottomNavigationView bottomNavigationView;
    private static final int MAX_LENGTH = 100 ;
    private EditText newPostText;
    private ImageView newPostImg;
    private Uri main_uri = null;
    private  String postText;
    private Bitmap compressedImageBitmap;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment=null;
            switch (item.getItemId()) {
                case R.id.navigationMyProfile:
                {
                    //   fragment= new Citizen_Profile();
                    Intent intent =new Intent(DashboardHome.this,ShowPlaces.class);
                    startActivity(intent);


                }
                case R.id.navigationMyCourses: {
                    FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.drawer,fragment).commit();

                    return true;

                }
                case R.id.navigationHome: {
                    Intent a = new Intent(DashboardHome.this,Ministry.class);
                    startActivity(a);
                    return  true;
                }
                case  R.id.navigationSearch:
                {

                    return  true;
                }
                case  R.id.navigationMenu:
                    Intent a = new Intent(DashboardHome.this,Complaint.class);
                    startActivity(a);
                    return true;
            }
            return false;
        }
    };
    private Toolbar supportActionBar;
    Toolbar mtoolbar;
    DrawerLayout mdrawer;
    CommonData commonData;
    ActionBarDrawerToggle mtoggle;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         commonData=new CommonData();

        setContentView(R.layout.activity_dashboard_home);

        mdrawer=findViewById(R.id.drawer);


        if(commonData.getLocale()!=null)
        {
            mdrawer.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }



       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        */
        mtoolbar= (Toolbar) findViewById(R.id.toolbar) ;
        setSupportActionBar(mtoolbar);

        mtoggle=new ActionBarDrawerToggle( this,mdrawer, navigation_drawer_open, navigation_drawer_close);
        mdrawer.addDrawerListener(mtoggle);
        mtoggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView = findViewById(R.id.navigation);
        View headerview=navigationView.getHeaderView(0);
        name=headerview.findViewById(R.id.textName);
        nId=headerview.findViewById(R.id.textNId);

        SharedPreferences sharedPreferences = getSharedPreferences("CitizenData", Context.MODE_PRIVATE);
        nId.setText( sharedPreferences.getString("NId","").toString());
        String Name=sharedPreferences.getString("CFirstName","")+" "+sharedPreferences.getString("CSecondName","")
                +" "+sharedPreferences.getString("CThirdName","")+" "+sharedPreferences.getString("CFourthName","");

        name.setText(Name);
//


        //handling floating action menu
        findViewById(R.id.floatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
                drawer.openDrawer(GravityCompat.START);
            }
        });
        LayoutInflater inflater=this.getLayoutInflater();
        View add_menu_layout=inflater.inflate(R.layout.add_post,null);


        newPostText = add_menu_layout.findViewById(R.id.newPostDesc);


        findViewById(R.id.faButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
          showDialog();
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
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
            Intent intent =new Intent(DashboardHome.this,ShowPlaces.class);
            startActivity(intent);
        }   else if (id == R.id.nav_home) {
            Intent a = new Intent(DashboardHome.this,Ministry.class);
            startActivity(a);

        }
        else if (id == R.id.nav_changepin) {
            Change_pinNumber changepinNumber =new Change_pinNumber();
            changepinNumber.showChangePasswordDialog(DashboardHome.this);

        } else if (id == R.id.nav_slideshow) {
            Intent intent=new Intent(this, ComplaintList.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {

        }
        else if (id == R.id.nav_dashboard) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void setSupportActionBar(Toolbar supportActionBar) {
        this.supportActionBar = supportActionBar;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                main_uri = result.getUri();
                newPostImg.setImageURI(main_uri);

//                String tct = main_uri.toString();
//                Toast.makeText(AccounrSetup.this,tct,Toast.LENGTH_LONG).show();

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(getApplicationContext(),
                        getString(R.string.SomthingsWronghappen),Toast.LENGTH_LONG).show();
            }
        }
    }

    public static String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(MAX_LENGTH);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    protected  void showDialog() {
        AlertDialog.Builder aBuilder=new AlertDialog.Builder(DashboardHome.this);
        aBuilder.setTitle(getResources().getString(R.string.AddNewPost));
        LayoutInflater inflater=this.getLayoutInflater();
        View add_menu_layout=inflater.inflate(R.layout.add_post,null);
        newPostText=add_menu_layout.findViewById(R.id.newPostDesc);
        if(commonData.getLocale()!=null)
        {
            add_menu_layout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        aBuilder.setView(add_menu_layout);
        aBuilder.setPositiveButton(getResources().getString(R.string.Yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(newPostText.getText().toString().equals(null )||newPostText.getText().toString().equals(""))
                {        newPostText.setError(getResources().getString(R.string.EmptyText));
                    return;
                }


                dialog.dismiss();

            }
        });
        aBuilder.setNegativeButton(getResources().getString(R.string.No), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        aBuilder.show();
    }


}