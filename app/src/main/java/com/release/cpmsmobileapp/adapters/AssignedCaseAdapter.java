package com.release.cpmsmobileapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.release.cpmsmobileapp.R;
import com.release.cpmsmobileapp.RespectiveCaseActivity;
import com.release.cpmsmobileapp.responsebody.SearchCaseResponse;

import java.util.ArrayList;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;

public class AssignedCaseAdapter extends Section {
    private List<SearchCaseResponse> assignedCaseList = new ArrayList<>();
    private final Context context;
    private final String section;

    public AssignedCaseAdapter(List<SearchCaseResponse> assignedCaseList,String section,Context context){
        super(SectionParameters.builder()
                .itemResourceId(R.layout.assigned_case_item_layout)
                .headerResourceId(R.layout.assigned_case_header_layout)
                .build());
        this.assignedCaseList = assignedCaseList;
        this.context = context;
        this.section=section;
    }

    @Override
    public int getContentItemsTotal() {
        return assignedCaseList.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        SearchCaseResponse caseResponse = assignedCaseList.get(position);
        ItemViewHolder viewHolder = (ItemViewHolder) holder;

        SpannableString content = new SpannableString(""+caseResponse.getCase_detail_id());
        content.setSpan(new UnderlineSpan(),0,content.length(),0);

        viewHolder.sr.setText(""+(position+1));
        viewHolder.firAndYear.setText(caseResponse.getFIR_no() + " / " + caseResponse.getYear());
        viewHolder.date.setText(caseResponse.getReg_date());
        viewHolder.psName.setText(caseResponse.getPs_name());
//        viewHolder.year.setText(caseResponse.getYear());
        viewHolder.assignedCaseLayout.setOnClickListener(v -> {
            Intent intent = new Intent(context, RespectiveCaseActivity.class);
            intent.putExtra("CaseDetails",caseResponse);
            context.startActivity(intent);

        });
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        super.onBindHeaderViewHolder(holder);
        HeaderViewHolder viewHolder = (HeaderViewHolder) holder;
        viewHolder.title.setText(section);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView sr,firAndYear,date,psName;
        LinearLayout assignedCaseLayout;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            sr=itemView.findViewById(R.id.sr_no);
            firAndYear=itemView.findViewById(R.id.fir_no_and_year);
            date=itemView.findViewById(R.id.reg_date);
//            year=itemView.findViewById(R.id.year);
            psName=itemView.findViewById(R.id.ps_name);
            assignedCaseLayout=itemView.findViewById(R.id.layout);
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder{

        private TextView title;

        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
        }
    }
}
