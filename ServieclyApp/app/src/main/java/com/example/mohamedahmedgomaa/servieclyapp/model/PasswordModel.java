package com.example.mohamedahmedgomaa.servieclyapp.model;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mohamedahmedgomaa.servieclyapp.MySingleton;
import com.example.mohamedahmedgomaa.servieclyapp.R;

import java.util.HashMap;
import java.util.Map;

public class PasswordModel {



    public void showChangePasswordDialog(final Context context) {
        final AlertDialog.Builder alertdialog=new AlertDialog.Builder(context);
        alertdialog.setTitle(context.getString(R.string.changePin));
        alertdialog.setMessage(context.getString(R.string.fillFields));
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View passLayout=layoutInflater.inflate(R.layout.change_pin_number,null);

        final EditText pass=passLayout.findViewById(R.id.edtPassword);
        final EditText newPass=passLayout.findViewById(R.id.edtNewPassword);
        final EditText conPass=passLayout.findViewById(R.id.edtConPassword);
        alertdialog.setView(passLayout);

        alertdialog.setPositiveButton(context.getString(R.string.Change), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(pass.getText().toString().equals(null )||pass.getText().toString().equals(""))
                {
                    Toast.makeText(context,context.getString(R.string. missedCurrent),Toast.LENGTH_SHORT).show();

                    pass.setError("Enter Pin Number");
                    return;
                }
                if(newPass.getText().toString().equals(null )||newPass.getText().toString().equals(""))
                {
                    Toast.makeText(context,context.getString(R.string. missedNew),Toast.LENGTH_SHORT).show();

                    newPass.setError("Enter New  Pin Number");
                    return;
                }
                if(newPass.getText().toString().length()<=8)
                {
                    Toast.makeText(context,context.getString(R.string. NewPin),Toast.LENGTH_SHORT).show();

                    newPass.setError("Rang Pin Number between 8 & 20");
                    return;
                }
                if(conPass.getText().toString().equals(null )||conPass.getText().toString().equals(""))
                {
                    Toast.makeText(context,context.getString(R.string. missedConfirm),Toast.LENGTH_SHORT).show();

                    conPass.setError("Enter confirm Pin Number");
                    return;
                }
                SharedPreferences sharedPreferences =context.getSharedPreferences("CitizenData", Context.MODE_PRIVATE);
                 String pin=sharedPreferences.getString("PinNumber",null);
                if(newPass.getText().toString().equals(conPass.getText().toString()))
                {
                    if(pass.getText().toString().equals(pin)) {
                        String url = "http://192.168.1.34:88/api/?";
                        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {


                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(context, "Error :" + error, Toast.LENGTH_LONG).show();


                                //  mProgressDialog.dismiss();

                            }
                        });
                        MySingleton.getInstance(context).addToRequestQueue(stringRequest);


                    }
                    else
                    {
                        //  KToast.warningToast(Home.this, "Not Matched Password", Gravity.BOTTOM, KToast.LENGTH_AUTO);
                        Toast.makeText(context,context.getString(R.string. WorngCurrent),Toast.LENGTH_SHORT).show();


                    }


                }
                else
                {
                    Toast.makeText(context,context.getString(R.string. NotMatched),Toast.LENGTH_SHORT).show();

                }

            }
        });
        alertdialog.setNegativeButton(context.getString(R.string.Cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertdialog.show();
    }
}
