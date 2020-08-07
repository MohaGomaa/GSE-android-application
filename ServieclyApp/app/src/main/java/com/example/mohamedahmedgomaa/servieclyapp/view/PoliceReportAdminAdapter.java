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
import com.example.mohamedahmedgomaa.servieclyapp.model.PoliceReport;

import java.util.ArrayList;
import java.util.List;


public class PoliceReportAdminAdapter extends RecyclerView.Adapter<PoliceReportAdminAdapter.MyViewHolder> {

    private static Context mContext ;
    private static List<PoliceReport> mData ;


    public PoliceReportAdminAdapter(Context mContext, List<PoliceReport> mData) {
        this.mContext = mContext;
        this.mData = mData;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view ;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.police_report_admin_item,parent,false) ;
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

        holder.date.setText( mContext.getResources().getString(R.string.Datee)+mData.get(position).getDate());
        holder.ReportType.setText( mContext.getResources().getString(R.string.ReportType)+mData.get(position).getReport_type());
        holder.ClassificationName.setText( mContext.getResources().getString(R.string.ClassificationName)+mData.get(position).getClassification_name());
        holder.PoliceDepartment.setText( mContext.getResources().getString(R.string.PoliceDepartment)+mData.get(position).getPolice_department());
        holder.ReportNumber.setText( mContext.getResources().getString(R.string.ReportNumber)+mData.get(position).getReport_number());
        holder.citizenName.setText( mContext.getResources().getString(R.string.CitizenName)+mData.get(position).getCitizen_Name());
        holder.NId.setText( mContext.getResources().getString(R.string.NationalId)+mData.get(position).getNational_id());




    }

    @Override
    public int getItemCount() {
        return mData.size();

    }
    public void setfilter(List<PoliceReport> listitem)
    {
        mData=new ArrayList<>();
        mData.addAll(listitem);
        notifyDataSetChanged();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder implements com.example.mohamedahmedgomaa.servieclyapp.view.MyViewHolder {

        TextView date ;
        TextView ReportNumber ;
        TextView PoliceDepartment ;
        TextView ReportType ;
        TextView ClassificationName ;
        TextView citizenName ;
        TextView NId ;
        private ItemClickListener itemClickListener;

        public MyViewHolder(View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.tvDatee);
            ClassificationName = itemView.findViewById(R.id.tvClassificationName);
            ReportNumber = itemView.findViewById(R.id.tvReportNumber);
            PoliceDepartment = itemView.findViewById(R.id.tvPoliceDepartment);
            ReportType = itemView.findViewById(R.id.tvReportType);
            citizenName = itemView.findViewById(R.id.tvCitizenName);
            NId = itemView.findViewById(R.id.tvNationalId);


        }


        @Override
        public void onClick(View v) {

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        }
    }

}
