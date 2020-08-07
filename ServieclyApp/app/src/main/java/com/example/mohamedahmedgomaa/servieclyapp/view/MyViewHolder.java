package com.example.mohamedahmedgomaa.servieclyapp.view;

import android.view.ContextMenu;
import android.view.View;

interface MyViewHolder {
    void onClick(View v);

    void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo);
}
