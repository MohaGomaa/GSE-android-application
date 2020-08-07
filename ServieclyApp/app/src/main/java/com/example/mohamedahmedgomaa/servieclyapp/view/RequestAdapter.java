package com.example.mohamedahmedgomaa.servieclyapp.view;

import android.content.Context;
import android.graphics.Color;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.amulyakhare.textdrawable.TextDrawable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mohamedahmedgomaa.servieclyapp.R;
import com.example.mohamedahmedgomaa.servieclyapp.model.Request;

import java.text.BreakIterator;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

class RequestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener{
    TextView service ;
    TextView language ;
    TextView address ;
    TextView date;
    TextView requestType;
    ImageView quantity;
    TextView id;
    LinearLayout view_container;
    public RequestViewHolder(@NonNull View itemView) {
        super(itemView);

        view_container = itemView.findViewById(R.id.container);
        service = itemView.findViewById(R.id.tvService);
        language = itemView.findViewById(R.id.tvLang);
        address = itemView.findViewById(R.id.tvAddress);
        date = itemView.findViewById(R.id.txtDate);
        requestType = itemView.findViewById(R.id.tvType);
        quantity = itemView.findViewById(R.id.count);
        id = itemView.findViewById(R.id.tvId);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Select action");
    }
}
public class RequestAdapter extends RecyclerView.Adapter<RequestViewHolder>{
    private List<Request> listData=new ArrayList<>();
    private Context context;

    public RequestAdapter(List<Request> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.request_item,parent,false);
        return  new RequestViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        TextDrawable drawable= TextDrawable.builder().buildRound(""+listData.get(position).getQuantity(), Color.GRAY);
        holder.quantity.setImageDrawable(drawable);

        holder.service.setText( context.getResources().getString(R.string.Servicee)+listData.get(position).getService());
        holder.language.setText(context.getResources().getString(R.string.Languagee)+listData.get(position).getLanguage());
        holder.address.setText(context.getResources().getString(R.string.Addresss)+listData.get(position).getAddress());
        holder.date.setText(context.getResources().getString(R.string.Datee)+listData.get(position).getDate());
        holder.requestType.setText(context.getResources().getString(R.string.RequestType)+listData.get(position).getRequestType());
       holder.id.setText(context.getResources().getString(R.string.RequestId)+String.valueOf(listData.get(position).getId()));

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public void setfilter(List<Request> listitem)
    {
        listData=new ArrayList<>();
        listData.addAll(listitem);
        notifyDataSetChanged();
    }

}

