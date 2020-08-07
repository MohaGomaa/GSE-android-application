package com.example.mohamedahmedgomaa.servieclyapp.view;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mohamedahmedgomaa.servieclyapp.Interface.ItemClickListener;
import com.example.mohamedahmedgomaa.servieclyapp.R;
import com.example.mohamedahmedgomaa.servieclyapp.model.Contact;
import com.example.mohamedahmedgomaa.servieclyapp.model.Vaccination;

import java.util.ArrayList;
import java.util.List;


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {

    private Context mContext ;
    private List<Contact> mData ;


    public ContactAdapter(Context mContext, List<Contact> mData) {
        this.mContext = mContext;
        this.mData = mData;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.vaccination_item,parent,false) ;
        final MyViewHolder viewHolder = new MyViewHolder(view) ;




        return viewHolder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.Contact_id.setText("Contact Id : "+mData.get(position).getContact_id());
        holder.Contact_data.setText("Contact Data : "+mData.get(position).getContact_data());
        holder.Contact_type.setText("Contact Type : " +mData.get(position).getContact_type());

    }

    @Override
    public int getItemCount() {
        return mData.size();

    }
    public void setfilter(List<Contact> listitem)
    {
        mData=new ArrayList<>();
        mData.addAll(listitem);
        notifyDataSetChanged();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
            , View.OnCreateContextMenuListener{

        TextView Contact_id ;
        TextView Contact_type ;
        TextView Contact_data ;
        public  ItemClickListener itemClickListener;

        public MyViewHolder(View itemView) {
            super(itemView);

            Contact_id = itemView.findViewById(R.id.tvContactId);
            Contact_type = itemView.findViewById(R.id.tvType);
            Contact_data = itemView.findViewById(R.id.tvContactData);
            itemView.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(this);


        }



        public  void setItemClickListener(ItemClickListener itmClickListener){
            this.itemClickListener = itmClickListener;


        }

        @Override
        public void onClick(View v ) {
            itemClickListener.onClick(v,getAdapterPosition(),false);
        }


        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Select an action");
            contextMenu.add(0,0,getAdapterPosition(),"Update");
            contextMenu.add(0,1,getAdapterPosition(),"Delete");



        }
    }

}
