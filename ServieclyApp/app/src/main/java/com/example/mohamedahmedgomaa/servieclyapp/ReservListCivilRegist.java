package com.example.mohamedahmedgomaa.servieclyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.mohamedahmedgomaa.servieclyapp.Common.CommonData;
import com.example.mohamedahmedgomaa.servieclyapp.model.Reservations;
import com.example.mohamedahmedgomaa.servieclyapp.view.RequestAdapter;
import com.example.mohamedahmedgomaa.servieclyapp.view.ReservationAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservListCivilRegist extends AppCompatActivity {
    Map<String,String> mapoffice=new HashMap<>();
    Map<String,String> mapdocument=new HashMap<>();
    private List<Reservations> lstReserv ;
    ProgressDialog progressDialog;
    private RecyclerView recyclerView ;
    ReservationAdapter myadapter;
    SearchView searchView;
    private Toolbar mtoolbar;
    CardView cardView;
    CommonData commonData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserv_list_civil_regist);
        progressDialog=new ProgressDialog(ReservListCivilRegist.this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle(R.string.Waiting);
        mtoolbar=(Toolbar) findViewById(R.id.nav_action) ;
        cardView=findViewById(R.id.ReservListCivilRegist);

        getOffices();
        getDocuments();
        commonData=new CommonData();
        if(commonData.getLocale()!=null)
        {
            cardView.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        getOffices();
        getDocuments();

        lstReserv = new ArrayList<>() ;
        recyclerView = findViewById(R.id.recyclerviewReserv);
        getOffices();
        getDocuments();
        jsonrequest();


    }
    private  void getOffices(){
        String url="http://192.168.1.34:88/api/Offices";
        StringRequest stringRequest1=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonRoot = null;
                    try {
                        jsonRoot = new JSONArray(response);

                    for (int i = 0; i < jsonRoot.length(); i++) {

                        JSONObject jsonObject = jsonRoot.getJSONObject(i);
                        int id = jsonObject.getInt("office_id");
                        String name="";
                        if(commonData.getLocale()!=null)
                        {

                             name = jsonObject.getString("office_name_arabic");

                        }
                        else
                        {
                             name = jsonObject.getString("office_name");

                        }
                        mapoffice.put(String.valueOf(id), name);
                    }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }catch (Exception E)
                {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        getString(R.string.SomthingsWronghappen),Toast.LENGTH_LONG).show();
            }
        }) ;
        MySingleton.getInstance(this).addToRequestQueue(stringRequest1);
    }

    private  void getDocuments(){

            String url="http://192.168.1.34:88/api/services";
            StringRequest stringRequest1=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONArray jsonRoot = null;
                        try {
                            jsonRoot = new JSONArray(response);

                            for (int i = 0; i < jsonRoot.length(); i++) {

                                JSONObject jsonObject = jsonRoot.getJSONObject(i);
                                int id = jsonObject.getInt("service_id");
                                String name="";
                                if(commonData.getLocale()!=null)
                                {
                                    name = jsonObject.getString("service_name_arabic");

                                }
                                else
                                {
                                    name = jsonObject.getString("service_name");

                                }
                                mapdocument.put(String.valueOf(id), name);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }catch (Exception E)
                    {

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.SomthingsWronghappen),Toast.LENGTH_LONG).show();
                }
            }) ;
            MySingleton.getInstance(this).addToRequestQueue(stringRequest1);



    }
    private void jsonrequest() {
        progressDialog.show();
        SharedPreferences sharedPreferences =getSharedPreferences("CitizenData", Context.MODE_PRIVATE);
        String cid=sharedPreferences.getString("Cid",null);
        final String url = "http://192.168.1.34:88/api/Reservations?reservation_citizen_id="+cid ;
        StringRequest stringRequest1=new StringRequest(com.android.volley.Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray  = new JSONArray(response) ;

                    for (int i = 0 ; i < jsonArray.length(); i++ ) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i) ;
                        Reservations reserv  = new Reservations() ;
                        reserv.setReservation_date(jsonObject.getString("reservation_date"));
                        reserv.setReservation_id(jsonObject.getInt("reservation_id"));
                        String officename=mapoffice.get(String.valueOf(jsonObject.getInt("reservation_office_id")));
                        String documentname=mapdocument.get(String.valueOf(jsonObject.getInt("service_id")));
                        reserv.setReservation_document_type_id(documentname);
                        reserv.setReservation_office_id(officename);
                        lstReserv.add(reserv);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

               setuprecyclerview(lstReserv);
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        getString(R.string.SomthingsWronghappen),Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(stringRequest1);

    }
    private void setuprecyclerview(List<Reservations> lstReserv) {

        if(lstReserv.isEmpty())
        {

            Toast.makeText(this,getString(R.string.NoReservationyet),Toast.LENGTH_SHORT).show();
        }
         myadapter = new ReservationAdapter(this,lstReserv) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myadapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.searchfile, menu);
        final MenuItem myActionMenuItem = menu.findItem(R.id.search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        changeSearchViewTextColor(searchView);
        ((EditText) searchView.findViewById(R.id.search_src_text)).
                setHintTextColor(getResources().getColor(R.color.black));
        ((EditText) searchView.findViewById(R.id.search_src_text)).
                setTextColor(getResources().getColor(R.color.black));
        ((EditText) searchView.findViewById(R.id.search_src_text)).
                setCursorVisible(true);
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
                if(!lstReserv.isEmpty()) {
                    final List<com.example.mohamedahmedgomaa.servieclyapp.model.Reservations> filtermodelist = filter(lstReserv, newText);
                    myadapter.setfilter(filtermodelist);
                }
                    return true;

            }
        });
        return true;
    }
    private List<Reservations> filter(List<Reservations> pl,String query)
    {
        query=query.toLowerCase();
        final List<Reservations> filteredModeList=new ArrayList<>();
        for (Reservations model:pl)
        {
            String text=model.getReservation_office_id().toLowerCase();
            String date=model.getReservation_date().toLowerCase();
            String type=model.getReservation_document_type_id().toLowerCase();
            String id= String.valueOf(model.getReservation_id()).toLowerCase();
            if (text.startsWith(query))
            {
                filteredModeList.add(model);
            }
            else if(date.startsWith(query))
            {
                filteredModeList.add(model);
            }
            else if(type.startsWith(query))
            {
                filteredModeList.add(model);
            }

            else if(id.startsWith(query))
            {
                filteredModeList.add(model);
            }

        }
        return filteredModeList;
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


}
