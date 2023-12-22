package com.release.cpmsmobileapp.reports.absentee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.release.cpmsmobileapp.ApiClient;
import com.release.cpmsmobileapp.ApiInterface;
import com.release.cpmsmobileapp.LoginActivity;
import com.release.cpmsmobileapp.R;
import com.release.cpmsmobileapp.adapters.AbsenteeWitnessWiseAdapter;

import com.release.cpmsmobileapp.databinding.ActivityWitnessWiseReportBinding;

import com.release.cpmsmobileapp.requestbody.AbsenteeRequestBody;
import com.release.cpmsmobileapp.responsebody.AbsenteeResponse;

import com.release.cpmsmobileapp.utils.CPMSUser;
import com.release.cpmsmobileapp.utils.HelperUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WitnessWiseReport extends AppCompatActivity {

    ActivityWitnessWiseReportBinding binding;

    private AbsenteeRequestBody requestBody = new AbsenteeRequestBody("","", "", "");

    private HelperUtils utils;

    private String userDistrictId, token, gpfNo;
    private ApiInterface apiInterface;

    private AbsenteeWitnessWiseAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWitnessWiseReportBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setToolbar();
        initialisations();
        setRecyclerView();
        binding.txtSearch.setOnClickListener(v -> searchFunc());
    }

    private void searchFunc()
    {
        binding.txtSearch.setVisibility(View.INVISIBLE);
        binding.progressBar.setVisibility(View.VISIBLE);


        String name = binding.witnessNameEditText.getText().toString().trim();
        String fatherName = binding.fatherNameEditText.getText().toString().trim();
        Log.e("Testing", "name: " + name + " , father Name: " + fatherName );
        requestBody.setGpf_no(gpfNo);
        requestBody.setUser_district_id(userDistrictId);
        requestBody.setName(name);
        requestBody.setFather_name(fatherName);

        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + utils.getToken());
        Call<List<AbsenteeResponse>> call = apiInterface.getAbsenteeReport(map, requestBody);

        call.enqueue(new Callback<List<AbsenteeResponse>>() {
            @Override
            public void onResponse(Call<List<AbsenteeResponse>> call, Response<List<AbsenteeResponse>> response) {
                List<AbsenteeResponse> list = response.body();

                if (list == null || response.body() == null) {
                    binding.txtSearch.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    binding.hsv.setVisibility(View.GONE);
                    toastShort("Could not get hearing cases\n\terror code : " + response.code(), WitnessWiseReport.this);
                    return;
                }

                if (list.size() != 0) {

                    binding.txtSearch.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    binding.hsv.setVisibility(View.VISIBLE);
                    adapter.setAbsenteeWitnessWiseAdapter(list, WitnessWiseReport.this);
                } else {
                    toastShort("No reports found", WitnessWiseReport.this);
//                    noHearingError.setVisibility(View.VISIBLE);
                    binding.txtSearch.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    binding.hsv.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<AbsenteeResponse>> call, Throwable t) {
                Toast.makeText(WitnessWiseReport.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void initialisations() {

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        utils = new HelperUtils(this);
        loadToken();

    }

    private void setRecyclerView() {
        binding.absenteeWitnessWiseRv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AbsenteeWitnessWiseAdapter();
        binding.absenteeWitnessWiseRv.setAdapter(adapter);
    }


    private void setToolbar() {
        binding.topAppBar.setNavigationOnClickListener(view -> this.finish());
    }

    private void toastShort(String string, Context context) {
        if (context != null) {
            Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
        }
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