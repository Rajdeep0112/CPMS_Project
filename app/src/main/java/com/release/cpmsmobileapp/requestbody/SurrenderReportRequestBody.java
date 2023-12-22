package com.release.cpmsmobileapp.requestbody;

public class SurrenderReportRequestBody {

    private String gpf_no,user_district_id,fir_no,year,ps_id,district_id,start_date,end_date,court_id;

    public SurrenderReportRequestBody(String gpf_no, String user_district_id, String fir_no, String year, String district_id, String ps_id) {
        this.gpf_no = gpf_no;
        this.user_district_id = user_district_id;
        this.fir_no = fir_no;
        this.year = year;
        this.ps_id = ps_id;
        this.district_id = district_id;
    }

    public SurrenderReportRequestBody(String gpf_no, String user_district_id, String start_date, String end_date) {
        this.gpf_no = gpf_no;
        this.user_district_id = user_district_id;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public SurrenderReportRequestBody(String gpf_no, String user_district_id, String court_id) {
        this.gpf_no = gpf_no;
        this.user_district_id = user_district_id;
        this.court_id = court_id;
    }

    public String getUser_district_id() {
        return user_district_id;
    }

    public void setUser_district_id(String user_district_id) {
        this.user_district_id = user_district_id;
    }

    public String getFir_no() {
        return fir_no;
    }

    public void setFir_no(String fir_no) {
        this.fir_no = fir_no;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPs_id() {
        return ps_id;
    }

    public void setPs_id(String ps_id) {
        this.ps_id = ps_id;
    }

    public String getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(String district_id) {
        this.district_id = district_id;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }
}
