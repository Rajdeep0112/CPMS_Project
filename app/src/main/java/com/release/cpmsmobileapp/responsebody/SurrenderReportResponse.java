package com.release.cpmsmobileapp.responsebody;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SurrenderReportResponse implements Serializable {

    @SerializedName("id")
    private Integer id;
    @SerializedName("name")
    private String name;
    @SerializedName("fathers_name")
    private String fathers_name;
    @SerializedName("address")
    private String address;
    @SerializedName("surrender_date")
    private String surrender_date;
    @SerializedName("case_detail_id")
    private Integer case_detail_id;
    @SerializedName("fir_no")
    private String fir_no;
    @SerializedName("year")
    private String year;
    @SerializedName("act_section")
    private String act_section;
    @SerializedName("district")
    private String district;
    @SerializedName("ps")
    private String ps;
    @SerializedName("court")
    private String court;
    @SerializedName("remarks")
    private String remarks;


    public SurrenderReportResponse(Integer id, String name, String fathers_name, String address, String surrender_date, Integer case_detail_id, String fir_no, String year, String act_section, String district, String ps, String court, String remarks) {
        this.id = id;
        this.name = name;
        this.fathers_name = fathers_name;
        this.address = address;
        this.surrender_date = surrender_date;
        this.case_detail_id = case_detail_id;
        this.fir_no = fir_no;
        this.year = year;
        this.act_section = act_section;
        this.district = district;
        this.ps = ps;
        this.court = court;
        this.remarks = remarks;
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

    public String getSurrender_date() {
        return surrender_date;
    }

    public void setSurrender_date(String surrender_date) {
        this.surrender_date = surrender_date;
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

    public String getAct_section() {
        return act_section;
    }

    public void setAct_section(String act_section) {
        this.act_section = act_section;
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

    public String getCourt() {
        return court;
    }

    public void setCourt(String court) {
        this.court = court;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
