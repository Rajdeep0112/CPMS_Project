package com.release.cpmsmobileapp.reports.surrender;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.release.cpmsmobileapp.databinding.ActivitySurrenderAccusedBinding;

public class SurrenderAccusedActivity extends AppCompatActivity {

    private ActivitySurrenderAccusedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySurrenderAccusedBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setToolbar();
        btnFunc();
    }

    private void setToolbar() {
        binding.topAppBar.setNavigationOnClickListener(view -> this.finish());
    }

    private void btnFunc(){
        binding.caseNoWise.setOnClickListener(view -> {
            startActivity(new Intent(this, CaseNoWiseSearchActivity.class));
        });

        binding.dateWise.setOnClickListener(view -> {
            startActivity(new Intent(this, DateWiseSearchActivity.class));
        });

        binding.courtWise.setOnClickListener(view -> {
            startActivity(new Intent(this, CourtWiseActivity.class));
        });
    }
}