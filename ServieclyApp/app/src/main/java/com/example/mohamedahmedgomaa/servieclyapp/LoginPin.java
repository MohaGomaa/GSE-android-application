package com.example.mohamedahmedgomaa.servieclyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mohamedahmedgomaa.servieclyapp.database.LoginData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginPin extends AppCompatActivity {
   EditText txtPin;
   TextView textView;
   Button btnLogin;
   ProgressDialog ProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pin);
        txtPin  = findViewById(R.id.txtPin);
        btnLogin=findViewById(R.id.btnLog);
        textView=findViewById(R.id.welcomeMsg);
        ProgressDialog=new ProgressDialog(LoginPin.this);
        ProgressDialog.setTitle("Please waiting.....");
        ProgressDialog.setTitle("waiting.....");
        ProgressDialog.setCancelable(false);
        SharedPreferences sharedPreferences =getSharedPreferences("CitizenData", Context.MODE_PRIVATE);

         textView.setText("Welcome "+sharedPreferences.getString("CFirstName","User")+"\n\n In Servicely");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtPin.getText().toString().equals(null )||txtPin.getText().toString().equals(""))
                {        txtPin.setError("Enter your Pin Number");
                    return;
                }
                  getPinNumber(txtPin.getText().toString());
            }
        });
    }

    private void getPinNumber(final String pin) {
          ProgressDialog.show();

            // String url="https://localhost:44389/api/LoginApi?Login_CitizenNId="+NationalId+"&Login_Password="+qrcode;
            String url="http://192.168.1.34:88/api/LoginApi?Login_PinNumber="+pin;
            StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    ///Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();

                if(response.equals("\"0\"")) {


                    ProgressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Invalid Pin Number",Toast.LENGTH_LONG).show();

                }
                else  if(response.equals("\"1\"")) {
                    ProgressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),"Success login",Toast.LENGTH_LONG).show();
                   SharedPreferences sharedPreferences =getSharedPreferences("CitizenData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("PinNumber",pin);
                    editor.commit();
                      Intent intent=new Intent(LoginPin.this,Ministry.class);
                      startActivity(intent);


                }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    ProgressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.SomthingsWronghappen),Toast.LENGTH_LONG).show();            /*        LoginData loginData=new LoginData(LoginPin.this);
                    Cursor c= loginData.selectCitizen(txtPin.getText().toString());

                    if(c.moveToNext()&& c.getString(3)!=null)
                    {

                        Intent intentPin = new Intent(LoginPin.this, Ministry.class);
                        startActivity(intentPin);
                    }

                    // mProgressDialog.dismiss();
              /*  Intent intentHome = new Intent(LogIn.this, Reservation.class);
                startActivity(intentHome);
              */

                }
            }) ;
            MySingleton.getInstance(this).addToRequestQueue(stringRequest);


    }
}
