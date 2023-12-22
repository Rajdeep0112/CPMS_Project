package com.release.cpmsmobileapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.release.cpmsmobileapp.R;
import com.release.cpmsmobileapp.databinding.RespectiveHearingItemLayoutBinding;
import com.release.cpmsmobileapp.responsebody.HearingsOfRespectiveCase;

import java.util.ArrayList;
import java.util.List;

public class RespectiveCaseHearingDetailsAdapter extends RecyclerView.Adapter<RespectiveCaseHearingDetailsAdapter.RespectiveCaseHearingViewHolder> {


    private List<HearingsOfRespectiveCase> respectiveCaseHearingList = new ArrayList<>();
    private Context context;


    public void setRespectiveCaseHearingListAdpater(List<HearingsOfRespectiveCase> respectiveCaseHearingList, Context context)
    {
        this.respectiveCaseHearingList = respectiveCaseHearingList;
        this.context = context;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RespectiveCaseHearingDetailsAdapter.RespectiveCaseHearingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RespectiveHearingItemLayoutBinding binding = RespectiveHearingItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new RespectiveCaseHearingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RespectiveCaseHearingDetailsAdapter.RespectiveCaseHearingViewHolder holder, int position) {
        HearingsOfRespectiveCase response = respectiveCaseHearingList.get(position);

        holder.srNo.setText("" + (position + 1));
        holder.hearing_date.setText(response.getHearing_date());
        holder.court_name.setText(response.getCourt_name());
        holder.witnesses.setText(response.getWitnesses());
        holder.bailer.setText(response.getBailer());
        holder.accused.setText(response.getAccused());
        holder.remarks.setText(response.getRemarks());
        holder.testified.setText(response.getTestified());

    }

    @Override
    public int getItemCount() {
        return respectiveCaseHearingList.size();
    }

    public static class RespectiveCaseHearingViewHolder extends RecyclerView.ViewHolder {

        TextView srNo,hearing_date, case_detail_id, court_name, witnesses, bailer, accused, remarks, testified;
        public RespectiveCaseHearingViewHolder(@NonNull RespectiveHearingItemLayoutBinding itemView) {
            super(itemView.getRoot());

            srNo=itemView.srNo;
            hearing_date = itemView.hHearingDate;
            court_name = itemView.hCourtName;
            witnesses = itemView.hWitnesses;
            bailer = itemView.hBailer;
            accused = itemView.hAccused;
            remarks = itemView.hRemarks;
            testified = itemView.hTestified;
        }
    }
}
