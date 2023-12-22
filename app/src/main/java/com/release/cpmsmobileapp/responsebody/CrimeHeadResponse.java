package com.release.cpmsmobileapp.responsebody;

import com.google.gson.annotations.SerializedName;

public class CrimeHeadResponse {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    public CrimeHeadResponse(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
