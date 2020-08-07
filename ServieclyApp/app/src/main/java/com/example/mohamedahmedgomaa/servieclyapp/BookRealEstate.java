package com.example.mohamedahmedgomaa.servieclyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
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

public class BookRealEstate extends AppCompatActivity implements AdapterView.OnItemSelectedListener, MaterialSpinner.OnItemSelectedListener, AdapterView.OnItemClickListener {
    List<Reservation.StringWithTag> itemListCity;
    ArrayAdapter<Reservation.StringWithTag> spinnerAdapterCity;
    List<Reservation.StringWithTag> itemListRegion;
    ArrayAdapter<Reservation.StringWithTag> spinnerAdapterRegion;
    List<Reservation.StringWithTag> itemListDistrict;
    ArrayAdapter<Reservation.StringWithTag> spinnerAdapterDistrict;
    List<Reservation.StringWithTag> itemListOffice;
    ArrayAdapter<Reservation.StringWithTag> spinnerAdapterOffice;
    EditText etDate;
    Calendar myCalendar;
    MaterialSpinner spServices;
    MaterialSpinner spOffice,spStat,spCity,spRegion,spDistrict;
    ProgressDialog mProgressDialog;
    Map<String,String>  mapDocument = null;
    Map <String,String> mapOffice = null;
    Map <String,String> mapServices=null;
    Map <String,String> mapState=null;
    Map <String,String> mapCity = null;
    Map <String,String> mapRegion=null;
    Map <String,String> mapDistrict=null;
    int stId,ctId,reId,dsId=0;
    String state,city,region,district,office,serv="";
    CoordinatorLayout coordinatorLayout;

    CommonData commonData;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        coordinatorLayout=findViewById(R.id.Reservation);
        commonData=new CommonData();

        CommonData commonData=new CommonData();
        if(commonData.getLocale()!=null)
        {
            coordinatorLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }



        myCalendar = Calendar.getInstance();
        mProgressDialog=new ProgressDialog(BookRealEstate.this);

        etDate= (EditText) findViewById(R.id.etDate);
        spServices= (MaterialSpinner) findViewById(R.id.spServices);
        spOffice=  (MaterialSpinner)findViewById(R.id.spOffice);
        spStat= (MaterialSpinner) findViewById(R.id.spStat);
        spCity=  (MaterialSpinner)findViewById(R.id.spCity);
        spRegion= (MaterialSpinner) findViewById(R.id.spRegion);
        spDistrict=  (MaterialSpinner)findViewById(R.id.spDistrict);
        getStateSpinner();
        spStat.setOnItemSelectedListener(this);
        getServicesSpinner();

        spServices.setOnItemSelectedListener(this);
/*
        getCitySpinner();
        city = spCity.getText().toString();
        ctId=Integer.parseInt(getKey(mapCity,city));

        getRegionSpinner();
        region = spRegion.getText().toString();
        reId=Integer.parseInt(getKey(mapRegion,region));

        getDistrictSpinner();
        district = spDistrict.getText().toString();
        dsId=Integer.parseInt(getKey(mapDistrict,district));
        getOfficeSpinner();


        getDocumentSpinner();
*/
     /*   ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this,
                R.array.array1, android.R.layout.simple_spinner_item);
        spState.setAdapter(adapter1);
       spState.setOnItemSelectedListener(this);

       /* if (spState.getText().toString().equals("القاهرة")) {


            ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.cairo_array, android.R.layout.simple_spinner_item);
            spOffice.setAdapter(adapter2);
        } else {
            ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.alex_array, android.R.layout.simple_spinner_item);
            spOffice.setAdapter(adapter2);
        }
        */

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                view.setMinDate(year);


                updateLabel();
            }

        };

        etDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog =  new DatePickerDialog(BookRealEstate.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis()-1000);
                datePickerDialog.show();            }
        });
    }


    private void updateLabel() {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etDate.setText(sdf.format(myCalendar.getTime()));
    }

    public void SendReserve(View view) {
        showDialog();
    }



    private   void showDialog()
    {new TTFancyGifDialog.Builder(BookRealEstate.this)
            .setTitle(getString(R.string.ReserveRealEstateAppointment))
            .setMessage(getString(R.string.Areyouwanttoreservethisappointment))
            .setPositiveBtnText(getString(R.string.Reserve))
            .setPositiveBtnBackground("#020000")
            .setNegativeBtnText(getString(R.string.Cancel))
            .setNegativeBtnBackground("#4c4c4c")
            .setGifResource(R.drawable.ppp)      //pass your gif, png or jpg
            .isCancellable(true)
            .OnPositiveClicked(new TTFancyGifDialogListener() {
                @Override
                public void OnClick() {
                    if(etDate.getText().toString().equals(null )||etDate.getText().toString().equals(""))
                    {

                        etDate.setError( getString(R.string.SelectDate));

                        return;
                    }
                    else
                    {
                        etDate.setError(null);
                    }
                    office=spOffice.getText().toString();
                    if(office.equals(null )||office.equals(""))
                    {
                        spOffice.setError( getString(R.string.Selectoffice));
                        return;
                    } else
                    {
                        spOffice.setError(null);
                    }

                    mProgressDialog.show();
                    serv= spServices.getText().toString();
                    int st=Integer.parseInt(getKey(mapServices,serv));



                    office= spOffice.getText().toString();
                    int of=Integer.parseInt(getKey(mapOffice,office));

                    String date= etDate.getText().toString().replace(" ","%20");

                    SharedPreferences sharedPreferences =getSharedPreferences("CitizenData", Context.MODE_PRIVATE);
                    String cid=sharedPreferences.getString("Cid",null);
                    boolean Ar=false;
                    if(commonData.getLocale()!=null)
                    {
                        Ar=true;
                    }

                    String url="http://192.168.1.34:88/api/Reservations?reservation_id=0&reservation_date="+date+"&reservation_office_id=" +of+"&reservation_citizen_id="+cid+"&service_id="+st+"&Ar="+Ar;
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
                            Toast.makeText(getApplicationContext(),"Error :"+error,Toast.LENGTH_LONG).show();


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
                    MySingleton.getInstance(BookRealEstate.this).addToRequestQueue(stringRequest);






                }
            })
            .OnNegativeClicked(new TTFancyGifDialogListener() {
                @Override
                public void OnClick() {
                }
            })
            .build();

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }



    @Override
    protected void onStart() {
        super.onStart();

    }

    // Services Spinner and parsing data
    public  void getServicesSpinner() {
        mProgressDialog.show();
        String url="http://192.168.1.34:88/api/Services?Govid=1";
        StringRequest stringRequest1=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),"Error :"+response,Toast.LENGTH_LONG).show();
                jsonParsingService(response);
                mProgressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error :"+error,Toast.LENGTH_LONG).show();

            }
        }) ;
        MySingleton.getInstance(this).addToRequestQueue(stringRequest1);


    }
    public void jsonParsingService(String response){

        ArrayList<Map> arrayList=new ArrayList<>() ;
        try {
            JSONArray jsonRoot = new JSONArray(response);
            mapServices=new HashMap<>();
            for (int i=0; i<jsonRoot.length(); i++ ){

                JSONObject jsonObject = jsonRoot.getJSONObject(i);
                int id = jsonObject.getInt("service_id");
                String name="";
                if(commonData.getLocale()!= null)
                {
                    name = jsonObject.getString("service_name_arabic");
                }
                else
                {
                    name = jsonObject.getString("service_name");
                }

                mapServices.put(String.valueOf(id),name);
                //arrayList.add(map);
            }
            List<Reservation.StringWithTag> itemList = new ArrayList<Reservation.StringWithTag>();
            for (Map.Entry<String, String> entry : mapServices.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                /* Build the StringWithTag List using these keys and values. */
                itemList.add(new Reservation.StringWithTag(value, key));
            }

            ArrayAdapter<Reservation.StringWithTag> spinnerAdapter = new ArrayAdapter<Reservation.StringWithTag>(this, android.R.layout.simple_spinner_item, itemList);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spServices.setAdapter(spinnerAdapter);
            final String[] loadsearchkeyID = new String[1];
            spServices.setOnItemSelectedListener(this);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
                Toast.makeText(getApplicationContext(),"Error :"+error,Toast.LENGTH_LONG).show();
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
                if ( commonData.getLocale() !=null)
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
                Toast.makeText(getApplicationContext(),"Error :"+error,Toast.LENGTH_LONG).show();
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
                Toast.makeText(getApplicationContext(),"Error :"+error,Toast.LENGTH_LONG).show();
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
                Toast.makeText(getApplicationContext(),"Error :"+error,Toast.LENGTH_LONG).show();
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

    // office Spinner and parsing data
    public  void getOfficeSpinner() {
        mProgressDialog.show();
        String url="http://192.168.1.34:88/api/Offices?DId="+dsId+"&GovId=1";
        StringRequest stringRequest1=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),"Error :"+response,Toast.LENGTH_LONG).show();
                jsonParsing(response);
                mProgressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error :"+error,Toast.LENGTH_LONG).show();
                mProgressDialog.dismiss();
            }
        }) ;
        MySingleton.getInstance(this).addToRequestQueue(stringRequest1);


    }
    public void jsonParsing(String response){

        ArrayList<Map> arrayList=new ArrayList<>() ;
        try {
            JSONArray jsonRoot = new JSONArray(response);
            mapOffice=new HashMap<>();
            for (int i=0; i<jsonRoot.length(); i++ ){

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

                mapOffice.put(String.valueOf(id),name);
                //arrayList.add(map);
            }
            itemListOffice = new ArrayList<Reservation.StringWithTag>();
            for (Map.Entry<String, String> entry : mapOffice.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                /* Build the StringWithTag List using these keys and values. */
                itemListOffice.add(new Reservation.StringWithTag(value, key));
            }

            spinnerAdapterOffice = new ArrayAdapter<Reservation.StringWithTag>(this, android.R.layout.simple_spinner_item, itemListOffice);
            spinnerAdapterOffice.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spOffice.setAdapter(spinnerAdapterOffice);
            final String[] loadsearchkeyID = new String[1];
            spOffice.setOnItemSelectedListener(this);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getDocumentSpinner() {
        mProgressDialog.show();
        String url="http://192.168.1.34:88/api/Document_Type";
        StringRequest stringRequest1=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText(getApplicationContext(),"Error :"+response,Toast.LENGTH_LONG).show();
                jsonDocumentSpinnerParsing(response);
                mProgressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error :"+error,Toast.LENGTH_LONG).show();

            }
        }) ;
        MySingleton.getInstance(this).addToRequestQueue(stringRequest1);
    }
    private void jsonDocumentSpinnerParsing(String response) {


        ArrayList<Map> arrayList=new ArrayList<>() ;
        try {
            JSONArray jsonRoot = new JSONArray(response);
            mapDocument=new HashMap<>();
            for (int i=0; i<jsonRoot.length(); i++ ){

                JSONObject jsonObject = jsonRoot.getJSONObject(i);
                int id = jsonObject.getInt("document_type_id");
                String name="";
                if(commonData.getLocale() !=null)
                {

                    name = jsonObject.getString("document_type_name_arabic");


                }
                else
                {
                    name = jsonObject.getString("document_type_name");

                }
                mapDocument.put(String.valueOf(id),name);
                //arrayList.add(map);
            }
            List<Reservation.StringWithTag> itemList = new ArrayList<Reservation.StringWithTag>();
            for (Map.Entry<String, String> entry : mapDocument.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                /* Build the StringWithTag List using these keys and values. */
                itemList.add(new Reservation.StringWithTag(value, key));
            }

            ArrayAdapter<Reservation.StringWithTag> spinnerAdapter = new ArrayAdapter<Reservation.StringWithTag>(this, android.R.layout.simple_spinner_item, itemList);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spServices.setAdapter(spinnerAdapter);
            final String[] loadsearchkeyID = new String[1];
            spServices.setOnItemSelectedListener(this);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    public <K, V> K getKey(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
        ArrayAdapter arrayAdapter= ArrayAdapter.createFromResource(this,
                R.array.service_array, android.R.layout.simple_spinner_item);
        if(view==spStat)
        {
            if(itemListRegion !=null) {

                itemListRegion = null;
                spRegion.setAdapter(arrayAdapter);
            }
            if(itemListDistrict!=null) {
                itemListDistrict = null;

                spDistrict.setAdapter(arrayAdapter);
            }
            if(itemListOffice!=null) {
                itemListOffice = null;

                spOffice.setAdapter(arrayAdapter);
            }
            state=spStat.getText().toString();
            stId=Integer.parseInt(getKey(mapState,state));
            getCitySpinner();

            spCity.setOnItemSelectedListener(this);
            city = spCity.getText().toString();

        }
        if(view==spCity)
        {
            if(itemListDistrict !=null) {

                itemListDistrict = null;
                spDistrict.setAdapter(arrayAdapter);
            }
            if(itemListOffice!=null) {
                itemListOffice = null;

                spOffice.setAdapter(arrayAdapter);
            }
            city = spCity.getText().toString();
            ctId=Integer.parseInt(getKey(mapCity,city));
            getRegionSpinner();
        }
        if(view==spRegion)
        {
            if(itemListOffice!=null) {
                itemListOffice = null;

                spOffice.setAdapter(arrayAdapter);
            }
            region = spRegion.getText().toString();
            reId=Integer.parseInt(getKey(mapRegion,region));
            getDistrictSpinner();
        }
        if(view==spDistrict)
        {
            district = spDistrict.getText().toString();
            dsId=Integer.parseInt(getKey(mapDistrict,district));
            getOfficeSpinner();
        }






    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


    }



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

/*
    public void sendmail(){



        String url="http://192.168.1.34:88/api/?";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                SharedPreferences sharedPreferences =getSharedPreferences("CitizenData", Context.MODE_PRIVATE);
                String cfName=sharedPreferences.getString("CFirstName",null);
                String csName=sharedPreferences.getString("CSecondName",null);
                String ctName=sharedPreferences.getString("CThirdName",null);
                String cfrName=sharedPreferences.getString("CFourthName",null);
                String
                String [] addres = {"mohamedahmed.mg97@gmail.com"};
                String sub = "Civil Registry: Reservation Details";
                String body = " ";


                body += "Dear, "+cfName+" "+csName+" "+ctName+" "+cfrName;
                body += "\nYou have booked an appointment in " + office+" that located in "+state+","+city+","+region+","
                        +district;
                body+="\nIn date: "+etDate.getText().toString()+" regarding to "+service
                body += "\n loc " + location;
                body += "\n pref " + pref;
                body += "\n Q " + q;
                body += "\n prise " + price;
                composeEmail(addres , sub , body);
                mProgressDialog.dismiss();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error :"+error,Toast.LENGTH_LONG).show();


                mProgressDialog.dismiss();

            }});
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);



    }

    public void composeEmail(String[] addresses, String subject , String body) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT , body);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
*/


}