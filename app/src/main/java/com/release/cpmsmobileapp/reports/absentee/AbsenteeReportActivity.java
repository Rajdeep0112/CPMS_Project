package com.release.cpmsmobileapp.reports.absentee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.release.cpmsmobileapp.R;
import com.release.cpmsmobileapp.databinding.ActivityAbsenteeReportBinding;
import com.release.cpmsmobileapp.databinding.ActivityDutrBinding;

public class AbsenteeReportActivity extends AppCompatActivity {

    private ActivityAbsenteeReportBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAbsenteeReportBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setToolbar();
        btnFunc();

    }

    private void setToolbar()
    {
        binding.topAppBar.setNavigationOnClickListener(view -> this.finish());
    }

    private void btnFunc(){
        binding.witnessWiseCardView.setOnClickListener(view -> {
            startActivity(new Intent(this, WitnessWiseReport.class));
        });

        binding.firNoWiseCardView.setOnClickListener(view -> {
            startActivity(new Intent(this, FirNoWiseAbsenteeReport.class));
        });

        binding.courtNameWiseCardView.setOnClickListener(view -> {
            startActivity(new Intent(this, CourtWiseAbsenteeReport.class));
        });

        binding.witnessTypeWiseCardView.setOnClickListener(view -> {
            startActivity(new Intent(this, WitnessTypeWiseAbsenteeReport.class));
        });
    }

}