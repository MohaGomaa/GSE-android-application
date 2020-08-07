package com.example.mohamedahmedgomaa.servieclyapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mohamedahmedgomaa.servieclyapp.Common.CommonData;
import com.example.mohamedahmedgomaa.servieclyapp.Controller.LogInController;
import com.example.mohamedahmedgomaa.servieclyapp.database.LoginData;
import com.google.android.material.textfield.TextInputLayout;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class LogIn extends AppCompatActivity {
     EditText txtNationalId,textInputPin;
     ProgressDialog mProgressDialog;

     String res;
     CommonData commonData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
         commonData=new CommonData();
        if(commonData.getLocale()!=null)
        {
            Resources resources=getResources();
            DisplayMetrics displayMetrics=resources.getDisplayMetrics();
            Configuration configuration=resources.getConfiguration();
            configuration.locale=commonData.getLocale();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        txtNationalId=findViewById(R.id.txtNationalId);
        Button buttonBarCodeScan = findViewById(R.id.btnLogIn);
        Button buttonPin = findViewById(R.id.btnPin);
        mProgressDialog=new ProgressDialog(LogIn.this);
        mProgressDialog.setTitle(R.string.Waiting);
        mProgressDialog.setMessage(getString(R.string.Pleasewaiting));
        mProgressDialog.setCancelable(false);
       handleSSLHandshake();
        buttonBarCodeScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(txtNationalId.getText().toString().equals(null )||txtNationalId.getText().toString().equals(""))
                {
                    txtNationalId.setError(  getString(R.string.EnteryourNationalId));
                    return;
                }

                //initiate scan with our custom scan activity
                new IntentIntegrator(LogIn.this).setCaptureActivity(Scanner.class).initiateScan();
            }
        });

        buttonPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtNationalId.getText().toString().equals(null )||txtNationalId.getText().toString().equals(""))
                {
                    txtNationalId.setError(getString(R.string.EnteryourNationalId));
                    return;
                }

                //initiate scan with our custom scan activity
               showPinNumberDialog(LogIn.this);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //We will get scan results here
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        //check for null
        if (result != null) {
            if (result.getContents() == null) {

                Toast.makeText(this, getString(R.string.LogInCancelled), Toast.LENGTH_LONG).show();
            } else {
                //show dialogue with result
               showResultDialogue(result.getContents());
                res=result.getContents();
               login(result.getContents(),txtNationalId.getText().toString());
            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    // function that will display pop-up dialog to enter pin number
    public void showPinNumberDialog(final Context context) {
        final AlertDialog.Builder alertdialog=new AlertDialog.Builder(context);
        alertdialog.setTitle(R.string.LoginByPinNumber);
        alertdialog.setMessage(R.string.PleaseEnterPinNumber);
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View passLayout=layoutInflater.inflate(R.layout.login_pin_number,null);

        if(commonData.getLocale() !=null)
        {
            passLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        final EditText pass=passLayout.findViewById(R.id.edtPin);
        alertdialog.setView(passLayout);

        alertdialog.setPositiveButton(R.string.login, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(pass.getText().toString().equals(null )||pass.getText().toString().equals(""))
                {
                    Toast.makeText(context,context.getString(R.string.YoumissedPinNumberEmpty),Toast.LENGTH_SHORT).show();
                    pass.setError(  context.getString(R.string.PleaseEnterPinNumber));
                    return;
                }

               loginByPin(pass.getText().toString(),txtNationalId.getText().toString());
            }
        });
        alertdialog.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertdialog.show();
    }


    public void getUserType(final int citizenId)
    {


        mProgressDialog.show();
        // String url="https://localhost:44389/api/LoginApi?Login_CitizenNId="+NationalId+"&Login_Password="+qrcode;
        String url="http://192.168.1.34:88/api/Citizen?citizenId="+citizenId;

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ///Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    if (response != " ")
                    {

                        SharedPreferences sharedPreferences = getSharedPreferences("CitizenData", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString("user_type_id", jsonObject.getString("user_type_id"));

                        editor.commit();


                        mProgressDialog.dismiss();

                    } else {
                        mProgressDialog.dismiss();
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                   // Toast.makeText(getApplicationContext(),
                     //       getString(R.string.SomthingsWronghappen),Toast.LENGTH_LONG).show();
                    mProgressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        getString(R.string.SomthingsWronghappen),Toast.LENGTH_LONG).show();

            }
        })   ;

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);





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


                        SharedPreferences sharedPreferences = getSharedPreferences("CitizenData", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                        editor.putString("NId", NationalId);
                        editor.putString("Npassword", qrcode);
                        editor.putString("Cid", jsonObject.getString("citizen_id"));
                        getUserType( jsonObject.getInt("citizen_id"));
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

                        Intent intentPin = new Intent(getApplicationContext(), Ministry.class);
                        Toast.makeText(getApplicationContext() ,getString(R.string.SuccessLogIn), Toast.LENGTH_LONG).show();

                        startActivity(intentPin);
                        mProgressDialog.dismiss();
                        finish();

                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.InvalidNationalQR), Toast.LENGTH_SHORT).show();
                        mProgressDialog.dismiss();
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"error"+response,Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getApplicationContext(),
                        getString(R.string.SomthingsWronghappen),Toast.LENGTH_LONG).show();

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

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

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



                        SharedPreferences sharedPreferences = getSharedPreferences("CitizenData", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                        editor.putString("NId", NationalId);
                        editor.putString("PinNumber",pin);
                        editor.putString("Cid", jsonObject.getString("citizen_id"));
                        getUserType( jsonObject.getInt("citizen_id"));
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


                        Intent intentPin = new Intent(getApplicationContext(), Ministry.class);
                        Toast.makeText(getApplicationContext(),getString(R.string.SuccessLogIn), Toast.LENGTH_LONG).show();
                        startActivity(intentPin);
                        mProgressDialog.dismiss();
                        finish();

                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.InvalidNationalPin), Toast.LENGTH_SHORT).show();
                        mProgressDialog.dismiss();
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),""+e.getMessage(),Toast.LENGTH_SHORT).show();
                    mProgressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        getString(R.string.SomthingsWronghappen),Toast.LENGTH_LONG).show();
            }
        })  ;

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

    }
    //method to construct dialogue with scan results
    public void showResultDialogue(final String result) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(getApplicationContext(), android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(getApplicationContext());
        }
        builder.setTitle("Scan Result")
                .setMessage("Scanned result is " + result)
                .setPositiveButton("Copy result", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("Scan Result", result);
                        clipboard.setPrimaryClip(clip);
                        Toast.makeText(getApplicationContext(), "Result copied to clipboard", Toast.LENGTH_SHORT).show();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        dialog.dismiss();
                    }
                });

    }
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
