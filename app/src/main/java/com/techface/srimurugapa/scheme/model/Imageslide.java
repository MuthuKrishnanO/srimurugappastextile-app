package com.techface.srimurugapa.scheme.model;

import com.google.gson.annotations.SerializedName;

public class Imageslide {





    @SerializedName("id")
    String id;

    @SerializedName("branch_id")
    String brandid;

    @SerializedName("file_name")
    String filename;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrandid() {
        return brandid;
    }

    public void setBrandid(String brandid) {
        this.brandid = brandid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
