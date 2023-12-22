package com.release.cpmsmobileapp.reports.surrender;

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
import com.release.cpmsmobileapp.adapters.SurrenderReportAdapter;
import com.release.cpmsmobileapp.databinding.ActivityCourtWiseBinding;
import com.release.cpmsmobileapp.requestbody.SurrenderReportRequestBody;
import com.release.cpmsmobileapp.responsebody.CourtResponse;
import com.release.cpmsmobileapp.responsebody.SurrenderReportResponse;
import com.release.cpmsmobileapp.utils.HelperUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourtWiseActivity extends AppCompatActivity {

    private ActivityCourtWiseBinding binding;
    private View view;
    private HelperUtils utils;
    private ApiInterface apiInterface;
    private String userDistrictId, token, courtID, gpfNo;
    private HashMap<String, String> courtTypeId;
    private ArrayList<String> courtDropdown;
    private Spinner courtSpinner;
    private List<CourtResponse> courtResponses;
    private SurrenderReportAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCourtWiseBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);

        initialisations();
        loadToken();
        configureToolbar();
        setRecyclerView();
        getCourtList();
        setCourtSpinnerAdapter();

        binding.searchSurrenderReportBtn.setOnClickListener(v -> searchFunc());
    }

    private void searchFunc() {
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + token);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);


        binding.txtSearch.setVisibility(View.INVISIBLE);
        binding.progressBar.setVisibility(View.VISIBLE);

        SurrenderReportRequestBody requestBody = new SurrenderReportRequestBody(gpfNo, userDistrictId, courtID);

        Call<List<SurrenderReportResponse>> call = apiInterface.getSurrenderAccusedReport(map, requestBody);

        call.enqueue(new Callback<List<SurrenderReportResponse>>() {
            @Override
            public void onResponse(Call<List<SurrenderReportResponse>> call, Response<List<SurrenderReportResponse>> response) {
                List<SurrenderReportResponse> model = response.body();

                Log.wtf("response", String.valueOf(response));

                if (response.body() == null) {
                    binding.txtSearch.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    binding.hsv.setVisibility(View.GONE);
                    Toast.makeText(CourtWiseActivity.this, "Could not get details for this case", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (model.size() != 0) {
                    binding.txtSearch.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    binding.hsv.setVisibility(View.VISIBLE);
//                    BailerReportResponse caseResponse = response.body().get(0);
                    adapter.setSurrenderReportAdapter(model, CourtWiseActivity.this);
                    Log.wtf("response", String.valueOf(response.code()));
                } else {
                    binding.txtSearch.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    binding.hsv.setVisibility(View.GONE);
//                    binding.resultCard.setVisibility(View.GONE);
                    Toast.makeText(CourtWiseActivity.this, "No details found", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(CourtWiseActivity.this, "Court list is empty", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e)
                {
                    Toast.makeText(CourtWiseActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CourtResponse>> call, Throwable t) {

            }
        });
    }

    private void setCourtSpinnerAdapter(){
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(CourtWiseActivity.this, R.layout.drop_down_item_layout,courtDropdown);
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
        binding.surrenderCourtWiseRv.setLayoutManager(new LinearLayoutManager(CourtWiseActivity.this));
        adapter = new SurrenderReportAdapter(3);
        binding.surrenderCourtWiseRv.setAdapter(adapter);
    }

    private void configureToolbar() {
        binding.topAppBar.setNavigationOnClickListener(view -> this.finish());
    }

    private void initialisations() {
        courtSpinner = findViewById(R.id.court_spinner);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        utils = new HelperUtils(CourtWiseActivity.this);
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