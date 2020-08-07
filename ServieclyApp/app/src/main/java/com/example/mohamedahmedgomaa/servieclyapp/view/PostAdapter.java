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
import com.example.mohamedahmedgomaa.servieclyapp.model.ComplaintModel;
import com.example.mohamedahmedgomaa.servieclyapp.model.Post;

import java.util.ArrayList;
import java.util.List;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

    private static Context mContext ;
    private static List<Post> mData ;


    public PostAdapter(Context mContext, List<Post> mData) {
        this.mContext = mContext;
        this.mData = mData;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.complaint_item,parent,false) ;
        final MyViewHolder viewHolder = new MyViewHolder(view) ;

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.postText.setText(mData.get(position).getTextPost());
        holder.postUsername.setText(mData.get(position).getUsernamePost());
        holder.postDate.setText((mData.get(position).getDatePost()));

        // Load Image from the internet and set it into Imageview using Glide




    }

    @Override
    public int getItemCount() {
        return mData.size();

    }
    public void setfilter(List<Post> listitem)
    {
        mData=new ArrayList<>();
        mData.addAll(listitem);
        notifyDataSetChanged();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder implements com.example.mohamedahmedgomaa.servieclyapp.view.MyViewHolder, View.OnClickListener, View.OnCreateContextMenuListener {

        TextView postUsername ;
        TextView postDate ;
        TextView postText;
        TextView postPhoto;
        private ItemClickListener itemClickListener;

        public MyViewHolder(View itemView) {
            super(itemView);

            postUsername = itemView.findViewById(R.id.tvGover);
            postDate = itemView.findViewById(R.id.tvSenDate);
            postText = itemView.findViewById(R.id.tvComplaint);
            postPhoto = itemView.findViewById(R.id.tvComplaint);
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
           // menu.add(0,0,getAdapterPosition(), R.string.ShowResponse);


        }


    }

}
