package com.release.cpmsmobileapp.reports.surrender;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.release.cpmsmobileapp.databinding.ActivityRespectiveSurrenderReportDetailsBinding;
import com.release.cpmsmobileapp.responsebody.SurrenderReportResponse;

public class RespectiveSurrenderReportDetailsActivity extends AppCompatActivity {

    private ActivityRespectiveSurrenderReportDetailsBinding binding;
    private View view;
    private SurrenderReportResponse surrenderDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRespectiveSurrenderReportDetailsBinding.inflate(getLayoutInflater());
        view=binding.getRoot();
        setContentView(view);

        getDetails();
        configureToolbar();
        setCaseDetails();
    }

    private void configureToolbar(){
        binding.topAppBar.setNavigationOnClickListener(view -> this.finish());
    }

    private void getDetails(){
        Intent intent = getIntent();
        surrenderDetails = (SurrenderReportResponse) intent.getSerializableExtra("SurrenderDetails");
    }

    private void setCaseDetails(){
        binding.id.setText(""+surrenderDetails.getId());
        binding.name.setText(surrenderDetails.getName());
        binding.fathersName.setText(surrenderDetails.getFathers_name());
        binding.address.setText(surrenderDetails.getAddress());
        binding.surrenderDate.setText(surrenderDetails.getSurrender_date());
        binding.caseDetailId.setText(""+surrenderDetails.getCase_detail_id());
        binding.firNo.setText(surrenderDetails.getFir_no());
        binding.year.setText(surrenderDetails.getYear());
        binding.actSection.setText(surrenderDetails.getAct_section());
        binding.district.setText(surrenderDetails.getDistrict());
        binding.ps.setText(surrenderDetails.getPs());
        binding.court.setText(surrenderDetails.getCourt());
        binding.remarks.setText(surrenderDetails.getRemarks());

        binding.resultCard.setVisibility(View.VISIBLE);
    }
}