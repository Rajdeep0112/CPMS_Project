package com.release.cpmsmobileapp.reports.dutr;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.release.cpmsmobileapp.ApiClient;
import com.release.cpmsmobileapp.ApiInterface;
import com.release.cpmsmobileapp.R;
import com.release.cpmsmobileapp.adapters.DutrAdapter;
import com.release.cpmsmobileapp.databinding.ActivityDutrWiseReportBinding;
import com.release.cpmsmobileapp.requestbody.DutrRequestBody;
import com.release.cpmsmobileapp.responsebody.CourtResponse;
import com.release.cpmsmobileapp.responsebody.DistrictResponse;
import com.release.cpmsmobileapp.responsebody.DutrResponse;
import com.release.cpmsmobileapp.utils.CPMSUser;
import com.release.cpmsmobileapp.utils.HelperUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DutrWiseReport extends AppCompatActivity {
    ActivityDutrWiseReportBinding binding;
    private DutrRequestBody requestBody = new DutrRequestBody("", "", "", "");
    private HashMap<String, String> districtMap;
    HelperUtils utils;
    private ApiInterface apiInterface;
    private HashMap<String, Integer> courtMap;
    private ArrayList<String> courtDropdown;
    private DutrAdapter adapter;
    private List<DutrResponse> list;
    private Long selectedDate=MaterialDatePicker.todayInUtcMilliseconds();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDutrWiseReportBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initialisations();
        setToolbar();
        datePicking();
        getDistricts();
        getCourts();
        searchBtn();
    }

    private void searchBtn() {
        binding.searchBtn.setOnClickListener(view -> searchFunc());
    }

    private void searchFunc() {
        try {
            if (requestBody.getCourtId().equals("-6969")) requestBody.setCourtId(null);
        } catch (Exception ignored) {

        }
        binding.hsv.setVisibility(View.GONE);
        binding.shimmerViewContainer.startShimmerAnimation();
        binding.shimmerViewContainer.setVisibility(View.VISIBLE);
//        if(requestBody.getCourtId() == null || requestBody.getCourtId().equals("-6969")){
//            Toast.makeText(this, "Enter all fields", Toast.LENGTH_SHORT).show();
//            binding.shimmerViewContainer.stopShimmerAnimation();
//            binding.shimmerViewContainer.setVisibility(View.GONE);
//            return;
//        }
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + utils.getToken());
        Call<List<DutrResponse>> call = apiInterface.getDutrReport(map, requestBody);
        call.enqueue(new Callback<List<DutrResponse>>() {
            @Override
            public void onResponse(Call<List<DutrResponse>> call, Response<List<DutrResponse>> response) {
                list = response.body();
                if (list == null || response.body() == null) {
                    binding.shimmerViewContainer.stopShimmerAnimation();
                    binding.shimmerViewContainer.setVisibility(View.GONE);
                    binding.hsv.setVisibility(View.GONE);
                    toastShort("Could not get report\n\terror code : " + response.code(), DutrWiseReport.this);
//                    noHearingError.setVisibility(View.VISIBLE);
                    return;
                }
                binding.shimmerViewContainer.stopShimmerAnimation();
                binding.shimmerViewContainer.setVisibility(View.GONE);
                if (list.size() != 0) {
                    adapterWork(list);
//                    noHearingError.setVisibility(View.GONE);
                    binding.hsv.setVisibility(View.VISIBLE);
                } else {
                    toastShort("No reports found", DutrWiseReport.this);
//                    noHearingError.setVisibility(View.VISIBLE);
                    binding.hsv.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<DutrResponse>> call, Throwable t) {
                binding.shimmerViewContainer.stopShimmerAnimation();
                binding.shimmerViewContainer.setVisibility(View.GONE);
                Toast.makeText(DutrWiseReport.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void datePicking() {
        binding.dateEditText.setOnClickListener(view -> {
            MaterialDatePicker<Long> datePicker=MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select Date")
                    .setSelection(selectedDate)
                    .build();

            datePicker.show(getSupportFragmentManager(),datePicker.toString());
            datePicker.addOnPositiveButtonClickListener(selection -> {
                selectedDate=selection;
                String date = dateFormat.format(selectedDate);
                String[] dateParts = date.split("/");
                changeDate(dateParts[2],dateParts[1],dateParts[0]);
            });
        });
    }

    private void changeDate(String yyyy, String mm, String dd) {
        binding.dateEditText.setText(dd + "/" + mm + "/" + yyyy);
        String date = yyyy + "-" + mm + "-" + dd;
        requestBody.setSearchDate(date);
    }

    private void getCourts() {
        courtMap = new HashMap<>();
        courtDropdown = new ArrayList<>();
        courtDropdown.add("Select Court");
        courtMap.put("Select Court", -6969);
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + utils.getToken());
        Call<List<CourtResponse>> call = apiInterface.getCourts(map, requestBody.getUserDistrictId());
        call.enqueue(new Callback<List<CourtResponse>>() {
            @Override
            public void onResponse(Call<List<CourtResponse>> call, Response<List<CourtResponse>> response) {
                List<CourtResponse> courtList = response.body();
                if (courtList != null) {
                    for (CourtResponse courtResponse : courtList) {
                        String court = courtResponse.getName();
                        courtMap.put(court, courtResponse.getId());
                        courtDropdown.add(court);
                    }
                    setCourtSpinnerAdapter();
                } else {
                    Toast.makeText(DutrWiseReport.this, "Court list is null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CourtResponse>> call, Throwable t) {

            }
        });
    }

    private void setCourtSpinnerAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.drop_down_item_layout, courtDropdown);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.courtSpinner.setAdapter(adapter);
        binding.courtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String court = adapterView.getItemAtPosition(i).toString();
                requestBody.setCourtId(courtMap.get(court).toString());
                binding.courtText.setText(court);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getDistricts() {
        districtMap = new HashMap<>();
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + utils.getToken());
        Call<List<DistrictResponse>> call = apiInterface.getDistrictList(map);
        call.enqueue(new Callback<List<DistrictResponse>>() {
            @Override
            public void onResponse(Call<List<DistrictResponse>> call, Response<List<DistrictResponse>> response) {
                List<DistrictResponse> distList = response.body();
                if (distList != null) {
                    for (DistrictResponse districtResponse : distList) {
                        String district = districtResponse.getName() + " (" + districtResponse.getName_hi() + ")";
                        districtMap.put(districtResponse.getId().toString(), district);
                    }
                    String district = districtMap.get(requestBody.getUserDistrictId());
                    if (district == null || district.equals("")) {
                        Toast.makeText(DutrWiseReport.this, "Your District Id is invalid.", Toast.LENGTH_SHORT).show();
                    }
                    binding.districtText.setText(districtMap.get(requestBody.getUserDistrictId()));

                } else {
                    Toast.makeText(DutrWiseReport.this, "District list is null", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DistrictResponse>> call, Throwable t) {

            }
        });
    }

    private void initialisations() {
        utils = new HelperUtils(this);
        CPMSUser user = new CPMSUser(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        requestBody.setUserDistrictId(user.getDistrictID());
        requestBody.setGpf_no(user.getGpfNo());
        String date = dateFormat.format(selectedDate);
        String[] dateParts = date.split("/");
        changeDate(dateParts[2],dateParts[1],dateParts[0]);
        setRecyclerView();
    }

    private void setRecyclerView() {
        binding.dutrRV.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DutrAdapter(1);
        binding.dutrRV.setAdapter(adapter);
    }

    private void adapterWork(List<DutrResponse> dutrResponses) {
        adapter.setDutrAdapter(dutrResponses, this);
    }


    private void setToolbar() {
        binding.topAppBar.setNavigationOnClickListener(view -> this.finish());
    }

    private void toastShort(String string, Context context) {
        if (context != null) {
            Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
        }
    }
}