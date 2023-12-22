package com.release.cpmsmobileapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.release.cpmsmobileapp.databinding.ReportCaseAccusedWiseItemLayoutBinding;
import com.release.cpmsmobileapp.reports.caseacc.RespectiveCaseAccusedDetails;
import com.release.cpmsmobileapp.responsebody.ReportCaseAccusedResponse;

import java.util.ArrayList;
import java.util.List;

public class CaseAccusedWiseReportAdapter extends RecyclerView.Adapter<CaseAccusedWiseReportAdapter.CaseAccusedWiseReportViewHolder> {

    private List<ReportCaseAccusedResponse> responseList = new ArrayList<>();

    private Context context;

    public void setCaseAccusedWiseReportAdapter(List<ReportCaseAccusedResponse> responseList, Context context)
    {
        this.responseList = responseList;
        this.context = context;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public CaseAccusedWiseReportAdapter.CaseAccusedWiseReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ReportCaseAccusedWiseItemLayoutBinding itemLayoutBinding = ReportCaseAccusedWiseItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CaseAccusedWiseReportAdapter.CaseAccusedWiseReportViewHolder(itemLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CaseAccusedWiseReportAdapter.CaseAccusedWiseReportViewHolder holder, int position) {
        ReportCaseAccusedResponse response = responseList.get(position);

        holder.srNo.setText(""+(position+1));
        holder.name.setText(response.getName());
        holder.status.setText(response.getStatus());
        holder.disposalType.setText(response.getDisposal_type());
        holder.actSection.setText(response.getAct_section());
        holder.punishmentType.setText(response.getPunishment_type());
        holder.punishmentPeriod.setText(response.getPunishment_period());
        holder.fineAmount.setText(response.getFine_amount());
        holder.firNo.setText(response.getFir_no());
        holder.year.setText(response.getYear());
        holder.ps.setText(response.getPs());
        holder.layout.setOnClickListener(view -> {
            Intent intent = new Intent(context, RespectiveCaseAccusedDetails.class);
            intent.putExtra("CaseAccusedDetails", response);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return responseList.size();
    }

    public static class CaseAccusedWiseReportViewHolder extends RecyclerView.ViewHolder {

        TextView srNo, name, status, disposalType, actSection, punishmentType, punishmentPeriod, fineAmount, firNo, year, ps;

        LinearLayout layout;
        public CaseAccusedWiseReportViewHolder(@NonNull ReportCaseAccusedWiseItemLayoutBinding itemLayoutBinding) {
            super(itemLayoutBinding.getRoot());

            srNo = itemLayoutBinding.srNo;
            name = itemLayoutBinding.name;
            status = itemLayoutBinding.status;
            disposalType = itemLayoutBinding.disposalType;
            actSection = itemLayoutBinding.actSection;
            punishmentPeriod = itemLayoutBinding.punishmentPeriod;
            punishmentType = itemLayoutBinding.punishmentType;
            fineAmount = itemLayoutBinding.fineAmount;
            firNo = itemLayoutBinding.firNo;
            year = itemLayoutBinding.year;
            ps = itemLayoutBinding.ps;
            layout = itemLayoutBinding.layout;
        }
    }
}
