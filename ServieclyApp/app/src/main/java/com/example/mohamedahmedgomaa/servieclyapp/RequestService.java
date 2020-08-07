package com.example.mohamedahmedgomaa.servieclyapp;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.LayoutDirection;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.mohamedahmedgomaa.servieclyapp.Common.CommonData;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.ReferenceQueue;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class RequestService extends AppCompatActivity implements MaterialSpinner.OnItemSelectedListener {
    EditText edAddress;
    TextView tvPrice;
    Date date;
    ElegantNumberButton number_button;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    ProgressDialog mProgressDialog;
    String service,address,lang,nCopies,id,governmentBody;
    int Govid;
    List<RequestService.StringWithTag> itemListService;
    ArrayAdapter<RequestService.StringWithTag> spinnerAdapterService;
    MaterialSpinner spService,spGovernmentBody;
    Map<String,String>mapServices;
    Map<String,String>mapServicesPrice;
    Map<String,String>mapGovernmentBody;
    CoordinatorLayout coordinatorLayout;
    CommonData commonData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_service);
        commonData=new CommonData();
        mProgressDialog=new ProgressDialog(RequestService.this);
        mProgressDialog.setCancelable(false);
        coordinatorLayout=findViewById(R.id.RequestService);
        tvPrice=findViewById(R.id.tvPrice);
        //edAddress= (EditText) findViewById(R.id.edAddress);
        number_button= (ElegantNumberButton) findViewById(R.id.number_button);
        spService= (MaterialSpinner) findViewById(R.id.spService);
        spGovernmentBody= (MaterialSpinner) findViewById(R.id.spGovernmentBody);
        radioGroup=  findViewById(R.id.radioLanguage);

        CommonData commonData=new CommonData();
        if(commonData.getLocale()!=null)
        {
            coordinatorLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }

       /* ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this, R.array.service_array, android.R.layout.simple_spinner_item);
        spService.setAdapter(adapter1);
        int selectedId = radioGroup.getCheckedRadioButtonId();
        */
                      getGovernementSpinner();

        getServicesSpinner();

        // find the radiobutton by returned id
      //  radioButton = (RadioButton) findViewById(selectedId);

    }
    @TargetApi(Build.VERSION_CODES.O)


    public  void getGovernementSpinner() {
        mProgressDialog.show();
        mProgressDialog.show();
        String url="http://192.168.1.34:88/api/governement_body";
        StringRequest stringRequest1=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),"Error :"+response,Toast.LENGTH_LONG).show();
                jsonParsinggovernement_body(response);
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
    public void jsonParsinggovernement_body(String response){

        ArrayList<Map> arrayList=new ArrayList<>() ;
        try {
            JSONArray jsonRoot = new JSONArray(response);
            mapGovernmentBody=new HashMap<>();
            for (int i=0; i<jsonRoot.length(); i++ ){

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
                mapGovernmentBody.put(String.valueOf(id),name);
                //arrayList.add(map);
            }
            List<RequestService.StringWithTag> itemList = new ArrayList<RequestService.StringWithTag>();
            itemList.add(new RequestService.StringWithTag(" ", " "));

            for (Map.Entry<String, String> entry : mapGovernmentBody.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                /* Build the StringWithTag List using these keys and values. */
                itemList.add(new RequestService.StringWithTag(value, key));
            }

            ArrayAdapter<RequestService.StringWithTag> spinnerAdapter = new ArrayAdapter<RequestService.StringWithTag>(this, android.R.layout.simple_spinner_item, itemList);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spGovernmentBody.setAdapter(spinnerAdapter);
            final String[] loadsearchkeyID = new String[1];
            spGovernmentBody.setOnItemSelectedListener(this);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void sendRequest(View view) {
        if (spGovernmentBody.getSelectedIndex()==0)
        {
            spGovernmentBody.setError( getString(R.string.SelectGove));
            return;
        }
        else
        {
            spGovernmentBody.setError(null);
        }
        if (spService.getSelectedIndex()==0)
        {
            spService.setError( getString(R.string.PleaseSelectService));
            return;
        }
        else
        {
            spService.setError(null);
        }
               showAlertDialog();

    /*    if(radioGroup.getCheckedRadioButtonId()==0)
        {
            return;
        }
        if(edAddress.getText().toString().equals(null )||edAddress.getText().toString().equals(""))
        {
            edAddress.setError("Enter your Address");
            return;
        }

        mProgressDialog.show();
       /* String service= spService.getText().toString();
        String address= edAddress.getText().toString();
        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedId);
        String lang= radioButton.getText().toString();
        String nCopies=number_button.getNumber();
        SharedPreferences sharedPreferences =getSharedPreferences("CitizenData", Context.MODE_PRIVATE);
        String id=sharedPreferences.getString("CId",null);

        Date date= Calendar.getInstance().getTime();
*/
/*
    SimpleDateFormat format=new SimpleDateFormat("yyy-MM-dd");
    Date da=new Date(System.currentTimeMillis());
       String d=format.format(da).replace(" ","%20");
         service= spService.getText().toString().replace(" ","%20");
         address= edAddress.getText().toString().replace(" ","%20");
        int selectedId = radioGroup.getCheckedRadioButtonId();
        service = spService.getText().toString();
        int serv=Integer.parseInt(getKey(mapServices,service));
        radioButton = (RadioButton) findViewById(selectedId);
         lang= radioButton.getText().toString();
         nCopies=number_button.getNumber();
        // Toast.makeText(this,d,Toast.LENGTH_LONG).show();
        SharedPreferences sharedPreferences =getSharedPreferences("CitizenData", Context.MODE_PRIVATE);
         id=sharedPreferences.getString("Cid",null);
         String url="http://192.168.1.34:88/api/RequestsApi?request_id=0&request_citizenId="+id+"&address="+address+"&governmentAgency=Civil%20Registration&service="+serv+"&language="+lang+"&quantity="+nCopies+"&typeRequest=1&date="+d;


        StringRequest stringRequest1=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
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
                                     //   Log.e("myerror ", error.toString());
                mProgressDialog.dismiss();
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(stringRequest1);
*/

    }


    public  void getServicesSpinner() {
        mProgressDialog.show();
        String url="http://192.168.1.34:88/api/Services?Govid="+Govid;
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
                Toast.makeText(getApplicationContext(),
                        getString(R.string.SomthingsWronghappen),Toast.LENGTH_LONG).show();
                mProgressDialog.dismiss();
            }
        }) ;
        MySingleton.getInstance(this).addToRequestQueue(stringRequest1);


    }
    public void jsonParsing(String response){


        try {
            JSONArray jsonRoot = new JSONArray(response);
             mapServices=new HashMap<>();
            mapServicesPrice=new HashMap<>();
            for (int i=0; i<jsonRoot.length(); i++ ){

                JSONObject jsonObject = jsonRoot.getJSONObject(i);
                int id = jsonObject.getInt("service_id");
                String name="";
                String price="";
                price = jsonObject.getString("service_price");
                if(commonData.getLocale()!=null)
                 {
                      name = jsonObject.getString("service_name_arabic");
                 }
                 else
                 {
                      name = jsonObject.getString("service_name");
                 }

                mapServices.put(String.valueOf(id),name);
                mapServicesPrice.put(String.valueOf(id),price);
                //arrayList.add(map);
            }
            itemListService = new ArrayList<RequestService.StringWithTag>();
            itemListService.add(new RequestService.StringWithTag(" ", " "));

            for (Map.Entry<String, String> entry : mapServices.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                /* Build the StringWithTag List using these keys and values. */
                itemListService.add(new RequestService.StringWithTag(value, key));
            }

           spinnerAdapterService = new ArrayAdapter<RequestService.StringWithTag>(this, android.R.layout.simple_spinner_item, itemListService);
            spinnerAdapterService.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spService.setAdapter(spinnerAdapterService);
            final String[] loadsearchkeyID = new String[1];
            spService.setOnItemSelectedListener(this);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
        ArrayAdapter arrayAdapter= ArrayAdapter.createFromResource(this,
                R.array.service_array, android.R.layout.simple_spinner_item);
        if(view==spGovernmentBody)
        {
            if(itemListService !=null) {

                itemListService = null;
                spService.setAdapter(arrayAdapter);
            }
            if ( tvPrice.getText().toString() !="0.0" ||  tvPrice.getText().toString() != null)
            {
                tvPrice.setText("0.0");
            }
            governmentBody=spGovernmentBody.getText().toString();
            if (governmentBody ==" " || governmentBody == null)
            {
                return;
            }
            Govid=Integer.parseInt(getKey(mapGovernmentBody,governmentBody));
            getServicesSpinner();
            spService.setOnItemSelectedListener(this);
            service = spService.getText().toString();

        }

        if(view==spService)
        {

            if ( tvPrice.getText().toString() !="0.0" ||  tvPrice.getText().toString() == null)
            {
                tvPrice.setText("0.0");

            }

            service=spService.getText().toString();
            if (service ==" " || service == null)
            {
                tvPrice.setText("0.0");
                return;
            }
            String servId=getKey(mapServices,service);
            String Price=mapServicesPrice.get(servId);
            tvPrice.setText(Price);
            spService.setOnItemSelectedListener(this);






        }







    }



    private static class StringWithTag {
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

    public <K, V> K getKey(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }


    private void showAlertDialog() {
        final AlertDialog.Builder alertDialog=new AlertDialog.Builder(RequestService.this);
        alertDialog.setTitle(R.string.OnemoreStep);
        alertDialog.setMessage(R.string.EnteryourPlaceofReceipt);

        final EditText edtAddress=new EditText(RequestService.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT );

        if (commonData.getLocale()!=null)
        {
            layoutParams.setLayoutDirection(LayoutDirection.RTL);
        }
        edtAddress.setLayoutParams(layoutParams);
        alertDialog.setView(edtAddress);
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        alertDialog.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
          showDialog(edtAddress.getText().toString());
                dialog.dismiss();

            }
        });

        alertDialog.setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private   void showDialog(final String Address)
    {new TTFancyGifDialog.Builder(RequestService.this)
            .setTitle(getString(R.string.RequestServiceFromCivilRegistry))
            .setMessage(getString(R.string.Areyouwanttosendthisrequest))
            .setPositiveBtnText(getString(R.string.Send))
            .setPositiveBtnBackground("#020000")
            .setNegativeBtnText(getString(R.string.Cancel))
            .setNegativeBtnBackground("#4c4c4c")
            .setGifResource(R.drawable.send)      //pass your gif, png or jpg
            .isCancellable(true)
            .OnPositiveClicked(new TTFancyGifDialogListener() {
                @Override
                public void OnClick() {
                    if( Address.equals(null )|| Address.equals(""))
                    {

                        Toast.makeText(getApplicationContext(), getString(R.string.pleaseEnteryouraddress),Toast.LENGTH_SHORT).show();
                        return;

                    }
                    String serve= spService.getText().toString();



                    mProgressDialog.show();

                    SimpleDateFormat format=new SimpleDateFormat("yyy-MM-dd");
                    Date da=new Date(System.currentTimeMillis());
                    String d=format.format(da).replace(" ","%20");
                    service= spService.getText().toString().replace(" ","%20");
                    address= Address.replace(" ","%20");
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    governmentBody = spGovernmentBody.getText().toString();
                    Govid=Integer.parseInt(getKey(mapGovernmentBody,governmentBody));
                    service = spService.getText().toString();
                    int serv=Integer.parseInt(getKey(mapServices,service));
                    radioButton = (RadioButton) findViewById(selectedId);
                    lang= radioButton.getText().toString();
                    nCopies=number_button.getNumber();
                    // Toast.makeText(this,d,Toast.LENGTH_LONG).show();
                    SharedPreferences sharedPreferences =getSharedPreferences("CitizenData", Context.MODE_PRIVATE);
                    id=sharedPreferences.getString("Cid",null);
                    boolean Ar=false;
                    if(commonData.getLocale()!=null)
                    {
                        Ar=true;
                    }
                    String url="http://192.168.1.34:88/api/RequestsApi?request_id=0&request_citizenId="+id+"&address="+address+"&governmentAgency="+Govid+"&service="+serv+"&language="+lang+"&quantity="+nCopies+"&typeRequest=1&date="+d+"&Ar="+Ar;


                    StringRequest stringRequest1=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
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
                            //   Log.e("myerror ", error.toString());
                            mProgressDialog.dismiss();
                        }
                    });
                    MySingleton.getInstance(RequestService.this).addToRequestQueue(stringRequest1);




                }
            })
            .OnNegativeClicked(new TTFancyGifDialogListener() {
                @Override
                public void OnClick() {
                }
            })
            .build();

    }






    /**
     * Enables https connections
     */
    @SuppressLint("TrulyRandom")
    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception ignored) {
        }
    }
}
