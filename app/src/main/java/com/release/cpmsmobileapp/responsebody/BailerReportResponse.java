package com.release.cpmsmobileapp.responsebody;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BailerReportResponse implements Serializable {

    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;
    @SerializedName("fathers_name")
    private String fathers_name;
    @SerializedName("address")
    private String address;
    @SerializedName("bail_date")
    private String bail_date;
    @SerializedName("accused_name")
    private String accused_name;
    @SerializedName("accused_fathers_name")
    private String accused_fathers_name;
    @SerializedName("case_detail_id")
    private Integer case_detail_id;
    @SerializedName("fir_no")
    private String fir_no;
    @SerializedName("year")
    private String year;
    @SerializedName("district")
    private String district;
    @SerializedName("ps")
    private String ps;
    @SerializedName("bail_remarks")
    private String bail_remarks;

    public BailerReportResponse(Integer id, String name, String fathers_name, String address, String bail_date, String accused_name, String accused_fathers_name, Integer case_detail_id, String fir_no, String year, String district, String ps, String bail_remarks) {
        this.id = id;
        this.name = name;
        this.fathers_name = fathers_name;
        this.address = address;
        this.bail_date = bail_date;
        this.accused_name = accused_name;
        this.accused_fathers_name = accused_fathers_name;
        this.case_detail_id = case_detail_id;
        this.fir_no = fir_no;
        this.year = year;
        this.district = district;
        this.ps = ps;
        this.bail_remarks = bail_remarks;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFathers_name() {
        return fathers_name;
    }

    public void setFathers_name(String fathers_name) {
        this.fathers_name = fathers_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBail_date() {
        return bail_date;
    }

    public void setBail_date(String bail_date) {
        this.bail_date = bail_date;
    }

    public String getAccused_name() {
        return accused_name;
    }

    public void setAccused_name(String accused_name) {
        this.accused_name = accused_name;
    }

    public String getAccused_fathers_name() {
        return accused_fathers_name;
    }

    public void setAccused_fathers_name(String accused_fathers_name) {
        this.accused_fathers_name = accused_fathers_name;
    }

    public Integer getCase_detail_id() {
        return case_detail_id;
    }

    public void setCase_detail_id(Integer case_detail_id) {
        this.case_detail_id = case_detail_id;
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

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPs() {
        return ps;
    }

    public void setPs(String ps) {
        this.ps = ps;
    }

    public String getBail_remarks() {
        return bail_remarks;
    }

    public void setBail_remarks(String bail_remarks) {
        this.bail_remarks = bail_remarks;
    }
}
