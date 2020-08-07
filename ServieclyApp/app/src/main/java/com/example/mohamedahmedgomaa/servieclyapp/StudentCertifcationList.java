package com.example.mohamedahmedgomaa.servieclyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialog;
import com.bestsoft32.tt_fancy_gif_dialog_lib.TTFancyGifDialogListener;
import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.PRDownloaderConfig;
import com.downloader.Progress;
import com.example.mohamedahmedgomaa.servieclyapp.Common.CommonData;
import com.example.mohamedahmedgomaa.servieclyapp.model.StudentCertification;
import com.example.mohamedahmedgomaa.servieclyapp.model.TrafficViolation;
import com.example.mohamedahmedgomaa.servieclyapp.model.Vaccination;
import com.example.mohamedahmedgomaa.servieclyapp.view.StudentCertifcationAdapter;
import com.example.mohamedahmedgomaa.servieclyapp.view.TrafficViolationsAdapter;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StudentCertifcationList extends AppCompatActivity {
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 1;
    PermissionUtils permissionUtils;
    CommonData commonData;

    private List<StudentCertification> lstVacc ;
    ProgressDialog progressDialog;
    private RecyclerView recyclerView ;
    StudentCertifcationAdapter myadapter;
    SearchView searchView;
    CardView cardViewlay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_certifcation);
        PRDownloader.initialize(getApplicationContext());

        progressDialog=new ProgressDialog(StudentCertifcationList.this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle(R.string.Waiting);
        commonData=new CommonData();
        cardViewlay=findViewById(R.id.StudentCertifcationList);
        CommonData commonData=new CommonData();
        if(commonData.getLocale()!=null)
        {
            cardViewlay.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }


        lstVacc = new ArrayList<>() ;
        recyclerView = findViewById(R.id.recyclerviewid);

        jsonrequest();


    }



    private void jsonrequest() {
        progressDialog.show();
        SharedPreferences sharedPreferences =getSharedPreferences("CitizenData", Context.MODE_PRIVATE);
        String cid=sharedPreferences.getString("Cid",null);
        // final String url = "http://192.168.1.34:88/api/RequestsApi?request_citizenId="+cid ;
        final String url = "http://192.168.1.34:88/api/Citizen?citId="+cid ;
        StringRequest stringRequest=new StringRequest(com.android.volley.Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray  = new JSONArray(response) ;

                    for (int i = 0 ; i < jsonArray.length(); i++ ) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i) ;
                        StudentCertification reserv  = new StudentCertification() ;

                        if(commonData.getLocale()!=null)
                        {
                            reserv.setCertifcation_Name(jsonObject.getString("CirtificateTypeName"));
                            reserv.setGradeName(jsonObject.getString("CirtificateTypeNameArabic"));
                        }
                        else {
                            reserv.setCertifcation_Name(jsonObject.getString("CirtificateTypeName"));
                            reserv.setGradeName(jsonObject.getString("GradeName"));
                        }
                        reserv.setDate(jsonObject.getString("Date"));


                        lstVacc.add(reserv);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                setuprecyclerview(lstVacc);
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        getString(R.string.SomthingsWronghappen),Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }
    private void setuprecyclerview(List<StudentCertification> lstVacc) {

        if(lstVacc.isEmpty())
        {

            Toast.makeText(this,   getString(R.string.NoInformationYet),Toast.LENGTH_SHORT).show();
        }
        myadapter = new StudentCertifcationAdapter (this,lstVacc) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myadapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.searchfile, menu);
        final MenuItem myActionMenuItem = menu.findItem(R.id.search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        changeSearchViewTextColor(searchView);
        ((EditText) searchView.findViewById(R.id.search_src_text)).
                setHintTextColor(getResources().getColor(R.color.black));
        ((EditText) searchView.findViewById(R.id.search_src_text)).
                setTextColor(getResources().getColor(R.color.black));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override            public boolean onQueryTextSubmit(String query) {
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if(!lstVacc.isEmpty()) {
                    final List<StudentCertification> filtermodelist = (List<StudentCertification>) filter(lstVacc, newText);
                    myadapter.setfilter(filtermodelist);
                }
                return true;

            }
        });
        return true;
    }
    private List<StudentCertification> filter(List<StudentCertification> pl, String query)
    {
        query=query.toLowerCase();
        final List<StudentCertification> filteredModeList=new ArrayList<>();
        for (StudentCertification model:pl)
        {
            String child=model.getCertifcation_Name().toLowerCase();
            String vacctype=model.getDate().toLowerCase();
            String grade=model.getGradeName().toLowerCase();

            if (child.startsWith(query))
            {
                filteredModeList.add(model);
            }
            else if(vacctype.startsWith(query))
            {
                filteredModeList.add(model);
            }
            else if(grade.startsWith(query))
            {
                filteredModeList.add(model);
            }

        }
        return filteredModeList;
    }

    //for changing the text color of searchview
    private void changeSearchViewTextColor(View view) {
        if (view != null) {
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(Color.WHITE);
                return;
            } else if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    changeSearchViewTextColor(viewGroup.getChildAt(i));
                }
            }
        }
    }

    /*
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if(item.getTitle()==getString(R.string.download))
        {
            permissionUtils = new PermissionUtils();
            StudentCertification i =lstVacc.get(item.getOrder());
            if (permissionUtils.checkPermission(StudentCertifcationList.this, STORAGE_PERMISSION_REQUEST_CODE, item.getActionView())) {
                if (i.getData().length() > 0) {
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(i.getData())));
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }

            }
        }
        else
        {
            Toast.makeText(StudentCertifcationList.this,item.getTitle(),Toast.LENGTH_LONG).show();
        }
        return super.onContextItemSelected(item);

    }
*/

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case STORAGE_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Snackbar.make(cardViewlay, "Permission Granted, Now you can write storage.", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(cardViewlay, "Permission Denied, You cannot access storage.", Snackbar.LENGTH_LONG).show();
                }
                break;
        }
    }


}
