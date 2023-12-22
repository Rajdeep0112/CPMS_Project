package com.release.cpmsmobileapp.reports.notice;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.release.cpmsmobileapp.ApiClient;
import com.release.cpmsmobileapp.ApiInterface;
import com.release.cpmsmobileapp.R;
import com.release.cpmsmobileapp.adapters.SubNoticePendencyAdapter;
import com.release.cpmsmobileapp.databinding.ActivitySubNoticePendencyReportBinding;
import com.release.cpmsmobileapp.requestbody.NoticePendencyRequestBody;
import com.release.cpmsmobileapp.responsebody.DistrictResponse;
import com.release.cpmsmobileapp.responsebody.NoticePendencyResponse;
import com.release.cpmsmobileapp.responsebody.NoticeTypeResponse;
import com.release.cpmsmobileapp.responsebody.PsResponse;
import com.release.cpmsmobileapp.utils.CPMSUser;
import com.release.cpmsmobileapp.utils.HelperUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubNoticePendencyReport extends AppCompatActivity {
    ActivitySubNoticePendencyReportBinding binding;
    private HelperUtils utils;
    private ApiInterface apiInterface;
    private NoticePendencyRequestBody requestBody = new NoticePendencyRequestBody();
    private HashMap<String, String> districtMap;
    private CPMSUser user;
    private ArrayList<String> districtDropdown,yearsDropdown;
    private HashMap<String, Integer> psMap;
    private ArrayList<String> psDropdown;
    private HashMap<String, Integer> noticeMap;
    private ArrayList<String> noticeDropdown;
    private SubNoticePendencyAdapter adapter;
    private List<NoticePendencyResponse> list;
    private String year,userDistrictId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubNoticePendencyReportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initialisations();
        setToolbar();
        setYearSpinnerAdapter();
        getDistricts();
        getNoticeTypes();
        binding.searchBtn.setOnClickListener(view -> searchBtn());
    }

    private void searchBtn() {
        try {
            if (requestBody.getNoticeTypeId().equals("-6969")) requestBody.setNoticeTypeId(null);
            if (requestBody.getPoliceStationId().equals("-6969"))
                requestBody.setPoliceStationId(null);
        } catch (Exception ignored){

        }

        binding.hsv.setVisibility(View.GONE);
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.txtSearch.setVisibility(View.INVISIBLE);
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + utils.getToken());
        Call<List<NoticePendencyResponse>> call = apiInterface.getNoticePendencyReport(map, requestBody);
        call.enqueue(new Callback<List<NoticePendencyResponse>>() {
            @Override
            public void onResponse(Call<List<NoticePendencyResponse>> call, Response<List<NoticePendencyResponse>> response) {
                list = response.body();
                if (list == null || response.body() == null) {
                    binding.progressBar.setVisibility(View.INVISIBLE);
                    binding.txtSearch.setVisibility(View.VISIBLE);
                    binding.hsv.setVisibility(View.GONE);
                    toastShort("Could not get report\n\terror code : " + response.code(), SubNoticePendencyReport.this);
                    return;
                }
                binding.progressBar.setVisibility(View.INVISIBLE);
                binding.txtSearch.setVisibility(View.VISIBLE);
                if (list.size() != 0) {
                    adapterWork(list);
                    binding.hsv.setVisibility(View.VISIBLE);
                } else {
                    toastShort("No reports found", SubNoticePendencyReport.this);
                    binding.hsv.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<NoticePendencyResponse>> call, Throwable t) {
                Toast.makeText(SubNoticePendencyReport.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void adapterWork(List<NoticePendencyResponse> list) {
        Collections.reverse(list);
        adapter.setSubNoticePendencyAdapter(list, this);
    }

    private void setYearSpinnerAdapter(){
        yearsDropdown = new ArrayList<>();
        yearsDropdown.add("Select Year");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(SubNoticePendencyReport.this, R.layout.drop_down_item_layout,yearsDropdown);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for(int i=1990;i<=thisYear;i++){
            yearsDropdown.add(Integer.toString(i));
        }
        binding.yearSpinner.setAdapter(adapter);

        binding.yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                year = adapterView.getItemAtPosition(i).toString();
                binding.yearTypeText.setText(year);
                if(year.equals("Select Year")) year="";
                requestBody.setYear(year);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getNoticeTypes() {
        noticeMap = new HashMap<>();
        noticeDropdown = new ArrayList<>();
        noticeDropdown.add("Select Notice Type");
        noticeMap.put("Select Notice Type", -6969);
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + utils.getToken());
        Call<List<NoticeTypeResponse>> call = apiInterface.getNoticeTypes(map);
        call.enqueue(new Callback<List<NoticeTypeResponse>>() {
            @Override
            public void onResponse(Call<List<NoticeTypeResponse>> call, Response<List<NoticeTypeResponse>> response) {
                List<NoticeTypeResponse> responses = response.body();
                if (responses != null) {
                    for (NoticeTypeResponse typeResponse : responses) {
                        noticeMap.put(typeResponse.getName(), typeResponse.getId());
                        noticeDropdown.add(typeResponse.getName());
                    }
                    setNoticeTypeSpinnerAdapter();
                } else {
                    Toast.makeText(SubNoticePendencyReport.this, "Notice Types not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<NoticeTypeResponse>> call, Throwable t) {
                Toast.makeText(SubNoticePendencyReport.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setNoticeTypeSpinnerAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.drop_down_item_layout, noticeDropdown);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.noticeTypeSpinner.setAdapter(adapter);
        binding.noticeTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String type = adapterView.getItemAtPosition(i).toString();
                requestBody.setNoticeTypeId(String.valueOf(noticeMap.get(type)));
                binding.noticeTypeText.setText(type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getDistricts() {
        districtMap = new HashMap<>();
        districtDropdown = new ArrayList<>();
        districtDropdown.add("Select District");
        districtMap.put("Select District", "");
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + utils.getToken());
        Call<List<DistrictResponse>> call = apiInterface.getDistrictList(map);
        call.enqueue(new Callback<List<DistrictResponse>>() {
            @Override
            public void onResponse(Call<List<DistrictResponse>> call, Response<List<DistrictResponse>> response) {
                List<DistrictResponse> distList = response.body();
                if (distList != null) {
                    for (DistrictResponse districtResponse : distList) {
                        if(userDistrictId.equals(districtResponse.getId().toString())) {
                            String district = districtResponse.getName() + " (" + districtResponse.getName_hi() + ")";
                            districtMap.put(district, districtResponse.getId().toString());
                            districtDropdown.add(district);
                        }
                    }
                    setDistrictSpinnerAdapter();

                } else {
                    Toast.makeText(SubNoticePendencyReport.this, "District list is empty", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DistrictResponse>> call, Throwable t) {

            }
        });
    }

    private void setDistrictSpinnerAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.drop_down_item_layout, districtDropdown);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.districtSpinner.setAdapter(adapter);
        binding.districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String district = adapterView.getItemAtPosition(i).toString();
                requestBody.setDistrictId(districtMap.get(district));
                binding.districtSpinnerText.setText(district);
                getPoliceStations();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getPoliceStations() {
        psMap = new HashMap<>();
        psDropdown = new ArrayList<>();
        psDropdown.add("Select Police Station");
        psMap.put("Select Police Station", -6969);
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + utils.getToken());
        Call<List<PsResponse>> call = apiInterface.getPsList(map, requestBody.getDistrictId());
        call.enqueue(new Callback<List<PsResponse>>() {
            @Override
            public void onResponse(Call<List<PsResponse>> call, Response<List<PsResponse>> response) {
                List<PsResponse> responses = response.body();
                if (responses != null) {
                    for (PsResponse psResponse : responses) {
                        String ps = psResponse.getName() + " (" + psResponse.getName_hi() + ")";
                        psMap.put(ps, psResponse.getDistrictId());
                        psDropdown.add(ps);
                    }
                    setPsSpinnerAdapter();
                } else {
                    Toast.makeText(SubNoticePendencyReport.this, "Police Station list in empty", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PsResponse>> call, Throwable t) {
                Toast.makeText(SubNoticePendencyReport.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setPsSpinnerAdapter() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.drop_down_item_layout, psDropdown);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.psSpinner.setAdapter(adapter);
        binding.psSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String ps = adapterView.getItemAtPosition(i).toString();
                requestBody.setPoliceStationId(String.valueOf(psMap.get(ps)));
                binding.psText.setText(ps);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setToolbar() {
        binding.topAppBar.setNavigationOnClickListener(view -> this.finish());
    }

    private void initialisations() {
        utils = new HelperUtils(this);
        user = new CPMSUser(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        userDistrictId=utils.getUserDistrictId();
        requestBody.setUserDistrictId(user.getDistrictID());
        requestBody.setGpf_no(user.getGpfNo());
        setRecyclerView();
    }

    private void setRecyclerView() {
        binding.noticePendencyRV.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SubNoticePendencyAdapter();
        binding.noticePendencyRV.setAdapter(adapter);
    }

    private void toastShort(String string, Context context) {
        if (context != null) {
            Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
        }
    }
}