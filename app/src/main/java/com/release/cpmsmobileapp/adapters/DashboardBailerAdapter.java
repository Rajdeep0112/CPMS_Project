package com.release.cpmsmobileapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.release.cpmsmobileapp.databinding.BailerItemLayoutBinding;
import com.release.cpmsmobileapp.reports.bailer.RespectiveBailerReportDetailsActivity;
import com.release.cpmsmobileapp.responsebody.BailerReportResponse;

import java.util.ArrayList;
import java.util.List;

public class DashboardBailerAdapter extends RecyclerView.Adapter<DashboardBailerAdapter.BailerViewHolder> {

    private List<BailerReportResponse> bailerResponse = new ArrayList<>();

    private Context context;

    public void setBailerAdapter(List<BailerReportResponse> bailerResponse, Context context)
    {
        this.bailerResponse = bailerResponse;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DashboardBailerAdapter.BailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BailerItemLayoutBinding bailerItemLayoutBinding = BailerItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new DashboardBailerAdapter.BailerViewHolder(bailerItemLayoutBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull DashboardBailerAdapter.BailerViewHolder holder, int position) {
        BailerReportResponse bailerItem = bailerResponse.get(position);
        holder.srNo.setText(""+(position+1));
        holder.bailer.setText(bailerItem.getName());
        holder.accused.setText(bailerItem.getAccused_name());
        holder.date.setText(bailerItem.getBail_date());
        holder.firNo.setText(bailerItem.getFir_no());
        holder.year.setText(bailerItem.getYear());
        holder.ps.setText(bailerItem.getPs());
        holder.remarks.setText(bailerItem.getBail_remarks());

        holder.row.setOnClickListener(v -> {
            Intent intent = new Intent(context, RespectiveBailerReportDetailsActivity.class);
            intent.putExtra("BailerDetails", bailerItem);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return bailerResponse.size();
    }

    public class BailerViewHolder extends RecyclerView.ViewHolder{

        TextView srNo, bailer, accused, date, firNo, year, ps, remarks;
        LinearLayout bailerLayout;

        TableRow row;
        public BailerViewHolder(@NonNull BailerItemLayoutBinding itemView) {
            super(itemView.getRoot());
            srNo = itemView.srNo;
            bailer = itemView.bailer;
            accused = itemView.accused;
            date = itemView.date;
            firNo = itemView.firNo;
            year = itemView.year;
            ps = itemView.ps;
            remarks = itemView.remarks;

            row = itemView.row;

            bailerLayout = itemView.layout;

        }
    }
}
