package com.release.cpmsmobileapp.requestbody;

import com.google.gson.annotations.SerializedName;

public class ReportCaseAccusedRequest {

    @SerializedName("gpf_no")
    private String gpf_no;
    @SerializedName("user_district_id")
    private String user_district_id;

    @SerializedName("fir_no")
    private String fir_no;

    @SerializedName("year")
    private String year;

    @SerializedName("ps_id")
    private String ps_id;

    @SerializedName("district_id")
    private String district_id;

    @SerializedName("start_date")
    private String start_date;

    @SerializedName("end_date")
    private String end_date;

    @SerializedName("court_id")
    private String court_id;

    @SerializedName("crime_head_id")
    private String crime_head_id;

    public ReportCaseAccusedRequest(String gpf_no, String user_district_id, String fir_no, String year, String ps_id, String district_id) {
        this.gpf_no = gpf_no;
        this.user_district_id = user_district_id;
        this.fir_no = fir_no;
        this.year = year;
        this.ps_id = ps_id;
        this.district_id = district_id;
    }

    public String getGpf_no() {
        return gpf_no;
    }

    public void setGpf_no(String gpf_no) {
        this.gpf_no = gpf_no;
    }

    public ReportCaseAccusedRequest(String gpf_no, String user_district_id, String crime_head_id) {
        this.gpf_no = gpf_no;
        this.user_district_id = user_district_id;
        this.crime_head_id = crime_head_id;
    }

    public ReportCaseAccusedRequest(String gpf_no, String user_district_id, String start_date, String end_date) {
        this.gpf_no = gpf_no;
        this.user_district_id = user_district_id;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public ReportCaseAccusedRequest(String gpf_no, String user_district_id, String court_id, Integer temp) {
        this.gpf_no = gpf_no;
        this.user_district_id = user_district_id;
        this.court_id = court_id;
    }

    public ReportCaseAccusedRequest(String user_district_id) {
        this.user_district_id = user_district_id;
    }

    public void setUser_district_id(String user_district_id) {
        this.user_district_id = user_district_id;
    }

    public void setFir_no(String fir_no) {
        this.fir_no = fir_no;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setPs_id(String ps_id) {
        this.ps_id = ps_id;
    }

    public void setDistrict_id(String district_id) {
        this.district_id = district_id;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public void setCourt_id(String court_id) {
        this.court_id = court_id;
    }

    public void setCrime_head_id(String crime_head_id) {
        this.crime_head_id = crime_head_id;
    }

    public String getUser_district_id() {
        return user_district_id;
    }

    public String getFir_no() {
        return fir_no;
    }

    public String getYear() {
        return year;
    }

    public String getPs_id() {
        return ps_id;
    }

    public String getDistrict_id() {
        return district_id;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getCourt_id() {
        return court_id;
    }

    public String getCrime_head_id() {
        return crime_head_id;
    }
}
