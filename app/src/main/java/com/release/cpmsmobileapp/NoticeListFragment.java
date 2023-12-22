package com.release.cpmsmobileapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.release.cpmsmobileapp.adapters.NoticeListAdapter;
import com.release.cpmsmobileapp.databinding.FragmentNoticeListBinding;
import com.release.cpmsmobileapp.notices.NoticeUtils;
import com.release.cpmsmobileapp.requestbody.AssignedCaseRequestBody;
import com.release.cpmsmobileapp.responsebody.NoticeTypeResponse;
import com.release.cpmsmobileapp.responsebody.NoticesResponse;
import com.release.cpmsmobileapp.utils.HelperUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NoticeListFragment extends Fragment {


    String token, gpfNo;
    View view;
    private ApiInterface apiInterface;
    private HelperUtils utils;
    LinearLayout noNoticeError;
    @NonNull
    private FragmentNoticeListBinding binding;
    private LinearLayout hsv;
    private List<NoticesResponse> list = new ArrayList<>();
    private NoticeUtils noticeUtils;
    private NoticeListAdapter adapter1, adapter2;
    private ArrayList<NoticesResponse> completeList;
    private ArrayList<NoticesResponse> incompleteList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentNoticeListBinding.inflate(inflater, container, false);
        view = binding.getRoot();
        initializations();
        loadToken();
        setRecyclerView();
        binding.removeAllBtn.setOnClickListener(view1 -> {
            binding.horiView.removeAllViews();
            getNoticeTypes();
            binding.completeBtn.setBackgroundResource(R.drawable.selected_button_background);
            binding.incompleteBtn.setBackgroundResource(R.drawable.unselected_background);
            binding.hsv2.setVisibility(View.GONE);
            binding.noNoticeContainer.setVisibility(View.VISIBLE);
            binding.hsv1.setVisibility(View.GONE);
        });
        getNoticeTypes();
        getNoticeList();
        return view;
    }

    private void getNoticeTypes() {
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + utils.getToken());
        Call<List<NoticeTypeResponse>> call = apiInterface.getNoticeTypes(map);
        call.enqueue(new Callback<List<NoticeTypeResponse>>() {
            @Override
            public void onResponse(Call<List<NoticeTypeResponse>> call, Response<List<NoticeTypeResponse>> response) {
                List<NoticeTypeResponse> responses = response.body();
                if (responses != null) {
                    for (NoticeTypeResponse typeResponse : responses) {
                        addView(typeResponse.getName());
                    }
                } else {
                    toastShort("Notice Types not found", getContext());
                }
            }

            @Override
            public void onFailure(Call<List<NoticeTypeResponse>> call, Throwable t) {
                toastShort(t.getMessage(), getContext());
            }
        });
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void addView(String name) {
        if(isAdded() && getActivity()!=null) {
            View tv = getLayoutInflater().inflate(R.layout.header_item_layout, null);
            TextView nameView = tv.findViewById(R.id.itemName);
            nameView.setText(name);
            nameView.setBackgroundResource(R.drawable.unselected_background);
            completeList.removeIf(response -> response.getNoticetypes_name() != null && response.getNoticetypes_name().contentEquals(nameView.getText()));
            incompleteList.removeIf(response -> response.getNoticetypes_name() != null && response.getNoticetypes_name().contentEquals(nameView.getText()));
            binding.completeBtn.setText("Complete (" + completeList.size() + ")");
            binding.incompleteBtn.setText("Incomplete (" + incompleteList.size() + ")");
            adapter1.setNoticeListAdapter(completeList, getContext());
            adapter2.setNoticeListAdapter(incompleteList, getContext());
            nameView.setOnClickListener(view1 -> {
                if (nameView.getBackground().getConstantState().equals(
                        getResources().getDrawable(R.drawable.selected_button_background).getConstantState())) {
                    nameView.setBackgroundResource(R.drawable.unselected_background);
                    completeList.removeIf(response -> response.getNoticetypes_name() != null && response.getNoticetypes_name().contentEquals(nameView.getText()));
                    incompleteList.removeIf(response -> response.getNoticetypes_name() != null && response.getNoticetypes_name().contentEquals(nameView.getText()));
                } else {
                    nameView.setBackgroundResource(R.drawable.selected_button_background);
                    for (NoticesResponse response : list) {
                        if (response.getNoticetypes_name() != null && response.getNoticetypes_name().contentEquals(nameView.getText())) {
                            if (response.getNotice_completed_date() != null && !response.getNotice_completed_date().equals(""))
                                completeList.add(response);
                            else
                                incompleteList.add(response);
                        }
                    }
                }
                if (binding.hsv1.getVisibility() == View.VISIBLE) showhsv1();
                else showhsv2();
                binding.completeBtn.setText("Complete (" + completeList.size() + ")");
                binding.incompleteBtn.setText("Incomplete (" + incompleteList.size() + ")");
                adapter1.setNoticeListAdapter(completeList, getContext());
                adapter2.setNoticeListAdapter(incompleteList, getContext());
            });
            binding.horiView.addView(tv);
        }
    }

    private void headerFunction() {
        binding.completeBtn.setText("Complete (" + completeList.size() + ")");
        binding.incompleteBtn.setText("Incomplete (" + incompleteList.size() + ")");
        showhsv1();

        binding.completeBtn.setOnClickListener(view -> {
            showhsv1();
        });
        binding.incompleteBtn.setOnClickListener(view -> {
            showhsv2();
        });

    }

    private void showhsv1() {
        binding.completeBtn.setBackgroundResource(R.drawable.selected_button_background);
        binding.incompleteBtn.setBackgroundResource(R.drawable.unselected_background);
        binding.hsv2.setVisibility(View.GONE);
        if (completeList.size() == 0) {
            binding.noNoticeContainer.setVisibility(View.VISIBLE);
            binding.hsv1.setVisibility(View.GONE);
        } else {
            binding.noNoticeContainer.setVisibility(View.GONE);
            binding.hsv1.setVisibility(View.VISIBLE);
        }
    }

    private void showhsv2() {
        binding.incompleteBtn.setBackgroundResource(R.drawable.selected_button_background);
        binding.completeBtn.setBackgroundResource(R.drawable.unselected_background);
        binding.hsv1.setVisibility(View.GONE);
        if (incompleteList.size() == 0) {
            binding.noNoticeContainer.setVisibility(View.VISIBLE);
            binding.hsv2.setVisibility(View.GONE);
        } else {
            binding.noNoticeContainer.setVisibility(View.GONE);
            binding.hsv2.setVisibility(View.VISIBLE);
        }
    }

    private void getNoticeList() {
//        gpfNo = "123";
        Map<String, String> map = new HashMap<>();
        map.put("Authorization", "Bearer " + token);

        Call<List<NoticesResponse>> call = apiInterface.getAllNotices(map, new AssignedCaseRequestBody(gpfNo));

        call.enqueue(new Callback<List<NoticesResponse>>() {
            @Override
            public void onResponse(Call<List<NoticesResponse>> call, Response<List<NoticesResponse>> response) {
                list = response.body();
                if (response.body() == null || list == null) {
                    binding.shimmerViewContainer.stopShimmerAnimation();
                    binding.shimmerViewContainer.setVisibility(View.GONE);
                    hsv.setVisibility(View.GONE);
                    toastShort("Could not get hearing cases\n\terror code : " + response.code(), getContext());
                    noNoticeError.setVisibility(View.VISIBLE);
                    return;
                }
                binding.shimmerViewContainer.stopShimmerAnimation();
                binding.shimmerViewContainer.setVisibility(View.GONE);
                hsv.setVisibility(View.VISIBLE);
                if (list.size() != 0) {
                    noticeUtils.sortListByIOIssueDate(list);
//                    adapterWork(list);
                    noNoticeError.setVisibility(View.GONE);
                } else {
                    toastShort("No Notice available", getContext());
                    noNoticeError.setVisibility(View.VISIBLE);
                    hsv.setVisibility(View.GONE);
                }
                headerFunction();

            }

            @Override
            public void onFailure(Call<List<NoticesResponse>> call, Throwable t) {
                binding.shimmerViewContainer.stopShimmerAnimation();
                binding.shimmerViewContainer.setVisibility(View.GONE);
                toastShort(t.getMessage(), getContext());
                noNoticeError.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setRecyclerView() {
        binding.completeNoticeRv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter1 = new NoticeListAdapter();
        binding.completeNoticeRv.setAdapter(adapter1);
        binding.incompleteNoticeRv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter2 = new NoticeListAdapter();
        binding.incompleteNoticeRv.setAdapter(adapter2);
    }

    private void initializations() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        utils = new HelperUtils(requireContext());
        token = utils.getToken();
        gpfNo = utils.getGpfNo();
        noNoticeError = view.findViewById(R.id.noNoticeContainer);
        hsv = view.findViewById(R.id.hsv);
        noticeUtils = new NoticeUtils(requireContext());
        binding.shimmerViewContainer.startShimmerAnimation();
        completeList = new ArrayList<>();
        incompleteList = new ArrayList<>();
    }

    private void loadToken() {
        token = utils.getToken();
        if (token.length() == 0) {
            Intent intent = new Intent(requireContext(), LoginActivity.class);
            startActivity(intent);
        }
    }

//    private void loadGpfNo(SharedPreferences sharedPreferences) {
//        gpfNo = sharedPreferences.getString(String.valueOf(R.string.gpf_no), "");
//    }

    private void toastShort(String string, Context context) {
        if (context != null) {
            Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
        }
    }

}