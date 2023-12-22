package com.release.cpmsmobileapp.reports.dutr;

import android.annotation.SuppressLint;
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
import com.release.cpmsmobileapp.adapters.DutrAdapter;
import com.release.cpmsmobileapp.databinding.ActivityPsWiseBinding;
import com.release.cpmsmobileapp.requestbody.DutrRequestBody;
import com.release.cpmsmobileapp.responsebody.DutrResponse;
import com.release.cpmsmobileapp.responsebody.PsResponse;
import com.release.cpmsmobileapp.utils.HelperUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PsWiseActivity extends AppCompatActivity {

    ActivityPsWiseBinding binding;
    HelperUtils utils;
    private String userDistrictId, stationID, token, gpfNo;
    private ApiInterface apiInterface;
    private HashMap<String, String> psNameId;
    private ArrayList<String> psDropdown;
    private Spinner psSpinner;
    private DutrAdapter adapter;
    private List<PsResponse> psList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPsWiseBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initialisations();
        configureToolbar();
        loadToken();
        setRecyclerView();
        setPsSpinnerAdapter();
        getPsList();
        searchBtn();
    }

    private void searchBtn() {
        binding.searchDutrReportBtn.setOnClickListener(view -> searchFunc());
    }

    private void configureToolbar() {
        binding.topAppBar.setNavigationOnClickListener(view -> this.finish());
    }

    private void setRecyclerView() {
        binding.resultRv.setLayoutManager(new LinearLayoutManager(PsWiseActivity.this));
        adapter = new DutrAdapter(1);
        binding.resultRv.setAdapter(adapter);
    }

    private void searchFunc() {
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + token);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        binding.txtSearch.setVisibility(View.INVISIBLE);
        binding.progressBar.setVisibility(View.VISIBLE);

        DutrRequestBody requestBody = new DutrRequestBody(gpfNo, userDistrictId, stationID);

        Call<List<DutrResponse>> call = apiInterface.getDutrReport(map, requestBody);

        call.enqueue(new Callback<List<DutrResponse>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<DutrResponse>> call, Response<List<DutrResponse>> response) {
                List<DutrResponse> model = response.body();
                Log.wtf("response", String.valueOf(response));
                if (response.body() == null) {
                    binding.txtSearch.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    binding.hsv.setVisibility(View.GONE);
                    Toast.makeText(PsWiseActivity.this, "Could not get details for this case", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (model.size() != 0) {
                    binding.txtSearch.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    binding.hsv.setVisibility(View.VISIBLE);
//                    BailerReportResponse caseResponse = response.body().get(0);
                    adapter.setDutrAdapter(model, PsWiseActivity.this);
                    Log.wtf("response", String.valueOf(response.code()));
                } else {
                    binding.txtSearch.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    binding.hsv.setVisibility(View.GONE);
//                    binding.resultCard.setVisibility(View.GONE);
                    Toast.makeText(PsWiseActivity.this, "No details found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DutrResponse>> call, Throwable t) {
                Log.wtf("response", t.getMessage());
                binding.txtSearch.setVisibility(View.VISIBLE);
                binding.progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void getPsList() {
        psNameId = new HashMap<>();
        psDropdown = new ArrayList<>();
        psDropdown.add("Select police station");
        psNameId.put("Select police station", "");
        setPsSpinnerAdapter();

        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + token);
        Call<List<PsResponse>> call = apiInterface.getPsList(map, userDistrictId);
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
                        Toast.makeText(PsWiseActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(PsWiseActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PsResponse>> call, Throwable t) {
                Toast.makeText(PsWiseActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setPsSpinnerAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(PsWiseActivity.this, R.layout.drop_down_item_layout, psDropdown);
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
        psSpinner = binding.psSpinner;
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