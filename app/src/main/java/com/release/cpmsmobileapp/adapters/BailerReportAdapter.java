package com.release.cpmsmobileapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.release.cpmsmobileapp.databinding.BailerReportByBailerNameItemLayoutBinding;
import com.release.cpmsmobileapp.databinding.BailerReportByCriminalNameItemLayoutBinding;
import com.release.cpmsmobileapp.databinding.BailerReportByDateWiseItemLayoutBinding;
import com.release.cpmsmobileapp.databinding.BailerReportByFirNoItemLayoutBinding;
import com.release.cpmsmobileapp.databinding.BailerReportItemLayoutBinding;
import com.release.cpmsmobileapp.reports.bailer.RespectiveBailerReportDetailsActivity;
import com.release.cpmsmobileapp.responsebody.BailerReportResponse;

import java.util.ArrayList;
import java.util.List;

public class BailerReportAdapter extends RecyclerView.Adapter<BailerReportAdapter.BailerReportViewHolder> {

    private List<BailerReportResponse> list=new ArrayList<>();
    private Context context;
    public int secIdx=0;

    public BailerReportAdapter(int secIdx) {
        this.secIdx = secIdx;
    }
    public void setBailerReportAdapter(List<BailerReportResponse> list, Context context) {
        this.list = list;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BailerReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BailerReportItemLayoutBinding itemLayoutBinding = BailerReportItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new BailerReportAdapter.BailerReportViewHolder(itemLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BailerReportViewHolder holder, int position) {
        if(secIdx==1) setFirNoWise(holder,position);
        else if(secIdx==2) setBailerNameWise(holder,position);
        else if(secIdx==3) setCriminalNameWise(holder,position);
        else if(secIdx==4) setDateWise(holder,position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class BailerReportViewHolder extends RecyclerView.ViewHolder{

        private BailerReportByFirNoItemLayoutBinding firNoBinding;
        private BailerReportByBailerNameItemLayoutBinding bailerNameBinding;
        private BailerReportByCriminalNameItemLayoutBinding criminalNameBinding;
        private BailerReportByDateWiseItemLayoutBinding dateWiseBinding;

        public BailerReportViewHolder(@NonNull BailerReportItemLayoutBinding itemLayoutBinding) {
            super(itemLayoutBinding.getRoot());

            firNoBinding=itemLayoutBinding.firNoWiseItemLayout;
            bailerNameBinding=itemLayoutBinding.bailerNameWiseItemLayout;
            criminalNameBinding=itemLayoutBinding.criminalNameWiseItemLayout;
            dateWiseBinding=itemLayoutBinding.dateWiseItemLayout;
        }
    }

    private void setFirNoWise(BailerReportViewHolder holder,int position){
        BailerReportResponse response = list.get(position);

        holder.firNoBinding.layout.setVisibility(View.VISIBLE);
        holder.firNoBinding.srNo.setText(""+(position+1));
        holder.firNoBinding.bailerName.setText(response.getName());
        holder.firNoBinding.fathersName.setText(response.getFathers_name());
        holder.firNoBinding.address.setText(response.getAddress());

        holder.firNoBinding.row.setOnClickListener(view -> {
            Intent intent=new Intent(context, RespectiveBailerReportDetailsActivity.class);
            intent.putExtra("BailerDetails",response);
            context.startActivity(intent);
        });
    }

    private void setBailerNameWise(BailerReportViewHolder holder,int position){
        BailerReportResponse response = list.get(position);

        holder.bailerNameBinding.layout.setVisibility(View.VISIBLE);
        holder.bailerNameBinding.srNo.setText(""+(position+1));
        holder.bailerNameBinding.firNo.setText(response.getFir_no());
        holder.bailerNameBinding.year.setText(response.getYear());
        holder.bailerNameBinding.ps.setText(response.getPs());
        holder.bailerNameBinding.district.setText(response.getDistrict());
        holder.bailerNameBinding.address.setText(response.getAddress());
        holder.bailerNameBinding.accusedName.setText(response.getAccused_name());

        holder.bailerNameBinding.row.setOnClickListener(view -> {
            Intent intent=new Intent(context, RespectiveBailerReportDetailsActivity.class);
            intent.putExtra("BailerDetails",response);
            context.startActivity(intent);
        });
    }

    private void setCriminalNameWise(BailerReportViewHolder holder,int position){
        BailerReportResponse response = list.get(position);

        holder.criminalNameBinding.layout.setVisibility(View.VISIBLE);
        holder.criminalNameBinding.srNo.setText(""+(position+1));
        holder.criminalNameBinding.firNo.setText(response.getFir_no());
        holder.criminalNameBinding.year.setText(response.getYear());
        holder.criminalNameBinding.ps.setText(response.getPs());
        holder.criminalNameBinding.district.setText(response.getDistrict());
        holder.criminalNameBinding.address.setText(response.getAddress());
        holder.criminalNameBinding.bailerName.setText(response.getName());

        holder.criminalNameBinding.row.setOnClickListener(view -> {
            Intent intent=new Intent(context, RespectiveBailerReportDetailsActivity.class);
            intent.putExtra("BailerDetails",response);
            context.startActivity(intent);
        });
    }

    private void setDateWise(BailerReportViewHolder holder,int position){
        BailerReportResponse response = list.get(position);

        holder.dateWiseBinding.layout.setVisibility(View.VISIBLE);
        holder.dateWiseBinding.srNo.setText(""+(position+1));
        holder.dateWiseBinding.bailRemarks.setText(response.getBail_remarks());

        holder.dateWiseBinding.row.setOnClickListener(view -> {
            Intent intent=new Intent(context, RespectiveBailerReportDetailsActivity.class);
            intent.putExtra("BailerDetails",response);
            context.startActivity(intent);
        });
    }
}
