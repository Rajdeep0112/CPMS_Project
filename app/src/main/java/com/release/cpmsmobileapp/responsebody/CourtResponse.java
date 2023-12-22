package com.release.cpmsmobileapp.responsebody;

import com.google.gson.annotations.SerializedName;

public class CourtResponse {
    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    public CourtResponse(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
