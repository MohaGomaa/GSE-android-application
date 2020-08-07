package com.example.mohamedahmedgomaa.servieclyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
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
import com.example.mohamedahmedgomaa.servieclyapp.model.ReservationDoctorAppointment;
import com.example.mohamedahmedgomaa.servieclyapp.model.Vaccination;
import com.example.mohamedahmedgomaa.servieclyapp.view.ReservDocAppAdapter;
import com.example.mohamedahmedgomaa.servieclyapp.view.VaccinationAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static androidx.core.view.ViewCompat.LAYOUT_DIRECTION_RTL;

public class ReservDocAppList extends AppCompatActivity {


    Map<String,String> mapSpec=new HashMap<>();
    Map<String,String> mapDoc=new HashMap<>();
    Map<String,String> mapHeal=new HashMap<>();
    Map<String,String> mapSch=new HashMap<>();
    private List<ReservationDoctorAppointment> lstDocApp ;
    ProgressDialog progressDialog;
    private RecyclerView recyclerView ;
    ReservDocAppAdapter myadapter;
    CardView cardViewlay;
    SearchView searchView;
    CommonData commonData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserv_doc_app_list);
        progressDialog=new ProgressDialog(ReservDocAppList.this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle(R.string.Waiting);
        cardViewlay=findViewById(R.id.ReservDocAppList);

        commonData=new CommonData();
        if(commonData.getLocale()!=null)
        {
            cardViewlay.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        getDoctor();
        getHealthcare();


        lstDocApp = new ArrayList<>() ;
        recyclerView = findViewById(R.id.recyclerviewid);

        jsonrequest();


    }
    private  void getSpec(){
        String url="http://192.168.1.34:88/api/HealthCareSpecializations";
        StringRequest stringRequest1=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonRoot = null;
                    try {
                        jsonRoot = new JSONArray(response);

                        for (int i = 0; i < jsonRoot.length(); i++) {

                            JSONObject jsonObject = jsonRoot.getJSONObject(i);
                            int id = jsonObject.getInt("specialization_id");
                            String name="";
                            if(commonData.getLocale()!=null)
                            {
                                name = jsonObject.getString("specialization_name_arabic");

                            }
                            else
                            {
                                name = jsonObject.getString("specialization_name");

                            }
                            mapSpec.put(String.valueOf(id), name);
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

/////////////////////////////////////////////////////////////////////////////////////////////////
    private  void getDoctor(){
        String url="http://192.168.1.34:88/api/Healthcare_Doctor";
        StringRequest stringRequest1=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonRoot = null;
                    try {
                        jsonRoot = new JSONArray(response);

                        for (int i = 0; i < jsonRoot.length(); i++) {

                            JSONObject jsonObject = jsonRoot.getJSONObject(i);
                            int id = jsonObject.getInt("doctor_id");
                            String name="";
                            if(commonData.getLocale()!=null)
                            {
                                name = jsonObject.getString("citizen_first_name_arabic")+" "+jsonObject.getString("citizen_fourth_name_arabic");

                            }
                            else
                            {
                                name = jsonObject.getString("citizen_first_name")+" "+jsonObject.getString("citizen_fourth_name");

                            }


                            mapDoc.put(String.valueOf(id), name);
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
///////////////////////////////////////////////////////////////////////////////////////////
    private  void getHealthcare(){
        String url="http://192.168.1.34:88/api/HealthCares";
        StringRequest stringRequest1=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonRoot = null;
                    try {
                        jsonRoot = new JSONArray(response);

                        for (int i = 0; i < jsonRoot.length(); i++) {

                            JSONObject jsonObject = jsonRoot.getJSONObject(i);
                            int id = jsonObject.getInt("hospital_id");
                            String name="";
                            if(commonData.getLocale()!=null)
                            {
                                name = jsonObject.getString("hospital_name_arabic");
                            }
                            else
                            {
                                name = jsonObject.getString("hospital_name");
                            }
                            mapHeal.put(String.valueOf(id), name);
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
    }  // done
//////////////////////////////////////////////////////////////////////////////////////////
    private void jsonrequest() {
        progressDialog.show();
        SharedPreferences sharedPreferences =getSharedPreferences("CitizenData", Context.MODE_PRIVATE);
        String cid=sharedPreferences.getString("Cid",null);
        final String url = "http://192.168.1.34:88/api/HealthcareReservations?citizenid="+cid ;
        StringRequest stringRequest1=new StringRequest(com.android.volley.Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray  = new JSONArray(response) ;

                    for (int i = 0 ; i < jsonArray.length(); i++ ) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i) ;
                        ReservationDoctorAppointment reserv  = new ReservationDoctorAppointment() ;
                        String sch=jsonObject.getString("Reservation_date");
                        String heal=mapHeal.get(String.valueOf(jsonObject.getInt("healthcareReservation_hospital_id")));
                         String spec=jsonObject.getString("specialization_name");

                         if(commonData.getLocale()!=null)
                         {

                             spec=jsonObject.getString("specialization_name_arabic");
                         }
                        String doc=mapDoc.get(String.valueOf(jsonObject.getInt("healthcareReservation_doctor_id")));
                        String code=jsonObject.getString("healthcareReservation_Code");
                        reserv.setRDA_id(jsonObject.getInt("healthcareReservation_id"));
                        reserv.setRDA_Specialization(spec);
                        reserv.setRDA_Doctor(doc);
                        reserv.setRDA_Schedual(sch);
                        reserv.setRDA_Code(code);
                        reserv.setRDA_Healthcare(heal);
                        lstDocApp.add(reserv);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                setuprecyclerview(lstDocApp);
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
///////////////////////////////////////////////////////////////////////////////////////////
    private void setuprecyclerview(List<ReservationDoctorAppointment> lstVacc) {

        if(lstVacc.isEmpty())
        {

            Toast.makeText(this, getString(R.string.NoReservationyet),Toast.LENGTH_SHORT).show();
        }
        myadapter = new ReservDocAppAdapter(this,lstVacc) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myadapter);

    }

//////////////////////////////////////////////////////////////////////////////////////////////////
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
                if(!lstDocApp.isEmpty()) {
                    final List<ReservationDoctorAppointment> filtermodelist = (List<ReservationDoctorAppointment>) filter(lstDocApp, newText);
                    myadapter.setfilter(filtermodelist);
                }
                return true;

            }
        });
        return true;
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private List<ReservationDoctorAppointment> filter(List<ReservationDoctorAppointment> pl, String query)
    {
        query=query.toLowerCase();
        final List<ReservationDoctorAppointment> filteredModeList=new ArrayList<>();
        for (ReservationDoctorAppointment model:pl)
        {
            String spec=model.getRDA_Specialization().toLowerCase();
            String doctor=model.getRDA_Doctor().toLowerCase();
            String sch=model.getRDA_Schedual().toLowerCase();
            String health= String.valueOf(model.getRDA_Healthcare()).toLowerCase();
            String code= String.valueOf(model.getRDA_Code()).toLowerCase();
            if (spec.startsWith(query))
            {
                filteredModeList.add(model);
            }
            else if(doctor.startsWith(query))
            {
                filteredModeList.add(model);
            }
            else if(sch.startsWith(query))
            {
                filteredModeList.add(model);
            }

            else if(health.startsWith(query))
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
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if(item.getTitle()==getString(R.string.Cancel))
        {
            ReservationDoctorAppointment i =lstDocApp.get(item.getOrder());
            cancelReserve(i.getRDA_id(),item);
        }
        return super.onContextItemSelected(item);

    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////
    private  void cancelReserve(final int ReserveId, final MenuItem item)
    {
        new TTFancyGifDialog.Builder(ReservDocAppList.this)

                .setTitle( getString(R.string.CancelHealthcareAppointment))
                .setMessage(getString(R.string.AreyouwanttoDeletethisappointment))
                .setPositiveBtnText(getString(R.string.Delete))
                .setPositiveBtnBackground("#020000")
                .setNegativeBtnText(getString(R.string.Cancel))
                .setNegativeBtnBackground("#4c4c4c")
                .setGifResource(R.drawable.whitebin)      //pass your gif, png or jpg
                .isCancellable(true)
                .OnPositiveClicked(new TTFancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        progressDialog.show();

                        SharedPreferences sharedPreferences =getSharedPreferences("CitizenData", Context.MODE_PRIVATE);
                        String cid=sharedPreferences.getString("Cid",null);
                        boolean Ar=false;
                             if(commonData.getLocale()!=null)
                             {
                                 Ar=true;
                             }
                        String url="http://192.168.1.34:88/api/HealthcareReservations?citizenid="+cid+"&Reservation_id="+ReserveId+"&Ar="+Ar;
                        // Toast.makeText(getApplicationContext(),""+st+""+of,Toast.LENGTH_LONG).show();
                        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //      Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                                lstDocApp.remove(item.getOrder());
                                setuprecyclerview(lstDocApp);
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
                        MySingleton.getInstance(ReservDocAppList.this).addToRequestQueue(stringRequest);






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
