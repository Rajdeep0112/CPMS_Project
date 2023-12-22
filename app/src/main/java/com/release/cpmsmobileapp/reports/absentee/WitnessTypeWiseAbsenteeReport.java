package com.release.cpmsmobileapp.reports.absentee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.release.cpmsmobileapp.ApiClient;
import com.release.cpmsmobileapp.ApiInterface;
import com.release.cpmsmobileapp.LoginActivity;
import com.release.cpmsmobileapp.R;
import com.release.cpmsmobileapp.adapters.AbsenteeWitnessWiseAdapter;
import com.release.cpmsmobileapp.databinding.ActivityFirNoWiseAbsenteeReportBinding;
import com.release.cpmsmobileapp.databinding.ActivityWitnessTypeWiseAbsenteeReportBinding;
import com.release.cpmsmobileapp.requestbody.AbsenteeRequestBody;
import com.release.cpmsmobileapp.responsebody.AbsenteeResponse;
import com.release.cpmsmobileapp.responsebody.DistrictResponse;
import com.release.cpmsmobileapp.responsebody.PsResponse;
import com.release.cpmsmobileapp.responsebody.WitnessTypeResponse;
import com.release.cpmsmobileapp.utils.HelperUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WitnessTypeWiseAbsenteeReport extends AppCompatActivity {

    private ActivityWitnessTypeWiseAbsenteeReportBinding binding;

    private View view;
    private HelperUtils utils;

    private ApiInterface apiInterface;
    private String userDistrictId, token, witnessTypeID, gpfNo;

    private HashMap<String, String> witnessTypeId;

    private ArrayList<String> witnessTypeDropdown;

    private Spinner witnessTypeSpinner;

    private List<WitnessTypeResponse> witnessTypeList;

    private AbsenteeWitnessWiseAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWitnessTypeWiseAbsenteeReportBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);
        setRecyclerView();

//        setWitnessTypeSinnerAdapter();
        initialisations();
        getWitnessTypeList();
        configureToolbar();
        binding.txtSearch.setOnClickListener(v -> searchFunc());

    }

    private void configureToolbar() {
        binding.topAppBar.setNavigationOnClickListener(view -> this.finish());
    }

    private void searchFunc() {
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + token);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);


        binding.txtSearch.setVisibility(View.INVISIBLE);
        binding.progressBar.setVisibility(View.VISIBLE);

        AbsenteeRequestBody requestBody = new AbsenteeRequestBody(gpfNo, userDistrictId, witnessTypeID);

        Call<List<AbsenteeResponse>> call = apiInterface.getAbsenteeReport(map, requestBody);

        call.enqueue(new Callback<List<AbsenteeResponse>>() {
            @Override
            public void onResponse(Call<List<AbsenteeResponse>> call, Response<List<AbsenteeResponse>> response) {
                List<AbsenteeResponse> model = response.body();

                Log.wtf("response", String.valueOf(response));

                if (response.body() == null) {
                    binding.txtSearch.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    binding.hsv.setVisibility(View.GONE);
                    Toast.makeText(WitnessTypeWiseAbsenteeReport.this, "Could not get details for this case", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (model.size() != 0) {
                    binding.txtSearch.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    binding.hsv.setVisibility(View.VISIBLE);
//                    BailerReportResponse caseResponse = response.body().get(0);
                    adapter.setAbsenteeWitnessWiseAdapter(model, WitnessTypeWiseAbsenteeReport.this);
                    Log.wtf("response", String.valueOf(response.code()));
                } else {
                    binding.txtSearch.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    binding.hsv.setVisibility(View.GONE);
//                    binding.resultCard.setVisibility(View.GONE);
                    Toast.makeText(WitnessTypeWiseAbsenteeReport.this, "No details found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<AbsenteeResponse>> call, Throwable t) {
                Log.wtf("response", t.getMessage());
                binding.txtSearch.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void setRecyclerView() {
        binding.absenteeWitnessWiseRv.setLayoutManager(new LinearLayoutManager(WitnessTypeWiseAbsenteeReport.this));
        adapter = new AbsenteeWitnessWiseAdapter();
        binding.absenteeWitnessWiseRv.setAdapter(adapter);
    }

    private void getWitnessTypeList() {
        witnessTypeId = new HashMap<>();
        witnessTypeDropdown = new ArrayList<>();
        witnessTypeDropdown.add("Select Witness Type");
        witnessTypeId.put("Select Witness Type", "");
//        setWitnessTypeSinnerAdapter();

        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + token);
        Call<List<WitnessTypeResponse>> call = apiInterface.getWitnessType(map);

        call.enqueue(new Callback<List<WitnessTypeResponse>>() {
            @Override
            public void onResponse(Call<List<WitnessTypeResponse>> call, Response<List<WitnessTypeResponse>> response) {
                witnessTypeList = response.body();
                try {
                    if (witnessTypeList != null) {
                        for (WitnessTypeResponse witnessTypeResponse : witnessTypeList) {
                            String witnessType = witnessTypeResponse.getName();
                            witnessTypeId.put(witnessType, witnessTypeResponse.getId().toString());
                            witnessTypeDropdown.add(witnessType);
                        }
                        setWitnessTypeSinnerAdapter();

                    } else {
                        Toast.makeText(WitnessTypeWiseAbsenteeReport.this, "Error", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e) {
                    Toast.makeText(WitnessTypeWiseAbsenteeReport.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<WitnessTypeResponse>> call, Throwable t) {
                Toast.makeText(WitnessTypeWiseAbsenteeReport.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void setWitnessTypeSinnerAdapter() {
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(WitnessTypeWiseAbsenteeReport.this, R.layout.drop_down_item_layout, witnessTypeDropdown);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        witnessTypeSpinner.setAdapter(adapter1);

        witnessTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String witnessType = adapterView.getItemAtPosition(position).toString();
                witnessTypeID = witnessTypeId.get(witnessType);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                witnessTypeID = "";
            }
        });
    }

    private void initialisations() {
        witnessTypeSpinner = findViewById(R.id.witness_type_spinner);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        utils = new HelperUtils(WitnessTypeWiseAbsenteeReport.this);
        loadToken();
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