package com.release.cpmsmobileapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.release.cpmsmobileapp.databinding.AbsenteeWitnessWiseItemLayoutBinding;

import com.release.cpmsmobileapp.reports.absentee.RespectiveAbsenteeDetails;

import com.release.cpmsmobileapp.responsebody.AbsenteeResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AbsenteeWitnessWiseAdapter extends RecyclerView.Adapter<AbsenteeWitnessWiseAdapter.AbsenteeWitnessWiseHolder> {

    private List<AbsenteeResponse> absenteeResponses = new ArrayList<>();

    private Context context;

    public void setAbsenteeWitnessWiseAdapter(List<AbsenteeResponse> absenteeResponses, Context context) {
        this.absenteeResponses = absenteeResponses;
        this.context = context;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public AbsenteeWitnessWiseAdapter.AbsenteeWitnessWiseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AbsenteeWitnessWiseItemLayoutBinding itemLayoutBinding = AbsenteeWitnessWiseItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AbsenteeWitnessWiseAdapter.AbsenteeWitnessWiseHolder(itemLayoutBinding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AbsenteeWitnessWiseAdapter.AbsenteeWitnessWiseHolder holder, int position) {
        AbsenteeResponse response = absenteeResponses.get(position);

        holder.sr_no.setText("" + (position+1));
        holder.fir_no.setText(response.getFir_no());
        holder.year.setText(response.getYear());
        holder.ps_name.setText(response.getPs());
        holder.district_name.setText(response.getDistrict());
        holder.court.setText(response.getCourt());
        holder.date.setText(response.getHearing_date());
        holder.witness_type.setText(response.getWitness_type());
        holder.witness_testified.setText(response.getTestified());
        holder.remarks.setText(response.getRemarks());

        holder.layout.setOnClickListener(view -> {
            Intent intent = new Intent(context, RespectiveAbsenteeDetails.class);
            intent.putExtra("AbsenteeDetails", response);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return absenteeResponses.size();
    }

    public static class AbsenteeWitnessWiseHolder extends RecyclerView.ViewHolder {

        TextView sr_no,fir_no, year, ps_name, district_name, court, date, witness_type, witness_testified, remarks;
        LinearLayout layout;


        public AbsenteeWitnessWiseHolder(@NonNull AbsenteeWitnessWiseItemLayoutBinding itemLayoutBinding) {

            super(itemLayoutBinding.getRoot());

            sr_no = itemLayoutBinding.srNo;
            fir_no = itemLayoutBinding.firNo;
            year = itemLayoutBinding.year;
            ps_name = itemLayoutBinding.psName;
            district_name = itemLayoutBinding.districtName;
            court = itemLayoutBinding.courtName;
            date = itemLayoutBinding.hearingDate;
            witness_type = itemLayoutBinding.wWitnessType;
            witness_testified = itemLayoutBinding.witnessTestified;
            remarks = itemLayoutBinding.remarks;
            layout = itemLayoutBinding.layout;
        }
    }
}
