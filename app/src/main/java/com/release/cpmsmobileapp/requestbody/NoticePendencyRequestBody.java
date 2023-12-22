package com.release.cpmsmobileapp.requestbody;

import com.google.gson.annotations.SerializedName;

public class NoticePendencyRequestBody {

    @SerializedName("gpf_no")
    private String gpf_no;
    @SerializedName("user_district_id")
    private String userDistrictId;

    @SerializedName("fir_no")
    private String firNumber;

    @SerializedName("year")
    private String year;

    @SerializedName("ps_id")
    private String policeStationId;

    @SerializedName("district_id")
    private String districtId;

    @SerializedName("start_date")
    private String startDate;

    @SerializedName("end_date")
    private String endDate;

    @SerializedName("notice_type_id")
    private String noticeTypeId;

    public NoticePendencyRequestBody(String gpf_no, String userDistrictId, String firNumber, String year, String policeStationId, String districtId, String startDate, String endDate, String noticeTypeId) {
        this.gpf_no = gpf_no;
        this.userDistrictId = userDistrictId;
        this.firNumber = firNumber;
        this.year = year;
        this.policeStationId = policeStationId;
        this.districtId = districtId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.noticeTypeId = noticeTypeId;
    }

    public NoticePendencyRequestBody() {
        this.gpf_no = "";
        this.userDistrictId = "";
        this.firNumber = "";
        this.year = "";
        this.policeStationId = "";
        this.districtId = "";
        this.startDate = "";
        this.endDate = "";
        this.noticeTypeId = "";
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

    public String getFirNumber() {
        return firNumber;
    }

    public String getYear() {
        return year;
    }

    public String getPoliceStationId() {
        return policeStationId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getNoticeTypeId() {
        return noticeTypeId;
    }

    public void setUserDistrictId(String userDistrictId) {
        this.userDistrictId = userDistrictId;
    }

    public void setFirNumber(String firNumber) {
        this.firNumber = firNumber;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setPoliceStationId(String policeStationId) {
        this.policeStationId = policeStationId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setNoticeTypeId(String noticeTypeId) {
        this.noticeTypeId = noticeTypeId;
    }
}
