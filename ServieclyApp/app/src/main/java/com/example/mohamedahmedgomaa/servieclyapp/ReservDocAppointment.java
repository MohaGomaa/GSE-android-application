package com.example.mohamedahmedgomaa.servieclyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
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
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.shreyaspatil.MaterialDialog.interfaces.DialogInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ReservDocAppointment extends AppCompatActivity  implements MaterialSpinner.OnItemSelectedListener {
   CommonData commonData;
    List<Reservation.StringWithTag> itemListCity;
    ArrayAdapter<Reservation.StringWithTag> spinnerAdapterCity;

    List<Reservation.StringWithTag> itemListRegion;
    ArrayAdapter<Reservation.StringWithTag> spinnerAdapterRegion;

    List<Reservation.StringWithTag> itemListDistrict;
    ArrayAdapter<Reservation.StringWithTag> spinnerAdapterDistrict;

    List<StringWithTag> itemListHealthcareType;
    ArrayAdapter<StringWithTag> spinnerAdapterHealthcareType;

    ArrayList<StringWithTag> itemListSpec;
    ArrayAdapter<StringWithTag> spinnerAdapterSpec;

    List<StringWithTag> itemListHealthcare;
    ArrayAdapter<StringWithTag> spinnerAdapterHealthcare;

    List<StringWithTag> itemListDoctor;
    ArrayAdapter<StringWithTag> spinnerAdapterDoctor;

    ArrayList<StringWithTag> itemListSchedual;
    ArrayAdapter<StringWithTag> spinnerAdapterSchedual;

    TextView etDate;
    Calendar myCalendar;
    MaterialSpinner spServices,spHealthcareType,spSpec,spHealthcare,spSchedual,spDoctor;
    MaterialSpinner spOffice,spStat,spCity,spRegion,spDistrict;
    ProgressDialog mProgressDialog;
    Map<String,String> mapHealthcareType = null;
    Map <String,String> mapSpec=null;
    Map <String,String> mapState=null;
    Map <String,String>mapCity = null;
    Map <String,String> mapRegion=null;
    Map <String,String> mapDistrict=null;
    Map <String,String>mapHealthcare = null;
    Map <String,String> mapSchedual=null;
    Map <String,String> mapDoctor=null;
    CoordinatorLayout coordinatorLayout;
    int stId,ctId,reId,dsId,healthcareId,schedualId,doctorId,heathTyId,specId=0;
    String state,city,region,district,healthcare,schedual,doctor,healthTy,spec="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserv_doc_appointment);

         commonData =new CommonData();

        mProgressDialog=new ProgressDialog(ReservDocAppointment.this);


        spHealthcareType=findViewById(R.id.spHealthcareType);
        spSpec=findViewById(R.id.spSpec);
        spHealthcare=findViewById(R.id.spHealthcare);
        spSchedual=findViewById(R.id.spSchedual);
        spDoctor=findViewById(R.id.spDoctor);

        spServices= (MaterialSpinner) findViewById(R.id.spServices);
        spOffice=  (MaterialSpinner)findViewById(R.id.spOffice);
        spStat= (MaterialSpinner) findViewById(R.id.spStat);
        spCity=  (MaterialSpinner)findViewById(R.id.spCity);
        spRegion= (MaterialSpinner) findViewById(R.id.spRegion);
        spDistrict=  (MaterialSpinner)findViewById(R.id.spDistrict);

        coordinatorLayout=findViewById(R.id.ReservDocAppointment);
        CommonData commonData=new CommonData();
        if(commonData.getLocale()!=null)
        {
            coordinatorLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }



        getStateSpinner();
        spStat.setOnItemSelectedListener(this);

        getHeathcareTypeSpinner();
        spHealthcareType.setOnItemSelectedListener(this);

        getSpecSpinner();
        spHealthcareType.setOnItemSelectedListener(this);


    }

///////////////////////////////////////// State API ///////////////////////////////////////////////////////////
    public  void getStateSpinner() {
        mProgressDialog.show();
        String url="http://192.168.1.34:88/api/States";
        StringRequest stringRequest1=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),"Error :"+response,Toast.LENGTH_LONG).show();
                jsonParsingState(response);
                mProgressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        getString(R.string.SomthingsWronghappen),Toast.LENGTH_LONG).show();                mProgressDialog.dismiss();
            }
        }) ;
        MySingleton.getInstance(this).addToRequestQueue(stringRequest1);


    }
    public void jsonParsingState(String response){

        ArrayList<Map> arrayList=new ArrayList<>() ;
        try {
            JSONArray jsonRoot = new JSONArray(response);
            mapState=new HashMap<>();
            for (int i=0; i<jsonRoot.length(); i++ ){

                JSONObject jsonObject = jsonRoot.getJSONObject(i);
                int id = jsonObject.getInt("state_id");
                String name="";
                if(commonData.getLocale()!=null)
                {
                    name = jsonObject.getString("state_arabic_name");
                }
                else
                {
                    name = jsonObject.getString("state_name");
                }

                mapState.put(String.valueOf(id),name);
                //arrayList.add(map);
            }
            List<Reservation.StringWithTag> itemList = new ArrayList<Reservation.StringWithTag>();
            itemList.add(new Reservation.StringWithTag(" ", " "));
            for (Map.Entry<String, String> entry : mapState.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                /* Build the StringWithTag List using these keys and values. */
                itemList.add(new Reservation.StringWithTag(value, key));
            }

            ArrayAdapter<Reservation.StringWithTag> spinnerAdapter = new ArrayAdapter<Reservation.StringWithTag>(this, android.R.layout.simple_spinner_item, itemList);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spStat.setAdapter(spinnerAdapter);
            final String[] loadsearchkeyID = new String[1];
            spStat.setOnItemSelectedListener(this);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

/////////////////////////////////////////// City API ///////////////////////////////////////////////////////////////

    // City Spinner and parsing data
    public  void getCitySpinner() {
        mProgressDialog.show();
        String url="http://192.168.1.34:88/api/Cities?SId="+stId;
        StringRequest stringRequest1=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),"Error :"+response,Toast.LENGTH_LONG).show();
                jsonParsingCity(response);
                mProgressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        getString(R.string.SomthingsWronghappen),Toast.LENGTH_LONG).show();
                mProgressDialog.dismiss();
            }
        }) ;
        MySingleton.getInstance(this).addToRequestQueue(stringRequest1);


    }
    public void jsonParsingCity(String response){

        ArrayList<Map> arrayList=new ArrayList<>() ;
        try {
            JSONArray jsonRoot = new JSONArray(response);
            mapCity=new HashMap<>();
            for (int i=0; i<jsonRoot.length(); i++ ){

                JSONObject jsonObject = jsonRoot.getJSONObject(i);
                int id = jsonObject.getInt("city_id");
                String name="";
                if(commonData.getLocale()!=null)
                {
                    name = jsonObject.getString("city_arabic_name");
                }
                else
                {
                    name = jsonObject.getString("city_name");
                }
                mapCity.put(String.valueOf(id),name);
                //arrayList.add(map);
            }
            itemListCity = new ArrayList<Reservation.StringWithTag>();
            itemListCity.add(new Reservation.StringWithTag(" ", " "));

            for (Map.Entry<String, String> entry : mapCity.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                /* Build the StringWithTag List using these keys and values. */
                itemListCity.add(new Reservation.StringWithTag(value, key));
            }

            spinnerAdapterCity = new ArrayAdapter<Reservation.StringWithTag>(this, android.R.layout.simple_spinner_item, itemListCity);
            spinnerAdapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spCity.setAdapter(spinnerAdapterCity);
            final String[] loadsearchkeyID = new String[1];
            spCity.setOnItemSelectedListener(this);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

 ///////////////////////////////////////////// Region API /////////////////////////////////////////////////////////////////////

    // Region Spinner and parsing data
    public  void getRegionSpinner() {
        mProgressDialog.show();
        String url="http://192.168.1.34:88/api/Regions?CId="+ctId;
        StringRequest stringRequest1=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),"Error :"+response,Toast.LENGTH_LONG).show();
                jsonParsingRegion(response);
                mProgressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        getString(R.string.SomthingsWronghappen),Toast.LENGTH_LONG).show();
                mProgressDialog.dismiss();
            }
        }) ;
        MySingleton.getInstance(this).addToRequestQueue(stringRequest1);


    }
    public void jsonParsingRegion(String response){

        ArrayList<Map> arrayList=new ArrayList<>() ;
        try {
            JSONArray jsonRoot = new JSONArray(response);
            mapRegion=new HashMap<>();
            for (int i=0; i<jsonRoot.length(); i++ ){

                JSONObject jsonObject = jsonRoot.getJSONObject(i);
                int id = jsonObject.getInt("region_id");
                String name="";
                if(commonData.getLocale()!=null)
                {
                    name = jsonObject.getString("region_arabic_name");
                }
                else
                {
                    name = jsonObject.getString("region_name");
                }
                mapRegion.put(String.valueOf(id),name);
                //arrayList.add(map);
            }
            itemListRegion = new ArrayList<Reservation.StringWithTag>();
            itemListRegion.add(new Reservation.StringWithTag(" ", " "));

            for (Map.Entry<String, String> entry : mapRegion.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                /* Build the StringWithTag List using these keys and values. */
                itemListRegion.add(new Reservation.StringWithTag(value, key));
            }

            spinnerAdapterRegion = new ArrayAdapter<Reservation.StringWithTag>(this, android.R.layout.simple_spinner_item, itemListRegion);
            spinnerAdapterRegion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spRegion.setAdapter(spinnerAdapterRegion);
            final String[] loadsearchkeyID = new String[1];
            spRegion.setOnItemSelectedListener(this);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

///////////////////////////////////////////////// District API /////////////////////////////////////////////////////

    // District Spinner and parsing data
    public  void getDistrictSpinner() {
        mProgressDialog.show();
        String url="http://192.168.1.34:88/api/Districts?RId="+reId;
        StringRequest stringRequest1=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),"Error :"+response,Toast.LENGTH_LONG).show();
                jsonParsingDistrict(response);
                mProgressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        getString(R.string.SomthingsWronghappen),Toast.LENGTH_LONG).show();
                mProgressDialog.dismiss();
            }
        }) ;
        MySingleton.getInstance(this).addToRequestQueue(stringRequest1);


    }
    public void jsonParsingDistrict(String response){

        ArrayList<Map> arrayList=new ArrayList<>() ;
        try {
            JSONArray jsonRoot = new JSONArray(response);
            mapDistrict=new HashMap<>();
            for (int i=0; i<jsonRoot.length(); i++ ){

                JSONObject jsonObject = jsonRoot.getJSONObject(i);
                int id = jsonObject.getInt("district_id");
                String name="";
                if(commonData.getLocale()!=null)
                {
                    name = jsonObject.getString("district_arabic_name");
                }
                else
                {
                    name = jsonObject.getString("district_name");
                }
                mapDistrict.put(String.valueOf(id),name);
                //arrayList.add(map);
            }
            itemListDistrict = new ArrayList<Reservation.StringWithTag>();
            itemListDistrict.add(new Reservation.StringWithTag(" ", " "));

            for (Map.Entry<String, String> entry : mapDistrict.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                /* Build the StringWithTag List using these keys and values. */
                itemListDistrict.add(new Reservation.StringWithTag(value, key));
            }

            spinnerAdapterDistrict = new ArrayAdapter<Reservation.StringWithTag>(this, android.R.layout.simple_spinner_item, itemListDistrict);
            spinnerAdapterDistrict.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spDistrict.setAdapter(spinnerAdapterDistrict);
            final String[] loadsearchkeyID = new String[1];
            spDistrict.setOnItemSelectedListener(this);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



////////////////////////////////////////////// Healthcare Type //////////////////////////////////////////////////////////////////////


    // Healthcare Type Spinner and parsing data
    public  void getHeathcareTypeSpinner() {
        mProgressDialog.show();
        String url="http://192.168.1.34:88/api/HealthCare_Type";
        StringRequest stringRequest1=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),"Error :"+response,Toast.LENGTH_LONG).show();
                jsonParsingHeathcareType(response);
                mProgressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        getString(R.string.SomthingsWronghappen),Toast.LENGTH_LONG).show();
                mProgressDialog.dismiss();
            }
        }) ;
        MySingleton.getInstance(this).addToRequestQueue(stringRequest1);


    }
    public void jsonParsingHeathcareType(String response){

        ArrayList<Map> arrayList=new ArrayList<>() ;
        try {
            JSONArray jsonRoot = new JSONArray(response);
            mapHealthcareType=new HashMap<>();
            for (int i=0; i<jsonRoot.length(); i++ ){

                JSONObject jsonObject = jsonRoot.getJSONObject(i);
                int id = jsonObject.getInt("healthcare_type_id");
                String name="";
                if(commonData.getLocale()!=null)
                {
                    name = jsonObject.getString("healthcare_type_name_arabic");

                }
                else
                {
                    name = jsonObject.getString("healthcare_type_name");

                }
                mapHealthcareType.put(String.valueOf(id),name);
                //arrayList.add(map);
            }
            itemListHealthcareType = new ArrayList<StringWithTag>();
            itemListHealthcareType.add(new ReservDocAppointment.StringWithTag(" ", " "));

            for (Map.Entry<String, String> entry : mapHealthcareType.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                /* Build the StringWithTag List using these keys and values. */
                itemListHealthcareType.add(new ReservDocAppointment.StringWithTag(value, key));
            }

            spinnerAdapterHealthcareType = new ArrayAdapter<ReservDocAppointment.StringWithTag>(this, android.R.layout.simple_spinner_item, itemListHealthcareType);
            spinnerAdapterHealthcareType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spHealthcareType.setAdapter(spinnerAdapterHealthcareType);
            final String[] loadsearchkeyID = new String[1];
            spHealthcareType.setOnItemSelectedListener(this);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    // Special  Spinner and parsing data
    public  void getSpecSpinner() {
        mProgressDialog.show();
        String url="http://192.168.1.34:88/api/Citizen";
        StringRequest stringRequest1=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),"Error :"+response,Toast.LENGTH_LONG).show();
                jsonParsingSpec(response);
                mProgressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        getString(R.string.SomthingsWronghappen),Toast.LENGTH_LONG).show();
                mProgressDialog.dismiss();
            }
        }) ;
        MySingleton.getInstance(this).addToRequestQueue(stringRequest1);


    }
    public void jsonParsingSpec(String response){

        ArrayList<Map> arrayList=new ArrayList<>() ;
        try {
            JSONArray jsonRoot = new JSONArray(response);
            mapSpec=new HashMap<>();
            for (int i=0; i<jsonRoot.length(); i++ ){

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
                mapSpec.put(String.valueOf(id),name);
                //arrayList.add(map);
            }
            itemListSpec = new ArrayList<ReservDocAppointment.StringWithTag>();
            itemListSpec.add(new ReservDocAppointment.StringWithTag(" ", " "));

            for (Map.Entry<String, String> entry : mapSpec.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                /* Build the StringWithTag List using these keys and values. */
                itemListSpec.add(new ReservDocAppointment.StringWithTag(value, key));
            }

            spinnerAdapterSpec = new ArrayAdapter<ReservDocAppointment.StringWithTag>(this, android.R.layout.simple_spinner_item, itemListSpec);
            spinnerAdapterSpec.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spSpec.setAdapter(spinnerAdapterSpec);
            final String[] loadsearchkeyID = new String[1];
            spSpec.setOnItemSelectedListener(this);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Healthcare  Spinner and parsing data
    public  void getHealthcareSpinner() {
        mProgressDialog.show();
        String url="http://192.168.1.34:88/api/Citizen?dis="+dsId+"&htype="+heathTyId+"&spec="+specId;
        StringRequest stringRequest1=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),"Error :"+response,Toast.LENGTH_LONG).show();
                jsonParsingHealthcare(response);
                mProgressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        getString(R.string.SomthingsWronghappen),Toast.LENGTH_LONG).show();
                mProgressDialog.dismiss();
            }
        }) ;
        MySingleton.getInstance(this).addToRequestQueue(stringRequest1);


    }
    public void jsonParsingHealthcare(String response){

        ArrayList<Map> arrayList=new ArrayList<>() ;
        try {
            JSONArray jsonRoot = new JSONArray(response);
            mapHealthcare=new HashMap<>();
            for (int i=0; i<jsonRoot.length(); i++ ){

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
                mapHealthcare.put(String.valueOf(id),name);

                //arrayList.add(map);
            }
            itemListHealthcare = new ArrayList<ReservDocAppointment.StringWithTag>();
            itemListHealthcare.add(new ReservDocAppointment.StringWithTag(" ", " "));

            for (Map.Entry<String, String> entry : mapHealthcare.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                /* Build the StringWithTag List using these keys and values. */
                itemListHealthcare.add(new ReservDocAppointment.StringWithTag(value, key));
            }

            spinnerAdapterHealthcare = new ArrayAdapter<ReservDocAppointment.StringWithTag>(this, android.R.layout.simple_spinner_item, itemListHealthcare);
            spinnerAdapterHealthcare.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spHealthcare.setAdapter(spinnerAdapterHealthcare);
            final String[] loadsearchkeyID = new String[1];
            spHealthcare.setOnItemSelectedListener(this);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Doctor  Spinner and parsing data
    public  void getDoctorSpinner() {
        mProgressDialog.show();
        String url="http://192.168.1.34:88/api/Citizen?sid="+specId+"&hoss="+healthcareId;
        StringRequest stringRequest1=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),"Error :"+response,Toast.LENGTH_LONG).show();
                jsonParsingDoctor(response);
                mProgressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        getString(R.string.SomthingsWronghappen),Toast.LENGTH_LONG).show();
                mProgressDialog.dismiss();
            }
        }) ;
        MySingleton.getInstance(this).addToRequestQueue(stringRequest1);


    }
    public void jsonParsingDoctor(String response){

        ArrayList<Map> arrayList=new ArrayList<>() ;
        try {
            JSONArray jsonRoot = new JSONArray(response);
            mapDoctor=new HashMap<>();
            for (int i=0; i<jsonRoot.length(); i++ ){

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

                mapDoctor.put(String.valueOf(id),name);
                //arrayList.add(map);
            }
            itemListDoctor = new ArrayList<ReservDocAppointment.StringWithTag>();
            itemListDoctor.add(new ReservDocAppointment.StringWithTag(" ", " "));

            for (Map.Entry<String, String> entry : mapDoctor.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                /* Build the StringWithTag List using these keys and values. */
                itemListDoctor.add(new ReservDocAppointment.StringWithTag(value, key));
            }

            spinnerAdapterDoctor = new ArrayAdapter<ReservDocAppointment.StringWithTag>(this, android.R.layout.simple_spinner_item, itemListDoctor);
            spinnerAdapterDoctor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spDoctor.setAdapter(spinnerAdapterDoctor);
            final String[] loadsearchkeyID = new String[1];
            spDoctor.setOnItemSelectedListener(this);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    // schedual Spinner and parsing data
    public  void getSchedualSpinner() {
        mProgressDialog.show();
        String url="http://192.168.1.34:88/api/Citizen?docId="+doctorId+"&healtId="+healthcareId;
        StringRequest stringRequest1=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),"Error :"+response,Toast.LENGTH_LONG).show();
                jsonParsingSchedual(response);
                mProgressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        getString(R.string.SomthingsWronghappen),Toast.LENGTH_LONG).show();
                mProgressDialog.dismiss();
            }
        }) ;
        MySingleton.getInstance(this).addToRequestQueue(stringRequest1);


    }
    public void jsonParsingSchedual(String response){

        ArrayList<Map> arrayList=new ArrayList<>() ;
        try {
            JSONArray jsonRoot = new JSONArray(response);
            mapSchedual=new HashMap<>();
            for (int i=0; i<jsonRoot.length(); i++ ){

                JSONObject jsonObject = jsonRoot.getJSONObject(i);
                int id = jsonObject.getInt("schedule_id");
                String name = jsonObject.getString("checkup_date")+"\n"+jsonObject.getString("checkup_start")+"-" +jsonObject.getString("checkup_end");
                mapSchedual.put(String.valueOf(id),name);
                //arrayList.add(map);
            }
            itemListSchedual = new ArrayList<ReservDocAppointment.StringWithTag>();
            itemListSchedual.add(new ReservDocAppointment.StringWithTag(" ", " "));

            for (Map.Entry<String, String> entry : mapSchedual.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                /* Build the StringWithTag List using these keys and values. */
                itemListSchedual.add(new ReservDocAppointment.StringWithTag(value, key));
            }

            spinnerAdapterSchedual = new ArrayAdapter<ReservDocAppointment.StringWithTag>(this, android.R.layout.simple_spinner_item, itemListSchedual);
            spinnerAdapterSchedual.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spSchedual.setAdapter(spinnerAdapterSchedual);
            final String[] loadsearchkeyID = new String[1];
            spSchedual.setOnItemSelectedListener(this);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public <K, V> K getKey(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override

    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
        ArrayAdapter arrayAdapter= ArrayAdapter.createFromResource(this,
                R.array.service_array, android.R.layout.simple_spinner_item);
        if(view==spStat) {
            if (itemListCity != null) {

                itemListCity = null;
                spCity.setAdapter(arrayAdapter);
            }

            if (itemListRegion != null) {

                itemListRegion = null;
                spRegion.setAdapter(arrayAdapter);
            }
            if (itemListDistrict != null) {
                itemListDistrict = null;

                spDistrict.setAdapter(arrayAdapter);
            }
            if (itemListHealthcare != null) {
                itemListHealthcare = null;

                spHealthcare.setAdapter(arrayAdapter);
            }

            if (itemListDoctor != null) {
                itemListDoctor = null;

                spDoctor.setAdapter(arrayAdapter);
            }

            if (itemListSchedual != null) {
                itemListSchedual = null;

                spSchedual.setAdapter(arrayAdapter);
            }

            state = spStat.getText().toString();

            if (state ==" " || state == null)
            {
              return;
           }
            stId = Integer.parseInt(getKey(mapState, state));
            getCitySpinner();

            spCity.setOnItemSelectedListener(this);
            city = spCity.getText().toString();

        }
        if(view==spCity)
        {
            if(itemListRegion !=null) {

                itemListRegion = null;
                spRegion.setAdapter(arrayAdapter);
            }
            if(itemListDistrict!=null) {
                itemListDistrict = null;

                spDistrict.setAdapter(arrayAdapter);
            }
            if( itemListHealthcare!=null) {
                itemListHealthcare = null;

                spHealthcare.setAdapter(arrayAdapter);
            }
            if( itemListDoctor!=null) {
                itemListDoctor = null;

                spDoctor.setAdapter(arrayAdapter);
            }

            if( itemListSchedual!=null) {
                itemListSchedual = null;

                spSchedual.setAdapter(arrayAdapter);
            }


            city = spCity.getText().toString();
            if (city ==" " || city == null)
            {
                return;
            }
                ctId = Integer.parseInt(getKey(mapCity, city));
                getRegionSpinner();

        }

        if(view==spRegion)
        {

            if(itemListDistrict!=null) {
                itemListDistrict = null;

                spDistrict.setAdapter(arrayAdapter);
            }
            if( itemListHealthcare!=null) {
                itemListHealthcare = null;

                spHealthcare.setAdapter(arrayAdapter);
            }
            if( itemListDoctor!=null) {
                itemListDoctor = null;

                spDoctor.setAdapter(arrayAdapter);
            }

            if( itemListSchedual!=null) {
                itemListSchedual = null;

                spSchedual.setAdapter(arrayAdapter);
            }

            region = spRegion.getText().toString();
            if (region ==" " || region == null)
            {
                return;
            }
                reId = Integer.parseInt(getKey(mapRegion, region));
                getDistrictSpinner();

        }
        if(view==spDistrict)
        {
            if( itemListHealthcare!=null) {
                itemListHealthcare = null;

                spHealthcare.setAdapter(arrayAdapter);
            }
            if( itemListDoctor!=null) {
                itemListDoctor = null;

                spDoctor.setAdapter(arrayAdapter);
            }

            if( itemListSchedual!=null) {
                itemListSchedual = null;

                spSchedual.setAdapter(arrayAdapter);
            }
            healthTy = spHealthcareType.getText().toString();
            if (healthTy ==" " || healthTy == null)
            {
                return;
            }
                heathTyId = Integer.parseInt(getKey(mapHealthcareType, healthTy));

            district = spDistrict.getText().toString();
            if (district ==" " || district == null)
            {
                return;
            }
                dsId = Integer.parseInt(getKey(mapDistrict, district));

            spec = spSpec.getText().toString();
            if (spec ==" " || spec == null)
            {
                return;
            }
            specId = Integer.parseInt(getKey(mapSpec, spec));

            getHealthcareSpinner();

        }


        if(view==spHealthcare)
        {


            if( itemListDoctor!=null) {
                itemListDoctor = null;

                spDoctor.setAdapter(arrayAdapter);
            }

            if( itemListSchedual!=null) {
                itemListSchedual = null;

                spSchedual.setAdapter(arrayAdapter);
            }

            healthcare = spHealthcare.getText().toString();
            if (healthcare ==" " || healthcare == null)
            {
                return;
            }
                healthcareId = Integer.parseInt(getKey(mapHealthcare, healthcare));

            spec = spSpec.getText().toString();
            if (spec ==" " || spec == null)
            {
                return;
            }
            specId = Integer.parseInt(getKey(mapSpec, spec));

            getDoctorSpinner();

        }

        if(view==spSpec)
        {

            if(itemListCity !=null) {

                itemListCity = null;
                spCity.setAdapter(arrayAdapter);
            }

            if(itemListRegion !=null) {

                itemListRegion = null;
                spRegion.setAdapter(arrayAdapter);
            }
            if(itemListDistrict!=null) {
                itemListDistrict = null;

                spDistrict.setAdapter(arrayAdapter);
            }

            if( itemListDoctor!=null) {
                itemListDoctor = null;

                spDoctor.setAdapter(arrayAdapter);
            }

            if( itemListSchedual!=null) {
                itemListSchedual = null;

                spSchedual.setAdapter(arrayAdapter);
            }


            spec = spSpec.getText().toString();
            if (spec ==" " || spec == null)
            {
                return;
            }
                specId = Integer.parseInt(getKey(mapSpec, spec));

        }

        if(view==spDoctor)
        {



            if( itemListSchedual!=null) {
                itemListSchedual = null;

                spSchedual.setAdapter(arrayAdapter);
            }
            doctor = spDoctor.getText().toString();
            if (doctor ==" " || doctor == null)
            {
                return;
            }
                doctorId = Integer.parseInt(getKey(mapDoctor, doctor));


            healthcare = spHealthcare.getText().toString();
            if (healthcare ==" " || healthcare == null)
            {
                return;
            }
           healthcareId = Integer.parseInt(getKey(mapHealthcare, healthcare));
                getSchedualSpinner();

        }



        if(view==spHealthcareType)
        {
            if(itemListCity !=null) {

                itemListCity = null;
                spCity.setAdapter(arrayAdapter);
            }

            if(itemListRegion !=null) {

                itemListRegion = null;
                spRegion.setAdapter(arrayAdapter);
            }
            if(itemListDistrict!=null) {
                itemListDistrict = null;

                spDistrict.setAdapter(arrayAdapter);
            }
            if( itemListHealthcare!=null) {
                itemListHealthcare = null;

                spHealthcare.setAdapter(arrayAdapter);
            }

            if( itemListDoctor!=null) {
                itemListDoctor = null;

                spDoctor.setAdapter(arrayAdapter);
            }

            if( itemListSchedual!=null) {
                itemListSchedual = null;

                spSchedual.setAdapter(arrayAdapter);
            }
/*
            state=spStat.getText().toString();
            stId=Integer.parseInt(getKey(mapState,state));
            getCitySpinner();

            spCity.setOnItemSelectedListener(this);
            city = spCity.getText().toString();
*/
        }


    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void sendAppointment(View view) {

        healthcare=spHealthcare.getText().toString();
        if(spHealthcare.getSelectedIndex()==0)
        {


            spHealthcare.setError(  getString(R.string.Selecthealthcare));
            return;
        } else
        {
            spHealthcare.setError(null);
        }

        doctor=spDoctor.getText().toString();
        if(spDoctor.getSelectedIndex()==0)
        {
            spDoctor.setError( getString(R.string.SelectDoctor));
            return;
        } else
        {
            spDoctor.setError(null);
        }

        schedual=spSchedual.getText().toString();
        if(spSchedual.getSelectedIndex()==0)
        {
            spSchedual.setError(getString(R.string.Selectschedual));
            return;
        } else
        {
            spSchedual.setError(null);
        }

        showDialog();
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private   void showDialog()
    {new TTFancyGifDialog.Builder(ReservDocAppointment.this)
            .setTitle(getString(R.string.ReserveHealthcareAppointment))
            .setMessage(getString(R.string.Areyouwanttoreservethisappointment))
            .setPositiveBtnText(getString(R.string.Reserve))
            .setPositiveBtnBackground("#020000")
            .setNegativeBtnText(getString(R.string.Cancel))
            .setNegativeBtnBackground("#4c4c4c")
            .setGifResource(R.drawable.send)      //pass your gif, png or jpg
            .isCancellable(true)
            .OnPositiveClicked(new TTFancyGifDialogListener() {
                @Override
                public void OnClick() {

                    mProgressDialog.show();
                    String health= spHealthcare.getText().toString();
                    int ht=Integer.parseInt(getKey(mapHealthcare,health));



                    String schedual= spSchedual.getText().toString();
                    final int sch=Integer.parseInt(getKey(mapSchedual,schedual));


                    SharedPreferences sharedPreferences =getSharedPreferences("CitizenData", Context.MODE_PRIVATE);
                    final String cid=sharedPreferences.getString("Cid",null);
                    Boolean Ar=false;
                       if(commonData.getLocale()!=null)
                       {
                           Ar=true;
                       }
                    String url="http://192.168.1.34:88/api/HealthcareReservations?healthcareReservation_id=0&healthcareReservation_citizen_id="+cid+"&healthcareReservation_doctor_id="+doctorId+"&healthcareReservation_hospital_id="+healthcareId+"&schedule="+sch+"&Ar="+Ar;
                    // Toast.makeText(getApplicationContext(),""+st+""+of,Toast.LENGTH_LONG).show();
                    StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                   Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();

                            mProgressDialog.dismiss();


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(),
                                    getString(R.string.SomthingsWronghappen),Toast.LENGTH_LONG).show();

                            mProgressDialog.dismiss();

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            String state= spServices.getText().toString();
                            int docId=spServices.getSelectedIndex();
                            String office= spOffice.getText().toString();
                            int of=spServices.getSelectedIndex();
                            String date= etDate.getText().toString();
                            SharedPreferences sharedPreferences =getSharedPreferences("CitizenData", Context.MODE_PRIVATE);
                            String cid=sharedPreferences.getString("Cid",null);
                            Map<String,String>map=new HashMap<>();
                            map.put("reservation_id","0");
                            map.put("reservation_citizen_id",cid);
                            map.put("reservation_document_type_id",String.valueOf(docId));
                            map.put("reservation_office_id",String.valueOf(of));
                            map.put("reservation_date",date);

                            return  map;
                        }
                    }   ;
                    MySingleton.getInstance(ReservDocAppointment.this).addToRequestQueue(stringRequest);




                }
            })
            .OnNegativeClicked(new TTFancyGifDialogListener() {
                @Override
                public void OnClick() {
                }
            })
            .build();

    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    static class StringWithTag {
        public String string;
        public Object tag;

        public StringWithTag(String string, Object tag) {
            this.string = string;
            this.tag = tag;
        }

        @Override
        public String toString() {
            return string;
        }
    }

 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
