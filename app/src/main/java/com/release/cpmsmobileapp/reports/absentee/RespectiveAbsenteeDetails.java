package com.release.cpmsmobileapp.reports.absentee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.release.cpmsmobileapp.R;
import com.release.cpmsmobileapp.databinding.ActivityRespectiveAbsenteeDetailsBinding;
import com.release.cpmsmobileapp.databinding.ActivityRespectiveDutrDetailsBinding;
import com.release.cpmsmobileapp.responsebody.AbsenteeResponse;
import com.release.cpmsmobileapp.responsebody.DutrResponse;

public class RespectiveAbsenteeDetails extends AppCompatActivity {

    ActivityRespectiveAbsenteeDetailsBinding binding;

    private AbsenteeResponse response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRespectiveAbsenteeDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        getDetails();
        configureToolbar();
        setAbsenteeDetails();
    }

    private void setAbsenteeDetails() {

        binding.firNo.setText(response.getFir_no());
        binding.year.setText(response.getYear());
        binding.ps.setText(response.getPs());
        binding.district.setText(response.getDistrict());
        binding.court.setText(response.getCourt());
        binding.address.setText(response.getAddress());
        binding.name.setText(response.getName());
        binding.hearingDate.setText(response.getHearing_date());
        binding.fatherName.setText(response.getFathers_name());
        binding.witnessType.setText(response.getWitness_type());
        binding.testified.setText(response.getTestified());
        binding.remarks.setText(response.getRemarks());
    }

    private void configureToolbar() {
        binding.topAppBar.setNavigationOnClickListener(view -> this.finish());
    }

    private void getDetails() {
        Intent intent = getIntent();
        response = (AbsenteeResponse) intent.getSerializableExtra("AbsenteeDetails");
        Log.v("Guddu Testing", response.getName() + " " + response.getAddress());
    }
}