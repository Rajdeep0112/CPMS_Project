package com.release.cpmsmobileapp.responsebody;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DutrResponse implements Serializable {

    @SerializedName("fir_no")
    private String firNumber;

    @SerializedName("year")
    private String year;

    @SerializedName("st_no")
    private String stNumber;

    @SerializedName("gr_no")
    private String grNumber;

    @SerializedName("tr_no")
    private String trNumber;

    @SerializedName("district")
    private String district;

    @SerializedName("ps")
    private String policeStation;

    @SerializedName("court")
    private String court;

    @SerializedName("name")
    private String name;

    @SerializedName("testified")
    private String testified;

    @SerializedName("testified_remarks")
    private String testified_remarks;

    public DutrResponse(String firNumber, String year, String stNumber, String grNumber, String trNumber, String district, String policeStation, String court, String name, String testified, String testified_remarks) {
        this.firNumber = firNumber;
        this.year = year;
        this.stNumber = stNumber;
        this.grNumber = grNumber;
        this.trNumber = trNumber;
        this.district = district;
        this.policeStation = policeStation;
        this.court = court;
        this.name = name;
        this.testified = testified;
        this.testified_remarks = testified_remarks;
    }

    public String getTestified_remarks() {
        return testified_remarks;
    }

    public void setTestified_remarks(String testified_remarks) {
        this.testified_remarks = testified_remarks;
    }

    public String getFirNumber() {
        return firNumber;
    }

    public String getYear() {
        return year;
    }

    public String getStNumber() {
        return stNumber;
    }

    public String getGrNumber() {
        return grNumber;
    }

    public String getTrNumber() {
        return trNumber;
    }

    public String getDistrict() {
        return district;
    }

    public String getPoliceStation() {
        return policeStation;
    }

    public String getCourt() {
        return court;
    }

    public String getName() {
        return name;
    }

    public String getTestified() {
        return testified;
    }

    public void setFirNumber(String firNumber) {
        this.firNumber = firNumber;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setStNumber(String stNumber) {
        this.stNumber = stNumber;
    }

    public void setGrNumber(String grNumber) {
        this.grNumber = grNumber;
    }

    public void setTrNumber(String trNumber) {
        this.trNumber = trNumber;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setPoliceStation(String policeStation) {
        this.policeStation = policeStation;
    }

    public void setCourt(String court) {
        this.court = court;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTestified(String testified) {
        this.testified = testified;
    }
}

