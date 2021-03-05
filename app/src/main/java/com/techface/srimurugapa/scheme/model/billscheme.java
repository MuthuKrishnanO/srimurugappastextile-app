package com.techface.srimurugapa.scheme.model;

import com.google.gson.annotations.SerializedName;

public class billscheme {



    @SerializedName("status")
    String mStatus;

    @SerializedName("data")
    String mData;

    @SerializedName("cus_scheme_id")
    String mSchemeId;

    @SerializedName("scheme_name")
    String mSchemename;

    @SerializedName("scheme_startdate")
    String mSchemestartdate;

    @SerializedName("scheme_enddate")
    String mSchemeenddate;

    @SerializedName("scheme_amount")
    String mSchemeamunt;

    @SerializedName("scheme_duration")
    String mScheme_duration;

    @SerializedName("scheme_remaningcount")
    String mCount;

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

    public String getmSchemeId() {
        return mSchemeId;
    }

    public void setmSchemeId(String mSchemeId) {
        this.mSchemeId = mSchemeId;
    }

    public String getmSchemename() {
        return mSchemename;
    }

    public void setmSchemename(String mSchemename) {
        this.mSchemename = mSchemename;
    }

    public String getmSchemestartdate() {
        return mSchemestartdate;
    }

    public void setmSchemestartdate(String mSchemestartdate) {
        this.mSchemestartdate = mSchemestartdate;
    }

    public String getmSchemeenddate() {
        return mSchemeenddate;
    }

    public void setmSchemeenddate(String mSchemeenddate) {
        this.mSchemeenddate = mSchemeenddate;
    }

    public String getmSchemeamunt() {
        return mSchemeamunt;
    }

    public void setmSchemeamunt(String mSchemeamunt) {
        this.mSchemeamunt = mSchemeamunt;
    }

    public String getmScheme_duration() {
        return mScheme_duration;
    }

    public void setmScheme_duration(String mScheme_duration) {
        this.mScheme_duration = mScheme_duration;
    }

    public String getmCount() {
        return mCount;
    }

    public void setmCount(String mCount) {
        this.mCount = mCount;
    }
}
