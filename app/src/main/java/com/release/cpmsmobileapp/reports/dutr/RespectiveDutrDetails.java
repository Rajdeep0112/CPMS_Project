package com.release.cpmsmobileapp.reports.dutr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.release.cpmsmobileapp.databinding.ActivityRespectiveDutrDetailsBinding;
import com.release.cpmsmobileapp.responsebody.DutrResponse;

public class RespectiveDutrDetails extends AppCompatActivity {
    ActivityRespectiveDutrDetailsBinding binding;
    private DutrResponse details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRespectiveDutrDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getDetails();
        configureToolbar();
        setDutrDetails();
    }

    private void setDutrDetails() {
        binding.firNo.setText(details.getFirNumber());
        binding.year.setText(details.getYear());
        binding.ps.setText(details.getPoliceStation());
        binding.district.setText(details.getDistrict());
        binding.court.setText(details.getCourt());
        binding.stNo.setText(details.getStNumber());
        binding.grNo.setText(details.getGrNumber());
        binding.trNo.setText(details.getTrNumber());
        binding.witness.setText(details.getName());
        binding.testified.setText(details.getTestified());
        binding.testifiedRemarks.setText(details.getTestified_remarks());
    }

    private void configureToolbar() {
        binding.topAppBar.setNavigationOnClickListener(view -> this.finish());
    }

    private void getDetails() {
        Intent intent = getIntent();
        details = (DutrResponse) intent.getSerializableExtra("DutrDetails");
    }
}