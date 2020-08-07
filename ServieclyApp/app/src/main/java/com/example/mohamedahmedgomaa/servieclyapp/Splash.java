package com.example.mohamedahmedgomaa.servieclyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mohamedahmedgomaa.servieclyapp.Common.CommonData;
import com.example.mohamedahmedgomaa.servieclyapp.database.LoginData;

import java.util.Locale;

public class Splash extends AppCompatActivity {
    LinearLayout l1,l2,backparent;
    Button btnsub;
    Animation uptodown,downtoup;
    Locale locale;
    CommonData commonData;
    TextView tt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences sharedPreferences=getSharedPreferences("CitizenData", Context.MODE_PRIVATE);

        if(sharedPreferences.contains("Cid")&& sharedPreferences.contains("NId")&&(sharedPreferences.contains("PinNumber") ||(sharedPreferences.contains("Npassword") )))
        {
            Intent intent=new Intent(this,Ministry.class);
            startActivity(intent);
            finish();

        }

        CommonData commonData =new CommonData();
        tt=(TextView)findViewById(R.id.tvv);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        btnsub = (Button)findViewById(R.id.buttonsub);
        l1 = (LinearLayout) findViewById(R.id.l1);
        l2 = (LinearLayout) findViewById(R.id.l2);

        backparent = (LinearLayout) findViewById(R.id.backParent);
        uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);
        l1.setAnimation(uptodown);
        l2.setAnimation(downtoup);

      LoginData  loginData=new LoginData(Splash.this);
   Cursor c= loginData.selectAllData();

   if(!c.moveToNext())
   {
       loginData.insertLoginData(4,"10201911021945","20161539","86546154");

   }


    }

    public void gotoLogIn(View view) {
        Intent intent=new Intent(this,LogIn.class);
          startActivity(intent);
          finish();
    }

    public void gotoLogInAr(View view) {
     locale =new Locale("ar");
        if(commonData.getLocale()==null) {
            commonData.setLocale(locale);
            Resources resources = getResources();
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            Configuration configuration = resources.getConfiguration();
            configuration.locale = locale;
            finish();
            startActivity(getIntent());

        }
        else
        {
            locale =new Locale("en");
            commonData.setLocale(null);
            Resources resources = getResources();
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            Configuration configuration = resources.getConfiguration();
            configuration.locale = locale;
            finish();
            startActivity(getIntent());
        }

    }
}
