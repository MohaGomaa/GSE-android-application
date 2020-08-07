package com.example.mohamedahmedgomaa.servieclyapp.view;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mohamedahmedgomaa.servieclyapp.Interface.ItemClickListener;
import com.example.mohamedahmedgomaa.servieclyapp.R;
import com.example.mohamedahmedgomaa.servieclyapp.ReservDocAppointment;
import com.example.mohamedahmedgomaa.servieclyapp.model.ReservationDoctorAppointment;
import com.example.mohamedahmedgomaa.servieclyapp.model.Vaccination;

import java.util.ArrayList;
import java.util.List;


public class ReservDocAppAdapter extends RecyclerView.Adapter<ReservDocAppAdapter.MyViewHolder> {

    private Context mContext ;
    private List<ReservationDoctorAppointment> mData ;


    public ReservDocAppAdapter(Context mContext, List<ReservationDoctorAppointment> mData) {
        this.mContext = mContext;
        this.mData = mData;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.reserv_doc_app_item,parent,false) ;
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

        holder.Spec.setText( mContext.getResources().getString(R.string.specializationss)+mData.get(position).getRDA_Specialization());
        holder.Doctor.setText( mContext.getResources().getString(R.string.Doctors)+mData.get(position).getRDA_Doctor());
        holder.Heathcare.setText( mContext.getResources().getString(R.string.Healthcares )+mData.get(position).getRDA_Healthcare());
        holder.Scedual.setText( mContext.getResources().getString(R.string.Scheduals)+String.valueOf(mData.get(position).getRDA_Schedual()));
        holder.code.setText( mContext.getResources().getString(R.string.Codes)+String.valueOf(mData.get(position).getRDA_Code()));
        holder.itemView.setTag(mData.get(position).getRDA_id());

        // Load Image from the internet and set it into Imageview using Glide




    }

    @Override
    public int getItemCount() {
        return mData.size();

    }



    public void setfilter(List<ReservationDoctorAppointment> listitem)
    {
        mData=new ArrayList<>();
        mData.addAll(listitem);
        notifyDataSetChanged();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {

        TextView Spec ;
        TextView Doctor ;
        TextView Heathcare ;
        TextView Scedual;
        TextView code;
       private ItemClickListener itemClickListener;
        public MyViewHolder(View itemView) {
            super(itemView);

            Spec = itemView.findViewById(R.id.tvSpec);
            Doctor = itemView.findViewById(R.id.tvDoctor);
            Heathcare = itemView.findViewById(R.id.tvHealthcare);
            Scedual = itemView.findViewById(R.id.tvSchedual);
            code = itemView.findViewById(R.id.tvCode);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);

        }

        public ItemClickListener getItemClickListener() {
            return itemClickListener;
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick( v,getAdapterPosition(),false);
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle(R.string.SelectAction);
            menu.add(0,0,getAdapterPosition(), R.string.Cancel);


        }


    }

}
