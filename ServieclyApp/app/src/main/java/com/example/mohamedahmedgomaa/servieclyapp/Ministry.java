package com.example.mohamedahmedgomaa.servieclyapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mohamedahmedgomaa.servieclyapp.Common.CommonData;
import com.example.mohamedahmedgomaa.servieclyapp.model.Change_pinNumber;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.mohamedahmedgomaa.servieclyapp.R.string.navigation_drawer_close;
import static com.example.mohamedahmedgomaa.servieclyapp.R.string.navigation_drawer_open;

public class Ministry extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView name,nId;
    CircleImageView profilePhoto;

    private BottomNavigationView bottomNavigationView;
    private static final int MODE_DARK = 0;
    private static final int MODE_LIGHT = 1;
    ProgressDialog mProgressDialog;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment=null;
            switch (item.getItemId()) {
                case R.id.navigationMyProfile:
                {
                 //   fragment= new Citizen_Profile();
                    Intent intent =new Intent(Ministry.this,ShowPlaces.class);
                    startActivity(intent);


                }
                case R.id.navigationMyCourses: {

                    return true;

                }
                case R.id.navigationHome: {
                    return  true;
                }
                case  R.id.navigationSearch:
                {

                    return  true;
                }
                case  R.id.navigationMenu:
                    Intent a = new Intent(Ministry.this,Complaint.class);
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
      //  setDarkMode(getWindow());
        CommonData commonData=new CommonData();

        setContentView(R.layout.activity_ministry);

        mdrawer=findViewById(R.id.drawer);

        mProgressDialog=new ProgressDialog(Ministry.this);
        mProgressDialog.setMessage(getString(R.string.Pleasewaiting));
        mProgressDialog.setCancelable(false);
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
        SharedPreferences sharedPreferences = getSharedPreferences("CitizenData", Context.MODE_PRIVATE);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        String TyId = sharedPreferences.getString("user_type_id","2");

              if(!TyId.equals("2")) {
                  navigationView.inflateMenu(R.menu.activity_main_drawer);
              }
              else {
                  navigationView.inflateMenu(R.menu.activity_main1_drawer);
              }

        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        View headerview=navigationView.getHeaderView(0);
        name=headerview.findViewById(R.id.textName);
        nId=headerview.findViewById(R.id.textNId);
        profilePhoto=headerview.findViewById(R.id.imgProfile);
        String Id = sharedPreferences.getString("Cid","");
        nId.setText( sharedPreferences.getString("NId","").toString());
        String Name=sharedPreferences.getString("CFirstName","")+" "+sharedPreferences.getString("CSecondName","")
                +" "+sharedPreferences.getString("CThirdName","")+" "+sharedPreferences.getString("CFourthName","");
        Picasso.with(Ministry.this)
                .load("http://192.168.1.34:88/api/Citizen?Id="+Id)
                .into(profilePhoto);
        name.setText(Name);
//
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());

        bottomNavigationView.setSelectedItemId(R.id.navigationHome);

        //handling floating action menu
        findViewById(R.id.floatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
                drawer.openDrawer(GravityCompat.START);
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
            Intent intent =new Intent(Ministry.this,ShowPlaces.class);
            startActivity(intent);
        }   else if (id == R.id.nav_home) {

        }
        else if (id == R.id.nav_changepin) {
            Change_pinNumber changepinNumber =new Change_pinNumber();
            changepinNumber.showChangePasswordDialog(Ministry.this);

        } else if (id == R.id.nav_slideshow) {
                    Intent intent=new Intent(this, ComplaintList.class);
                     startActivity(intent);
        } else if (id == R.id.nav_logout) {
            SharedPreferences sharedPreferences=getSharedPreferences("CitizenData",MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.clear();
            editor.commit();
             finish();
        } else if (id == R.id.nav_balance) {
            SharedPreferences sharedPreferences =getSharedPreferences("CitizenData", Context.MODE_PRIVATE);
              String cd=sharedPreferences.getString("Cid",null);
            getBalance(cd);
        }

        else if (id == R.id.nav_dashboard) {
            //Intent intent=new Intent(this, RequestAmbulanceListActivity.class);
            Intent intent=new Intent(this, DashboardHome.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setDarkMode(Window window){
        if(new DarkModePrefManager(this).isNightMode()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            changeStatusBar(MODE_DARK,window);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            changeStatusBar(MODE_LIGHT,window);
        }
    }
    public void changeStatusBar(int mode, Window window){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(this.getResources().getColor(R.color.contentStatusBar));
            //Light mode
            if(mode==MODE_LIGHT){
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }
    public void setSupportActionBar(Toolbar supportActionBar) {
        this.supportActionBar = supportActionBar;
    }

    public void gotoHome(View view) {
        Intent intent=new Intent(this,Home.class);
        startActivity(intent);
    }

    public void gotoHealthHome(View view) {
        Intent intent=new Intent(this,HealthHome.class);
        startActivity(intent);
    }


    public void gotoJusticeHome(View view) {
        Intent intent=new Intent(this,JusticeHome.class);
        startActivity(intent);
    }

    public void gotoEducationHome(View view) {
        Intent intent=new Intent(this,StudentCertifcationList.class);
        startActivity(intent);
    }


    public void getBalance(final String citizenId)
    {
     mProgressDialog.show();
        // String url="https://localhost:44389/api/LoginApi?Login_CitizenNId="+NationalId+"&Login_Password="+qrcode;
        String url="http://192.168.1.34:88/api/Citizen?ctId="+citizenId;

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ///Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                try {
                    String balance="0.0";
                            JSONObject jsonObject = new JSONObject(response);
                    if (response != " ")
                    {

                        balance=  jsonObject.getString("CitizenBalance_balance");





                    } else {
                        balance=getString(R.string.nonBalance);
                    }

                    showDialogofBalance( balance);
                }catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.SomthingsWronghappen),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        getString(R.string.SomthingsWronghappen),Toast.LENGTH_LONG).show();

            }
        })   ;

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);





    }
    void showDialogofBalance(String balance)
    {
        mProgressDialog.dismiss();
        new FancyGifDialog.Builder(this)
                .setTitle(getString(R.string.BalanceInfo))
                .setMessage(getString(R.string.BalanceMsg)+" "+balance)
                .setPositiveBtnBackground("#6F6A6A")
                .setPositiveBtnText(getString(R.string.Yes))
                .setGifResource(R.drawable.coins)   //Pass your Gif here
                .isCancellable(true)
                .OnPositiveClicked(new FancyGifDialogListener() {
                    @Override
                    public void OnClick() {

                    }
                })
                .OnNegativeClicked(new FancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                    }
                })
                .build();

    }

}
