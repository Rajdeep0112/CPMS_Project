package com.release.cpmsmobileapp.reports.dutr;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.release.cpmsmobileapp.databinding.ActivityDutrBinding;

public class DutrActivity extends AppCompatActivity {
    private ActivityDutrBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDutrBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setToolbar();
        binding.dutrWiseCard.setOnClickListener(view1 -> startActivity(new Intent(this, DutrWiseReport.class)));
        binding.psWiseCard.setOnClickListener(view1 -> {
            startActivity(new Intent(this,PsWiseActivity.class));
        });
    }

    private void setToolbar() {
        binding.topAppBar.setNavigationOnClickListener(view -> this.finish());
    }
}