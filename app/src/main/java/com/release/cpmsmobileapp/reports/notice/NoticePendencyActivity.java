package com.release.cpmsmobileapp.reports.notice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.release.cpmsmobileapp.R;
import com.release.cpmsmobileapp.databinding.ActivityNoticePendencyBinding;

public class NoticePendencyActivity extends AppCompatActivity {
    ActivityNoticePendencyBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNoticePendencyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.dateRangeCard.setOnClickListener(view -> startActivity(new Intent(this, SubDateRangeReport.class)));
        binding.noticeDependencyCard.setOnClickListener(view -> startActivity(new Intent(this, SubNoticePendencyReport.class)));
        binding.topAppBar.setNavigationOnClickListener(view -> this.finish());
    }
}