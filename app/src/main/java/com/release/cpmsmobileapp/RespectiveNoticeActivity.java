package com.release.cpmsmobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.release.cpmsmobileapp.databinding.ActivityRespectiveNoticeBinding;
import com.release.cpmsmobileapp.responsebody.NoticesResponse;

public class RespectiveNoticeActivity extends AppCompatActivity {


    private ActivityRespectiveNoticeBinding binding;
    private View view;
    private NoticesResponse noticeDetails;
//    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRespectiveNoticeBinding.inflate(getLayoutInflater());
        view=binding.getRoot();
        setContentView(view);

        getDetails();
        configureToolbar();
        setCaseDetails();
    }

    private void configureToolbar(){
        binding.topAppBar.setNavigationOnClickListener(view -> this.finish());
    }

    private void getDetails(){
        Intent intent = getIntent();
        noticeDetails = (NoticesResponse) intent.getSerializableExtra("NoticeDetails");
    }

    private void setCaseDetails(){
        binding.noticeDetails.name.setText(noticeDetails.getName());
        binding.noticeDetails.ioName.setText(noticeDetails.getIo_name());
        binding.noticeDetails.ioAssignDate.setText(noticeDetails.getIo_assign_date());
        binding.noticeDetails.ioComplianceDate.setText(""+noticeDetails.getIo_compliance_date());
        binding.noticeDetails.noticeCompletedDate.setText(noticeDetails.getNotice_completed_date());
        binding.noticeDetails.issueDate.setText(noticeDetails.getIssue_date());
        binding.noticeDetails.appearanceDate.setText(noticeDetails.getAppearance_date());
        binding.noticeDetails.noticeComplianceDate.setText(noticeDetails.getNotice_compliance_date());
        binding.noticeDetails.fromDistrictName.setText(noticeDetails.getFrom_district_name());
        binding.noticeDetails.districtName.setText(noticeDetails.getDistrict_name());
        binding.noticeDetails.psName.setText(noticeDetails.getPs_name());
        binding.noticeDetails.distReferId.setText(noticeDetails.getDist_refer_id());
        binding.noticeDetails.courtName.setText(noticeDetails.getCourt_name());
        binding.noticeDetails.noticetypesName.setText(noticeDetails.getNoticetypes_name());
        binding.noticeDetails.FIRNo.setText(""+noticeDetails.getFIR_no());
        binding.noticeDetails.year.setText(noticeDetails.getYear());
        binding.noticeDetails.regDate.setText(noticeDetails.getReg_date());

        binding.resultCard.setVisibility(View.VISIBLE);
    }
}