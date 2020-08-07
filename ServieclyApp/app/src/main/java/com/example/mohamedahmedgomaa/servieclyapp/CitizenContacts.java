package com.example.mohamedahmedgomaa.servieclyapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.mohamedahmedgomaa.servieclyapp.model.Contact;
import com.example.mohamedahmedgomaa.servieclyapp.view.ContactAdapter;
import com.rengwuxian.materialedittext.MaterialEditText;

public class CitizenContacts extends AppCompatActivity {
    MaterialEditText edtName;
    Button btnUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citizen_contacts);
    }
/*
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle()=="Update")
        {
            showUpdateDialog(contactAdapter.getRef(item.getOrder()).getKey(),contactAdapter.getItem(item.getOrder()));
        }
        else if(item.getTitle()=="Delete")
        {
            deleteCategory(contactAdapter.getRef(item.getOrder()).getKey(),contactAdapter.getItem(item.getOrder()));
        }
        return super.onContextItemSelected(item);

    }

    private void deleteCategory(String key, Contact item) {
        category.child(key).removeValue();
        Toast.makeText(this,"Category "+item.getName()+" is Deleted",Toast.LENGTH_SHORT).show();
    }

    private void showUpdateDialog(final String key, final Contact item) {
        AlertDialog.Builder aBuilder=new AlertDialog.Builder(CitizenContacts.this);
        aBuilder.setTitle("Update Category");
        aBuilder.setMessage("Please Fill all Information");
        aBuilder.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        LayoutInflater inflater=this.getLayoutInflater();
        View add_menu_layout=inflater.inflate(R.layout.add_new_contact_layout,null);

        edtName=add_menu_layout.findViewById(R.id.edtName);
        btnSelect=add_menu_layout.findViewById(R.id.btnSelect);
        btnUpload=add_menu_layout.findViewById(R.id.btnUpload);

        edtName.setText(item.getName());
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImage(item);
            }
        });
        aBuilder.setView(add_menu_layout);
        aBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(edtName.getText().toString().equals(null )||edtName.getText().toString().equals(""))
                {        edtName.setError("Enter Category Name");
                    return;
                }
                dialog.dismiss();

                item.setName(edtName.getText().toString());
                category.child(key).setValue(item);
            }
        });
        aBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        aBuilder.show();
    }
*/
}
