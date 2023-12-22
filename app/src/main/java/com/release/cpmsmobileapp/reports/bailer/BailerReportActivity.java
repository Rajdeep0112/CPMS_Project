package com.release.cpmsmobileapp.reports.bailer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.release.cpmsmobileapp.databinding.ActivityBailerReportBinding;

public class BailerReportActivity extends AppCompatActivity {

    private ActivityBailerReportBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBailerReportBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setToolbar();
        btnFunc();
    }

    private void setToolbar() {
        binding.topAppBar.setNavigationOnClickListener(view -> this.finish());
    }

    private void btnFunc(){
        binding.firNoWise.setOnClickListener(view -> {
            startActivity(new Intent(this,FirNoWiseSearchActivity.class));
        });

        binding.bailerNameWise.setOnClickListener(view -> {
            startActivity(new Intent(this,BailerNameWiseSearchActivity.class));
        });

        binding.criminalNameWise.setOnClickListener(view -> {
            startActivity(new Intent(this,CriminalNameWiseSearchActivity.class));
        });

        binding.dateWise.setOnClickListener(view -> {
            startActivity(new Intent(this, DateRangeWiseActivity.class));
        });
    }
}