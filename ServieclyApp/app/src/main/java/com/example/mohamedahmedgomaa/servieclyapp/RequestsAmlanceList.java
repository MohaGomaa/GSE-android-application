package com.example.mohamedahmedgomaa.servieclyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.example.mohamedahmedgomaa.servieclyapp.Common.CommonData;
import com.example.mohamedahmedgomaa.servieclyapp.model.RequestAmbulance;
import com.example.mohamedahmedgomaa.servieclyapp.model.Vaccination;
import com.example.mohamedahmedgomaa.servieclyapp.view.RequestAmulanceAdapter;
import com.example.mohamedahmedgomaa.servieclyapp.view.VaccinationAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestsAmlanceList extends AppCompatActivity {

    Map<String,String> mapVaccType=new HashMap<>();  //done
    Map<String,String> mapChild=new HashMap<>();    //done
    Map<String,String> mapSchedual=new HashMap<>();
    Map<String,String> mapHealthcare=new HashMap<>();
    CommonData commonData;

    private List<RequestAmbulance> lstVacc ;
    ProgressDialog progressDialog;
    private RecyclerView recyclerView ;
    RequestAmulanceAdapter myadapter;
    SearchView searchView;
    CardView cardViewlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests_amlance_list);
        progressDialog=new ProgressDialog(RequestsAmlanceList.this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle(R.string.Waiting);
        commonData=new CommonData();

        cardViewlay=findViewById(R.id.VaccinationList);
        CommonData commonData=new CommonData();
        if(commonData.getLocale()!=null)
        {
            cardViewlay.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }



        lstVacc = new ArrayList<>() ;
        recyclerView = findViewById(R.id.recyclerviewid);

        jsonrequest();


    }

    private void jsonrequest() {
        progressDialog.show();
        SharedPreferences sharedPreferences =getSharedPreferences("CitizenData", Context.MODE_PRIVATE);
        String cid=sharedPreferences.getString("Cid",null);
        // final String url = "http://192.168.1.34:88/api/RequestsApi?request_citizenId="+cid ;
        final String url = "http://192.168.1.34:88/api/Request_Ambulance?cId="+cid ;
        StringRequest stringRequest=new StringRequest(com.android.volley.Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray  = new JSONArray(response) ;

                    for (int i = 0 ; i < jsonArray.length(); i++ ) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i) ;
                        RequestAmbulance reserv  = new RequestAmbulance() ;

                        reserv.setCode("");
                        reserv.setDate(jsonObject.getString("request_Date"));
                        reserv.setDetails(jsonObject.getString("request_details"));
                        reserv.setLat(jsonObject.getDouble("location_latitude"));
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
                Toast.makeText(getApplicationContext(),
                        getString(R.string.SomthingsWronghappen),Toast.LENGTH_LONG).show();
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
        myadapter = new RequestAmulanceAdapter(this,lstVacc) ;
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
                if(!lstVacc.isEmpty()) {
                    final List<RequestAmbulance> filtermodelist = (List<RequestAmbulance>) filter(lstVacc, newText);
                    myadapter.setfilter(filtermodelist);
                }
                return true;

            }
        });
        return true;
    }
    private List<RequestAmbulance> filter(List<RequestAmbulance> pl, String query)
    {
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
            //Toast.makeText(VaccinationList.this, ""+ , Toast.LENGTH_LONG).show();
       //     cancelReserve(i.getId(),item);

            Intent intent=new Intent(RequestsAmlanceList.this,MapsRequest.class);
            intent.putExtra("lat",i.getLat());
            intent.putExtra("lon",i.getLon());
           // Toast.makeText(RequestsAmlanceList.this, ""+i.getLat()+"-"+i.getLon() , Toast.LENGTH_LONG).show();
            startActivity(intent);
        }
        return super.onContextItemSelected(item);

    }

    private  void cancelReserve(final int ReserveId, final MenuItem item)
    {
        new TTFancyGifDialog.Builder(RequestsAmlanceList.this)
                .setTitle(getString(R.string.CancelVaccinationAppointment))
                .setMessage(getString(R.string.AreyouwanttoDeletethisappointment))
                .setPositiveBtnText(getString(R.string.Delete))
                .setPositiveBtnBackground("#020000")
                .setNegativeBtnText(getString(R.string.Cancel))
                .setNegativeBtnBackground("#4c4c4c")
                .setGifResource(R.drawable.pb)      //pass your gif, png or jpg
                .isCancellable(true)
                .OnPositiveClicked(new TTFancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        progressDialog.show();

                        SharedPreferences sharedPreferences =getSharedPreferences("CitizenData", Context.MODE_PRIVATE);
                        String cid=sharedPreferences.getString("Cid",null);

                        String url="http://192.168.1.34:88/api/VaccinationReservations?citizenid="+cid+"&VaccReservation_id="+ReserveId;
                        // Toast.makeText(getApplicationContext(),""+st+""+of,Toast.LENGTH_LONG).show();
                        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //      Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                                lstVacc.remove(item.getOrder());
                                setuprecyclerview(lstVacc);
                                progressDialog.dismiss();


                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(),
                                        getString(R.string.SomthingsWronghappen),Toast.LENGTH_LONG).show();

                                progressDialog.dismiss();

                            }
                        }) {
                        }   ;
                        MySingleton.getInstance(RequestsAmlanceList.this).addToRequestQueue(stringRequest);






                    }
                })
                .OnNegativeClicked(new TTFancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                    }
                })
                .build();


    }
















}
