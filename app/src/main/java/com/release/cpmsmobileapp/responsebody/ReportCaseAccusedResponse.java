package com.release.cpmsmobileapp.responsebody;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReportCaseAccusedResponse implements Serializable {

    @SerializedName("name")
    private String name;

    @SerializedName("status")
    private String status;

    @SerializedName("disposal_type")
    private String disposal_type;

    @SerializedName("act_section")
    private String act_section;

    @SerializedName("punishment_type")
    private String punishment_type;

    @SerializedName("punishment_period")
    private String punishment_period;

    @SerializedName("fine_amount")
    private String fine_amount;
    @SerializedName("fir_no")
    private String fir_no;
    @SerializedName("year")
    private String year;
    @SerializedName("ps")
    private String ps;

    public ReportCaseAccusedResponse(String name, String status, String disposal_type, String act_section, String punishment_type, String punishment_period, String fine_amount, String fir_no, String year, String ps) {
        this.name = name;
        this.status = status;
        this.disposal_type = disposal_type;
        this.act_section = act_section;
        this.punishment_type = punishment_type;
        this.punishment_period = punishment_period;
        this.fine_amount = fine_amount;
        this.fir_no = fir_no;
        this.year = year;
        this.ps = ps;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDisposal_type() {
        return disposal_type;
    }

    public void setDisposal_type(String disposal_type) {
        this.disposal_type = disposal_type;
    }

    public String getAct_section() {
        return act_section;
    }

    public void setAct_section(String act_section) {
        this.act_section = act_section;
    }

    public String getPunishment_type() {
        return punishment_type;
    }

    public void setPunishment_type(String punishment_type) {
        this.punishment_type = punishment_type;
    }

    public String getPunishment_period() {
        return punishment_period;
    }

    public void setPunishment_period(String punishment_period) {
        this.punishment_period = punishment_period;
    }

    public String getFine_amount() {
        return fine_amount;
    }

    public void setFine_amount(String fine_amount) {
        this.fine_amount = fine_amount;
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

    public String getPs() {
        return ps;
    }

    public void setPs(String ps) {
        this.ps = ps;
    }
}
