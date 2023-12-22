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
import com.release.cpmsmobileapp.adapters.CaseAccusedWiseReportAdapter;
import com.release.cpmsmobileapp.databinding.ActivityCourtWiseAbsenteeReportBinding;
import com.release.cpmsmobileapp.databinding.ActivityCourtWiseCaseAccusedReportBinding;
import com.release.cpmsmobileapp.reports.absentee.CourtWiseAbsenteeReport;
import com.release.cpmsmobileapp.requestbody.ReportCaseAccusedRequest;
import com.release.cpmsmobileapp.responsebody.CourtResponse;
import com.release.cpmsmobileapp.responsebody.ReportCaseAccusedResponse;
import com.release.cpmsmobileapp.utils.HelperUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourtWiseCaseAccusedReport extends AppCompatActivity {

    ActivityCourtWiseCaseAccusedReportBinding binding;
    private View view;

    private HelperUtils utils;
    private String userDistrictId, token, gpfNo, courtID;
    private ApiInterface apiInterface;

    private HashMap<String, String> courtTypeId;

    private ArrayList<String> courtDropdown;

    private List<CourtResponse> courtResponses;

    private Spinner courtSpinner;
    private CaseAccusedWiseReportAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCourtWiseCaseAccusedReportBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);

        setToolbar();
        initialisations();
        setRecyclerView();
        getCourtList();
        setCourtSpinnerAdapter();
        binding.txtSearch.setOnClickListener(v -> searchFunc());
    }

    private void searchFunc()
    {
        binding.txtSearch.setVisibility(View.INVISIBLE);
        binding.progressBar.setVisibility(View.VISIBLE);


        ReportCaseAccusedRequest requestBody = new ReportCaseAccusedRequest("", userDistrictId, courtID, -999);

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
                    Toast.makeText(CourtWiseCaseAccusedReport.this, "Could not get details for this case", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (model.size() != 0) {
                    binding.txtSearch.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    binding.hsv.setVisibility(View.VISIBLE);
                    adapter.setCaseAccusedWiseReportAdapter(model, CourtWiseCaseAccusedReport.this);
                    Log.wtf("response", String.valueOf(response.code()));
                } else {
                    binding.txtSearch.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    binding.hsv.setVisibility(View.GONE);
                    Toast.makeText(CourtWiseCaseAccusedReport.this, "No details found", Toast.LENGTH_SHORT).show();
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

    private void getCourtList(){
        courtTypeId = new HashMap<>();
        courtDropdown = new ArrayList<>();
        courtDropdown.add("Select Court");
        courtTypeId.put("Select court","");
        setCourtSpinnerAdapter();

        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + token);
        Call<List<CourtResponse>> call = apiInterface.getCourts(map, userDistrictId);

        call.enqueue(new Callback<List<CourtResponse>>() {
            @Override
            public void onResponse(Call<List<CourtResponse>> call, Response<List<CourtResponse>> response) {
                courtResponses = response.body();
                try {
                    if(courtResponses != null) {
                        for(CourtResponse courtResponse : courtResponses)
                        {
                            String courtName = courtResponse.getName();
                            courtTypeId.put(courtName, Integer.toString(courtResponse.getId()));
                            courtDropdown.add(courtName);
                        }
                        setCourtSpinnerAdapter();
                    }else {
                        Toast.makeText(CourtWiseCaseAccusedReport.this, "Court list is empty", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e)
                {
                    Toast.makeText(CourtWiseCaseAccusedReport.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CourtResponse>> call, Throwable t) {

            }
        });
    }

    private void setCourtSpinnerAdapter(){
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(CourtWiseCaseAccusedReport.this, R.layout.drop_down_item_layout,courtDropdown);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courtSpinner.setAdapter(adapter1);
        courtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String court = adapterView.getItemAtPosition(i).toString();
                courtID= courtTypeId.get(court);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                courtID = "";
            }
        });
    }


    private void setRecyclerView() {
        binding.caseAccusedWiseRv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CaseAccusedWiseReportAdapter();
        binding.caseAccusedWiseRv.setAdapter(adapter);
    }

    private void initialisations() {
        courtSpinner = findViewById(R.id.court_spinner);
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