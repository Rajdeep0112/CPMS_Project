package com.release.cpmsmobileapp.reports.bailer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.release.cpmsmobileapp.ApiClient;
import com.release.cpmsmobileapp.ApiInterface;
import com.release.cpmsmobileapp.LoginActivity;
import com.release.cpmsmobileapp.adapters.BailerReportAdapter;
import com.release.cpmsmobileapp.databinding.ActivityBailerNameWiseSearchBinding;
import com.release.cpmsmobileapp.requestbody.BailerReportRequestBody;
import com.release.cpmsmobileapp.responsebody.BailerReportResponse;
import com.release.cpmsmobileapp.utils.HelperUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BailerNameWiseSearchActivity extends AppCompatActivity {

    private ActivityBailerNameWiseSearchBinding binding;
    private View view;
    private HelperUtils utils;
    private String userDistrictId, bailerName, fatherName, token, gpfNo;
    private ApiInterface apiInterface;
    private BailerReportAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBailerNameWiseSearchBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);
        initialisations();
        configureToolbar();
        loadToken();
        setRecyclerView();
        binding.searchBailerReportBtn.setOnClickListener(v -> searchFunc());
    }

    private void configureToolbar() {
        binding.topAppBar.setNavigationOnClickListener(view -> this.finish());
    }

    private void setRecyclerView() {
        binding.resultRv.setLayoutManager(new LinearLayoutManager(BailerNameWiseSearchActivity.this));
        adapter = new BailerReportAdapter(2);
        binding.resultRv.setAdapter(adapter);
    }

    private void searchFunc() {
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + token);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        bailerName = binding.bailerNameEdittext.getText().toString();
        fatherName = binding.fatherNameEdittext.getText().toString();

        binding.txtSearch.setVisibility(View.INVISIBLE);
        binding.progressBar.setVisibility(View.VISIBLE);

        BailerReportRequestBody requestBody = new BailerReportRequestBody(gpfNo, userDistrictId, bailerName,fatherName);

        Call<List<BailerReportResponse>> call = apiInterface.getBailerReport(map, requestBody);

        call.enqueue(new Callback<List<BailerReportResponse>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<BailerReportResponse>> call, Response<List<BailerReportResponse>> response) {
                List<BailerReportResponse> model = response.body();
                Log.wtf("response", String.valueOf(response));
                if (response.body() == null) {
                    binding.txtSearch.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    binding.hsv.setVisibility(View.GONE);
                    Toast.makeText(BailerNameWiseSearchActivity.this, "Could not get details for this case", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (model.size() != 0) {
                    binding.txtSearch.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    binding.hsv.setVisibility(View.VISIBLE);
//                    BailerReportResponse caseResponse = response.body().get(0);
                    adapter.setBailerReportAdapter(model, BailerNameWiseSearchActivity.this);
                    Log.wtf("response", String.valueOf(response.code()));
                } else {
                    binding.txtSearch.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    binding.hsv.setVisibility(View.GONE);
//                    binding.resultCard.setVisibility(View.GONE);
                    Toast.makeText(BailerNameWiseSearchActivity.this, "No details found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<BailerReportResponse>> call, Throwable t) {
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