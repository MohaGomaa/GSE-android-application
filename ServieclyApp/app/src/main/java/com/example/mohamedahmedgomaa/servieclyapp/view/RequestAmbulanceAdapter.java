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
import com.example.mohamedahmedgomaa.servieclyapp.model.RequestAmbulance;

import java.util.ArrayList;
import java.util.List;


public class RequestAmbulanceAdapter extends RecyclerView.Adapter<RequestAmbulanceAdapter.MyViewHolder> {

    private static Context mContext ;
    private static List<RequestAmbulance> mData ;


    public RequestAmbulanceAdapter(Context mContext, List<RequestAmbulance> mData) {
        this.mContext = mContext;
        this.mData = mData;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.request_ambulance_item,parent,false) ;
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

        holder.details.setText( mContext.getResources().getString(R.string.Details)+mData.get(position).getDetails());
        holder.date.setText( mContext.getResources().getString(R.string.Datee)+mData.get(position).getDate());





    }

    @Override
    public int getItemCount() {
        return mData.size();

    }
    public void setfilter(List<RequestAmbulance> listitem)
    {
        mData=new ArrayList<>();
        mData.addAll(listitem);
        notifyDataSetChanged();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder implements com.example.mohamedahmedgomaa.servieclyapp.view.MyViewHolder, View.OnClickListener, View.OnCreateContextMenuListener {

        TextView date ;
        TextView details ;
        private ItemClickListener itemClickListener;

        public MyViewHolder(View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.tvDatee);
            details = itemView.findViewById(R.id.tvDetails);

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

            menu.add(0,0,getAdapterPosition(), R.string.ShowInMap);

            menu.add(0,1,getAdapterPosition(), R.string.Finshed);


        }


    }

}
