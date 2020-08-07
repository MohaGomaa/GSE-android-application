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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.mohamedahmedgomaa.servieclyapp.Common.CommonData;
import com.example.mohamedahmedgomaa.servieclyapp.model.RequestEducationalService;
import com.example.mohamedahmedgomaa.servieclyapp.view.RequestAdapter;
import com.example.mohamedahmedgomaa.servieclyapp.view.RequestEducationalServiceAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestEducationalServiceList extends AppCompatActivity {
    Map<String,String> map=new HashMap<>();
    Map<String,String>mapTypeRequest=new HashMap<>();
    Map<String,String>mapPhase=new HashMap<>();
    SearchView searchView;
    RecyclerView listshowrcy;
    private JsonArrayRequest request ;
    private RequestQueue requestQueue ;
    private List<RequestEducationalService> lstAnime ;
    private RecyclerView recyclerView ;
    ProgressDialog progressDialog;
    RequestEducationalServiceAdapter myadapter;
    CardView cardViewLay;
    Map<String,String> mapChild=new HashMap<>();    //done
    CommonData commonData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        progressDialog=new ProgressDialog(RequestEducationalServiceList.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(getString(R.string.Waiting));
//        setSupportActionBar(mtoolbar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_educational_service_list);
        cardViewLay=findViewById(R.id.RequestEducationalServiceList);
        commonData=new CommonData();
        if(commonData.getLocale()!=null)
        {
            cardViewLay.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }


        lstAnime = new ArrayList<>() ;
        recyclerView = findViewById(R.id.recyclerviewid);
        getPhases();
        getRequestTypes();
        getChild();
        jsonrequest();


    }

    @Override
    protected void onStart() {
        super.onStart();

    }
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
    private void getRequestType() {
        final String url = "http://192.168.1.34:88/api/RequestsApi" ;
        StringRequest stringRequest=new StringRequest(com.android.volley.Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonArray=new JSONArray(response);
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        map.put(String.valueOf(jsonObject.getInt("id")),jsonObject.getString(""));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    private void jsonrequest() {
        progressDialog.show();
        SharedPreferences sharedPreferences =getSharedPreferences("CitizenData", Context.MODE_PRIVATE);
        String cid=sharedPreferences.getString("Cid",null);
        final String url = "http://192.168.1.34:88/api/RequestsApi?request_citizenId="+cid ;
        final StringRequest stringRequest1=new StringRequest(com.android.volley.Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //



                try {
                    JSONArray jsonArray  = new JSONArray(response) ;

                    for (int i = 0 ; i < jsonArray.length(); i++ ) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i) ;
                        com.example.mohamedahmedgomaa.servieclyapp.model.RequestEducationalService request  = new   com.example.mohamedahmedgomaa.servieclyapp.model.RequestEducationalService() ;
                        String ser=mapPhase.get(jsonObject.getString(""));
                        String child=mapChild.get(jsonObject.getString(""));
                        request.setPhase(ser);
                        request.setChild(child);
                        if( commonData.getLocale()!=null)
                        {
                            String lan=jsonObject.getString("language");
                            if(lan.equals("English"))
                            {
                                request.setLanguage("الانجليزية");
                            }
                            else
                            {
                                request.setLanguage("العربية");
                            }

                        }
                        else
                        {
                            request.setLanguage(jsonObject.getString("language"));
                        }


                        request.setQuantity(jsonObject.getInt("quantity"));
                        request.setAddress(jsonObject.getString("address"));
                        request.setDate(jsonObject.getString("date"));
                        String type=mapTypeRequest.get(jsonObject.getString("typeRequest"));
                        request.setRequestType(type);
                        request.setId(jsonObject.getString("request_id"));
                        lstAnime.add(request);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                setuprecyclerview(lstAnime);
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        getString(R.string.SomthingsWronghappen),Toast.LENGTH_LONG).show();
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(stringRequest1);

    }
    private void setuprecyclerview(List<RequestEducationalService> lstAnime) {


        myadapter = new RequestEducationalServiceAdapter(lstAnime,this) ;
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
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override            public boolean onQueryTextSubmit(String query) {
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();
                return false;
            }
            @Override            public boolean onQueryTextChange(String newText) {
                final  List<RequestEducationalService> filtermodelist=filter(lstAnime,newText);
                myadapter.setfilter(filtermodelist);
                return true;
            }
        });
        return true;
    }
    private List<RequestEducationalService> filter(List<RequestEducationalService> pl,String query)
    {
        query=query.toLowerCase();
        final List<RequestEducationalService> filteredModeList=new ArrayList<>();
        for (RequestEducationalService model:pl)
        {
            String text=model.getPhase().toLowerCase();
            String child=model.getChild().toLowerCase();
            String lang=model.getLanguage().toLowerCase();
            String type=model.getRequestType().toLowerCase();
            String date=model.getDate().toLowerCase();
            String id= String.valueOf(model.getId()).toLowerCase();
            String quant= String.valueOf(model.getQuantity()).toLowerCase();
            if (text.startsWith(query))
            {
                filteredModeList.add(model);
            }
            else if(lang.startsWith(query))
            {
                filteredModeList.add(model);
            }
            else if(type.startsWith(query))
            {
                filteredModeList.add(model);
            }
            else if(date.startsWith(query))
            {
                filteredModeList.add(model);
            }
            else if(id.startsWith(query))
            {
                filteredModeList.add(model);
            }
            else if(quant.startsWith(query))
            {
                filteredModeList.add(model);
            }
            else if(child.startsWith(query))
            {
                filteredModeList.add(model);
            }
        }
        return filteredModeList;
    }

    private  void getPhases(){
        String url="http://192.168.1.34:88/api/phases";
        StringRequest stringRequest1=new StringRequest(com.android.volley.Request.Method.GET, url, new Response.Listener<String>() {
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

                            mapPhase.put(String.valueOf(id), name);
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
    private  void getRequestTypes(){
        String url="http://192.168.1.34:88/api/TypeRequests";
        StringRequest stringRequest1=new StringRequest(com.android.volley.Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonRoot = null;
                    try {
                        jsonRoot = new JSONArray(response);

                        for (int i = 0; i < jsonRoot.length(); i++) {

                            JSONObject jsonObject = jsonRoot.getJSONObject(i);
                            int id = jsonObject.getInt("typeReaquest_id");
                            String name="";
                            if(commonData.getLocale()!=null)
                            {
                                name = jsonObject.getString("typeReaquest_name_arabic");
                            }
                            else
                            {
                                name = jsonObject.getString("typeReaquest_name");
                            }

                            mapTypeRequest.put(String.valueOf(id), name);
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