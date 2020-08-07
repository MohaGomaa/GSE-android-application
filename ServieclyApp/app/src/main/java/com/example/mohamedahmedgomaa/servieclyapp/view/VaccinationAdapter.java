package com.example.mohamedahmedgomaa.servieclyapp.view;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;


import com.example.mohamedahmedgomaa.servieclyapp.Interface.ItemClickListener;
import com.example.mohamedahmedgomaa.servieclyapp.R;
import com.example.mohamedahmedgomaa.servieclyapp.model.Vaccination;

import java.util.ArrayList;
import java.util.List;


public class VaccinationAdapter extends RecyclerView.Adapter<VaccinationAdapter.MyViewHolder> {

    private static Context mContext ;
    private static List<Vaccination> mData ;


    public VaccinationAdapter(Context mContext, List<Vaccination> mData) {
        this.mContext = mContext;
        this.mData = mData;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.vaccination_item,parent,false) ;
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

        holder.Child_Name.setText( mContext.getResources().getString(R.string.ChildName)+mData.get(position).getChild_Name());
        holder.Vaccination_Type.setText( mContext.getResources().getString(R.string.VaccinationTypee)+mData.get(position).getVaccination_Type());
        holder.Heathcare.setText( mContext.getResources().getString(R.string.Healthcares)+mData.get(position).getHeathcare());
        holder.Scedual.setText( mContext.getResources().getString(R.string.Scheduals)+String.valueOf(mData.get(position).getScedual()));
        holder.code.setText( mContext.getResources().getString(R.string.Codes)+String.valueOf(mData.get(position).getCode()));
        holder.vaccinated.setText( mContext.getResources().getString(R.string.Vaccinated)+String.valueOf(mData.get(position).getVaccinated()));

        holder.itemView.setTag(mData.get(position).getId());
        // Load Image from the internet and set it into Imageview using Glide




    }

    @Override
    public int getItemCount() {
        return mData.size();

    }
    public void setfilter(List<Vaccination> listitem)
    {
        mData=new ArrayList<>();
        mData.addAll(listitem);
        notifyDataSetChanged();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder implements com.example.mohamedahmedgomaa.servieclyapp.view.MyViewHolder, View.OnClickListener, View.OnCreateContextMenuListener {

        TextView Child_Name ;
        TextView Vaccination_Type ;
        TextView Heathcare ;
        TextView Scedual;
        TextView code;
        TextView vaccinated;
        private ItemClickListener itemClickListener;

        public MyViewHolder(View itemView) {
            super(itemView);

            Child_Name = itemView.findViewById(R.id.tvChildName);
            Vaccination_Type = itemView.findViewById(R.id.tvType);
            Heathcare = itemView.findViewById(R.id.tvHealthcare);
            Scedual = itemView.findViewById(R.id.tvSchedual);
            code = itemView.findViewById(R.id.tvCode);
            vaccinated = itemView.findViewById(R.id.tvVacc);
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
