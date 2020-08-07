package com.example.mohamedahmedgomaa.servieclyapp.model;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.mohamedahmedgomaa.servieclyapp.Common.CommonData;
import com.example.mohamedahmedgomaa.servieclyapp.LogIn;
import com.example.mohamedahmedgomaa.servieclyapp.MySingleton;
import com.example.mohamedahmedgomaa.servieclyapp.R;
import com.example.mohamedahmedgomaa.servieclyapp.Reservation;

import java.util.HashMap;
import java.util.Map;

public class Change_pinNumber {



    public void showChangePasswordDialog(final Activity context) {
        final AlertDialog.Builder alertdialog=new AlertDialog.Builder(context);
        alertdialog.setTitle(context.getString(R.string.changePin));
        alertdialog.setMessage(context.getString(R.string.fillFields));
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        final CommonData commonData =new CommonData();
        View passLayout=layoutInflater.inflate(R.layout.change_pin_number,null);
        final ProgressDialog  mProgressDialog=new ProgressDialog(context);
        final EditText pass=passLayout.findViewById(R.id.edtPassword);
        final EditText newPass=passLayout.findViewById(R.id.edtNewPassword);
        final EditText conPass=passLayout.findViewById(R.id.edtConPassword);
        alertdialog.setView(passLayout);
        mProgressDialog.show();
        alertdialog.setPositiveButton(context.getString(R.string.Change), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(pass.getText().toString().equals(null )||pass.getText().toString().equals(""))
                {
                    mProgressDialog.dismiss();
                    Toast.makeText(context,context.getString(R.string. missedCurrent),Toast.LENGTH_SHORT).show();

                    pass.setError("Enter Pin Number");
                    return;
                }
                if(newPass.getText().toString().equals(null )||newPass.getText().toString().equals(""))
                {
                    mProgressDialog.dismiss();
                    Toast.makeText(context,context.getString(R.string. missedNew),Toast.LENGTH_SHORT).show();

                    newPass.setError("Enter New  Pin Number");
                    return;
                }
                if(newPass.getText().toString().length()<=8)
                {
                    mProgressDialog.dismiss();
                    Toast.makeText(context,context.getString(R.string. NewPin),Toast.LENGTH_SHORT).show();

                    newPass.setError("Rang Pin Number between 8 & 20");
                    return;
                }
                if(conPass.getText().toString().equals(null )||conPass.getText().toString().equals(""))
                {
                    mProgressDialog.dismiss();
                    Toast.makeText(context,context.getString(R.string. missedConfirm),Toast.LENGTH_SHORT).show();

                    conPass.setError("Enter confirm Pin Number");
                    return;
                }
                SharedPreferences sharedPreferences =context.getSharedPreferences("CitizenData", Context.MODE_PRIVATE);
                 String pin=sharedPreferences.getString("PinNumber",null);
                 String cid=sharedPreferences.getString("Cid",null);

                if(newPass.getText().toString().equals(conPass.getText().toString()))
                {
                    if(pass.getText().toString().equals(pin))
                    {
                        boolean Ar=false;
                        String npin=newPass.getText().toString();
                        if(commonData.getLocale() !=null)
                        {
                            Ar=true;
                        }
                        String url = "http://192.168.1.34:88/api/LoginApi?Login_CitizenId="+cid+"&new_pass="+npin+"&Ar="+Ar;

                        StringRequest stringRequest = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                   mProgressDialog.dismiss();
                           Toast.makeText(context,response,Toast.LENGTH_LONG).show();

                                AlertDialog.Builder alter=new  AlertDialog.Builder(context);
                                alter.setTitle(context.getString(R.string.Logout));
                                alter.setMessage(context.getString(R.string.Mess));
                                alter.setIcon(R.drawable.ic_exit_to_app_black_24dp);
                                alter.setPositiveButton(context.getString(R.string.Logout), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        SharedPreferences sharedPreferences=context.getSharedPreferences("CitizenData",context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor=sharedPreferences.edit();
                                        editor.clear();
                                        editor.commit();
                                        Intent loginscreen=new Intent(context, LogIn.class);
                                        loginscreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        context.startActivity(loginscreen);
                                        context.finish();

                                    }
                                });
                                alter.setNegativeButton(context.getString(R.string.Cancel), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });

                                alter.create().show();
                                alter.setCancelable(true);

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                mProgressDialog.dismiss();
                                Toast.makeText(context, "Error :" + error, Toast.LENGTH_LONG).show();


                                //  mProgressDialog.dismiss();

                            }
                        });
                        MySingleton.getInstance(context).addToRequestQueue(stringRequest);


                    }
                    else
                    {
                        mProgressDialog.dismiss();
                        //  KToast.warningToast(Home.this, "Not Matched Password", Gravity.BOTTOM, KToast.LENGTH_AUTO);
                        Toast.makeText(context,context.getString(R.string. WorngCurrent),Toast.LENGTH_SHORT).show();


                    }


                }
                else
                {
                    mProgressDialog.dismiss();
                    Toast.makeText(context,context.getString(R.string. NotMatched),Toast.LENGTH_SHORT).show();

                }

            }
        });
        alertdialog.setNegativeButton(context.getString(R.string.Cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mProgressDialog.dismiss();
                dialog.dismiss();
            }
        });

        alertdialog.show();
    }
}
