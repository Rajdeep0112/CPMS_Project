package com.release.cpmsmobileapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.release.cpmsmobileapp.databinding.DutrByDutrWiseItemLayoutBinding;
import com.release.cpmsmobileapp.databinding.DutrByPsWiseItemLayoutBinding;
import com.release.cpmsmobileapp.databinding.DutrReportItemLayoutBinding;
import com.release.cpmsmobileapp.reports.dutr.RespectiveDutrDetails;
import com.release.cpmsmobileapp.responsebody.DutrResponse;

import java.util.ArrayList;
import java.util.List;

public class DutrAdapter extends RecyclerView.Adapter<DutrAdapter.DutrViewHolder> {

    private List<DutrResponse> responses = new ArrayList<>();
    private Context context;
    int secIdx=0;

    public DutrAdapter(int secIdx) {
        this.secIdx = secIdx;
    }

    public void setDutrAdapter(List<DutrResponse> responses, Context context) {
        this.responses = responses;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DutrViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DutrReportItemLayoutBinding itemLayoutBinding = DutrReportItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new DutrViewHolder(itemLayoutBinding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DutrAdapter.DutrViewHolder holder, int position) {
        if(secIdx==1) setDutrWise(holder,position);
//        else if(secIdx==2) setPsWise(holder,position);
    }

    @Override
    public int getItemCount() {
        return responses.size();
    }

    public static class DutrViewHolder extends RecyclerView.ViewHolder {
        private DutrByDutrWiseItemLayoutBinding dutrWiseBinding;
//        private DutrByPsWiseItemLayoutBinding psWiseBinding;

        public DutrViewHolder(@NonNull DutrReportItemLayoutBinding itemBinding) {
            super(itemBinding.getRoot());

            dutrWiseBinding = itemBinding.dutrWiseItemLayout;
//            psWiseBinding=itemBinding.psWiseItemLayout;
        }
    }

    private void setDutrWise(DutrViewHolder holder,int position){
        DutrResponse response = responses.get(position);

        holder.dutrWiseBinding.layout.setVisibility(View.VISIBLE);
        holder.dutrWiseBinding.srNo.setText("" + (position + 1));
        holder.dutrWiseBinding.firNo.setText(response.getFirNumber());
        holder.dutrWiseBinding.year.setText(response.getYear());
        holder.dutrWiseBinding.psName.setText(response.getPoliceStation());
        holder.dutrWiseBinding.districtName.setText(response.getDistrict());
        holder.dutrWiseBinding.testified.setText(response.getTestified());
        holder.dutrWiseBinding.testifiedRemarks.setText(response.getTestified_remarks());
        holder.dutrWiseBinding.stNo.setText(response.getStNumber());
        holder.dutrWiseBinding.grNo.setText(response.getGrNumber());
        holder.dutrWiseBinding.trNo.setText(response.getTrNumber());

        holder.dutrWiseBinding.row.setOnClickListener(view -> {
            Intent intent = new Intent(context, RespectiveDutrDetails.class);
            intent.putExtra("DutrDetails",  response);
            context.startActivity(intent);
        });
    }

//    private void setPsWise(DutrViewHolder holder,int position){
//        DutrResponse response = responses.get(position);
//
//        holder.psWiseBinding.layout.setVisibility(View.VISIBLE);
//        holder.psWiseBinding.srNo.setText("" + (position + 1));
//        holder.psWiseBinding.testified.setText(response.getTestified());
//        holder.psWiseBinding.testifiedRemarks.setText(response.getTestified_remarks());
//
//        holder.psWiseBinding.row.setOnClickListener(view -> {
//            Intent intent = new Intent(context, RespectiveDutrDetails.class);
//            intent.putExtra("DutrDetails",  response);
//            context.startActivity(intent);
//        });
//    }
}
