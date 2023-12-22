package com.release.cpmsmobileapp.reports.caseacc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.release.cpmsmobileapp.databinding.ActivityRespectiveCaseAccusedDetailsBinding;
import com.release.cpmsmobileapp.responsebody.ReportCaseAccusedResponse;

public class RespectiveCaseAccusedDetails extends AppCompatActivity {

    ActivityRespectiveCaseAccusedDetailsBinding binding;

    private ReportCaseAccusedResponse response;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRespectiveCaseAccusedDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        configureToolbar();
        getDetails();
        setCaseAccusedDetails();

    }


    private void setCaseAccusedDetails()
    {
        binding.name.setText(response.getName());
        binding.status.setText(response.getStatus());
        binding.disposalType.setText(response.getDisposal_type());
        binding.actSection.setText(response.getAct_section());
        binding.punishmentType.setText(response.getPunishment_type());
        binding.punishmentPeriod.setText(response.getPunishment_period());
        binding.fineAmount.setText(response.getFine_amount());
        binding.firNo.setText(response.getFir_no());
        binding.year.setText(response.getYear());
        binding.ps.setText(response.getPs());
    }

    private void configureToolbar() {
        binding.topAppBar.setNavigationOnClickListener(view -> this.finish());
    }

    private void getDetails() {
        Intent intent = getIntent();
        response = (ReportCaseAccusedResponse) intent.getSerializableExtra("CaseAccusedDetails");
        Log.v("Guddu Testing", response.getName() + " ");
    }
}