package com.release.cpmsmobileapp.reports.bailer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.release.cpmsmobileapp.databinding.ActivityRespectiveBailerReportDetailsBinding;
import com.release.cpmsmobileapp.responsebody.BailerReportResponse;

public class RespectiveBailerReportDetailsActivity extends AppCompatActivity {

    private ActivityRespectiveBailerReportDetailsBinding binding;
    private View view;
    private BailerReportResponse bailerDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRespectiveBailerReportDetailsBinding.inflate(getLayoutInflater());
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
        bailerDetails = (BailerReportResponse) intent.getSerializableExtra("BailerDetails");
    }

    private void setCaseDetails(){
        binding.id.setText(""+bailerDetails.getId());
        binding.name.setText(bailerDetails.getName());
        binding.fathersName.setText(bailerDetails.getFathers_name());
        binding.address.setText(bailerDetails.getAddress());
        binding.bailDate.setText(bailerDetails.getBail_date());
        binding.accusedName.setText(bailerDetails.getAccused_name());
        binding.accusedFathersName.setText(bailerDetails.getAccused_fathers_name());
        binding.caseDetailId.setText(""+bailerDetails.getCase_detail_id());
        binding.firNo.setText(bailerDetails.getFir_no());
        binding.year.setText(bailerDetails.getYear());
        binding.district.setText(bailerDetails.getDistrict());
        binding.ps.setText(bailerDetails.getPs());
        binding.bailRemarks.setText(bailerDetails.getBail_remarks());

        binding.resultCard.setVisibility(View.VISIBLE);
    }
}