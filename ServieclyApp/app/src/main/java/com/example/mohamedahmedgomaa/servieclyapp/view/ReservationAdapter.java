package com.example.mohamedahmedgomaa.servieclyapp.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.mohamedahmedgomaa.servieclyapp.R;
import com.example.mohamedahmedgomaa.servieclyapp.Reservation;
import com.example.mohamedahmedgomaa.servieclyapp.model.Request;
import com.example.mohamedahmedgomaa.servieclyapp.model.Reservations;

import java.util.ArrayList;
import java.util.List;


public class ReservationAdapter extends RecyclerView.Adapter<ReservationAdapter.MyViewHolder> {

    private Context mContext ;
    private List<Reservations> mData ;


    public ReservationAdapter(Context mContext, List<Reservations> mData) {
        this.mContext = mContext;
        this.mData = mData;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.reservation_item,parent,false) ;
        final MyViewHolder viewHolder = new MyViewHolder(view) ;

        /*viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mContext, AnimeActivity.class);
                i.putExtra("anime_name",mData.get(viewHolder.getAdapterPosition()).getName());
                i.putExtra("anime_description",mData.get(viewHolder.getAdapterPosition()).getDescription());
                i.putExtra("anime_studio",mData.get(viewHolder.getAdapterPosition()).getStudio());
                i.putExtra("anime_category",mData.get(viewHolder.getAdapterPosition()).getCategorie());
                i.putExtra("anime_nb_episode",mData.get(viewHolder.getAdapterPosition()).getNb_episode());
                i.putExtra("anime_rating",mData.get(viewHolder.getAdapterPosition()).getRating());
                i.putExtra("anime_img",mData.get(viewHolder.getAdapterPosition()).getImage_url());

                mContext.startActivity(i);

            }
        });
*/



        return viewHolder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.txtDocument.setText( mContext.getResources().getString(R.string.Servicee)+mData.get(position).getReservation_document_type_id());
        holder.txtOffice.setText( mContext.getResources().getString(R.string.Officee)+mData.get(position).getReservation_office_id());
        holder.txtDate.setText( mContext.getResources().getString(R.string.Datee)+mData.get(position).getReservation_date());
        holder.txtId.setText( mContext.getResources().getString(R.string.ReservationId)+String.valueOf(mData.get(position).getReservation_id()));

        // Load Image from the internet and set it into Imageview using Glide




    }

    @Override
    public int getItemCount() {
        return mData.size();

    }
    public void setfilter(List<Reservations> listitem)
    {
        mData=new ArrayList<>();
        mData.addAll(listitem);
        notifyDataSetChanged();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtDocument ;
        TextView txtOffice ;
        TextView txtDate ;
        TextView txtId;






        public MyViewHolder(View itemView) {
            super(itemView);

            txtDocument = itemView.findViewById(R.id.txtDocument);
            txtOffice = itemView.findViewById(R.id.txtOffice);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtId = itemView.findViewById(R.id.txtId);

        }
    }

}
