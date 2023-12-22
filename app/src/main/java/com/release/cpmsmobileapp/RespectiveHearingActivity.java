package com.release.cpmsmobileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.release.cpmsmobileapp.databinding.ActivityRespectiveHearingBinding;
import com.release.cpmsmobileapp.responsebody.HearingListResponse;


public class RespectiveHearingActivity extends AppCompatActivity {

    private ActivityRespectiveHearingBinding binding;
    private View view;
    private HearingListResponse hearingDetails;
    private LinearLayout respectiveHearingLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityRespectiveHearingBinding.inflate(getLayoutInflater());
        view=binding.getRoot();
        setContentView(view);

        getDetails();
        initialisation();
        configureToolbar();
        setCaseDetails();
    }

    private void configureToolbar(){
        binding.toolbarHearing.setTitle("Hearing Details");
        setSupportActionBar(binding.toolbarHearing);
        binding.toolbarHearing.setNavigationIcon(R.drawable.back);
        binding.toolbarHearing.setNavigationOnClickListener(view -> onBackPressed());
    }

    private void initialisation() {
        respectiveHearingLv = findViewById(R.id.respective_hearing_data_lv);
    }

    private void getDetails(){
        Intent intent = getIntent();
        hearingDetails = (HearingListResponse) intent.getSerializableExtra("HearingDetails");
    }

    private void setCaseDetails() {
        binding.hearingDetails.firNo.setText(hearingDetails.getFIR_no().toString());
        binding.hearingDetails.year.setText(hearingDetails.getYear());
        binding.hearingDetails.regDate.setText(hearingDetails.getReg_date());
        binding.hearingDetails.psName.setText(hearingDetails.getPs_name());
        binding.hearingDetails.districtName.setText(hearingDetails.getDistrict_name());
        binding.hearingDetails.hearingDate.setText(hearingDetails.getHearing_date());
        binding.hearingDetails.courtName.setText(hearingDetails.getCourt_name());
        binding.hearingDetails.witnesses.setText(hearingDetails.getWitnesses());
        binding.hearingDetails.bailer.setText(hearingDetails.getBailer());
        binding.hearingDetails.accused.setText(hearingDetails.getAccused());
        binding.hearingDetails.ioName.setText(hearingDetails.getIo());

    }
}