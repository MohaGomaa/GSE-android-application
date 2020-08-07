package com.example.mohamedahmedgomaa.servieclyapp.view;

import android.content.Context;
import android.content.res.Resources;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mohamedahmedgomaa.servieclyapp.Interface.ItemClickListener;
import com.example.mohamedahmedgomaa.servieclyapp.R;
import com.example.mohamedahmedgomaa.servieclyapp.model.StudentCertification;
import com.example.mohamedahmedgomaa.servieclyapp.model.Vaccination;

import java.util.ArrayList;
import java.util.List;


public class StudentCertifcationAdapter extends RecyclerView.Adapter<StudentCertifcationAdapter.MyViewHolder> {

    private static Context mContext ;
    private static List<StudentCertification> mData ;


    public StudentCertifcationAdapter(Context mContext, List<StudentCertification> mData) {
        this.mContext = mContext;
        this.mData = mData;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.student_cirtification_item,parent,false) ;
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


        holder.CertifcationName.setText( mContext.getResources().getString(R.string.CertificationName)+mData.get(position).getCertifcation_Name());
        holder.Date.setText( mContext.getResources().getString(R.string.Datee)+mData.get(position).getDate());
        holder.Grade.setText( mContext.getResources().getString(R.string.CertificationGrade)+mData.get(position).getGradeName());
        // Load Image from the internet and set it into Imageview using Glide




    }

    @Override
    public int getItemCount() {
        return mData.size();

    }
    public void setfilter(List<StudentCertification> listitem)
    {
        mData=new ArrayList<>();
        mData.addAll(listitem);
        notifyDataSetChanged();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder implements com.example.mohamedahmedgomaa.servieclyapp.view.MyViewHolder, View.OnClickListener, View.OnCreateContextMenuListener {

        TextView CertifcationName ;
        TextView Date ;
        TextView Grade ;

        private ItemClickListener itemClickListener;

        public MyViewHolder(View itemView) {
            super(itemView);

            CertifcationName = itemView.findViewById(R.id.tvCertificationName);
            Date = itemView.findViewById(R.id.tvDate);
            Grade = itemView.findViewById(R.id.tvCertificationGrade);
            /*
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
            */

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
            menu.setHeaderTitle(mContext.getResources().getString(R.string.SelectAction));
            menu.add(0,0,getAdapterPosition(), mContext.getResources().getString(R.string.download));


        }


    }

}
