package com.techface.srimurugapa.scheme.model;

import com.google.gson.annotations.SerializedName;

public class DataStatus {

    @SerializedName("status")
    String mStatus;

    @SerializedName("data")
    String mData;

    public String getmStatus() {
        return mStatus;
    }

    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public String getmData() {
        return mData;
    }

    public void setmData(String mData) {
        this.mData = mData;
    }
}
