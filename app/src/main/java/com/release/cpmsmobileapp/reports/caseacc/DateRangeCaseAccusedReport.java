package com.release.cpmsmobileapp.reports.caseacc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.release.cpmsmobileapp.ApiClient;
import com.release.cpmsmobileapp.ApiInterface;
import com.release.cpmsmobileapp.LoginActivity;
import com.release.cpmsmobileapp.adapters.CaseAccusedWiseReportAdapter;
import com.release.cpmsmobileapp.databinding.ActivityDateRangeCaseAccusedReportBinding;
import com.release.cpmsmobileapp.requestbody.ReportCaseAccusedRequest;
import com.release.cpmsmobileapp.responsebody.ReportCaseAccusedResponse;
import com.release.cpmsmobileapp.utils.CPMSUser;
import com.release.cpmsmobileapp.utils.HelperUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DateRangeCaseAccusedReport extends AppCompatActivity {


    ActivityDateRangeCaseAccusedReportBinding binding;
    private View view;

    private CPMSUser user;
    private HelperUtils utils;
    private String userDistrictId, token, gpfNo;
    private ApiInterface apiInterface;

    private ReportCaseAccusedRequest requestBody = new ReportCaseAccusedRequest("", "", "", "");
    private CaseAccusedWiseReportAdapter adapter;
    private Long startDate=MaterialDatePicker.todayInUtcMilliseconds(), endDate = MaterialDatePicker.todayInUtcMilliseconds();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDateRangeCaseAccusedReportBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);

        setToolbar();
        initialisations();
        setRecyclerView();

        binding.startDateText.setOnClickListener(view -> startDateFunc());
        binding.endDateText.setOnClickListener(view -> endDateFunc());

        binding.txtSearch.setOnClickListener(v -> searchFunc());

    }

    private void searchFunc() {
        if (!areDatesValid(requestBody.getStart_date(), requestBody.getEnd_date())) {
            Toast.makeText(this, "Please put valid date range.", Toast.LENGTH_SHORT).show();
        }

//        Log.wtf("body", requestBody.getUserDistrictId() +" "+requestBody.getNoticeTypeId());
        binding.hsv.setVisibility(View.GONE);
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.txtSearch.setVisibility(View.INVISIBLE);


        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + utils.getToken());
        Call<List<ReportCaseAccusedResponse>> call = apiInterface.getReportCaseAccused(map, requestBody);


        call.enqueue(new Callback<List<ReportCaseAccusedResponse>>() {
            @Override
            public void onResponse(Call<List<ReportCaseAccusedResponse>> call, Response<List<ReportCaseAccusedResponse>> response) {
                List<ReportCaseAccusedResponse> model = response.body();

                Log.wtf("response", String.valueOf(response));

                if (response.body() == null) {
                    binding.txtSearch.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    binding.hsv.setVisibility(View.GONE);
                    Toast.makeText(DateRangeCaseAccusedReport.this, "Could not get details for this case", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (model.size() != 0) {
                    binding.txtSearch.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    binding.hsv.setVisibility(View.VISIBLE);
                    adapter.setCaseAccusedWiseReportAdapter(model, DateRangeCaseAccusedReport.this);
                    Log.wtf("response", String.valueOf(response.code()));
                } else {
                    binding.txtSearch.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    binding.hsv.setVisibility(View.GONE);
                    Toast.makeText(DateRangeCaseAccusedReport.this, "No details found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ReportCaseAccusedResponse>> call, Throwable t) {
                Log.wtf("response", t.getMessage());
                binding.txtSearch.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.INVISIBLE);
            }
        });


    }

    public static boolean areDatesValid(String startDateStr, String endDateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startDate = sdf.parse(startDateStr);
            Date endDate = sdf.parse(endDateStr);

            if (startDate != null) {
                return !startDate.after(endDate);
            }
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
            return true;
        }
    }

    private void endDateFunc() {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("End Date")
                .setSelection(endDate)
                .build();

        datePicker.show(getSupportFragmentManager(), datePicker.toString());
        datePicker.addOnPositiveButtonClickListener(selection -> {
            endDate = selection;
            String date = dateFormat.format(endDate);
            String[] dateParts = date.split("/");
            changeEndDate(dateParts[2], dateParts[1], dateParts[0]);
        });
    }

    private void startDateFunc() {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Start Date")
                .setSelection(startDate)
                .build();

        datePicker.show(getSupportFragmentManager(), datePicker.toString());
        datePicker.addOnPositiveButtonClickListener(selection -> {
            startDate = selection;
            String date = dateFormat.format(startDate);
            String[] dateParts = date.split("/");
            changeStartDate(dateParts[2], dateParts[1], dateParts[0]);
        });
    }

    private void changeStartDate(String yyyy, String mm, String dd) {
        binding.startDateText.setText(dd + "/" + mm + "/" + yyyy);
        String date = yyyy + "-" + mm + "-" + dd;
        requestBody.setStart_date(date);
    }

    private void changeEndDate(String yyyy, String mm, String dd) {
        binding.endDateText.setText(dd + "/" + mm + "/" + yyyy);
        String date = yyyy + "-" + mm + "-" + dd;
        requestBody.setEnd_date(date);
    }

    private void setRecyclerView() {
        binding.caseAccusedWiseRv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CaseAccusedWiseReportAdapter();
        binding.caseAccusedWiseRv.setAdapter(adapter);
    }

    private void initialisations() {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        utils = new HelperUtils(this);
        user = new CPMSUser(this);
        requestBody.setUser_district_id(user.getDistrictID());
//        requestBody.setGpf_no(user.getGpfNo());
        loadToken();
    }


    private void setToolbar() {
        binding.topAppBar.setNavigationOnClickListener(view -> this.finish());
    }

    private void loadToken() {
        token = utils.getToken();
        if (token.length() == 0) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            gpfNo = utils.getGpfNo();
            userDistrictId = utils.getUserDistrictId();
        }
    }

}