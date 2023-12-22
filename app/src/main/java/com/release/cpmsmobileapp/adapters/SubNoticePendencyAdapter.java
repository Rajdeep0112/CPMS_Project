package com.release.cpmsmobileapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.release.cpmsmobileapp.ApiClient;
import com.release.cpmsmobileapp.ApiInterface;
import com.release.cpmsmobileapp.RespectiveCaseActivity;
import com.release.cpmsmobileapp.RespectiveNoticeActivity;
import com.release.cpmsmobileapp.databinding.SubNoticeItemLayoutBinding;
import com.release.cpmsmobileapp.requestbody.NoticeByIDRequestBody;
import com.release.cpmsmobileapp.requestbody.SearchCaseRequestBody;
import com.release.cpmsmobileapp.responsebody.NoticePendencyResponse;
import com.release.cpmsmobileapp.responsebody.NoticesResponse;
import com.release.cpmsmobileapp.responsebody.SearchCaseResponse;
import com.release.cpmsmobileapp.utils.HelperUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubNoticePendencyAdapter extends RecyclerView.Adapter<SubNoticePendencyAdapter.MyViewHolder> {

    private List<NoticePendencyResponse> responses = new ArrayList<>();
    private Context context;

    public void setSubNoticePendencyAdapter(List<NoticePendencyResponse> responses, Context context) {
        this.responses = responses;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SubNoticePendencyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SubNoticeItemLayoutBinding itemLayoutBinding = SubNoticeItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MyViewHolder(itemLayoutBinding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull SubNoticePendencyAdapter.MyViewHolder holder, int position) {
        NoticePendencyResponse response = responses.get(position);
        holder.srNo.setText("" + (position + 1));
        holder.io.setText(response.getIoName());
        holder.type.setText(response.getNoticeType());
        holder.pendingFor.setText(response.getPendingFor() + " days");
        holder.viewNotice.setOnClickListener(view -> viewNotice(response));
        holder.viewCase.setOnClickListener(view -> viewCase(response));
    }

    private void viewNotice(NoticePendencyResponse response) {
        Map<String, String> map = new HashMap<>();
        HelperUtils utils = new HelperUtils(context);
        map.put("Authorization", "Bearer " + utils.getToken());
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<NoticesResponse>> call = apiInterface.getNoticeByID(map, new NoticeByIDRequestBody(response.getNoticeId()));
        call.enqueue(new Callback<List<NoticesResponse>>() {
            @Override
            public void onResponse(Call<List<NoticesResponse>> call, Response<List<NoticesResponse>> response) {
                if (response.body() != null) {
                    List<NoticesResponse> list = response.body();
                    if (list.size() < 1) {
                        Toast.makeText(context, "Notice not found.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent intent = new Intent(context, RespectiveNoticeActivity.class);
                    intent.putExtra("NoticeDetails", list.get(0));
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "Notice not found " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<NoticesResponse>> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void viewCase(NoticePendencyResponse response) {
        Map<String, String> map = new HashMap<>();
        HelperUtils user = new HelperUtils(context);
        map.put("Authorization", "Bearer " + user.getToken());
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        SearchCaseRequestBody requestBody = new SearchCaseRequestBody(response.getFirNumber(), response.getYear(), response.getDistrictId(), response.getPoliceStationId());

        Call<List<SearchCaseResponse>> call = apiInterface.getCaseDetails(map, requestBody);

        call.enqueue(new Callback<List<SearchCaseResponse>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<SearchCaseResponse>> call, Response<List<SearchCaseResponse>> response) {
                List<SearchCaseResponse> model = response.body();
                Log.wtf("response", String.valueOf(response));
                if (response.body() == null) {
                    Toast.makeText(context, "Could not get details for this case", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (model.size() != 0) {
                    SearchCaseResponse caseResponse = response.body().get(0);
                    Intent intent = new Intent(context, RespectiveCaseActivity.class);
                    intent.putExtra("CaseDetails", caseResponse);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "No case found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SearchCaseResponse>> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return responses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView srNo, io, type, viewNotice, viewCase, pendingFor;

        public MyViewHolder(@NonNull SubNoticeItemLayoutBinding binding) {
            super(binding.getRoot());
            srNo = binding.srNo;
            io = binding.io;
            type = binding.type;
            viewNotice = binding.viewNotice;
            viewCase = binding.viewCase;
            pendingFor = binding.pendingFor;
        }
    }
}
