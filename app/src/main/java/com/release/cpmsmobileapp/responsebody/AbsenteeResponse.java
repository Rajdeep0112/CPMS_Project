package com.release.cpmsmobileapp.responsebody;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AbsenteeResponse implements Serializable {

    @SerializedName("id")
    private Integer id;

    @SerializedName("hearing_date")
    private String hearing_date;

    @SerializedName("fir_no")
    private String fir_no;
    @SerializedName("year")
    private String year;

    @SerializedName("district")
    private String district;

    @SerializedName("ps")
    private String ps;

    @SerializedName("court")
    private String court;

    @SerializedName("name")
    private String name;

    @SerializedName("fathers_name")
    private String fathers_name;

    @SerializedName("address")
    private String address;

    @SerializedName("witness_type")
    private String witness_type;

    @SerializedName("testified")
    private String testified;

    @SerializedName("remarks")
    private String remarks;

    public AbsenteeResponse(Integer id, String hearing_date, String fir_no, String year, String district, String ps, String court, String name, String fathers_name, String address, String witness_type, String testified, String remarks) {
        this.id = id;
        this.hearing_date = hearing_date;
        this.fir_no = fir_no;
        this.year = year;
        this.district = district;
        this.ps = ps;
        this.court = court;
        this.name = name;
        this.fathers_name = fathers_name;
        this.address = address;
        this.witness_type = witness_type;
        this.testified = testified;
        this.remarks = remarks;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHearing_date() {
        return hearing_date;
    }

    public void setHearing_date(String hearing_date) {
        this.hearing_date = hearing_date;
    }

    public String getFir_no() { return fir_no; }

    public void  setFir_no(String fir_no) { this.fir_no = fir_no; }

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

    public String getCourt() {
        return court;
    }

    public void setCourt(String court) {
        this.court = court;
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

    public String getWitness_type() {
        return witness_type;
    }

    public void setWitness_type(String witness_type) {
        this.witness_type = witness_type;
    }

    public String getTestified() {
        return testified;
    }

    public void setTestified(String testified) {
        this.testified = testified;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
