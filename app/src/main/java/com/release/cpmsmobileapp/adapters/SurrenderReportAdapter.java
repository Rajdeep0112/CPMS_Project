package com.release.cpmsmobileapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.release.cpmsmobileapp.databinding.SurrenderReportByCaseNoItemLayoutBinding;
import com.release.cpmsmobileapp.databinding.SurrenderReportByCourtWiseItemLayoutBinding;
import com.release.cpmsmobileapp.databinding.SurrenderReportByDateWiseItemLayoutBinding;
import com.release.cpmsmobileapp.databinding.SurrenderReportItemLayoutBinding;
import com.release.cpmsmobileapp.reports.surrender.RespectiveSurrenderReportDetailsActivity;
import com.release.cpmsmobileapp.responsebody.SurrenderReportResponse;

import java.util.ArrayList;
import java.util.List;

public class SurrenderReportAdapter extends RecyclerView.Adapter<SurrenderReportAdapter.SurrenderReportViewHolder> {

    private List<SurrenderReportResponse> list=new ArrayList<>();
    private Context context;
    public int secIdx=0;

    public SurrenderReportAdapter(int secIdx) {
        this.secIdx = secIdx;
    }

    public void setSurrenderReportAdapter(List<SurrenderReportResponse> list, Context context) {
        this.list = list;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SurrenderReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SurrenderReportItemLayoutBinding itemLayoutBinding = SurrenderReportItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new SurrenderReportAdapter.SurrenderReportViewHolder(itemLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SurrenderReportViewHolder holder, int position) {
        if(secIdx==1) setCaseNoWise(holder,position);
        else if(secIdx==2) setDateWise(holder,position);
        else if(secIdx==3) setCourtWise(holder,position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class SurrenderReportViewHolder extends RecyclerView.ViewHolder{

        private SurrenderReportByCaseNoItemLayoutBinding caseNoBinding;
        private SurrenderReportByDateWiseItemLayoutBinding dateWiseBinding;
        private SurrenderReportByCourtWiseItemLayoutBinding courtWiseBinding;

        public SurrenderReportViewHolder(@NonNull SurrenderReportItemLayoutBinding itemLayoutBinding) {
            super(itemLayoutBinding.getRoot());

            caseNoBinding=itemLayoutBinding.caseNoWiseItemLayout;
            dateWiseBinding=itemLayoutBinding.dateWiseItemLayout;
            courtWiseBinding=itemLayoutBinding.courtWiseItemLayout;
        }
    }

    private void setCaseNoWise(SurrenderReportViewHolder holder, int position){
        SurrenderReportResponse response = list.get(position);

        holder.caseNoBinding.layout.setVisibility(View.VISIBLE);
        holder.caseNoBinding.srNo.setText(""+(position+1));
        holder.caseNoBinding.accusedName.setText(response.getName());
        holder.caseNoBinding.fathersName.setText(response.getFathers_name());
        holder.caseNoBinding.address.setText(response.getAddress());
        holder.caseNoBinding.actSection.setText(response.getAct_section());

        holder.caseNoBinding.row.setOnClickListener(view -> {
            Intent intent=new Intent(context, RespectiveSurrenderReportDetailsActivity.class);
            intent.putExtra("SurrenderDetails",response);
            context.startActivity(intent);
        });
    }

    private void setDateWise(SurrenderReportViewHolder holder, int position){
        SurrenderReportResponse response = list.get(position);

        holder.dateWiseBinding.layout.setVisibility(View.VISIBLE);
        holder.dateWiseBinding.srNo.setText(""+(position+1));
        holder.dateWiseBinding.firNo.setText(response.getFir_no());
        holder.dateWiseBinding.year.setText(response.getYear());
        holder.dateWiseBinding.ps.setText(response.getPs());
        holder.dateWiseBinding.district.setText(response.getDistrict());
        holder.dateWiseBinding.address.setText(response.getAddress());
        holder.dateWiseBinding.accusedName.setText(response.getName());

        holder.dateWiseBinding.row.setOnClickListener(view -> {
            Intent intent=new Intent(context, RespectiveSurrenderReportDetailsActivity.class);
            intent.putExtra("SurrenderDetails",response);
            context.startActivity(intent);
        });
    }

    private void setCourtWise(SurrenderReportViewHolder holder, int position){
        SurrenderReportResponse response = list.get(position);

        holder.courtWiseBinding.layout.setVisibility(View.VISIBLE);
        holder.courtWiseBinding.srNo.setText(""+(position+1));
        holder.courtWiseBinding.court.setText(response.getCourt());
        holder.courtWiseBinding.remarks.setText(response.getRemarks());

        holder.courtWiseBinding.row.setOnClickListener(view -> {
            Intent intent=new Intent(context, RespectiveSurrenderReportDetailsActivity.class);
            intent.putExtra("SurrenderDetails",response);
            context.startActivity(intent);
        });
    }
}
