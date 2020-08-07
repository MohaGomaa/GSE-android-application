package com.example.mohamedahmedgomaa.servieclyapp;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mohamedahmedgomaa.servieclyapp.Common.CommonData;
import com.example.mohamedahmedgomaa.servieclyapp.model.RequestAmbulance;
import com.example.mohamedahmedgomaa.servieclyapp.view.RequestAmbulanceAdapter;
import com.example.mohamedahmedgomaa.servieclyapp.view.RequestAmulanceAdapter;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FinishRequestsActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {
    CommonData commonData;

    private List<RequestAmbulance> lstVacc ;
    ProgressDialog progressDialog;
    private RecyclerView recyclerView ;
    RequestAmbulanceAdapter myadapter;
    SearchView searchView;
    CardView cardViewlay;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_ambulance_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.setBackgroundColor(R.color.whiteBodyColor);
        if(commonData.getLocale()!=null)
        {
            drawer.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        progressDialog=new ProgressDialog(FinishRequestsActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle(R.string.Waiting);
        commonData=new CommonData();
        lstVacc = new ArrayList<>() ;
        recyclerView = findViewById(R.id.recyclerviewid);

        jsonrequest();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_RequestsRecent) {
            Intent intent =new Intent(this,RequestAmbulanceListActivity.class);
            startActivity(intent);
            finish();
        }
        else if (id == R.id.nav_RequestsApproved) {
            // Handle the camera action
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void jsonrequest() {
        progressDialog.show();
        SharedPreferences sharedPreferences =getSharedPreferences("CitizenData", Context.MODE_PRIVATE);
        String cid=sharedPreferences.getString("Cid",null);
        // final String url = "http://192.168.1.34:88/api/RequestsApi?request_citizenId="+cid ;
        final String url = "http://192.168.1.34:88/api/Request_Ambulance?Ar="+true ;
        StringRequest stringRequest=new StringRequest(com.android.volley.Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray  = new JSONArray(response) ;

                    for (int i = 0 ; i < jsonArray.length(); i++ ) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i) ;
                        RequestAmbulance reserv  = new RequestAmbulance() ;

                        reserv.setCode("");
                        reserv.setId(jsonObject.getInt("Id"));
                        reserv.setDate(jsonObject.getString("request_Date"));
                        reserv.setDetails(jsonObject.getString("request_details"));
                        reserv.setLat(jsonObject.getDouble("location_latitude"));
                        reserv.setLon(jsonObject.getDouble("location_longitude"));
                        reserv.setLon(jsonObject.getDouble("location_longitude"));
                        lstVacc.add(reserv);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                setuprecyclerview(lstVacc);
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error :"+error,Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
    private void setuprecyclerview(List<RequestAmbulance> lstVacc) {

        if(lstVacc.isEmpty())
        {

            Toast.makeText(this,   getString(R.string.NoInformationYet),Toast.LENGTH_SHORT).show();
        }
        myadapter = new RequestAmbulanceAdapter(this,lstVacc) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myadapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
           // getMenuInflater().inflate(R.menu.mai, menu);
            getMenuInflater().inflate(R.menu.searchfile2, menu);
            final MenuItem myActionMenuItem = menu.findItem(R.id.search);
            searchView = (SearchView) myActionMenuItem.getActionView();
            changeSearchViewTextColor(searchView);
            ((EditText) searchView.findViewById(R.id.search_src_text)).
                    setHintTextColor(getResources().getColor(R.color.black));
            ((EditText) searchView.findViewById(R.id.search_src_text)).
                    setTextColor(getResources().getColor(R.color.black));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override            public boolean onQueryTextSubmit(String query) {
                    if (!searchView.isIconified()) {
                        searchView.setIconified(true);
                    }
                    myActionMenuItem.collapseActionView();
                    return false;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    if(!lstVacc.isEmpty()) {
                        final List<RequestAmbulance> filtermodelist = (List<RequestAmbulance>) filter(lstVacc, newText);
                        myadapter.setfilter(filtermodelist);
                    }
                    return true;

                }
            });
            return true;
        }
        catch (Exception e)
        {
            return  false;
        }

    }
    private List<RequestAmbulance> filter(List<RequestAmbulance> pl, String query)
    {
        try {
            query=query.toLowerCase();
            final List<RequestAmbulance> filteredModeList=new ArrayList<>();

            for (RequestAmbulance model:pl)
            {
                String vacctype=model.getDate().toLowerCase();
                String sch=model.getDetails().toLowerCase();
                String code= String.valueOf(model.getCode()).toLowerCase();

                if(vacctype.startsWith(query))
                {
                    filteredModeList.add(model);
                }
                else if(sch.startsWith(query))
                {
                    filteredModeList.add(model);
                }
                else if(code.startsWith(query))
                {
                    filteredModeList.add(model);
                }

            }
            return filteredModeList;
        }
        catch (Exception e)
        {
            return null;
        }

    }

    //for changing the text color of searchview
    private void changeSearchViewTextColor(View view) {
        if (view != null) {
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(Color.WHITE);
                return;
            } else if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    changeSearchViewTextColor(viewGroup.getChildAt(i));
                }
            }
        }
    }



    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if(item.getTitle()==getString(R.string.ShowInMap))
        {
            RequestAmbulance i =lstVacc.get(item.getOrder());
            //Toast.makeText(VaccinationList.this, ""+ , Toast.LENGTH_LONG).show()
            //  cancelReserve(i.getId());

            Intent intent=new Intent(FinishRequestsActivity.this,MapsRequestsAmbActivity.class);
            intent.putExtra("lat",i.getLat());
            intent.putExtra("lon",i.getLon());
            // Toast.makeText(RequestsAmlanceList.this, ""+i.getLat()+"-"+i.getLon() , Toast.LENGTH_LONG).show();
            startActivity(intent);

        }

        return super.onContextItemSelected(item);

    }













}