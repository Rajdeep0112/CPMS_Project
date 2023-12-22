package com.release.cpmsmobileapp.requestbody;

public class BailerReportRequestBody {

    private String gpf_no;
    private String user_district_id;
    private String fir_no;
    private String year;
    private String ps_id;
    private String district_id;
    private String name;
    private String father_name;
    private String accused_name;
    private String accused_father_name;
    private String start_date,end_date;

    public BailerReportRequestBody(String gpf_no, String user_district_id, String fir_no, String year, String district_id, String ps_id) {
        this.gpf_no = gpf_no;
        this.user_district_id = user_district_id;
        this.fir_no = fir_no;
        this.year = year;
        this.ps_id = ps_id;
        this.district_id = district_id;
    }

    public BailerReportRequestBody(String gpf_no, String user_district_id, String name, String father_name) {
        this.gpf_no = gpf_no;
        this.user_district_id = user_district_id;
        this.name = name;
        this.father_name = father_name;
    }


    public BailerReportRequestBody(String  gpf_no, String user_district_id, String accused_name, String accused_father_name, int no) {
        this.gpf_no = gpf_no;
        this.user_district_id = user_district_id;
        this.accused_name = accused_name;
        this.accused_father_name = accused_father_name;
    }

    public BailerReportRequestBody(String  gpf_no, String user_district_id, String start_date, String end_date, String no) {
        this.gpf_no = gpf_no;
        this.user_district_id = user_district_id;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public String getGpf_no() {
        return gpf_no;
    }

    public void setGpf_no(String gpf_no) {
        this.gpf_no = gpf_no;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFather_name() {
        return father_name;
    }

    public void setFather_name(String father_name) {
        this.father_name = father_name;
    }

    public String getAccused_name() {
        return accused_name;
    }

    public void setAccused_name(String accused_name) {
        this.accused_name = accused_name;
    }

    public String getAccused_father_name() {
        return accused_father_name;
    }

    public void setAccused_father_name(String accused_father_name) {
        this.accused_father_name = accused_father_name;
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
