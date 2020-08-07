package com.example.mohamedahmedgomaa.servieclyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

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
import com.example.mohamedahmedgomaa.servieclyapp.model.Vaccination;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Vaccinations extends AppCompatActivity implements MaterialSpinner.OnItemSelectedListener {

    List<Vaccinations.StringWithTag> itemListCity;
    ArrayAdapter<Vaccinations.StringWithTag> spinnerAdapterCity;
    List<Vaccinations.StringWithTag> itemListRegion;
    ArrayAdapter<Vaccinations.StringWithTag> spinnerAdapterRegion;
    List<Vaccinations.StringWithTag> itemListDistrict;
    ArrayAdapter<Vaccinations.StringWithTag> spinnerAdapterDistrict;
    List<Vaccinations.StringWithTag> itemlistState;

    ArrayAdapter<Vaccinations.StringWithTag> spinnerAdapterState;

    List<Vaccinations.StringWithTag> itemListChild;
    ArrayAdapter<Vaccinations.StringWithTag> spinnerAdapterchild;

    List<Vaccinations.StringWithTag> itemListVaccinationType;
    ArrayAdapter<Vaccinations.StringWithTag> spinnerAdapterVaccinationType;

    ArrayList<Vaccinations.StringWithTag> itemListHealthcareType;
    ArrayAdapter<Vaccinations.StringWithTag> spinnerAdapterHealthcareType;

    ArrayList<Vaccinations.StringWithTag> itemListSchedual;
    ArrayAdapter<Vaccinations.StringWithTag> spinnerAdapterSchedual;

    ArrayList<Vaccinations.StringWithTag> itemListHealthcare;
    ArrayAdapter<Vaccinations.StringWithTag> spinnerAdapterHealthcare;

    TextView etDate;
    Calendar myCalendar;
    MaterialSpinner spServices,spChild,spVaccinationType,spHealthcareType,spSchedual,spHealthcare;
    MaterialSpinner spOffice,spStat,spCity,spRegion,spDistrict;
    ProgressDialog mProgressDialog;
    Map <String,String>mapOffice = null;

    Map <String,String> mapChild=null;
    Map <String,String> mapVaccinationType=null;
    Map <String,String> mapHealthcareType=null;
    Map <String,String>mapSchedual = null;
    Map <String,String> mapHealthcare=null;

    Map <String,String> mapState=null;
    Map <String,String>mapCity = null;
    Map <String,String> mapRegion=null;
    Map <String,String> mapDistrict=null;
    CoordinatorLayout coordinatorLayout;
    int stId,ctId,reId,dsId,healthcareId,schedualId,vaccId,healTyId=0;
    String state,city,region,district,healthcare,schedual,vacc,healTy="";
    CommonData commonData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccinations);
  commonData =new CommonData();
        coordinatorLayout=findViewById(R.id.Vaccinations);
        CommonData commonData=new CommonData();
        if(commonData.getLocale()!=null)
        {
            coordinatorLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }

        myCalendar = Calendar.getInstance();
        mProgressDialog=new ProgressDialog(Vaccinations.this);

        spServices= (MaterialSpinner) findViewById(R.id.spServices);
        spOffice=  (MaterialSpinner)findViewById(R.id.spOffice);
        spStat= (MaterialSpinner) findViewById(R.id.spStat);
        spCity=  (MaterialSpinner)findViewById(R.id.spCity);
        spRegion= (MaterialSpinner) findViewById(R.id.spRegion);
        spDistrict=  (MaterialSpinner)findViewById(R.id.spDistrict);


        spChild= (MaterialSpinner) findViewById(R.id.spChild);
        spHealthcare=  (MaterialSpinner)findViewById(R.id.spHealthcare);
        spVaccinationType= (MaterialSpinner) findViewById(R.id.spVaccinationType);
        spHealthcareType=  (MaterialSpinner)findViewById(R.id.spHealthcareType);
        spSchedual=  (MaterialSpinner)findViewById(R.id.spSchedual);







        getStateSpinner();
        spStat.setOnItemSelectedListener(this);

        getChildSpinner();
        spChild.setOnItemSelectedListener(this);


        getHeathcareTypeSpinner();
        spHealthcareType.setOnItemSelectedListener(this);

    }
////////////////////////////////////////////////////////////////////////////////////////////////////////

    // States Spinner and parsing data
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
                        getString(R.string.SomthingsWronghappen),Toast.LENGTH_LONG).show();
                mProgressDialog.dismiss();
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
             itemlistState = new ArrayList<Vaccinations.StringWithTag>();
            itemlistState.add(new Vaccinations.StringWithTag(" ", " "));
          //  itemlistState.add(null);

            for (Map.Entry<String, String> entry : mapState.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                /* Build the StringWithTag List using these keys and values. */
                itemlistState.add(new Vaccinations.StringWithTag(value, key));

            }

            spinnerAdapterState = new ArrayAdapter<Vaccinations.StringWithTag>(this, android.R.layout.simple_spinner_item, itemlistState);
            spinnerAdapterState.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spStat.setAdapter(spinnerAdapterState);
            final String[] loadsearchkeyID = new String[1];
            spStat.setOnItemSelectedListener(this);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////

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
            itemListCity = new ArrayList<Vaccinations.StringWithTag>();
            itemListCity.add(new Vaccinations.StringWithTag(" ", " "));

            //   itemListCity.add(null);
            for (Map.Entry<String, String> entry : mapCity.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                /* Build the StringWithTag List using these keys and values. */
                itemListCity.add(new Vaccinations.StringWithTag(value, key));
            }

            spinnerAdapterCity = new ArrayAdapter<Vaccinations.StringWithTag>(this, android.R.layout.simple_spinner_item, itemListCity);
            spinnerAdapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spCity.setAdapter(spinnerAdapterCity);
            final String[] loadsearchkeyID = new String[1];
            spCity.setOnItemSelectedListener(this);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
            itemListRegion = new ArrayList<Vaccinations.StringWithTag>();
            itemListRegion.add(new Vaccinations.StringWithTag(" ", " "));

            for (Map.Entry<String, String> entry : mapRegion.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                /* Build the StringWithTag List using these keys and values. */
                itemListRegion.add(new Vaccinations.StringWithTag(value, key));
            }

            spinnerAdapterRegion = new ArrayAdapter<Vaccinations.StringWithTag>(this, android.R.layout.simple_spinner_item, itemListRegion);
            spinnerAdapterRegion.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spRegion.setAdapter(spinnerAdapterRegion);
            final String[] loadsearchkeyID = new String[1];
            spRegion.setOnItemSelectedListener(this);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////

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
            itemListDistrict = new ArrayList<Vaccinations.StringWithTag>();
            itemListDistrict.add(new Vaccinations.StringWithTag(" ", " "));

            for (Map.Entry<String, String> entry : mapDistrict.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                /* Build the StringWithTag List using these keys and values. */
                itemListDistrict.add(new Vaccinations.StringWithTag(value, key));
            }

            spinnerAdapterDistrict = new ArrayAdapter<Vaccinations.StringWithTag>(this, android.R.layout.simple_spinner_item, itemListDistrict);
            spinnerAdapterDistrict.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spDistrict.setAdapter(spinnerAdapterDistrict);
            final String[] loadsearchkeyID = new String[1];
            spDistrict.setOnItemSelectedListener(this);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Child Spinner and parsing data
    public  void getChildSpinner() {
        mProgressDialog.show();

        SharedPreferences sharedPreferences =getSharedPreferences("CitizenData", Context.MODE_PRIVATE);
        String cid=sharedPreferences.getString("Cid",null);
        String url="http://192.168.1.34:88/api/Citizen?FId="+cid;
        StringRequest stringRequest1=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),"Error :"+response,Toast.LENGTH_LONG).show();
                jsonParsingChild(response);
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
    public void jsonParsingChild(String response){

        ArrayList<Map> arrayList=new ArrayList<>() ;
        try {
            JSONArray jsonRoot = new JSONArray(response);
            mapChild=new HashMap<>();
            for (int i=0; i<jsonRoot.length(); i++ ){

                JSONObject jsonObject = jsonRoot.getJSONObject(i);
                int id = jsonObject.getInt("citizen_id");
                String name="";
                if(commonData.getLocale()!=null)
                {
                     name = jsonObject.getString("citizen_first_name_arabic")+" "+jsonObject.getString("citizen_second_name_arabic");

                }
                else
                {
                     name = jsonObject.getString("citizen_first_name")+" "+jsonObject.getString("citizen_second_name");

                }
                mapChild.put(String.valueOf(id),name);
                //arrayList.add(map);
            }
            itemListChild = new ArrayList<Vaccinations.StringWithTag>();
            itemListChild.add(new Vaccinations.StringWithTag(" ", " "));

            //itemListChild.add(null);
            for (Map.Entry<String, String> entry : mapChild.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                /* Build the StringWithTag List using these keys and values. */
                itemListChild.add(new Vaccinations.StringWithTag(value, key));
            }

            spinnerAdapterchild = new ArrayAdapter<Vaccinations.StringWithTag>(this, android.R.layout.simple_spinner_item, itemListChild);
            spinnerAdapterchild.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spChild.setAdapter(spinnerAdapterchild);
            final String[] loadsearchkeyID = new String[1];
            spChild.setOnItemSelectedListener(this);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


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
            itemListHealthcareType = new ArrayList<Vaccinations.StringWithTag>();
            itemListHealthcareType.add(new Vaccinations.StringWithTag(" ", " "));

            for (Map.Entry<String, String> entry : mapHealthcareType.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                /* Build the StringWithTag List using these keys and values. */
                itemListHealthcareType.add(new Vaccinations.StringWithTag(value, key));
            }

            spinnerAdapterHealthcareType = new ArrayAdapter<Vaccinations.StringWithTag>(this, android.R.layout.simple_spinner_item, itemListHealthcareType);
            spinnerAdapterHealthcareType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spHealthcareType.setAdapter(spinnerAdapterHealthcareType);
            final String[] loadsearchkeyID = new String[1];
            spHealthcareType.setOnItemSelectedListener(this);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //  Vaccination Type Spinner and parsing data
    public  void getVaccinationTypeSpinner() {
        mProgressDialog.show();
        String url="http://192.168.1.34:88/api/Citizen?cti="+ctId;
        StringRequest stringRequest1=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),"Error :"+response,Toast.LENGTH_LONG).show();
                jsonParsingVaccinationType(response);
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
    public void jsonParsingVaccinationType(String response){

        ArrayList<Map> arrayList=new ArrayList<>() ;
        try {
            JSONArray jsonRoot = new JSONArray(response);
            mapVaccinationType=new HashMap<>();
            for (int i=0; i<jsonRoot.length(); i++ ){

                JSONObject jsonObject = jsonRoot.getJSONObject(i);
                int id = jsonObject.getInt("vaccination_type_id");
                String name="";
                if(commonData.getLocale()!=null)
                {
                    name = jsonObject.getString("vaccination_type_name_arabic");

                }
                else
                {
                    name = jsonObject.getString("vaccination_type_name");

                }
                mapVaccinationType.put(String.valueOf(id),name);
                //arrayList.add(map);
            }
            itemListVaccinationType = new ArrayList<Vaccinations.StringWithTag>();
            itemListVaccinationType.add(new Vaccinations.StringWithTag(" ", " "));

            for (Map.Entry<String, String> entry : mapVaccinationType.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                /* Build the StringWithTag List using these keys and values. */
                itemListVaccinationType.add(new Vaccinations.StringWithTag(value, key));
            }

            spinnerAdapterVaccinationType = new ArrayAdapter<Vaccinations.StringWithTag>(this, android.R.layout.simple_spinner_item, itemListVaccinationType);
            spinnerAdapterVaccinationType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spVaccinationType.setAdapter(spinnerAdapterVaccinationType);
            final String[] loadsearchkeyID = new String[1];
            spVaccinationType.setOnItemSelectedListener(this);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Healthcare  Spinner and parsing data
    public  void getHealthcareSpinner() {
        mProgressDialog.show();
    /*    String vacc=spVaccinationType.getText().toString();
        int vacId=Integer.parseInt(getKey(mapVaccinationType,vacc));

        String dis=spDistrict.getText().toString();
        dsId=Integer.parseInt(getKey(mapDistrict,dis));

        String health=spHealthcare.getText().toString();
        healthcareId=Integer.parseInt(getKey(mapHealthcare,health));
*/

        String url="http://192.168.1.34:88/api/Citizen?VtId="+vaccId+"&DId="+dsId+"&HCTId="+healTyId;
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
            itemListHealthcare = new ArrayList<Vaccinations.StringWithTag>();
            itemListHealthcare.add(new Vaccinations.StringWithTag(" ", " "));

            for (Map.Entry<String, String> entry : mapHealthcare.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                /* Build the StringWithTag List using these keys and values. */
                itemListHealthcare.add(new Vaccinations.StringWithTag(value, key));
            }

            spinnerAdapterHealthcare = new ArrayAdapter<Vaccinations.StringWithTag>(this, android.R.layout.simple_spinner_item, itemListHealthcare);
            spinnerAdapterHealthcare.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spHealthcare.setAdapter(spinnerAdapterHealthcare);
            final String[] loadsearchkeyID = new String[1];
            spHealthcare.setOnItemSelectedListener(this);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // schedual Spinner and parsing data
    public  void getSchedualSpinner() {
        mProgressDialog.show();

        String url="http://192.168.1.34:88/api/Citizen?hos="+healthcareId+"&vacType="+vaccId;
        StringRequest stringRequest1=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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
            itemListSchedual = new ArrayList<Vaccinations.StringWithTag>();
            itemListSchedual.add(new Vaccinations.StringWithTag(" ", " "));

            for (Map.Entry<String, String> entry : mapSchedual.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                /* Build the StringWithTag List using these keys and values. */
                itemListSchedual.add(new Vaccinations.StringWithTag(value, key));
            }

            spinnerAdapterSchedual = new ArrayAdapter<Vaccinations.StringWithTag>(this, android.R.layout.simple_spinner_item, itemListSchedual);
            spinnerAdapterSchedual.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spSchedual.setAdapter(spinnerAdapterSchedual);
            final String[] loadsearchkeyID = new String[1];
            spSchedual.setOnItemSelectedListener(this);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public <K, V> K getKey(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
        ArrayAdapter arrayAdapter= ArrayAdapter.createFromResource(this,
                R.array.service_array, android.R.layout.simple_spinner_item);


        if(view==spVaccinationType)
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
            if(itemListHealthcare!=null) {
                itemListHealthcare = null;

                spHealthcare.setAdapter(arrayAdapter);
            }
            if(itemListSchedual!=null) {
                itemListSchedual = null;

                spSchedual.setAdapter(arrayAdapter);
            }



        }

        if(view==spChild)
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
            if(itemListHealthcare!=null) {
                itemListHealthcare = null;

                spHealthcare.setAdapter(arrayAdapter);
            }
            if(itemListSchedual!=null) {
                itemListSchedual = null;

                spSchedual.setAdapter(arrayAdapter);
            }

            if(itemListVaccinationType!=null) {
                itemListVaccinationType = null;

                spVaccinationType.setAdapter(arrayAdapter);
            }
            String ch=spChild.getText().toString();
            if (ch ==" " || ch == null)
            {
                return;
            }
            ctId=Integer.parseInt(getKey(mapChild,ch));
            getVaccinationTypeSpinner();


        }


        if(view==spHealthcareType)
        {
            if(itemlistState !=null) {

                itemlistState = null;
                spStat.setAdapter(arrayAdapter);
            }
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
            if(itemListHealthcare!=null) {
                itemListHealthcare = null;

                spHealthcare.setAdapter(arrayAdapter);
            }
            if(itemListSchedual!=null) {
                itemListSchedual = null;

                spSchedual.setAdapter(arrayAdapter);
            }
            healTy=spHealthcareType.getText().toString();
            if (healTy ==" " || healTy == null)
            {
                return;
            }
            healTyId=Integer.parseInt(getKey(mapHealthcareType,healTy));
            getStateSpinner();


        }

        if(view==spStat)
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
            if(itemListHealthcare!=null) {
                itemListHealthcare = null;

                spHealthcare.setAdapter(arrayAdapter);
            }
            if(itemListSchedual!=null) {
                itemListSchedual = null;

                spSchedual.setAdapter(arrayAdapter);
            }
            state=spStat.getText().toString();
            if (state ==" " || state == null)
            {
                return;
            }
            stId=Integer.parseInt(getKey(mapState,state));
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
            if(itemListHealthcare!=null) {
                itemListHealthcare = null;

                spHealthcare.setAdapter(arrayAdapter);
            }
            if(itemListSchedual!=null) {
                itemListSchedual = null;

                spSchedual.setAdapter(arrayAdapter);
            }
            city = spCity.getText().toString();
            if (city ==" " || city == null)
            {
                return;
            }
            ctId=Integer.parseInt(getKey(mapCity,city));
            getRegionSpinner();
        }
        if(view==spRegion)
        {
            if(itemListDistrict!=null) {
                itemListDistrict = null;

                spDistrict.setAdapter(arrayAdapter);
            }
            if(itemListHealthcare!=null) {
                itemListHealthcare = null;

                spHealthcare.setAdapter(arrayAdapter);
            }
            if(itemListSchedual!=null) {
                itemListSchedual = null;

                spSchedual.setAdapter(arrayAdapter);
            }
            region = spRegion.getText().toString();
            if (region ==" " || region == null)
            {
                return;
            }
            reId=Integer.parseInt(getKey(mapRegion,region));
            getDistrictSpinner();
        }
        if(view==spDistrict)
        {
            if(itemListHealthcare!=null) {
                itemListHealthcare = null;

                spHealthcare.setAdapter(arrayAdapter);
            }
            if(itemListSchedual!=null) {
                itemListSchedual = null;

                spSchedual.setAdapter(arrayAdapter);
            }
            district = spDistrict.getText().toString();
            if (district ==" " || district == null)
            {
                return;
            }
            dsId=Integer.parseInt(getKey(mapDistrict,district));

            vacc = spVaccinationType.getText().toString();
            if (vacc ==" " || vacc == null)
            {
                return;
            }
            vaccId=Integer.parseInt(getKey(mapVaccinationType,vacc));

            healTy = spHealthcareType.getText().toString();
            if (healTy ==" " || healTy == null)
            {
                return;
            }
            healTyId=Integer.parseInt(getKey(mapHealthcareType,healTy));


            getHealthcareSpinner();
        }
        if(view==spHealthcare)
        {
            if(itemListSchedual!=null) {
                itemListSchedual = null;

                spSchedual.setAdapter(arrayAdapter);
            }
            healthcare = spHealthcare.getText().toString();
            if (healthcare ==" " || healthcare == null)
            {
                return;
            }
            vacc = spVaccinationType.getText().toString();
            if (vacc ==" " || vacc == null)
            {
                return;
            }
            vaccId=Integer.parseInt(getKey(mapVaccinationType,vacc));

            healthcareId=Integer.parseInt(getKey(mapHealthcare,healthcare));
            getSchedualSpinner();
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void sendReservVacc(View view) {

         //Toast.makeText(Vaccinations.this,etDate.getText().toString(),Toast.LENGTH_LONG).show();

        healthcare=spHealthcare.getText().toString();


        if(spChild.getSelectedIndex()==0)
        {
            spChild.setError(getString(R.string.SelectChlid));
            return;
        } else
        {
            spChild.setError(null);
        }
        if(spVaccinationType.getSelectedIndex()==0)
        {
            spVaccinationType.setError(getString(R.string.SelectVaccination));
            return;
        } else
        {
            spVaccinationType.setError(null);
        }

        if(spHealthcare.getSelectedIndex()==0)
        {

            spHealthcare.setError(   getString(R.string.Selecthealthcare));
            return;
        } else
        {
            spHealthcare.setError(null);
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

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private   void showDialog()
    {new TTFancyGifDialog.Builder(Vaccinations.this)
            .setTitle(getString(R.string.ReserveVaccinationAppointment))
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

                    String vac= spVaccinationType.getText().toString();
                    int vacId=Integer.parseInt(getKey(mapVaccinationType,vac));

                    String ch= spChild.getText().toString();
                    int chId=Integer.parseInt(getKey(mapChild,ch));

                    String schedual= spSchedual.getText().toString();
                    int sch=Integer.parseInt(getKey(mapSchedual,schedual));

                    SharedPreferences sharedPreferences =getSharedPreferences("CitizenData", Context.MODE_PRIVATE);
                    String cid=sharedPreferences.getString("Cid",null);
                    boolean Ar=false;
                        if(commonData.getLocale()!=null)
                        {
                            Ar=true;
                        }
                    String url="http://192.168.1.34:88/api/VaccinationReservations?VaccReservation_id=0&VaccReservation_HealthCare_id="+ht+"&VaccReservation_VaccinationType_id="+vacId+"&VaccReservation_Citizen_id="+cid+"&schedule="+sch+"&VaccReservation_child_id="+chId+"&Ar="+Ar;
                    // Toast.makeText(getApplicationContext(),""+st+""+of,Toast.LENGTH_LONG).show();
                    StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //      Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
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
                    MySingleton.getInstance(Vaccinations.this).addToRequestQueue(stringRequest);






                }
            })
            .OnNegativeClicked(new TTFancyGifDialogListener() {
                @Override
                public void OnClick() {
                }
            })
            .build();

    }
 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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

}
