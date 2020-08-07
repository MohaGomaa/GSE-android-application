package com.example.mohamedahmedgomaa.servieclyapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.LayoutDirection;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.mohamedahmedgomaa.servieclyapp.Common.CommonData;
import com.example.mohamedahmedgomaa.servieclyapp.model.ComplaintModel;
import com.example.mohamedahmedgomaa.servieclyapp.model.ResponseModel;
import com.example.mohamedahmedgomaa.servieclyapp.view.ComplaintAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComplaintList extends AppCompatActivity {
    Map<Integer,ResponseModel> map=new HashMap<>();
    Map<String,String>mapTypeRequest=new HashMap<>();
    Map<String,String>mapGovernment=new HashMap<>();
    SearchView searchView;
    RecyclerView listshowrcy;
    private JsonArrayRequest request ;
    private RequestQueue requestQueue ;
    private List<ComplaintModel> lstAnime ;
    private List<ResponseModel> lstRes ;
    private RecyclerView recyclerView ;
    ProgressDialog progressDialog;
    ComplaintAdapter myadapter;
    private Toolbar mtoolbar;
    CardView cardViewLay;
    CommonData commonData;
    String  Replay="",ReplayDate="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_list);

        progressDialog=new ProgressDialog( ComplaintList.this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle(R.string.Waiting);
        mtoolbar=(Toolbar) findViewById(R.id.nav_action) ;

        cardViewLay=findViewById(R.id.ComplaintList);
         commonData=new CommonData();
        if(commonData.getLocale()!=null)
        {
            cardViewLay.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }


        lstAnime = new ArrayList<>() ;
        lstRes = new ArrayList<>() ;
        recyclerView = findViewById(R.id.recyclerviewid);
        getGovernment();

        jsonrequest();


    }




    @Override
    protected void onStart() {
        super.onStart();

    }

    private void jsonrequests(final int cId) {
        progressDialog.show();
        final String url = "http://192.168.1.34:88/api/Responds?cId="+cId ;

        StringRequest stringRequest1=new StringRequest(com.android.volley.Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONArray jsonArray  = new JSONArray(response) ;
                    JSONObject jsonObject = jsonArray.getJSONObject(0) ;
                    ResponseModel responseModel=new ResponseModel();
                    Replay="";
                    ReplayDate="";
                    Replay=jsonObject.getString("RespondText");
                    ReplayDate=jsonObject.getString("Date");

                    responseModel.setReplayDate(ReplayDate);
                    responseModel.setReplay(Replay);
                    lstRes.add(responseModel);
                    map.put(cId,responseModel);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        getString(R.string.SomthingsWronghappen),Toast.LENGTH_LONG).show();            }
        });
        MySingleton.getInstance(this).addToRequestQueue(stringRequest1);
    }
    private void jsonrequest() {
        progressDialog.show();
        SharedPreferences sharedPreferences =getSharedPreferences("CitizenData", Context.MODE_PRIVATE);
        String cid=sharedPreferences.getString("Cid",null);
        final String url = "http://192.168.1.34:88/api/Complains?cId="+cid ;
        StringRequest stringRequest1=new StringRequest(com.android.volley.Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //



                try {
                    JSONArray jsonArray  = new JSONArray(response) ;
                    for (int i = 0 ; i < jsonArray.length(); i++ ) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i) ;
                        ComplaintModel request  = new ComplaintModel() ;
                        String ser=mapGovernment.get(jsonObject.getString("GovernementId"));
                        request.setGobernmentBody(ser);
                        request.setSendDate(jsonObject.getString("Date"));
                        request.setComplaint(jsonObject.getString("ComplainText"));
                        request.setId(jsonObject.getInt("Id"));
                        jsonrequests(jsonObject.getInt("Id"));
                      //  Toast.makeText(ComplaintList.this,jsonObject.getInt("Id")+""+arr[0]+arr[1],Toast.LENGTH_LONG).show();

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
                        getString(R.string.SomthingsWronghappen),Toast.LENGTH_LONG).show();            }
        });
        MySingleton.getInstance(this).addToRequestQueue(stringRequest1);

    }
    private void setuprecyclerview(List<ComplaintModel> lstAnime) {

        if(lstAnime.isEmpty())
        {

            Toast.makeText(this,   getString(R.string.NoInformationYet),Toast.LENGTH_SHORT).show();
        }
        myadapter = new ComplaintAdapter(this,lstAnime) ;
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
            @Override            public boolean onQueryTextChange(String newText) {
                final  List<ComplaintModel> filtermodelist=filter(lstAnime,newText);
                myadapter.setfilter(filtermodelist);
                return true;
            }
        });
        return true;
    }
    private List<ComplaintModel> filter(List<ComplaintModel> pl,String query)
    {
        query=query.toLowerCase();
        final List<ComplaintModel> filteredModeList=new ArrayList<>();
        for (ComplaintModel model:pl)
        {
            String text=model.getGobernmentBody().toLowerCase();
            String lang=model.getSendDate().toLowerCase();

            String datee=model.getComplaint().toLowerCase();
            String id= String.valueOf(model.getId()).toLowerCase();
            if (text.startsWith(query))
            {
                filteredModeList.add(model);
            }
            else if(lang.startsWith(query))
            {
                filteredModeList.add(model);
            }

            else if(id.startsWith(query))
            {
                filteredModeList.add(model);
            }
            else if(datee.startsWith(query))
            {
                filteredModeList.add(model);
            }
        }
        return filteredModeList;
    }
    private  void getGovernment(){
        String url="http://192.168.1.34:88/api/governement_body";
        StringRequest stringRequest1=new StringRequest(com.android.volley.Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray jsonRoot = null;
                    try {
                        jsonRoot = new JSONArray(response);

                        for (int i = 0; i < jsonRoot.length(); i++) {

                            JSONObject jsonObject = jsonRoot.getJSONObject(i);
                            int id = jsonObject.getInt("id");
                            String name="";
                            if(commonData.getLocale()!=null)
                            {
                                name = jsonObject.getString("governement_name_arabic");
                            }
                            else
                            {
                                name = jsonObject.getString("governement_name");
                            }

                            mapGovernment.put(String.valueOf(id), name);
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

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if(item.getTitle()==getString(R.string.ShowResponse))
        {

                ComplaintModel i = lstAnime.get(item.getOrder());
                 ResponseModel responseModel=map.get(i.getId());
                //Toast.makeText(VaccinationList.this, ""+ , Toast.LENGTH_LONG).show();
try {
    showResponse(responseModel.getReplay(), responseModel.getReplayDate());
}
catch (Exception E)
{
    Toast.makeText(ComplaintList.this,getString(R.string.NoRespondyet),Toast.LENGTH_LONG).show();
}


        }
        return super.onContextItemSelected(item);

    }
    private  void showResponse(final String  Replay,final String  ReplayDate)
    {
        final AlertDialog.Builder alertDialog=new AlertDialog.Builder(ComplaintList.this);

        final TextView edtAddress=new TextView(ComplaintList.this);
        ScrollView.LayoutParams layoutParams = new ScrollView.LayoutParams(
                ScrollView.LayoutParams.MATCH_PARENT,
                ScrollView.LayoutParams.MATCH_PARENT );
         layoutParams.setMargins(2,2,2,2);
        if (commonData.getLocale()!=null)
        {
            layoutParams.setLayoutDirection(LayoutDirection.RTL);
            alertDialog.setMessage(getString(R.string.Details)+"\n\n"+getString(R.string.ReplayDatee)+""+ReplayDate+"\n"+getString(R.string.Replayy)+""+Replay);
        }
       else
        {
            alertDialog.setTitle(R.string.Details);
            alertDialog.setIcon(R.drawable.complaint);
            alertDialog.setMessage(getString(R.string.ReplayDatee)+""+ReplayDate+"\n"+getString(R.string.Replayy)+""+Replay);
        }

        //edtAddress.setText(getString(R.string.ReplayDatee)+""+item.getReplayDate()+"\n"+getString(R.string.Replayy)+""+item.getReplay());


        edtAddress.setLayoutParams(layoutParams);
        //alertDialog.setView(edtAddress);

        alertDialog.setPositiveButton(R.string.Cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });


        alertDialog.show();

    }





}
