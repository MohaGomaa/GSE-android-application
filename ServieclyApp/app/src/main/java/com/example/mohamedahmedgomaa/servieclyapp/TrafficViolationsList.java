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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.example.mohamedahmedgomaa.servieclyapp.Common.CommonData;
import com.example.mohamedahmedgomaa.servieclyapp.model.CarLicence;
import com.example.mohamedahmedgomaa.servieclyapp.model.TrafficViolation;
import com.example.mohamedahmedgomaa.servieclyapp.model.Vaccination;
import com.example.mohamedahmedgomaa.servieclyapp.model.ViolationType;
import com.example.mohamedahmedgomaa.servieclyapp.view.TrafficViolationsAdapter;
import com.example.mohamedahmedgomaa.servieclyapp.view.VaccinationAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrafficViolationsList extends AppCompatActivity {

    CommonData commonData;
    Map<String, ViolationType> mapViolationType;  //done
    Map<String, CarLicence> mapCars=new HashMap<>();    //done
    Map<String,String> mapViol=new HashMap<>();
    Map<String,String> mapHealthcare=new HashMap<>();
    private List<TrafficViolation> lstVacc ;
    private List<ViolationType> lstVT ;
    ProgressDialog progressDialog;
    private RecyclerView recyclerView ;
    TrafficViolationsAdapter myadapter;
    SearchView searchView;
    CardView cardViewlay;
    CarLicence car;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        progressDialog=new ProgressDialog(TrafficViolationsList.this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle(R.string.Waiting);
        mapViolationType =new HashMap<>();

        commonData=new CommonData();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_violations_list);

        cardViewlay=findViewById(R.id.TrafficViolationsList);
        CommonData commonData=new CommonData();
        if(commonData.getLocale()!=null)
        {
            cardViewlay.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }


        lstVacc = new ArrayList<>() ;
        lstVT = new ArrayList<>() ;
        recyclerView = findViewById(R.id.recyclerviewid);

        jsonrequest();




    }


    private void jsonrequest() {



           progressDialog.show();

        SharedPreferences sharedPreferences =getSharedPreferences("CitizenData", Context.MODE_PRIVATE);
        String cid=sharedPreferences.getString("Cid",null);
           final String url = "http://192.168.1.34:88/api/Violation_CarLicenceM_M?cId="+cid;
           StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                   new Response.Listener<String>() {
                @Override
               public void onResponse(String response) {
                   try {

                   //    Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                       JSONArray jsonArray = new JSONArray(response);

                       for (int i = 0; i < jsonArray.length(); i++) {
                           JSONObject jsonObject = jsonArray.getJSONObject(i);
                           TrafficViolation reserv = new TrafficViolation();
                           if(commonData.getLocale()!=null)
                           {
                               reserv.setType_Violation(jsonObject.getString("ViolationNameArabic"));
                           }
                           else
                           {
                               reserv.setType_Violation(jsonObject.getString("ViolationName"));
                           }

                           reserv.setCar_code(jsonObject.getString("CarCode"));
                          reserv.setDate(jsonObject.getString("Date"));
                          reserv.setPrice(jsonObject.getString("ViolationPrice"));
                          reserv.setIs_Paid(jsonObject.getString("Is_Paid"));

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
                   Toast.makeText(getApplicationContext(), "Error :" + error, Toast.LENGTH_SHORT).show();
                   progressDialog.dismiss();
               }
           });

           MySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
    private void setuprecyclerview(List<TrafficViolation> lstVacc) {

        if(lstVacc.isEmpty())
        {

            Toast.makeText(this,   getString(R.string.NoInformationYet),Toast.LENGTH_SHORT).show();
        }
        myadapter = new TrafficViolationsAdapter(this,lstVacc) ;
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
                    final List<TrafficViolation> filtermodelist = (List<TrafficViolation>) filter(lstVacc, newText);
                    myadapter.setfilter(filtermodelist);
                }
                return true;

            }
        });
        return true;
    }
    private List<TrafficViolation> filter(List<TrafficViolation> pl, String query)
    {
        query=query.toLowerCase();
        final List<TrafficViolation> filteredModeList=new ArrayList<>();
        for (TrafficViolation model:pl)
        {
            String child=model.getCar_code().toLowerCase();
            String vacctype=model.getType_Violation().toLowerCase();
            String sch=model.getDate().toLowerCase();
            String health= String.valueOf(model.getIs_Paid()).toLowerCase();
            String code= String.valueOf(model.getPrice()).toLowerCase();
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


    private  void getViolations(){
        String url="http://192.168.1.34:88/api/Violations";
        StringRequest stringRequest1=new StringRequest(com.android.volley.Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonRoot = null;
                    try {
                        jsonRoot = new JSONArray(response);

                        for (int i = 0; i < jsonRoot.length(); i++) {

                            JSONObject jsonObject = jsonRoot.getJSONObject(i);
                            int id = jsonObject.getInt("Id");
                            String name="";
                            if(commonData.getLocale()!=null)
                            {
                                name = jsonObject.getString("ViolationNameArabic");
                            }
                            else
                            {
                                name = jsonObject.getString("ViolationName");
                            }

                            mapViol.put(String.valueOf(id), name);

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


}
