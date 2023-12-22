package com.release.cpmsmobileapp.requestbody;

import com.google.gson.annotations.SerializedName;

public class NoticeByIDRequestBody {
    @SerializedName("notice_id")
    private String noticeId;

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public String getNoticeId() {
        return noticeId;
    }

    public NoticeByIDRequestBody(String noticeId) {
        this.noticeId = noticeId;
    }
}
