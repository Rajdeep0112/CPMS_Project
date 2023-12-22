package com.release.cpmsmobileapp.responsebody;

import com.google.gson.annotations.SerializedName;

public class NoticeTypeResponse {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public NoticeTypeResponse(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
