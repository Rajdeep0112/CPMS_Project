package com.release.cpmsmobileapp.reports.caseacc;

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
import com.release.cpmsmobileapp.adapters.CaseAccusedWiseReportAdapter;
import com.release.cpmsmobileapp.databinding.ActivityCrimeHeadWiseCaseAccusedReportBinding;
import com.release.cpmsmobileapp.reports.absentee.WitnessTypeWiseAbsenteeReport;
import com.release.cpmsmobileapp.reports.absentee.WitnessWiseReport;
import com.release.cpmsmobileapp.requestbody.ReportCaseAccusedRequest;
import com.release.cpmsmobileapp.responsebody.AbsenteeResponse;
import com.release.cpmsmobileapp.responsebody.CrimeHeadResponse;
import com.release.cpmsmobileapp.responsebody.ReportCaseAccusedResponse;
import com.release.cpmsmobileapp.responsebody.WitnessTypeResponse;
import com.release.cpmsmobileapp.utils.HelperUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrimeHeadWiseCaseAccusedReport extends AppCompatActivity {


    ActivityCrimeHeadWiseCaseAccusedReportBinding binding;

    private View view;
    private HelperUtils utils;
    private String userDistrictId, token, crimeHeadID, gpfNo;
    private ApiInterface apiInterface;

    private HashMap<String, String> crimeHeadId;

    private ArrayList<String> crimeHeadDropdown;

    private Spinner crimeHeadSpinner;

    private List<CrimeHeadResponse> crimeHeadResponseList;
    private CaseAccusedWiseReportAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCrimeHeadWiseCaseAccusedReportBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);

        setToolbar();
        initialisations();
        setRecyclerView();
        getCrimeHeadList();
        binding.txtSearch.setOnClickListener(v -> searchFunc());

    }

    private void searchFunc()
    {



        ReportCaseAccusedRequest requestBody = new ReportCaseAccusedRequest("","", "");
//        requestBody.setGpf_no(gpfNo);
        requestBody.setUser_district_id(userDistrictId);
        requestBody.setCrime_head_id(crimeHeadID);


        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + utils.getToken());
        Call<List<ReportCaseAccusedResponse>> call = apiInterface.getReportCaseAccused(map, requestBody);

        binding.txtSearch.setVisibility(View.INVISIBLE);
        binding.progressBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<List<ReportCaseAccusedResponse>>() {
            @Override
            public void onResponse(Call<List<ReportCaseAccusedResponse>> call, Response<List<ReportCaseAccusedResponse>> response) {
                List<ReportCaseAccusedResponse> model = response.body();

                Log.wtf("response", String.valueOf(response));

                if (response.body() == null) {
                    binding.txtSearch.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    binding.hsv.setVisibility(View.GONE);
                    Toast.makeText(CrimeHeadWiseCaseAccusedReport.this, "Could not get details for this case", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (model.size() != 0) {
                    binding.txtSearch.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    binding.hsv.setVisibility(View.VISIBLE);
                    adapter.setCaseAccusedWiseReportAdapter(model, CrimeHeadWiseCaseAccusedReport.this);
                    Log.wtf("response", String.valueOf(response.code()));
                } else {
                    binding.txtSearch.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    binding.hsv.setVisibility(View.GONE);
                    Toast.makeText(CrimeHeadWiseCaseAccusedReport.this, "No details found", Toast.LENGTH_SHORT).show();
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


    private void setRecyclerView() {
        binding.caseAccusedWiseRv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CaseAccusedWiseReportAdapter();
        binding.caseAccusedWiseRv.setAdapter(adapter);
    }

    private void getCrimeHeadList() {
        crimeHeadId = new HashMap<>();
        crimeHeadDropdown = new ArrayList<>();
        crimeHeadDropdown.add("Select Crime Head");
        crimeHeadId.put("Select Crime Head", "");


        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + token);
        Call<List<CrimeHeadResponse>> call = apiInterface.getCrimeHeadList(map);

        call.enqueue(new Callback<List<CrimeHeadResponse>>() {
            @Override
            public void onResponse(Call<List<CrimeHeadResponse>> call, Response<List<CrimeHeadResponse>> response) {
                crimeHeadResponseList = response.body();
                try {
                    if (crimeHeadResponseList != null) {
                        for (CrimeHeadResponse crimeHeadResponse : crimeHeadResponseList) {
                            String witnessType = crimeHeadResponse.getName();
                            crimeHeadId.put(witnessType, Integer.toString(crimeHeadResponse.getId()));
                            crimeHeadDropdown.add(witnessType);
                        }
                        setCrimeHeadSinnerAdapter();

                    } else {
                        Toast.makeText(CrimeHeadWiseCaseAccusedReport.this, "Error", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e) {
                    Toast.makeText(CrimeHeadWiseCaseAccusedReport.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CrimeHeadResponse>> call, Throwable t) {
                Toast.makeText(CrimeHeadWiseCaseAccusedReport.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void setCrimeHeadSinnerAdapter() {
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(CrimeHeadWiseCaseAccusedReport.this, R.layout.drop_down_item_layout, crimeHeadDropdown);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        crimeHeadSpinner.setAdapter(adapter1);

        crimeHeadSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String crimeHead = adapterView.getItemAtPosition(position).toString();
                crimeHeadID = crimeHeadId.get(crimeHead);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                crimeHeadID = "";
            }
        });
    }


    private void initialisations() {
        crimeHeadSpinner = findViewById(R.id.crime_head_spinner);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        utils = new HelperUtils(this);
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