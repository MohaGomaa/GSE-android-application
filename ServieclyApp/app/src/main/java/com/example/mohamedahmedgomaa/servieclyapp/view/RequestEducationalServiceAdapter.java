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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.mohamedahmedgomaa.servieclyapp.R;
import com.example.mohamedahmedgomaa.servieclyapp.model.Request;
import com.example.mohamedahmedgomaa.servieclyapp.model.RequestEducationalService;

import java.util.ArrayList;
import java.util.List;

class RequestEducationalServiceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener{
    TextView phase ;
    TextView child ;
    TextView language ;
    TextView address ;
    TextView date;
    TextView requestType;
    ImageView quantity;
    TextView id;
    LinearLayout view_container;
    public RequestEducationalServiceViewHolder(@NonNull View itemView) {
        super(itemView);

        view_container = itemView.findViewById(R.id.container);
        phase = itemView.findViewById(R.id.tvPhase);
        language = itemView.findViewById(R.id.tvLang);
        address = itemView.findViewById(R.id.tvAddress);
        date = itemView.findViewById(R.id.txtDate);
        requestType = itemView.findViewById(R.id.tvType);
        quantity = itemView.findViewById(R.id.count);
        id = itemView.findViewById(R.id.tvId);
        child=itemView.findViewById(R.id.tvChildName);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Select action");
    }
}

public class RequestEducationalServiceAdapter extends RecyclerView.Adapter<RequestEducationalServiceViewHolder>{
    private List<RequestEducationalService> listData=new ArrayList<>();
    private Context context;

    public RequestEducationalServiceAdapter(List<RequestEducationalService> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @NonNull
    @Override
    public RequestEducationalServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View itemView=inflater.inflate(R.layout.request_item,parent,false);
        return  new RequestEducationalServiceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestEducationalServiceViewHolder holder, int position) {
        TextDrawable drawable= TextDrawable.builder().buildRound(""+listData.get(position).getQuantity(), Color.GRAY);
        holder.quantity.setImageDrawable(drawable);
        holder.child.setText(context.getResources().getString(R.string.child)+listData.get(position).getAddress());
        holder.phase.setText( context.getResources().getString(R.string.Phasee)+listData.get(position).getPhase());
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

    public void setfilter(List<RequestEducationalService> listitem)
    {
        listData=new ArrayList<>();
        listData.addAll(listitem);
        notifyDataSetChanged();
    }

}

