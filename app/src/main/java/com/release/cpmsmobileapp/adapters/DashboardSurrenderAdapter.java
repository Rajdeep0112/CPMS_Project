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

import com.release.cpmsmobileapp.databinding.SurrenderItemLayoutBinding;
import com.release.cpmsmobileapp.reports.bailer.RespectiveBailerReportDetailsActivity;
import com.release.cpmsmobileapp.reports.surrender.RespectiveSurrenderReportDetailsActivity;
import com.release.cpmsmobileapp.responsebody.SurrenderReportResponse;

import java.util.ArrayList;
import java.util.List;

public class DashboardSurrenderAdapter extends RecyclerView.Adapter<DashboardSurrenderAdapter.SurrenderViewHolder> {

    private List<SurrenderReportResponse> surrenderResponses = new ArrayList<>();
    private Context context;

    public void setSurrenderAdapter(List<SurrenderReportResponse> surrenderResponses, Context context)
    {
        this.surrenderResponses = surrenderResponses;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DashboardSurrenderAdapter.SurrenderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SurrenderItemLayoutBinding itemLayoutBinding = SurrenderItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new DashboardSurrenderAdapter.SurrenderViewHolder(itemLayoutBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull DashboardSurrenderAdapter.SurrenderViewHolder holder, int position) {
        SurrenderReportResponse surrenderItem = surrenderResponses.get(position);
        holder.srNo.setText(""+(position + 1));
        holder.name.setText(surrenderItem.getName());
        holder.date.setText(surrenderItem.getSurrender_date());
        holder.firNo.setText(surrenderItem.getFir_no());
        holder.year.setText(surrenderItem.getYear());
        holder.ps.setText(surrenderItem.getPs());
        holder.remarks.setText(surrenderItem.getRemarks());

        holder.row.setOnClickListener(v -> {
            Intent intent = new Intent(context, RespectiveSurrenderReportDetailsActivity.class);
            intent.putExtra("SurrenderDetails", surrenderItem);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return surrenderResponses.size();
    }

    public class SurrenderViewHolder extends RecyclerView.ViewHolder {

        TextView srNo, name, date, firNo, year, ps, remarks;
        LinearLayout layout;
        TableRow row;
        public SurrenderViewHolder(@NonNull SurrenderItemLayoutBinding itemView) {
            super(itemView.getRoot());
            srNo = itemView.srNo;
            name = itemView.name;
            date = itemView.date;
            firNo = itemView.firNo;
            year = itemView.year;
            ps = itemView.ps;
            remarks = itemView.remarks;

            layout = itemView.layout;

            row = itemView.row;


        }
    }
}
