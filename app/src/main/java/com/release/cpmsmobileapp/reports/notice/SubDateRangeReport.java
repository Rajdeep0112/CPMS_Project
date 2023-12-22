package com.release.cpmsmobileapp.reports.notice;

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
import com.release.cpmsmobileapp.adapters.SubNoticePendencyAdapter;
import com.release.cpmsmobileapp.databinding.ActivitySubDateRangeReportBinding;
import com.release.cpmsmobileapp.requestbody.NoticePendencyRequestBody;
import com.release.cpmsmobileapp.responsebody.NoticePendencyResponse;
import com.release.cpmsmobileapp.responsebody.NoticeTypeResponse;
import com.release.cpmsmobileapp.utils.CPMSUser;
import com.release.cpmsmobileapp.utils.HelperUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubDateRangeReport extends AppCompatActivity {
    ActivitySubDateRangeReportBinding binding;
    private HelperUtils utils;
    private CPMSUser user;
    private ApiInterface apiInterface;
    private NoticePendencyRequestBody requestBody = new NoticePendencyRequestBody();
    private HashMap<String, Integer> noticeMap;
    private ArrayList<String> noticeDropdown;
    private List<NoticePendencyResponse> list;
    private SubNoticePendencyAdapter adapter;
    private Long startDate= MaterialDatePicker.todayInUtcMilliseconds(), endDate = MaterialDatePicker.todayInUtcMilliseconds();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubDateRangeReportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initialisations();
        setToolbar();
        getNoticeTypes();
        binding.startDateText.setOnClickListener(view -> startDateFunc());
        binding.endDateText.setOnClickListener(view -> endDateFunc());

        binding.searchBtn.setOnClickListener(view -> searchBtn());
    }

    private void searchBtn() {

        if (!areDatesValid(requestBody.getStartDate(), requestBody.getEndDate())) {
            Toast.makeText(this, "Please put valid date range.", Toast.LENGTH_SHORT).show();
        }
        try {
            if (requestBody.getNoticeTypeId().equals("-6969")) requestBody.setNoticeTypeId(null);
        } catch (Exception ignored) {
        }
//        Log.wtf("body", requestBody.getUserDistrictId() +" "+requestBody.getNoticeTypeId());
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
                    toastShort("Could not get report\n\terror code : " + response.code(), SubDateRangeReport.this);
                    return;
                }
                binding.progressBar.setVisibility(View.INVISIBLE);
                binding.txtSearch.setVisibility(View.VISIBLE);
                if (list.size() != 0) {
                    adapterWork(list);
                    binding.hsv.setVisibility(View.VISIBLE);
                } else {
                    toastShort("No reports found", SubDateRangeReport.this);
                    binding.hsv.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<NoticePendencyResponse>> call, Throwable t) {
                Toast.makeText(SubDateRangeReport.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void adapterWork(List<NoticePendencyResponse> list) {
        Collections.reverse(list);
        adapter.setSubNoticePendencyAdapter(list, this);
    }

    private void toastShort(String s, Context context) {
        if (context != null) {
            Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean areDatesValid(String startDateStr, String endDateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date startDate = sdf.parse(startDateStr);
            Date endDate = sdf.parse(endDateStr);

            if (startDate != null) {
                return !startDate.after(endDate);
            }
            return true;
        } catch (ParseException e) {
            e.printStackTrace();
            return true;
        }
    }

    private void endDateFunc() {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("End Date")
                .setSelection(endDate)
                .build();

        datePicker.show(getSupportFragmentManager(), datePicker.toString());
        datePicker.addOnPositiveButtonClickListener(selection -> {
            endDate = selection;
            String date = dateFormat.format(endDate);
            String[] dateParts = date.split("/");
            changeEndDate(dateParts[2], dateParts[1], dateParts[0]);
        });
    }

    private void startDateFunc() {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Start Date")
                .setSelection(startDate)
                .build();

        datePicker.show(getSupportFragmentManager(), datePicker.toString());
        datePicker.addOnPositiveButtonClickListener(selection -> {
            startDate = selection;
            String date = dateFormat.format(startDate);
            String[] dateParts = date.split("/");
            changeStartDate(dateParts[2], dateParts[1], dateParts[0]);
        });
    }

    private void changeStartDate(String yyyy, String mm, String dd) {
        binding.startDateText.setText(dd + "/" + mm + "/" + yyyy);
        String date = yyyy + "-" + mm + "-" + dd;
        requestBody.setStartDate(date);
    }

    private void changeEndDate(String yyyy, String mm, String dd) {
        binding.endDateText.setText(dd + "/" + mm + "/" + yyyy);
        String date = yyyy + "-" + mm + "-" + dd;
        requestBody.setEndDate(date);
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
                    Toast.makeText(SubDateRangeReport.this, "Notice Types not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<NoticeTypeResponse>> call, Throwable t) {
                Toast.makeText(SubDateRangeReport.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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


    private void setToolbar() {
        binding.topAppBar.setNavigationOnClickListener(view -> this.finish());
    }

    private void initialisations() {
        utils = new HelperUtils(this);
        user = new CPMSUser(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        requestBody.setUserDistrictId(user.getDistrictID());
        requestBody.setGpf_no(user.getGpfNo());
        setRecyclerView();
    }

    private void setRecyclerView() {
        binding.dateRangeRV.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SubNoticePendencyAdapter();
        binding.dateRangeRV.setAdapter(adapter);
    }
}