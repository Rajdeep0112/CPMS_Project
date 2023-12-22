package com.release.cpmsmobileapp.reports.caseacc;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.release.cpmsmobileapp.ApiClient;
import com.release.cpmsmobileapp.ApiInterface;
import com.release.cpmsmobileapp.LoginActivity;
import com.release.cpmsmobileapp.R;
import com.release.cpmsmobileapp.adapters.CaseAccusedWiseReportAdapter;
import com.release.cpmsmobileapp.databinding.ActivityFirNoWiseCaseAccusedReportBinding;
import com.release.cpmsmobileapp.requestbody.ReportCaseAccusedRequest;
import com.release.cpmsmobileapp.responsebody.DistrictResponse;
import com.release.cpmsmobileapp.responsebody.PsResponse;
import com.release.cpmsmobileapp.responsebody.ReportCaseAccusedResponse;
import com.release.cpmsmobileapp.utils.HelperUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirNoWiseCaseAccusedReport extends AppCompatActivity {

    private ActivityFirNoWiseCaseAccusedReportBinding binding;
    private View view;
    private HelperUtils utils;
    private String userDistrictId, firNo, year, districtID, stationID, token, gpfNo;
    private ApiInterface apiInterface;
    private HashMap<String, String> distNameId, psNameId;

    private ArrayList<String> distDropdown, psDropdown,yearsDropdown;
    private Spinner distSpinner, psSpinner,yearSpinner;

    private List<DistrictResponse> distList;
    private List<PsResponse> psList;
    
    private CaseAccusedWiseReportAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFirNoWiseCaseAccusedReportBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);
        initialisations();
        configureToolbar();
        loadToken();
        setRecyclerView();
        setYearSpinnerAdapter();
        setDistSpinnerAdapter();
        setPsSpinnerAdapter();
        getDistrictList();
        
        binding.txtSearch.setOnClickListener(v -> searchFunc());
    }

    private void searchFunc() {
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + token);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        firNo = binding.firNoEdittext.getText().toString();

        binding.txtSearch.setVisibility(View.INVISIBLE);
        binding.progressBar.setVisibility(View.VISIBLE);


        ReportCaseAccusedRequest requestBody = new ReportCaseAccusedRequest("", userDistrictId, firNo, year, districtID, stationID);

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
                    Toast.makeText(FirNoWiseCaseAccusedReport.this, "Could not get details for this case", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (model.size() != 0) {
                    binding.txtSearch.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    binding.hsv.setVisibility(View.VISIBLE);
//                    BailerReportResponse caseResponse = response.body().get(0);
                    adapter.setCaseAccusedWiseReportAdapter(model, FirNoWiseCaseAccusedReport.this);
                    Log.wtf("response", model.size()+"");
                } else {
                    binding.txtSearch.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    binding.hsv.setVisibility(View.GONE);
//                    binding.resultCard.setVisibility(View.GONE);
                    Toast.makeText(FirNoWiseCaseAccusedReport.this, "No details found", Toast.LENGTH_SHORT).show();
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
        binding.caseAccusedWiseRv.setLayoutManager(new LinearLayoutManager(FirNoWiseCaseAccusedReport.this));
        adapter = new CaseAccusedWiseReportAdapter();
        binding.caseAccusedWiseRv.setAdapter(adapter);
    }
    
    private void configureToolbar() {
        binding.topAppBar.setNavigationOnClickListener(view -> this.finish());
    }

    private void setYearSpinnerAdapter(){
        yearsDropdown = new ArrayList<>();
        yearsDropdown.add("Select Year");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(FirNoWiseCaseAccusedReport.this, R.layout.drop_down_item_layout,yearsDropdown);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for(int i=1990;i<=thisYear;i++){
            yearsDropdown.add(Integer.toString(i));
        }
        yearSpinner.setAdapter(adapter);

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                year = adapterView.getItemAtPosition(i).toString();
                if(year.equals("Select Year")) year="";
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getDistrictList() {
        distNameId = new HashMap<>();
        distDropdown = new ArrayList<>();
        distDropdown.add("Select district");
        distNameId.put("Select district", "");
        setDistSpinnerAdapter();

        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + token);
        Call<List<DistrictResponse>> call = apiInterface.getDistrictList(map);
        call.enqueue(new Callback<List<DistrictResponse>>() {
            @Override
            public void onResponse(Call<List<DistrictResponse>> call, Response<List<DistrictResponse>> response) {
                distList = response.body();
//                int idx = 0;
                try {
                    if (distList != null) {
                        for (DistrictResponse districtResponse : distList) {
                            if (userDistrictId.equals(districtResponse.getId().toString())) {
                                String district = districtResponse.getName() + " (" + districtResponse.getName_hi() + ")";
                                distNameId.put(district, districtResponse.getId().toString());
                                distDropdown.add(district);
                            }
//                            if (userDistrictId.equals(districtResponse.getId().toString()))
//                                idx = distDropdown.size() - 1;
                        }
                        setDistSpinnerAdapter();

                    } else {
//                        idx = 0;
                        Toast.makeText(FirNoWiseCaseAccusedReport.this, "District list is null", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
//                    idx = 0;
                    Toast.makeText(FirNoWiseCaseAccusedReport.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
//                distSpinner.setSelection(idx);


            }

            @Override
            public void onFailure(Call<List<DistrictResponse>> call, Throwable t) {
                Toast.makeText(FirNoWiseCaseAccusedReport.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setDistSpinnerAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(FirNoWiseCaseAccusedReport.this, R.layout.drop_down_item_layout, distDropdown);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        distSpinner.setAdapter(adapter);
        distSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String district = adapterView.getItemAtPosition(i).toString();
                districtID = distNameId.get(district);
                if (districtID.length() != 0) getPsList(districtID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                districtID = "";
            }
        });
    }

    private void getPsList(String district_id) {
        psNameId = new HashMap<>();
        psDropdown = new ArrayList<>();
        psDropdown.add("Select police station");
        psNameId.put("Select police station", "");
        setPsSpinnerAdapter();

        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + token);
        Call<List<PsResponse>> call = apiInterface.getPsList(map, district_id);
        call.enqueue(new Callback<List<PsResponse>>() {
            @Override
            public void onResponse(Call<List<PsResponse>> call, Response<List<PsResponse>> response) {
                psList = response.body();
                try {
                    if (psList != null) {
                        for (PsResponse psResponse : psList) {
                            String ps = psResponse.getName() + " (" + psResponse.getName_hi() + ")";
                            psNameId.put(ps, psResponse.getId().toString());
                            psDropdown.add(ps);
                        }
                        setPsSpinnerAdapter();

                    } else {
                        Toast.makeText(FirNoWiseCaseAccusedReport.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(FirNoWiseCaseAccusedReport.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PsResponse>> call, Throwable t) {
                Toast.makeText(FirNoWiseCaseAccusedReport.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setPsSpinnerAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(FirNoWiseCaseAccusedReport.this, R.layout.drop_down_item_layout, psDropdown);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        psSpinner.setAdapter(adapter);
        psSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String ps = adapterView.getItemAtPosition(i).toString();
                stationID = psNameId.get(ps);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                stationID = "";
            }
        });
    }

    private void initialisations() {
        yearSpinner = view.findViewById(R.id.year_spinner);
        distSpinner = view.findViewById(R.id.dist_spinner);
        psSpinner = view.findViewById(R.id.ps_spinner);
        distNameId = new HashMap<>();
        distDropdown = new ArrayList<>();
        distDropdown.add("Select district");
        distNameId.put("Select district", "");
        psNameId = new HashMap<>();
        psDropdown = new ArrayList<>();
        psDropdown.add("Select police station");
        psNameId.put("Select police station", "");

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