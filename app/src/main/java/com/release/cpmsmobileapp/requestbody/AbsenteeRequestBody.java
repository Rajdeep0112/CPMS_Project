package com.release.cpmsmobileapp.requestbody;

import com.google.gson.annotations.SerializedName;

public class AbsenteeRequestBody {

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

    @SerializedName("name")
    private String name;

    @SerializedName("fathers_name")
    private String father_name;

    @SerializedName("court_id")
    private String court_id;

    @SerializedName("witness_type_id")
    private String witness_type_id;

    public AbsenteeRequestBody(String gpf_no, String user_district_id, String name, String father_name) {
        this.gpf_no = gpf_no;
        this.user_district_id = user_district_id;
        this.name = name;
        this.father_name = father_name;
    }

    public AbsenteeRequestBody(String gpf_no, String user_district_id, String fir_no, String year, String ps_id, String district_id) {
        this.gpf_no = gpf_no;
        this.user_district_id = user_district_id;
        this.fir_no = fir_no;
        this.year = year;
        this.ps_id = ps_id;
        this.district_id = district_id;
    }

    public AbsenteeRequestBody(String gpf_no, String user_district_id, String witness_type_id) {
        this.gpf_no = gpf_no;
        this.user_district_id = user_district_id;
        this.witness_type_id = witness_type_id;
    }

    public AbsenteeRequestBody(String gpf_no, String user_district_id, String court_id, int temp) {
        this.gpf_no = gpf_no;
        this.user_district_id = user_district_id;
        this.court_id = court_id;
    }

    public String getGpf_no() {
        return gpf_no;
    }

    public void setGpf_no(String gpf_no) {
        this.gpf_no = gpf_no;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setFather_name(String father_name) {
        this.father_name = father_name;
    }

    public void setCourt_id(String court_id) {
        this.court_id = court_id;
    }

    public void setWitness_type_id(String witness_type_id) {
        this.witness_type_id = witness_type_id;
    }
}
