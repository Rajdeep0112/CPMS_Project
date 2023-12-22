package com.release.cpmsmobileapp.reports.surrender;

import android.annotation.SuppressLint;
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
import com.release.cpmsmobileapp.adapters.SurrenderReportAdapter;
import com.release.cpmsmobileapp.databinding.ActivityDateWiseSearchBinding;
import com.release.cpmsmobileapp.requestbody.SurrenderReportRequestBody;
import com.release.cpmsmobileapp.responsebody.SurrenderReportResponse;
import com.release.cpmsmobileapp.utils.HelperUtils;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DateWiseSearchActivity extends AppCompatActivity {

    private ActivityDateWiseSearchBinding binding;
    private View view;
    private HelperUtils utils;
    private String userDistrictId, startDate, endDate, token, gpfNo;
    private Long selectedStartDate = MaterialDatePicker.todayInUtcMilliseconds();
    private Long selectedEndDate = MaterialDatePicker.todayInUtcMilliseconds();
    private ApiInterface apiInterface;
    private SurrenderReportAdapter adapter;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDateWiseSearchBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);
        initialisations();
        configureToolbar();
        loadToken();
        setDate();
        setRecyclerView();
        binding.searchSurrenderReportBtn.setOnClickListener(v -> searchFunc());
    }

    private void configureToolbar() {
        binding.topAppBar.setNavigationOnClickListener(view -> this.finish());
    }

    private void setRecyclerView() {
        binding.resultRv.setLayoutManager(new LinearLayoutManager(DateWiseSearchActivity.this));
        adapter = new SurrenderReportAdapter(2);
        binding.resultRv.setAdapter(adapter);
    }

    private void searchFunc() {
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + token);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        String sd=binding.startDate.getText().toString();
        String ed=binding.endDate.getText().toString();

        if(!sd.equals("")) sd=sd.substring(5);
        if(!ed.equals("")) ed=ed.substring(3);

        if(sd.length()==10) startDate = sd.substring(6,10)+"-"+sd.substring(3,5)+"-"+sd.substring(0,2);
        if(ed.length()==10) endDate = ed.substring(6,10)+"-"+ed.substring(3,5)+"-"+ed.substring(0,2);

        binding.txtSearch.setVisibility(View.INVISIBLE);
        binding.progressBar.setVisibility(View.VISIBLE);

        SurrenderReportRequestBody requestBody = new SurrenderReportRequestBody(gpfNo,userDistrictId, startDate,endDate);

        Call<List<SurrenderReportResponse>> call = apiInterface.getSurrenderAccusedReport(map, requestBody);

        call.enqueue(new Callback<List<SurrenderReportResponse>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<SurrenderReportResponse>> call, Response<List<SurrenderReportResponse>> response) {
                List<SurrenderReportResponse> model = response.body();
                Log.wtf("response", String.valueOf(response));
                if (response.body() == null) {
                    binding.txtSearch.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    binding.hsv.setVisibility(View.GONE);
                    Toast.makeText(DateWiseSearchActivity.this, "Could not get details for this case", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (model.size() != 0) {
                    binding.txtSearch.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    binding.hsv.setVisibility(View.VISIBLE);
//                    BailerReportResponse caseResponse = response.body().get(0);
                    adapter.setSurrenderReportAdapter(model, DateWiseSearchActivity.this);
                    Log.wtf("response", String.valueOf(response.code()));
                } else {
                    binding.txtSearch.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    binding.hsv.setVisibility(View.GONE);
//                    binding.resultCard.setVisibility(View.GONE);
                    Toast.makeText(DateWiseSearchActivity.this, "No details found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SurrenderReportResponse>> call, Throwable t) {
                Log.wtf("response", t.getMessage());
                binding.txtSearch.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void initialisations() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        utils = new HelperUtils(this);
    }

    private void setDate(){
        binding.startDate.setOnClickListener(view1 -> {
            MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Start Date")
                    .setSelection(selectedStartDate)
                    .build();

            datePicker.show(getSupportFragmentManager(),datePicker.toString());
            datePicker.addOnPositiveButtonClickListener(selection -> {
                selectedStartDate=selection;
                binding.startDate.setText("From "+dateFormat.format(selectedStartDate));
            });
        });

        binding.endDate.setOnClickListener(view1 -> {
            MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("End Date")
                    .setSelection(selectedEndDate)
                    .build();

            datePicker.show(getSupportFragmentManager(),datePicker.toString());
            datePicker.addOnPositiveButtonClickListener(selection -> {
                selectedEndDate=selection;
                binding.endDate.setText("To "+dateFormat.format(selectedEndDate));
            });
        });
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