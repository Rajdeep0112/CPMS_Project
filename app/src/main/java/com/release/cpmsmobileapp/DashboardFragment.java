package com.release.cpmsmobileapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.release.cpmsmobileapp.adapters.DashboardBailerAdapter;
import com.release.cpmsmobileapp.adapters.DashboardSurrenderAdapter;
import com.release.cpmsmobileapp.adapters.HearingListAdapter;
import com.release.cpmsmobileapp.adapters.NoticeListAdapter;
import com.release.cpmsmobileapp.requestbody.BailerReportRequestBody;
import com.release.cpmsmobileapp.requestbody.ByDateRequestBody;
import com.release.cpmsmobileapp.requestbody.SurrenderReportRequestBody;
import com.release.cpmsmobileapp.responsebody.BailerReportResponse;
import com.release.cpmsmobileapp.responsebody.HearingListResponse;
import com.release.cpmsmobileapp.responsebody.NoticesResponse;
import com.release.cpmsmobileapp.responsebody.SurrenderReportResponse;
import com.release.cpmsmobileapp.utils.HelperUtils;
import com.release.cpmsmobileapp.utils.SwipeDetector;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment {

    View view;
    String token;
    TextView tv;
    private ApiInterface apiInterface;
    private String gpfNo;
    TextView hearingHeader, noticeHeader, bailerHeader, surrenderHeader, dateBox;
    private RecyclerView hearingsRV, noticesRV, bailerRV, surrenderRV;
    private HearingListAdapter hearingListAdapter;

    private ImageButton calBtn;
    private NoticeListAdapter noticeListAdapter;

    private DashboardBailerAdapter bailerAdapter;

    private DashboardSurrenderAdapter surrenderAdapter;

    ImageView increaseDateBtn, decreaseDateBtn;
    LinearLayout noNoticeError, noHearingError, noBailerError, noSurrenderError;
    ConstraintLayout datePickerCard;
    LinearLayout noticeBody, hearingBody, bailerBody, surrenderBody;
    private FrameLayout progressBar1, progressBar2, progressBar3, progressBar4;
    private HorizontalScrollView hsv1, hsv2, hsv3, hsv4;
    @SuppressLint("SimpleDateFormat")
    private static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    private Long selectedDate = MaterialDatePicker.todayInUtcMilliseconds();
    private String time = " 06:00:00";

    private String userDistrictId;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        initializations();
        loadToken();
        setRecyclerViews();
        setDate();
        pickDate();
        increaseDateBtn();
        decreaseDateBtn();
        dateSwipeDetector();
        headerFunction();
        return view;
    }

    private void headerFunction() {
        noticeHeader.setBackgroundResource(R.drawable.selected_button_background);
        hearingHeader.setBackgroundResource(R.drawable.unselected_background);
        bailerHeader.setBackgroundResource(R.drawable.unselected_background);
        surrenderHeader.setBackgroundResource(R.drawable.unselected_background);

        noticeBody.setVisibility(View.VISIBLE);
        hearingBody.setVisibility(View.GONE);
        bailerBody.setVisibility(View.GONE);
        surrenderBody.setVisibility(View.GONE);

        noticeHeader.setOnClickListener(view1 -> {
            noticeBody.setVisibility(View.VISIBLE);
            hearingBody.setVisibility(View.GONE);
            bailerBody.setVisibility(View.GONE);
            surrenderBody.setVisibility(View.GONE);
            noticeHeader.setBackgroundResource(R.drawable.selected_button_background);
            hearingHeader.setBackgroundResource(R.drawable.unselected_background);
            surrenderHeader.setBackgroundResource(R.drawable.unselected_background);
            bailerHeader.setBackgroundResource(R.drawable.unselected_background);

        });

        hearingHeader.setOnClickListener(view1 -> {
            noticeBody.setVisibility(View.GONE);
            hearingBody.setVisibility(View.VISIBLE);
            bailerBody.setVisibility(View.GONE);
            surrenderBody.setVisibility(View.GONE);
            hearingHeader.setBackgroundResource(R.drawable.selected_button_background);
            noticeHeader.setBackgroundResource(R.drawable.unselected_background);
            surrenderHeader.setBackgroundResource(R.drawable.unselected_background);
            bailerHeader.setBackgroundResource(R.drawable.unselected_background);
        });

        bailerHeader.setOnClickListener(view1 -> {
            noticeBody.setVisibility(View.GONE);
            hearingBody.setVisibility(View.GONE);
            bailerBody.setVisibility(View.VISIBLE);
            surrenderBody.setVisibility(View.GONE);
            bailerHeader.setBackgroundResource(R.drawable.selected_button_background);
            noticeHeader.setBackgroundResource(R.drawable.unselected_background);
            hearingHeader.setBackgroundResource(R.drawable.unselected_background);
            surrenderHeader.setBackgroundResource(R.drawable.unselected_background);
        });

        surrenderHeader.setOnClickListener(view1 -> {
            noticeBody.setVisibility(View.GONE);
            hearingBody.setVisibility(View.GONE);
            bailerBody.setVisibility(View.GONE);
            surrenderBody.setVisibility(View.VISIBLE);
            surrenderHeader.setBackgroundResource(R.drawable.selected_button_background);
            bailerHeader.setBackgroundResource(R.drawable.unselected_background);
            noticeHeader.setBackgroundResource(R.drawable.unselected_background);
            hearingHeader.setBackgroundResource(R.drawable.unselected_background);
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void dateSwipeDetector() {
        SwipeDetector swipeDetector = new SwipeDetector(new SwipeDetector.OnSwipeListener() {
            @Override
            public void onSwipeRight() {
                String newDate = decreaseDate(tv.getText().toString());
                String[] dateParts = newDate.split("/");
                try {
                    selectedDate = sdf.parse(newDate + time).getTime();
                    changeDate(dateParts[2], dateParts[1], dateParts[0]);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onSwipeLeft() {
                String newDate = increaseDate(tv.getText().toString());
                String[] dateParts = newDate.split("/");
                try {
                    selectedDate = sdf.parse(newDate + time).getTime();
                    changeDate(dateParts[2], dateParts[1], dateParts[0]);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        datePickerCard.setOnTouchListener(swipeDetector);
        tv.setOnTouchListener(swipeDetector);
    }

    private void decreaseDateBtn() {
        decreaseDateBtn.setOnClickListener(view1 -> {
            String newDate = decreaseDate(tv.getText().toString());
            String[] dateParts = newDate.split("/");
            try {
                selectedDate = sdf.parse(newDate + time).getTime();
                changeDate(dateParts[2], dateParts[1], dateParts[0]);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void increaseDateBtn() {
        increaseDateBtn.setOnClickListener(view1 -> {
            String newDate = increaseDate(tv.getText().toString());
            String[] dateParts = newDate.split("/");
            try {
                selectedDate = sdf.parse(newDate + time).getTime();
                changeDate(dateParts[2], dateParts[1], dateParts[0]);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void setRecyclerViews() {
        hearingsRV.setLayoutManager(new LinearLayoutManager(getContext()));
        hearingListAdapter = new HearingListAdapter();
        hearingsRV.setAdapter(hearingListAdapter);
        noticesRV.setLayoutManager(new LinearLayoutManager(getContext()));
        noticeListAdapter = new NoticeListAdapter();
        noticesRV.setAdapter(noticeListAdapter);

        bailerRV.setLayoutManager(new LinearLayoutManager(getContext()));
        bailerAdapter = new DashboardBailerAdapter();
        bailerRV.setAdapter(bailerAdapter);

        surrenderRV.setLayoutManager(new LinearLayoutManager(getContext()));
        surrenderAdapter = new DashboardSurrenderAdapter();
        surrenderRV.setAdapter(surrenderAdapter);
    }

    private void getHearingByDate(String date) {
//        gpfNo = "123";
        ByDateRequestBody requestBody = new ByDateRequestBody(gpfNo, date);
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + token);
        Call<List<HearingListResponse>> call = apiInterface.getHearingsByDate(map, requestBody);
        call.enqueue(new Callback<List<HearingListResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<HearingListResponse>> call, @NonNull Response<List<HearingListResponse>> response) {
                List<HearingListResponse> list = response.body();
                if (list == null || response.body() == null) {
                    progressBar1.setVisibility(View.GONE);
                    hsv1.setVisibility(View.GONE);
                    toastShort("Hearing error code : " + response.code(), getContext());
                    return;
                }
                hearingListAdapter.setHearingListAdapter(list, getContext());
                progressBar1.setVisibility(View.GONE);
                if (list.size() > 0) {
                    noHearingError.setVisibility(View.GONE);
                    hsv1.setVisibility(View.VISIBLE);
                } else {
                    noHearingError.setVisibility(View.VISIBLE);
                    hsv1.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<HearingListResponse>> call, Throwable t) {
                toastShort(t.getMessage(), getContext());
                progressBar1.setVisibility(View.GONE);
                noHearingError.setVisibility(View.VISIBLE);
            }
        });
    }

    private void getNoticesByDate(String date) {
//        gpfNo = "123";
        ByDateRequestBody requestBody = new ByDateRequestBody(gpfNo, date);
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + token);
        Call<List<NoticesResponse>> call = apiInterface.getNoticesByDate(map, requestBody);
        call.enqueue(new Callback<List<NoticesResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<NoticesResponse>> call, @NonNull Response<List<NoticesResponse>> response) {
                List<NoticesResponse> list = response.body();
                if (list == null || response.body() == null) {
                    progressBar2.setVisibility(View.GONE);
                    hsv2.setVisibility(View.GONE);
                    toastShort("Notices error code : " + response.code(), getContext());
                    return;
                }
                noticeListAdapter.setNoticeListAdapter(list, getContext());
                progressBar2.setVisibility(View.GONE);
                if (list.size() > 0) {
//                    Toast.makeText(getContext(), ""+list.size(), Toast.LENGTH_SHORT).show();
                    noNoticeError.setVisibility(View.GONE);
                    hsv2.setVisibility(View.VISIBLE);
                } else {
                    noNoticeError.setVisibility(View.VISIBLE);
                    hsv2.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<NoticesResponse>> call, Throwable t) {
                progressBar2.setVisibility(View.GONE);
                toastShort(t.getMessage(), getContext());
                noNoticeError.setVisibility(View.VISIBLE);
            }
        });
    }

    private void getBailerByDate(String date){
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + token);
        BailerReportRequestBody requestBody = new BailerReportRequestBody(gpfNo,userDistrictId, date,date,"4");

        Call<List<BailerReportResponse>> call = apiInterface.getBailerReport(map, requestBody);

        call.enqueue(new Callback<List<BailerReportResponse>>() {
            @Override
            public void onResponse(Call<List<BailerReportResponse>> call, Response<List<BailerReportResponse>> response) {
                List<BailerReportResponse> list = response.body();
                if (list == null || response.body() == null) {
                    progressBar3.setVisibility(View.GONE);
                    hsv3.setVisibility(View.GONE);
                    toastShort("Bailer error code : " + response.code(), getContext());
                    return;
                }
//                noticeListAdapter.setNoticeListAdapter(list, getContext());
                bailerAdapter.setBailerAdapter(list, getContext());

                progressBar3.setVisibility(View.GONE);
                if (list.size() > 0) {
                    noBailerError.setVisibility(View.GONE);
                    hsv3.setVisibility(View.VISIBLE);
                } else {
                    noBailerError.setVisibility(View.VISIBLE);
                    hsv3.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<BailerReportResponse>> call, Throwable t) {
                progressBar3.setVisibility(View.GONE);
                toastShort(t.getMessage(), getContext());
                noBailerError.setVisibility(View.VISIBLE);
            }
        });
    }

    private void getSurrednerByDate(String date){
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + token);
        SurrenderReportRequestBody requestBody = new SurrenderReportRequestBody(gpfNo,userDistrictId, date,date);

        Call<List<SurrenderReportResponse>> call = apiInterface.getSurrenderAccusedReport(map, requestBody);

        call.enqueue(new Callback<List<SurrenderReportResponse>>() {
            @Override
            public void onResponse(Call<List<SurrenderReportResponse>> call, Response<List<SurrenderReportResponse>> response) {
                List<SurrenderReportResponse> list = response.body();
                if (list == null || response.body() == null) {
                    progressBar4.setVisibility(View.GONE);
                    hsv4.setVisibility(View.GONE);
                    toastShort("Surrender error code : " + response.code(), getContext());
                    return;
                }
//                noticeListAdapter.setNoticeListAdapter(list, getContext());
                surrenderAdapter.setSurrenderAdapter(list, getContext());
                progressBar4.setVisibility(View.GONE);
                if (list.size() > 0) {
                    noSurrenderError.setVisibility(View.GONE);
                    hsv4.setVisibility(View.VISIBLE);
                } else {
                    noSurrenderError.setVisibility(View.VISIBLE);
                    hsv4.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<SurrenderReportResponse>> call, Throwable t) {
                progressBar4.setVisibility(View.GONE);
                toastShort(t.getMessage(), getContext());
                noSurrenderError.setVisibility(View.VISIBLE);
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void setDate() {
        String date = dateFormat.format(selectedDate);
        String[] dateParts = date.split("/");

//        String yyyy, mm, dd;
//        Calendar cal = Calendar.getInstance();
//        int day = cal.get(Calendar.DAY_OF_MONTH);
//        int month = cal.get(Calendar.MONTH) + 1;
//        int year = cal.get(Calendar.YEAR);
//        yyyy = String.valueOf(year);
//        mm = month < 10 ? "0" + month : String.valueOf(month);
//        dd = day < 10 ? "0" + day : String.valueOf(day);
        changeDate(dateParts[2], dateParts[1], dateParts[0]);
    }

    public static String increaseDate(String currentDate) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(Objects.requireNonNull(dateFormat.parse(currentDate)));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            return dateFormat.format(calendar.getTime());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String decreaseDate(String currentDate) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(Objects.requireNonNull(dateFormat.parse(currentDate)));
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            Log.e("id", calendar.getTime() + "");
            return dateFormat.format(calendar.getTime());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @SuppressLint("SetTextI18n")
    private void pickDate() {
//        Calendar cal = Calendar.getInstance();
//        int day = cal.get(Calendar.DAY_OF_MONTH);
//        int month = cal.get(Calendar.MONTH);
//        int year = cal.get(Calendar.YEAR);
        calBtn.setOnClickListener(v -> {
//            DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
//                    (view, year1, monthOfYear, dayOfMonth) -> {
//                        String y = String.valueOf(year1);
//                        String m = (monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : String.valueOf(monthOfYear + 1);
//                        String d = dayOfMonth < 10 ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
//                        changeDate(y, m, d);
//                    }, year, month, day);
//            datePickerDialog.show();

            MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select Date")
                    .setSelection(selectedDate)
                    .build();

            datePicker.show(getParentFragmentManager(), datePicker.toString());
            datePicker.addOnPositiveButtonClickListener(selection -> {
                selectedDate = selection;
                String date = dateFormat.format(selectedDate);
                String[] dateParts = date.split("/");
                changeDate(dateParts[2], dateParts[1], dateParts[0]);
            });
        });
    }

    @SuppressLint("SetTextI18n")
    private void changeDate(String yyyy, String mm, String dd) {
        tv.setText(dd + "/" + mm + "/" + yyyy);
        String date = yyyy + "-" + mm + "-" + dd;
        progressBar1.setVisibility(View.VISIBLE);
        progressBar2.setVisibility(View.VISIBLE);
        getHearingByDate(date);
        getNoticesByDate(date);
        getBailerByDate(date);
        getSurrednerByDate(date);
    }

    private void initializations() {
        tv = view.findViewById(R.id.dateBox);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        HelperUtils utils = new HelperUtils(requireContext());
        token = utils.getToken();
        gpfNo = utils.getGpfNo();
        userDistrictId = utils.getUserDistrictId();
        hearingHeader = view.findViewById(R.id.hearingHeader);
        hearingsRV = view.findViewById(R.id.hearingsByDateRecyclerView);
        noticesRV = view.findViewById(R.id.noticesByDateRecyclerView);
        noticeHeader = view.findViewById(R.id.noticeHeader);
        increaseDateBtn = view.findViewById(R.id.increaseDateBtn);
        decreaseDateBtn = view.findViewById(R.id.decreaseDateBtn);
        noNoticeError = view.findViewById(R.id.noNoticeContainer);
        noHearingError = view.findViewById(R.id.noHearingContainer);
        datePickerCard = view.findViewById(R.id.datepickerCard);
        progressBar1 = view.findViewById(R.id.progressBar1);
        progressBar2 = view.findViewById(R.id.progressBar2);
        hsv1 = view.findViewById(R.id.hsv1);
        hsv2 = view.findViewById(R.id.hsv2);
        noticeBody = view.findViewById(R.id.noticeBody);
        hearingBody = view.findViewById(R.id.hearingBody);
        calBtn = view.findViewById(R.id.calBtn);

        bailerHeader = view.findViewById(R.id.bailerHeader);
        surrenderHeader = view.findViewById(R.id.surrenderHeader);
        bailerBody = view.findViewById(R.id.bailerBody);
        surrenderBody = view.findViewById(R.id.surrenderBody);
        bailerRV = view.findViewById(R.id.bailerByDateRecyclerView);
        surrenderRV = view.findViewById(R.id.surrenderByDateRecyclerView);
        noBailerError = view.findViewById(R.id.noBailerContainer);
        noSurrenderError = view.findViewById(R.id.noSurrenderContainer);
        progressBar3 = view.findViewById(R.id.progressBar3);
        progressBar4 = view.findViewById(R.id.progressBar4);
        hsv3 = view.findViewById(R.id.hsv3);
        hsv4 = view.findViewById(R.id.hsv4);

    }


    private void loadToken() {
        if (token.length() == 0) {
            Intent intent = new Intent(requireContext(), LoginActivity.class);
            startActivity(intent);
            requireActivity().finish();
        }
    }

    private void toastShort(String string, Context context) {
        if (context != null) {
            Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
        }
    }
}