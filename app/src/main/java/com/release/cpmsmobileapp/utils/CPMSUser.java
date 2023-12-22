package com.release.cpmsmobileapp.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.release.cpmsmobileapp.R;

public class CPMSUser {

    SharedPreferences sharedPreferences;
    Context context;

    public String getGpfNo() {
        return sharedPreferences.getString(String.valueOf(R.string.gpf_no), "");
    }

    public String getToken() {
        return sharedPreferences.getString(String.valueOf(R.string.token), "");
    }

    public String getDistrictID(){
        return sharedPreferences.getString(String.valueOf(R.string.district_id), "");
    }

    public String getRefreshToken(){
        return sharedPreferences.getString(String.valueOf(R.string.refresh_token), "");
    }

    public String getName(){
        return sharedPreferences.getString(String.valueOf(R.string.name), "");
    }

    public String getEcode(){
        return sharedPreferences.getString(String.valueOf(R.string.ecode), "");
    }

    public String getMobileNo(){
        return sharedPreferences.getString(String.valueOf(R.string.mobile_no), "");
    }

    public String getFatherName(){
        return sharedPreferences.getString(String.valueOf(R.string.father_name), "");
    }

    public String getBrassNo(){
        return sharedPreferences.getString(String.valueOf(R.string.brass_no), "");
    }

    public String getOfficerRank(){
        return sharedPreferences.getString(String.valueOf(R.string.officer_rank), "");
    }

    public CPMSUser(Context context){
        sharedPreferences = context.getSharedPreferences(String.valueOf(R.string.user_preferences), MODE_PRIVATE);
        this.context = context;
    }
}

