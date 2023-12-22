package com.release.cpmsmobileapp.requestbody;

import com.google.gson.annotations.SerializedName;

public class DutrRequestBody {

    @SerializedName("gpf_no")
    private String gpf_no;
    @SerializedName("user_district_id")
    private String userDistrictId;

    @SerializedName("search_date")
    private String searchDate;

    @SerializedName("court_id")
    private String courtId;
    @SerializedName("ps_id")
    private String psID;


    public DutrRequestBody(String gpf_no, String userDistrictId, String searchDate, String courtId) {
        this.gpf_no = gpf_no;
        this.userDistrictId = userDistrictId;
        this.searchDate = searchDate;
        this.courtId = courtId;
    }

    public DutrRequestBody(String gpf_no, String userDistrictId, String psID) {
        this.gpf_no = gpf_no;
        this.userDistrictId = userDistrictId;
        this.psID = psID;
    }

    public String getGpf_no() {
        return gpf_no;
    }

    public void setGpf_no(String gpf_no) {
        this.gpf_no = gpf_no;
    }

    public String getUserDistrictId() {
        return userDistrictId;
    }

    public String getSearchDate() {
        return searchDate;
    }

    public String getCourtId() {
        return courtId;
    }

    public void setUserDistrictId(String userDistrictId) {
        this.userDistrictId = userDistrictId;
    }

    public void setSearchDate(String searchDate) {
        this.searchDate = searchDate;
    }

    public void setCourtId(String courtId) {
        this.courtId = courtId;
    }

    public String getPsID() {
        return psID;
    }

    public void setPsID(String psID) {
        this.psID = psID;
    }
}
