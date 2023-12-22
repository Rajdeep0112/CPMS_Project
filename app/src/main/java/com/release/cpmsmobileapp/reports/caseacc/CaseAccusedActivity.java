package com.release.cpmsmobileapp.reports.caseacc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.release.cpmsmobileapp.R;
import com.release.cpmsmobileapp.databinding.ActivityCaseAccusedBinding;
import com.release.cpmsmobileapp.reports.absentee.CourtWiseAbsenteeReport;
import com.release.cpmsmobileapp.reports.absentee.FirNoWiseAbsenteeReport;
import com.release.cpmsmobileapp.reports.absentee.WitnessTypeWiseAbsenteeReport;
import com.release.cpmsmobileapp.reports.absentee.WitnessWiseReport;

public class CaseAccusedActivity extends AppCompatActivity {

    private ActivityCaseAccusedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCaseAccusedBinding.inflate(getLayoutInflater());
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
        binding.crimeHeadWiseCardView.setOnClickListener(view -> {
            startActivity(new Intent(this, CrimeHeadWiseCaseAccusedReport.class));
        });

        binding.firNoWiseCardView.setOnClickListener(view -> {
            startActivity(new Intent(this, FirNoWiseCaseAccusedReport.class));
        });

        binding.courtNameWiseCardView.setOnClickListener(view -> {
            startActivity(new Intent(this, CourtWiseCaseAccusedReport.class));
        });

        binding.dateRangeWiseCardView.setOnClickListener(view -> {
            startActivity(new Intent(this, DateRangeCaseAccusedReport.class));
        });
    }
}