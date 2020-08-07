package com.example.mohamedahmedgomaa.servieclyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Complaint extends AppCompatActivity implements MaterialSpinner.OnItemSelectedListener {
    EditText etNId,etComplaint;
    Date date;
    ElegantNumberButton number_button;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    ProgressDialog mProgressDialog;
    String service,address,lang,nCopies,id,governmentBody;
    int Govid;
    List<Complaint.StringWithTag> itemListService;
    ArrayAdapter<Complaint.StringWithTag> spinnerAdapterService;
    MaterialSpinner spGovernmentBody;
    Map<String,String>mapGovernmentBody;
    CoordinatorLayout coordinatorLayout;
    CommonData commonData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        commonData=new CommonData();
        mProgressDialog=new ProgressDialog(Complaint.this);
        mProgressDialog.setCancelable(false);
        coordinatorLayout=findViewById(R.id.Complaint);
        spGovernmentBody= (MaterialSpinner) findViewById(R.id.spGovernmentBody);
        etNId=findViewById(R.id.etNId);
        etComplaint=findViewById(R.id.etComplaint);

        CommonData commonData=new CommonData();
        if(commonData.getLocale()!=null)
        {
            coordinatorLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }

        getGovernementSpinner();

    }


    public  void getGovernementSpinner() {
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
            List<Complaint.StringWithTag> itemList = new ArrayList<Complaint.StringWithTag>();
            itemList.add(new Complaint.StringWithTag("", ""));
            for (Map.Entry<String, String> entry : mapGovernmentBody.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                /* Build the StringWithTag List using these keys and values. */
                itemList.add(new Complaint.StringWithTag(value, key));
            }

            ArrayAdapter<Complaint.StringWithTag> spinnerAdapter = new ArrayAdapter<Complaint.StringWithTag>(this, android.R.layout.simple_spinner_item, itemList);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spGovernmentBody.setAdapter(spinnerAdapter);
            final String[] loadsearchkeyID = new String[1];
            spGovernmentBody.setOnItemSelectedListener(this);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {

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

    public void sendComplaint(View view) {

    if(spGovernmentBody.getSelectedIndex()==0)
    {
        spGovernmentBody.setError(getString(R.string.SelectGove));
        return;
    }
     else
    {
        spGovernmentBody.setError(null);

    }

        if(etComplaint.getText().toString().equals(null)||etComplaint.getText().toString().equals(""))
        {
            etComplaint.setError(getString(R.string.EnterCom));
            return;
        }

        showDialog();
    }
    private   void showDialog()
    {new TTFancyGifDialog.Builder(Complaint.this)
            .setTitle(getString(R.string.Send_Complaint))
            .setMessage(getString(R.string.Areyouwanttosendthiscomplaint))
            .setPositiveBtnText(getString(R.string.Send))
            .setPositiveBtnBackground("#020000")
            .setNegativeBtnText(getString(R.string.Cancel))
            .setNegativeBtnBackground("#4c4c4c")
            .setGifResource(R.drawable.send)      //pass your gif, png or jpg
            .isCancellable(true)
            .OnPositiveClicked(new TTFancyGifDialogListener() {
                @Override
                public void OnClick() {

                    mProgressDialog.show();

                    String GovId= spGovernmentBody.getText().toString();
                    final int sch=Integer.parseInt(getKey(mapGovernmentBody,GovId));


                    SharedPreferences sharedPreferences =getSharedPreferences("CitizenData", Context.MODE_PRIVATE);
                    final String cid=sharedPreferences.getString("Cid",null);
                    String gov=spGovernmentBody.getText().toString();
                    int govId=Integer.parseInt(getKey(mapGovernmentBody,gov));
                    boolean Ar=false;
                    if(commonData.getLocale()!=null)
                    {
                        Ar=true;
                    }
                    String com=etComplaint.getText().toString().replace(" ","%20");
                    String url="http://192.168.1.34:88/api/Complains?Id=0&cId="+cid+"&NId="+etNId.getText().toString()+"&GovId="+govId+"&ComText="+com+"&Ar="+Ar;
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

                            boolean Ar=false;
                            if(commonData.getLocale()!=null)
                            {
                                Ar=true;
                            }
                            String gov=spGovernmentBody.getText().toString();
                            int GovId=Integer.parseInt(getKey(mapGovernmentBody,gov));

                            SharedPreferences sharedPreferences =getSharedPreferences("CitizenData", Context.MODE_PRIVATE);
                            String cid=sharedPreferences.getString("Cid",null);
                            Map<String,String>map=new HashMap<>();
                            map.put("Id","0");
                            map.put("cId",cid);
                            map.put("Ar",String.valueOf(Ar));
                            map.put("GovId",String.valueOf(GovId));
                            map.put("ComText",etComplaint.getText().toString());
                            map.put("NId",etNId.getText().toString());

                            return  map;
                        }
                    }   ;
                    MySingleton.getInstance(Complaint.this).addToRequestQueue(stringRequest);




                }
            })
            .OnNegativeClicked(new TTFancyGifDialogListener() {
                @Override
                public void OnClick() {
                }
            })
            .build();

    }

    public <K, V> K getKey(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }


}
