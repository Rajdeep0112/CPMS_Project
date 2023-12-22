package com.release.cpmsmobileapp.responsebody;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NoticePendencyResponse implements Serializable {
    @SerializedName("io_name")
    private String ioName;

    @SerializedName("notice_type")
    private String noticeType;

    @SerializedName("total_notice")
    private String totalNotice;

    @SerializedName("fir_no")
    private String firNumber;

    @SerializedName("year")
    private String year;

    @SerializedName("district_id")
    private String districtId;

    @SerializedName("ps_id")
    private String policeStationId;

    @SerializedName("pending_for")
    private String pendingFor;

    @SerializedName("notice_id")
    private String noticeId;

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public String getNoticeId() {
        return noticeId;
    }


    public NoticePendencyResponse(String ioName, String noticeType, String totalNotice, String firNumber, String year, String districtId, String policeStationId, String pendingFor, String noticeId) {
        this.ioName = ioName;
        this.noticeType = noticeType;
        this.totalNotice = totalNotice;
        this.firNumber = firNumber;
        this.year = year;
        this.districtId = districtId;
        this.policeStationId = policeStationId;
        this.pendingFor = pendingFor;
        this.noticeId = noticeId;
    }

    public String getIoName() {
        return ioName;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public String getTotalNotice() {
        return totalNotice;
    }

    public String getFirNumber() {
        return firNumber;
    }

    public String getYear() {
        return year;
    }

    public String getDistrictId() {
        return districtId;
    }

    public String getPoliceStationId() {
        return policeStationId;
    }

    public String getPendingFor() {
        return pendingFor;
    }

    public void setIoName(String ioName) {
        this.ioName = ioName;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public void setTotalNotice(String totalNotice) {
        this.totalNotice = totalNotice;
    }

    public void setFirNumber(String firNumber) {
        this.firNumber = firNumber;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public void setPoliceStationId(String policeStationId) {
        this.policeStationId = policeStationId;
    }

    public void setPendingFor(String pendingFor) {
        this.pendingFor = pendingFor;
    }
}
