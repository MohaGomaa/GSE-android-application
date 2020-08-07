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
import com.example.mohamedahmedgomaa.servieclyapp.model.TrafficViolation;

import java.util.ArrayList;
import java.util.List;


public class TrafficViolationsAdminAdapter extends RecyclerView.Adapter<TrafficViolationsAdminAdapter.MyViewHolder> {

    private static Context mContext ;
    private static List<TrafficViolation> mData ;


    public TrafficViolationsAdminAdapter(Context mContext, List<TrafficViolation> mData) {
        this.mContext = mContext;
        this.mData = mData;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.trafficviolations_admin_item,parent,false) ;
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

        holder.TypeViolation.setText( mContext.getResources().getString(R.string.TypeViolation)+mData.get(position).getType_Violation());
        holder.Car_code.setText( mContext.getResources().getString(R.string.CarCode)+mData.get(position).getCar_code());
        holder.Date.setText( mContext.getResources().getString(R.string.Datee)+mData.get(position).getDate());
        holder.price.setText( mContext.getResources().getString(R.string.price)+String.valueOf(mData.get(position).getPrice()));
        holder.IsPaid.setText( mContext.getResources().getString(R.string.IsPaid)+String.valueOf(mData.get(position).getIs_Paid()));
        holder.name.setText( mContext.getResources().getString(R.string.CitizenName)+String.valueOf(mData.get(position).getCitizen_Name()));
        holder.NId.setText( mContext.getResources().getString(R.string.NationalId)+String.valueOf(mData.get(position).getNational_id()));

        // Load Image from the internet and set it into Imageview using Glide




    }

    @Override
    public int getItemCount() {
        return mData.size();

    }
    public void setfilter(List<TrafficViolation> listitem)
    {
        mData=new ArrayList<>();
        mData.addAll(listitem);
        notifyDataSetChanged();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder implements com.example.mohamedahmedgomaa.servieclyapp.view.MyViewHolder, View.OnClickListener, View.OnCreateContextMenuListener {

        TextView TypeViolation ;
        TextView Car_code ;
        TextView NId ;
        TextView name ;
        TextView price ;
        TextView Date;
        TextView  IsPaid ;
        private ItemClickListener itemClickListener;

        public MyViewHolder(View itemView) {
            super(itemView);

            TypeViolation = itemView.findViewById(R.id.tvTypeofViolation);
            Car_code = itemView.findViewById(R.id.tvCarcode);
            price = itemView.findViewById(R.id.tvPrice);
            Date = itemView.findViewById(R.id.tvDate);
            IsPaid = itemView.findViewById(R.id.tvIsPaid);
            name = itemView.findViewById(R.id.tvCitizenName);
            NId = itemView.findViewById(R.id.tvNationalId);
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

           itemClickListener.onClick( v,getAdapterPosition(), false);
            //int itemPosition = mRecyclerView.getChildLayoutPosition(view);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle(R.string.SelectAction);
            menu.add(0,0,getAdapterPosition(), R.string.Cancel);


        }


    }

}
