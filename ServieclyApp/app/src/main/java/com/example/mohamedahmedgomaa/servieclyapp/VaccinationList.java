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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.example.mohamedahmedgomaa.servieclyapp.Common.CommonData;
import com.example.mohamedahmedgomaa.servieclyapp.model.Reservations;
import com.example.mohamedahmedgomaa.servieclyapp.model.Vaccination;
import com.example.mohamedahmedgomaa.servieclyapp.view.ReservationAdapter;
import com.example.mohamedahmedgomaa.servieclyapp.view.VaccinationAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VaccinationList extends AppCompatActivity {

    Map<String,String> mapVaccType=new HashMap<>();  //done
    Map<String,String> mapChild=new HashMap<>();    //done
    Map<String,String> mapSchedual=new HashMap<>();
    Map<String,String> mapHealthcare=new HashMap<>();
     CommonData commonData;

    private List<Vaccination> lstVacc ;
    ProgressDialog progressDialog;
    private RecyclerView recyclerView ;
    VaccinationAdapter myadapter;
    SearchView searchView;
    CardView cardViewlay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        progressDialog=new ProgressDialog(VaccinationList.this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle(R.string.Waiting);
            commonData=new CommonData();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccination_list);

        cardViewlay=findViewById(R.id.VaccinationList);
        CommonData commonData=new CommonData();
        if(commonData.getLocale()!=null)
        {
            cardViewlay.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }

        getChild();
        getHealthcare();
        getVacType();
        getSchedual();
        lstVacc = new ArrayList<>() ;
        recyclerView = findViewById(R.id.recyclerviewid);

        jsonrequest();


    }

////////////////////////////////// Get Healthcare ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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

                            String name ="";

                            if(commonData.getLocale()!=null)
                            {
                                name = jsonObject.getString("hospital_name_arabic");
                            }
                            else
                            {
                                name = jsonObject.getString("hospital_name");
                            }
                            mapHealthcare.put(String.valueOf(id), name);
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

 ////////////////////////////////// Get Schedual ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private  void getSchedual(){

        String url="http://192.168.1.34:88/api/ScheduleVaccinations";
        StringRequest stringRequest1=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonRoot = null;
                    try {
                        jsonRoot = new JSONArray(response);

                        for (int i = 0; i < jsonRoot.length(); i++) {

                            JSONObject jsonObject = jsonRoot.getJSONObject(i);
                            int id = jsonObject.getInt("schedule_id");
                            String name = jsonObject.getString("checkup_date")+"-"+jsonObject.getString("checkup_start")+"-" +jsonObject.getString("checkup_end");;
                            mapSchedual.put(String.valueOf(id), name);

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
/////////////////////////////////////////////////////////////////////////////////////////////////////
    private  void getVacType(){
        String url="http://192.168.1.34:88/api/VaccinationTypes";
        StringRequest stringRequest1=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonRoot = null;
                    try {
                        jsonRoot = new JSONArray(response);

                        for (int i = 0; i < jsonRoot.length(); i++) {

                            JSONObject jsonObject = jsonRoot.getJSONObject(i);
                            int id = jsonObject.getInt("vaccination_type_id");
                            String name ="";
                            if(commonData.getLocale()!=null)
                            {
                                name = jsonObject.getString("vaccination_type_name_arabic");
                            }
                            else
                            {
                                name = jsonObject.getString("vaccination_type_name");
                            }
                            mapVaccType.put(String.valueOf(id), name);
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
////////////////////////////////////////////////////////////////////////////////////////////////////
    private  void getChild(){
        SharedPreferences sharedPreferences =getSharedPreferences("CitizenData", Context.MODE_PRIVATE);
        String cid=sharedPreferences.getString("Cid",null);
        String url="http://192.168.1.34:88/api/Citizen?FId="+cid;
        StringRequest stringRequest1=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonRoot = null;
                    try {
                        jsonRoot = new JSONArray(response);

                        for (int i = 0; i < jsonRoot.length(); i++) {

                            JSONObject jsonObject = jsonRoot.getJSONObject(i);
                            int id = jsonObject.getInt("citizen_id");

                            String name ="";
                            if(commonData.getLocale()!=null)
                            {
                                name = jsonObject.getString("citizen_first_name_arabic")+" "+jsonObject.getString("citizen_second_name_arabic");
                            }
                            else
                            {
                                 name = jsonObject.getString("citizen_first_name")+" "+jsonObject.getString("citizen_second_name");
                            }
                            mapChild.put(String.valueOf(id), name);

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
////////////////////////////////////////////////////////////////////////////////////////////////////
    private void jsonrequest() {
        progressDialog.show();
        SharedPreferences sharedPreferences =getSharedPreferences("CitizenData", Context.MODE_PRIVATE);
        String cid=sharedPreferences.getString("Cid",null);
       // final String url = "http://192.168.1.34:88/api/RequestsApi?request_citizenId="+cid ;
        final String url = "http://192.168.1.34:88/api/VaccinationReservations?citizenid="+cid ;
        StringRequest stringRequest=new StringRequest(com.android.volley.Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray  = new JSONArray(response) ;
                    for (int i = 0 ; i < jsonArray.length(); i++ ) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i) ;
                        Vaccination reserv  = new Vaccination() ;

                        String sch=jsonObject.getString("VaccReservation_date");
                        String heal=mapHealthcare.get(String.valueOf(jsonObject.getInt("VaccReservation_HealthCare_id")));
                        String vacty=mapVaccType.get(String.valueOf(jsonObject.getInt("VaccReservation_VaccinationType_id")));
                        String child=mapChild.get(String.valueOf(jsonObject.getInt("VaccReservation_child_id")));
                        reserv.setVaccination_Type(vacty);
                        reserv.setHeathcare(heal);
                        reserv.setScedual(sch);
                        reserv.setChild_Name(child);
                        reserv.setId(jsonObject.getInt("VaccReservation_id"));

                        if (commonData.getLocale()!=null)
                        {
                            if(jsonObject.getString("VaccReservation_checked")=="true")
                            {
                                reserv.setVaccinated("نعم");
                            }
                            else

                            {
                                reserv.setVaccinated("لا");
                            }
                        }
                        else
                        {
                            reserv.setVaccinated(jsonObject.getString("VaccReservation_checked"));

                        }
                        reserv.setCode( jsonObject.getString("VaccReservation_Code"));
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
    private void setuprecyclerview(List<Vaccination> lstVacc) {

        if(lstVacc.isEmpty())
        {

            Toast.makeText(this,   getString(R.string.NoReservationyet),Toast.LENGTH_SHORT).show();
        }
        myadapter = new VaccinationAdapter(this,lstVacc) ;
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
                    final List<Vaccination> filtermodelist = (List<Vaccination>) filter(lstVacc, newText);
                    myadapter.setfilter(filtermodelist);
                }
                return true;

            }
        });
        return true;
    }
    private List<Vaccination> filter(List<Vaccination> pl, String query)
    {
        query=query.toLowerCase();
        final List<Vaccination> filteredModeList=new ArrayList<>();
        for (Vaccination model:pl)
        {
            String child=model.getChild_Name().toLowerCase();
            String vacctype=model.getVaccination_Type().toLowerCase();
            String sch=model.getScedual().toLowerCase();
            String health= String.valueOf(model.getHeathcare()).toLowerCase();
            String code= String.valueOf(model.getCode()).toLowerCase();
            if (child.startsWith(query))
            {
                filteredModeList.add(model);
            }
            else if(vacctype.startsWith(query))
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

         if(item.getTitle()==getString(R.string.Cancel))
        {
            Vaccination i =lstVacc.get(item.getOrder());
            //Toast.makeText(VaccinationList.this, ""+ , Toast.LENGTH_LONG).show();
            cancelReserve(i.getId(),item);
        }
        return super.onContextItemSelected(item);

    }

    private  void cancelReserve(final int ReserveId, final MenuItem item)
    {
        new TTFancyGifDialog.Builder(VaccinationList.this)
                .setTitle(getString(R.string.CancelVaccinationAppointment))
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
                        String url="http://192.168.1.34:88/api/VaccinationReservations?citizenid="+cid+"&VaccReservation_id="+ReserveId+"&Ar="+Ar;
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
                        MySingleton.getInstance(VaccinationList.this).addToRequestQueue(stringRequest);






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
