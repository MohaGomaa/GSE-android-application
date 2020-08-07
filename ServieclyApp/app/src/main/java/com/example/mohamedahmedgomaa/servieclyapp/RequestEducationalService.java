package com.example.mohamedahmedgomaa.servieclyapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.LayoutDirection;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.mohamedahmedgomaa.servieclyapp.Common.CommonData;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
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

public class RequestEducationalService extends AppCompatActivity implements MaterialSpinner.OnItemSelectedListener {


    List<RequestEducationalService.StringWithTag> itemListChild;
    ArrayAdapter<RequestEducationalService.StringWithTag> spinnerAdapterchild;

    List<RequestEducationalService.StringWithTag> itemListPhases;
    ArrayAdapter<RequestEducationalService.StringWithTag> spinnerAdapterPhases;

    TextView etDate;
    Calendar myCalendar;
    MaterialSpinner  spChild, spPhases;
    ProgressDialog mProgressDialog;

    Map<String, String> mapChild = null;

    CoordinatorLayout coordinatorLayout;
    int childId = 0;
    String phase = "";
    CommonData commonData;
    Date date;
    ElegantNumberButton number_button;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    String child, address, lang, nCopies, id, governmentBody;


    Map<String, String> mapPhases;
    Map<String, String> mapGovernmentBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_educational_service);
        commonData = new CommonData();
        mProgressDialog = new ProgressDialog(RequestEducationalService.this);
        mProgressDialog.setCancelable(false);
        coordinatorLayout = findViewById(R.id.RequestService);
        //edAddress= (EditText) findViewById(R.id.edAddress);
        number_button = (ElegantNumberButton) findViewById(R.id.number_button);
        radioGroup = findViewById(R.id.radioLanguage);

        CommonData commonData = new CommonData();
        if (commonData.getLocale() != null) {
            coordinatorLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }

       /* ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this, R.array.service_array, android.R.layout.simple_spinner_item);
        spService.setAdapter(adapter1);
        int selectedId = radioGroup.getCheckedRadioButtonId();
        */

        // find the radiobutton by returned id
        //  radioButton = (RadioButton) findViewById(selectedId);
        getChildSpinner();
    }

    @TargetApi(Build.VERSION_CODES.O)




    public void sendRequest(View view) {
        showAlertDialog();



    }


    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
        ArrayAdapter arrayAdapter = ArrayAdapter.createFromResource(this,
                R.array.service_array, android.R.layout.simple_spinner_item);
        if (view == spChild) {
            if (itemListPhases != null) {

                itemListPhases = null;
                spPhases.setAdapter(arrayAdapter);
            }
            child = spChild.getText().toString();
            childId = Integer.parseInt(getKey(mapGovernmentBody, governmentBody));
            getPhasesSpinner();
            spPhases.setOnItemSelectedListener(this);
            phase = spPhases.getText().toString();


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
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(RequestEducationalService.this);
        alertDialog.setTitle(R.string.OnemoreStep);
        alertDialog.setMessage(R.string.EnteryourPlaceofReceipt);

        final EditText edtAddress = new EditText(RequestEducationalService.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        if (commonData.getLocale() != null) {
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

    private void showDialog(final String Address) {
        new TTFancyGifDialog.Builder(RequestEducationalService.this)
                .setTitle(getString(R.string.RequestServiceFromMinistryEducation))
                .setMessage(getString(R.string.Areyouwanttosendthisrequest))
                .setPositiveBtnText(getString(R.string.Send))
                .setPositiveBtnBackground("#020000")
                .setNegativeBtnText(getString(R.string.Cancel))
                .setNegativeBtnBackground("#4c4c4c")
                .setGifResource(R.drawable.ppp)      //pass your gif, png or jpg
                .isCancellable(true)
                .OnPositiveClicked(new TTFancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                        if (Address.equals(null) || Address.equals("")) {

                            Toast.makeText(getApplicationContext(), getString(R.string.pleaseEnteryouraddress), Toast.LENGTH_SHORT).show();
                            return;

                        }

                        mProgressDialog.show();

                        SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd");
                        Date da = new Date(System.currentTimeMillis());
                        String d = format.format(da).replace(" ", "%20");
                        address = Address.replace(" ", "%20");
                        int selectedId = radioGroup.getCheckedRadioButtonId();
                        radioButton = (RadioButton) findViewById(selectedId);
                        lang = radioButton.getText().toString();
                        nCopies = number_button.getNumber();
                        // Toast.makeText(this,d,Toast.LENGTH_LONG).show();
                        SharedPreferences sharedPreferences = getSharedPreferences("CitizenData", Context.MODE_PRIVATE);
                        id = sharedPreferences.getString("Cid", null);
                        boolean Ar = false;
                        if (commonData.getLocale() != null) {
                            Ar = true;
                        }
                        String url = "http://192.168.1.34:88/api/RequestsApi?request_id=0&request_citizenId=" + id + "&address=" + address + "&governmentAgency="  + "&service="  + "&language=" + lang + "&quantity=" + nCopies + "&typeRequest=1&date=" + d + "&Ar=" + Ar;


                        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                                mProgressDialog.dismiss();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "Error :" + error, Toast.LENGTH_LONG).show();
                                //   Log.e("myerror ", error.toString());
                                mProgressDialog.dismiss();
                            }
                        });
                        MySingleton.getInstance(RequestEducationalService.this).addToRequestQueue(stringRequest1);


                    }
                })
                .OnNegativeClicked(new TTFancyGifDialogListener() {
                    @Override
                    public void OnClick() {
                    }
                })
                .build();

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
            itemListChild = new ArrayList<RequestEducationalService.StringWithTag>();
            //itemListChild.add(null);
            for (Map.Entry<String, String> entry : mapChild.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                /* Build the StringWithTag List using these keys and values. */
                itemListChild.add(new RequestEducationalService.StringWithTag(value, key));
            }

            spinnerAdapterchild = new ArrayAdapter<RequestEducationalService.StringWithTag>(this, android.R.layout.simple_spinner_item, itemListChild);
            spinnerAdapterchild.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spChild.setAdapter(spinnerAdapterchild);
            final String[] loadsearchkeyID = new String[1];
            spChild.setOnItemSelectedListener(this);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    // Phase Spinner and parsing data
    public  void getPhasesSpinner() {
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
            mapPhases=new HashMap<>();
            for (int i=0; i<jsonRoot.length(); i++ ){

                JSONObject jsonObject = jsonRoot.getJSONObject(i);
                int id = jsonObject.getInt("");

                String name="";
                if(commonData.getLocale()!=null)
                {
                    name = jsonObject.getString("");

                }
                else
                {
                    name = jsonObject.getString("");

                }
                 mapPhases.put(String.valueOf(id),name);
                //arrayList.add(map);
            }
            itemListPhases = new ArrayList<RequestEducationalService.StringWithTag>();
            for (Map.Entry<String, String> entry : mapPhases.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                /* Build the StringWithTag List using these keys and values. */
                itemListPhases.add(new RequestEducationalService.StringWithTag(value, key));
            }

            spinnerAdapterPhases = new ArrayAdapter<RequestEducationalService.StringWithTag>(this, android.R.layout.simple_spinner_item, itemListPhases);
            spinnerAdapterPhases.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spPhases.setAdapter(spinnerAdapterPhases);
            final String[] loadsearchkeyID = new String[1];
            spPhases.setOnItemSelectedListener(this);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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