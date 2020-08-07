package com.example.mohamedahmedgomaa.servieclyapp.Controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mohamedahmedgomaa.servieclyapp.Common.CommonData;
import com.example.mohamedahmedgomaa.servieclyapp.Ministry;
import com.example.mohamedahmedgomaa.servieclyapp.MySingleton;
import com.example.mohamedahmedgomaa.servieclyapp.R;
import com.example.mohamedahmedgomaa.servieclyapp.Scanner;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class LogInController  extends Activity {
     EditText txtNationalId;
     ProgressDialog mProgressDialog;
     CommonData commonData;
     Context context;

    public LogInController(Context c) {
        this.context=c;
        mProgressDialog=new ProgressDialog(context);
        mProgressDialog.setTitle(R.string.Waiting);
        mProgressDialog.setMessage(context.getString(R.string.Pleasewaiting));
        mProgressDialog.setCancelable(false);
    }

    // function that will use api to open app  using QR code and NId
    public void login(final String qrcode, final String NationalId)
    {

       mProgressDialog.show();
       // String url="https://localhost:44389/api/LoginApi?Login_CitizenNId="+NationalId+"&Login_Password="+qrcode;
       String url="http://192.168.1.34:88/api/LoginApi?Login_CitizenNId="+NationalId+"&Login_QRcode="+qrcode;

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              ///Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("citizen_id") != 0)
                    {

                        SharedPreferences sharedPreferences = context.getSharedPreferences("CitizenData", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                        editor.putString("NId", NationalId);
                        editor.putString("Npassword", qrcode);
                        editor.putString("Cid", jsonObject.getString("citizen_id"));
                        if(commonData.getLocale() !=null)
                        {
                            editor.putString("CFirstName", jsonObject.getString("citizen_first_name_arabic"));
                            editor.putString("CSecondName", jsonObject.getString("citizen_second_name_arabic"));
                            editor.putString("CThirdName", jsonObject.getString("citizen_third_name_arabic"));
                            editor.putString("CFourthName", jsonObject.getString("citizen_fourth_name_arabic"));
                            editor.putString("citizen_birthPlace", jsonObject.getString("citizen_birthPlace_arabic"));

                        }
                        else
                        {
                            editor.putString("CFirstName", jsonObject.getString("citizen_first_name"));
                            editor.putString("CSecondName", jsonObject.getString("citizen_second_name"));
                            editor.putString("CThirdName", jsonObject.getString("citizen_third_name"));
                            editor.putString("CFourthName", jsonObject.getString("citizen_fourth_name"));
                            editor.putString("citizen_birthPlace", jsonObject.getString("citizen_birthPlace"));
                        }

                        editor.putString("citizen_job_title", jsonObject.getString("citizen_job_title"));
                        editor.putString("citizen_gender", jsonObject.getString("citizen_gender"));
                        editor.putString("citizen_relegion", jsonObject.getString("citizen_relegion"));
                        editor.putString("citizen_birthDate", jsonObject.getString("citizen_birthDate"));
                        editor.commit();


                            Intent intentPin = new Intent(context, Ministry.class);
                        context.startActivity(intentPin);
                              mProgressDialog.dismiss();
                        Toast.makeText(context ,context.getString(R.string.SuccessLogIn), Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(context, context.getString(R.string.InvalidNationalQR), Toast.LENGTH_SHORT).show();
                          mProgressDialog.dismiss();
                    }
                }catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                    mProgressDialog.dismiss();
                    }

 /*               boolean b = Boolean.parseBoolean(response);                //  readJson(response);
                if(response.equals("\"0\"")) {


                    mProgressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Invalid National Id or QR code",Toast.LENGTH_LONG).show();

                }
                else{
                    Toast.makeText(getApplicationContext(),"Success login",Toast.LENGTH_LONG).show();
                   SharedPreferences sharedPreferences =getSharedPreferences("CitizenData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                          String s=response.replace("\"","");
                    //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                    editor.putString("NId",NationalId);
                    editor.putString("Npassword",qrcode);
                    editor.putString("Cid",s);
                    editor.commit();



                }

*/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,
                        context.getString(R.string.SomthingsWronghappen)+error.getMessage(),Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>map=new HashMap<>();


                map.put("Login_CitizenNId",NationalId);
                map.put("Login_Password",qrcode);

                return map;
            }
        }   ;

        MySingleton.getInstance(context).addToRequestQueue(stringRequest);

    }

    // function that will use api to open app  using Pin Number code and NId
    public void loginByPin(final String pin, final String NationalId)
    {


        mProgressDialog.show();
        // String url="https://localhost:44389/api/LoginApi?Login_CitizenNId="+NationalId+"&Login_Password="+qrcode;
        String url="http://192.168.1.34:88/api/LoginApi?Login_CitizenNId="+NationalId+"&Login_Password="+pin;

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ///Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                try {



                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("citizen_id") != 0) {



                        SharedPreferences sharedPreferences = context.getSharedPreferences("CitizenData", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                        editor.putString("NId", NationalId);
                        editor.putString("PinNumber",pin);
                        editor.putString("Cid", jsonObject.getString("citizen_id"));
                        if(commonData.getLocale() !=null)
                        {
                            editor.putString("CFirstName", jsonObject.getString("citizen_first_name_arabic"));
                            editor.putString("CSecondName", jsonObject.getString("citizen_second_name_arabic"));
                            editor.putString("CThirdName", jsonObject.getString("citizen_third_name_arabic"));
                            editor.putString("CFourthName", jsonObject.getString("citizen_fourth_name_arabic"));
                            editor.putString("citizen_birthPlace", jsonObject.getString("citizen_birthPlace_arabic"));

                        }
                        else
                        {
                            editor.putString("CFirstName", jsonObject.getString("citizen_first_name"));
                            editor.putString("CSecondName", jsonObject.getString("citizen_second_name"));
                            editor.putString("CThirdName", jsonObject.getString("citizen_third_name"));
                            editor.putString("CFourthName", jsonObject.getString("citizen_fourth_name"));
                            editor.putString("citizen_birthPlace", jsonObject.getString("citizen_birthPlace"));
                        }

                        editor.putString("citizen_job_title", jsonObject.getString("citizen_job_title"));
                        editor.putString("citizen_gender", jsonObject.getString("citizen_gender"));
                        editor.putString("citizen_relegion", jsonObject.getString("citizen_relegion"));
                        editor.putString("citizen_birthDate", jsonObject.getString("citizen_gender"));

                        editor.commit();


                        Intent intentPin = new Intent(context, Ministry.class);
                        context.startActivity(intentPin);
                        mProgressDialog.dismiss();
                        Toast.makeText(context.getApplicationContext(),context.getString(R.string.SuccessLogIn), Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(context, context.getString(R.string.InvalidNationalPin), Toast.LENGTH_SHORT).show();
                        mProgressDialog.dismiss();
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                    mProgressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context.getApplicationContext(),
                        context.getString(R.string.SomthingsWronghappen)+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        })  ;

        MySingleton.getInstance(context).addToRequestQueue(stringRequest);

    }




    //method to construct dialogue with scan results
    public void showResultDialogue(final String result) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setTitle("Scan Result")
                .setMessage("Scanned result is " + result)
                .setPositiveButton("Copy result", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("Scan Result", result);
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(context, "Result copied to clipboard", Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        dialog.dismiss();
                    }
                });

    }

}
